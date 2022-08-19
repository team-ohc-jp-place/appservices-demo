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

sleep 30

oc -n ${PRJ[1]} exec deployment/postgres-server-linux -- /usr/bin/psql -S postgres-server-linux -p 5432 -U postgres -d postgres -f /temp/workshop/data.sql

