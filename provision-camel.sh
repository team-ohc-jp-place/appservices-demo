#!/bin/bash

## Create Project
PRJ[0]="integration-demo"
PRJ[1]="shared-db-earth"
PRJ[2]="shared-app-earth"

#for i in "${PRJ[@]}"
#do
#    if [ "$(oc get project $i)" ]; then
#        echo  "$i already exists..."
#    else
#        oc new-project $i
#    fi
#done

sleep 5


kamel delete order
kamel delete postgressync

## Camel K Integration Deploy
kamel run -d camel:kafka -d camel:atlasmap ./openshift/camel-k/order.camel.yaml --resource file:openshift/camel-k/order-mapping.adm -n ${PRJ[0]} 
kamel run -d camel:kafka ./openshift/camel-k/postgressync.camel.yaml -n ${PRJ[0]} 

