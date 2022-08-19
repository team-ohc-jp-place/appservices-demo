#!/bin/bash
#e

## Create Project
PRJ[0]="integration-demo"
PRJ[1]="shared-db-earth"
PRJ[2]="shared-app-earth"

for i in "${PRJ[@]}"
do

  oc delete project $i

done