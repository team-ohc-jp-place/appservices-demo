#!/bin/bash

## Create Project
PRJ[0]="integration-demo"


#oc project ${PRJ[0]}
#cd ./apps/quarkusapp
#./mvnw clean package -Dquarkus.kubernetes.deploy=true 
#oc patch dc quarkusapp -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'

## Quarkus Application API Provider Integrations
oc project ${PRJ[0]}
cd ./apps/quarkusapi
./mvnw clean package -Dquarkus.kubernetes.deploy=true
oc patch dc quarkusapi -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'
