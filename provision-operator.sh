#!/bin/bash
#e

PRJ[0]="demo-pj"

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