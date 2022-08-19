#!/bin/bash
#e

## Create Project
PRJ[0]="demo-pj"
PRJ[1]="shared-db-earth"
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

oc apply -n ${PRJ[1]} -f ./openshift/postgres/configmap-postgres-data-sql.yaml 

sleep 5

oc apply -n ${PRJ[1]} -f ./openshift/postgres/postgres-server-linux.yaml

sleep 60

oc -n ${PRJ[1]} exec deployment/postgres-server-linux -- /usr/bin/psql -S postgres-server-linux -p 5432 -U postgres -d postgres -f /temp/workshop/data.sql

## Camel K Integration Deploy
kamel run ./openshift/camel-k/order.yaml --resource file:openshift/camel-k/order-mapping.adm -n ${PRJ[0]} 
kamel run ./openshift/camel-k/postgressync.yaml -n ${PRJ[0]} 

## API Provider Integrations
#oc project ${PRJ[0]}
#cd quarkusapi
#./mvnw clean package -Dquarkus.kubernetes.deploy=true

#oc patch dc quarkusapi -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'
