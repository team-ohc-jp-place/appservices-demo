## OCPの設定色々（適宜変更）
```
export MASTER_URL=https://api.cluster-fj2kx.fj2kx.sandbox407.opentlc.com:6443

export OCP_USER=opentlc-mgr

export OCP_PASSWORD=r3dh4t1!

export PJ_NAME=commons-demo
```

## OpenShift Login
```
oc login ${MASTER_URL} -u ${OCP_USER} -p ${OCP_PASSWORD} --insecure-skip-tls-verify=true
```

## Project作成
```
oc new-project ${PJ_NAME}
```

## Operator のインストール

Red Hat Integration - AMQ Streams (ver1.8.1)

## kafka クラスターの作成
```
oc -n ${PJ_NAME} apply -f openshift/kafka-deploy.yaml
```

## kafka トピックの作成
```
oc -n ${PJ_NAME} apply -f openshift/kafkatopic-create.yaml
```

## kafdrop　のデプロイ（別に無くても良い）
```
oc -n ${PJ_NAME} apply -f openshift/kafdrop-deploy.yaml
```

## KOGITO アプリケーションのデプロイ

```
mvn clean compile package -DskipTests

oc new-build --binary --name=decision-service -l app=decision-service -n ${PJ_NAME}

oc patch bc/decision-service -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.jvm\"}}}}" -n ${PJ_NAME}

oc start-build decision-service --from-dir=. --follow -n ${PJ_NAME}

oc new-app --image-stream=decision-service \
 -e quarkus.kafka-streams.bootstrap-servers=my-cluster-kafka-bootstrap.${PJ_NAME}.svc:9092 \
 -e quarkus.kafka-streams.application-server=my-cluster-kafka-bootstrap.${PJ_NAME}.svc:9092
```

## Mock　アプリケーションのデプロイ

```
oc new-app centos/python-36-centos7~https://github.com/team-ohc-jp-place/event-emitter \
 -e KAFKA_BROKERS=my-cluster-kafka-brokers:9092 \
 -e KAFKA_TOPIC=incoming-topic -e RATE=1 --name=emitter
```