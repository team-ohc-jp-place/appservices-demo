#!/bin/bash

## Create Project
PRJ[0]="demo-pj"
PRJ[1]="shared-db-earth"
PRJ[2]="shared-app-earth"

## Camel K Integration Deploy
#kamel run ./openshift/camel-k/order.yaml --resource file:./openshift/camel-k/order-mapping.adm -n ${PRJ[0]} 
#kamel run ./openshift/camel-k/postgressync.yaml -n ${PRJ[0]} 

## API Provider Integrations
oc project ${PRJ[0]}
cd quarkusapi
./mvnw clean package -Dquarkus.kubernetes.deploy=true

#oc patch dc quarkusapi -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'
