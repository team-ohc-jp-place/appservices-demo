project
    db_earth_namespace: "shared-db-earth"
    kafka_earth_namespace: "demo-pj"
    app_earth_namespace: "shared-app-earth"

oc new-project shared-db-earth
oc new-project demo-pj
oc new-project shared-app-earth
oc new-project demo-pj

# AMQ Streams Operator
oc apply -f ./openshift/kafka/amq-streams-operator.yaml

# Create Kafka Cluster
oc apply -n demo-pj -f ./openshift/legacy/kafka-earth.yaml

# Deploy Kafdrop
oc apply -n demo-pj -f ./openshift/legacy/kafdrop4.yaml

# Enable anyuid on Database Namespace
oc apply -n shared-db-earth -f ./openshift/legacy/scc-anyuid.yaml

# Create Data SQL ConfigMap
oc apply -n shared-db-earth -f ./openshift/legacy/configmap-data-sql.yaml

# Deploy MS SQL Server Database
oc apply -n shared-db-earth -f ./openshift/legacy/mssql-server-linux.yaml

# Configure CDC in Database
oc -n shared-db-earth exec deployment/mssql-server-linux -- /opt/mssql-tools/bin/sqlcmd -S mssql-server-linux -U sa -P 'Password!' -i /opt/workshop/data.sql

# Enable anyuid on Earth Application Namespace
oc adm policy add-scc-to-user anyuid -z default -n shared-app-earth

# Deploy Earth Application
oc new-app quay.io/hguerreroo/my-apache-php-app:latest -e 'SERVER_NAME=mssql-server-linux.shared-db-earth.svc' -n shared-app-earth

# Create Earth Application Route
oc expose service my-apache-php-app --name=www -n shared-app-earth

# Debezium
oc apply -n demo-pj -f ./openshift/legacy/debezium.yaml
oc apply -n demo-pj -f ./openshift/legacy/orders-connector.yaml


# Delete
oc delete project shared-db-earth
oc delete project demo-pj
oc delete project shared-app-earth
oc delete project demo-pj