#!/bin/bash
#e

## Create Project
PRJ[0]="demo-pj"
PRJ[1]="shared-db-earth"
PRJ[0]="demo-pj"
PRJ[2]="shared-app-earth"

for i in "${PRJ[@]}"
do
    if [ "$(oc get project $i)" ]; then
        echo  "$i already exists..."
    else
        oc new-project $i
    fi
done

sleep 5

# Delete Limitrange
oc delete limitrange ${PRJ[0]}-core-resource-limits -n ${PRJ[0]}

sleep 5

## Operator Install
# AQM Streams
    if [ "$(oc -n openshift-operators get subscription amq-streams)" ] ; then
        echo "Red Hat AMQ Streams already exists..."
    else
        oc apply -f ./openshift/kafka/amq-streams-operator.yaml
    fi

# Fuse Online
# CLI からだとOperatorのインストールが失敗する？Consoleからだと問題ないが。。。
#    if [ "$(oc -n ${PRJ[0]} get subscription fuse-online)" ] ; then
#        echo "Red Hat Integration - Fuse Online already exists..."
#    else
#        oc apply -f ./openshift/fuse/fuse-online-operator.yaml -n ${PRJ[0]}
#    fi

# Camel K Operator
    if [ "$(oc -n ${PRJ[0]} get subscription camel-k)" ] ; then
        echo "Camel K Operator already exists..."
    else
        oc apply -f ./openshift/camel-k/camel-k-operator.yaml
    fi

# Nexus Repository Operator
    if [ "$(oc -n openshift-operators get subscription nxrm-operator-certified)" ] ; then
        echo  "Nexus Repository Operator already exists..."
    else
        oc apply -f ./openshift/nexus/nexus-operator.yaml
    fi

sleep 30

## Kafka Deploy
# Kafka-Cluster Deploy
    if [ "$(oc get kafka -n ${PRJ[0]} earth-cluster)" ] ; then
        echo "kafka-cluster already exists..."
    else
        oc apply -n ${PRJ[0]} -f ./openshift/legacy/kafka-earth.yaml
    fi

Sleep 15

# Nexus Deploy
oc apply -f ./openshift/nexus/nexusrepo-deploy.yaml -n ${PRJ[0]}

# Kafdrop Deploy
    if [ "$(oc get dc kafdrop -n ${PRJ[0]})" ] ; then
        echo "kafdrop already exists..."
    else
        oc apply -n ${PRJ[0]} -f ./openshift/legacy/kafdrop4.yaml
    fi

sleep 5

## Legacy Application Deploy
# Enable anyuid on Database Namespace
oc apply -n ${PRJ[1]} -f ./openshift/legacy/scc-anyuid.yaml

sleep 5

# Create Data SQL ConfigMap
oc apply -n ${PRJ[1]} -f ./openshift/legacy/configmap-data-sql.yaml

sleep 5

# Deploy MS SQL Server Database
oc apply -n ${PRJ[1]} -f ./openshift/legacy/mssql-server-linux.yaml

sleep 5

# Configure CDC in Database
while [ true ] ; do
  if [ "$(oc get pod -n ${PRJ[1]} --field-selector status.phase=Running)" ] ; then
    sleep 15
    oc -n ${PRJ[1]} exec deployment/mssql-server-linux -- /opt/mssql-tools/bin/sqlcmd -S mssql-server-linux -U sa -P 'Password!' -i /opt/workshop/data.sql
    break
  fi
    echo waiting...
    sleep 10
done

# Enable anyuid on Earth Application Namespace
oc adm policy add-scc-to-user anyuid -z default -n ${PRJ[2]}

sleep 5

# Deploy Earth Application
SERVER_NAME=mssql-server-linux.${PRJ[1]}.svc
oc new-app quay.io/hguerreroo/my-apache-php-app:latest -e SERVER_NAME=${SERVER_NAME} -n ${PRJ[2]}

sleep 5

# Create Earth Application Route
oc expose service my-apache-php-app --name=www -n ${PRJ[2]}

sleep 5

# Debezium
oc apply -n ${PRJ[0]} -f ./openshift/legacy/debezium.yaml
oc apply -n ${PRJ[0]} -f ./openshift/legacy/orders-connector.yaml


## Decision Service Deploy
oc expose svc/nexusrepo-sonatype-nexus-service -n ${PRJ[0]}
sleep 5

MAVEN_MIRROR_URL=$(oc get route nexusrepo-sonatype-nexus-service --template='http://{{.spec.host}}' -n ${PRJ[0]})
    
while [ 1 ]; do
  STAT=$(curl -s -w '%{http_code}' -o /dev/null ${MAVEN_MIRROR_URL})
  if [ "$STAT" = 200 ] ; then
    sleep 5
    oc new-app -f ./openshift/kogito/decision-service.yaml -n ${PRJ[0]} -p MAVEN_MIRROR_URL=${MAVEN_MIRROR_URL}"/repository/maven-public/"
    break
  fi
    echo waiting...
    sleep 10
done

## Quarkus Application Deploy
oc new-app --as-deployment-config --name quarkusapp --docker-image="kamorisan/quarkusapp:v1" -n ${PRJ[0]}
oc apply -f ./openshift/quarkusapp/service-quarkusapp.yml -n ${PRJ[0]}
oc apply -f ./openshift/quarkusapp/route-quarkusapp.yml -n ${PRJ[0]}

##Fuse Online Deploy
#oc apply -n ${PRJ[0]} -f ./openshift/fuse/syndesis.yaml

## Camel K Integration Deploy
kamel run ./openshift/camel-k/order.yaml --resource file:./openshift/camel-k/order-mapping.adm -n demo-pj

oc patch dc quarkusapp -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'

# Fuse Online のインポートがコマンドでうまくいかないので、後で手動で
#oc patch dc i-order -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'