#!/bin/bash
#e

PRJ[0]="demo-pj"

## API Provider Integrations
#oc project ${PRJ[0]}
#cd quarkusapi
#./mvnw clean package -Dquarkus.kubernetes.deploy=true
#
#oc patch dc quarkusapi -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'
#
cd quarkusapp2
./mvnw clean package -Dquarkus.kubernetes.deploy=true

oc patch dc quarkusapp -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'

#oc new-app --as-deployment-config --name quarkusapp --docker-image="kamorisan/quarkusapp:v1" -n ${PRJ[0]}
#oc apply -f ./openshift/quarkusapp/service-quarkusapp.yml -n ${PRJ[0]}
#oc apply -f ./openshift/quarkusapp/route-quarkusapp.yml -n ${PRJ[0]}

#oc patch dc quarkusapp -n ${PRJ[0]} -p '{"metadata":{"labels":{"app.kubernetes.io/part-of":"modern-app"}}}'
