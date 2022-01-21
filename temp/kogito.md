mvn clean compile package -DskipTests



oc new-build --binary --name=cep-kogito -l app=cep-kogito -n demo-pj

oc patch bc/cep-kogito -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.jvm\"}}}}" -n demo-pj

oc start-build cep-kogito --from-dir=. --follow -n demo-pj

oc new-app --image-stream=cep-kogito \
 -e quarkus.kafka-streams.bootstrap-servers=earth-cluster-kafka-bootstrap.demo-pj.svc:9092 \
 -e quarkus.kafka-streams.application-server=earth-cluster-kafka-bootstrap.demo-pj.svc:9092



# デプロイ
oc apply -f ./openshift/kogito/is-decision-service.yml -n demo-pj
export MAVEN_MIRROR_URL=$(oc get route nexusrepo-sonatype-nexus-service --template='http://{{.spec.host}}' -n demo-pj)"repository/maven-public/"
oc apply -f ./openshift/kogito/bc-decision-service2.yml -n demo-pj -p MAVEN_MIRROR_URL=$MAVEN_MIRROR_URL
oc apply -f ./openshift/kogito/dc-decision-service.yml -n demo-pj
oc apply -f ./openshift/kogito/service-decision-service.yml -n demo-pj
oc apply -f ./openshift/kogito/route-decision-service.yml -n demo-pj

oc new-app -f ./openshift/kogito/decision-service.yaml -n demo-pj -p MAVEN_MIRROR_URL=$MAVEN_MIRROR_URL

# 消したい
oc delete is/decision-service -n demo-pj
oc delete bc/decision-service -n demo-pj
oc delete dc/decision-service -n demo-pj
oc delete svc/decision-service -n demo-pj
oc delete route/decision-service -n demo-pj

# local で実行
mvn clean compile quarkus:dev -Ddebug=6006 -f decision-service