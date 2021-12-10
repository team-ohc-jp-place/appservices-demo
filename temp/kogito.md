mvn clean compile package -DskipTests



oc new-build --binary --name=cep-kogito -l app=cep-kogito -n commons-demo

oc patch bc/cep-kogito -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.jvm\"}}}}" -n commons-demo

oc start-build cep-kogito --from-dir=. --follow -n commons-demo

oc new-app --image-stream=cep-kogito \
 -e quarkus.kafka-streams.bootstrap-servers=earth-cluster-kafka-bootstrap.shared-kafka-earth.svc:9092 \
 -e quarkus.kafka-streams.application-server=earth-cluster-kafka-bootstrap.shared-kafka-earth.svc:9092



# デプロイ
oc apply -f ./openshift/kogito/is-decision-service.yml -n commons-demo
oc apply -f ./openshift/kogito/bc-decision-service.yml -n commons-demo
oc apply -f ./openshift/kogito/dc-decision-service.yml -n commons-demo
oc apply -f ./openshift/kogito/service-decision-service.yml -n commons-demo
oc apply -f ./openshift/kogito/route-decision-service.yml -n commons-demo

# 消したい
oc delete is/decision-service -n commons-demo
oc delete bc/decision-service -n commons-demo
oc delete dc/decision-service -n commons-demo
oc delete svc/decision-service -n commons-demo
oc delete route/decision-service -n commons-demo