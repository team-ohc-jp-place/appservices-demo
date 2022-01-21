#!/bin/bash
#e

## Create Project

PRJ[0]="demo-pj"
PRJ[1]="shared-db-earth"
PRJ[0]="demo-pj"
PRJ[2]="shared-app-earth"


# リミットレンジを削除
oc delete limitrange demo-pj-core-resource-limits -n demo-pj

oc apply -f ./openshift/nexus/nexusrepo-deploy.yaml -n demo-pj

Sleep 10

oc expose svc/nexusrepo-sonatype-nexus-service
MAVEN_MIRROR_URL=$(oc get route nexusrepo-sonatype-nexus-service --template='http://{{.spec.host}}' -n demo-pj)
    
while [ 1 ]; do
  STAT=$(curl -s -w '%{http_code}' -o /dev/null ${MAVEN_MIRROR_URL})
  if [ "$STAT" = 200 ] ; then
    oc new-app -f ./openshift/kogito/decision-service.yaml -n demo-pj -p MAVEN_MIRROR_URL=${MAVEN_MIRROR_URL}"/repository/maven-public/"
    break
  fi
    echo waiting...
    sleep 10
done
