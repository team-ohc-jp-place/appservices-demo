# webapp
ビルドはJDK11で
GraalVMだとエラーになる
export JAVA_HOME=`/usr/libexec/java_home -v 11.0.2`

# Project 作成
oc new-project demo-pj

# AQM Streams Operator インストール
oc apply -f ./openshift/kafka/amq-streams-operator.yaml

# kafka-cluster 作成 (demo-cluster)
oc apply -f ./openshift/kafka/kafka.yml

# kafdrop デプロイ
oc apply -f ./openshift/kafka/kafdrop4.yml


# PostgreDB
oc apply -f ./openshift/db/postgres.yml

# daytrader のリミットレンジを削除
oc delete limitrange demo-pj-core-resource-limits


# Quarkus APP
## ImageStream
oc apply -f ./openshift/quarkusapp/is-quarkusapp.yml
## BuildConfig
oc apply -f ./openshift/quarkusapp/bc-quarkusapp.yml
## DeploymentConfig
oc apply -f ./openshift/quarkusapp/dc-quarkusapp.yml
## Service
oc apply -f ./openshift/quarkusapp/service-quarkusapp.yml
## Route
oc apply -f ./openshift/quarkusapp/route-quarkusapp.yml

### まとめて
oc apply -f ./openshift/quarkusapp/is-quarkusapp.yml
oc apply -f ./openshift/quarkusapp/bc-quarkusapp.yml
oc apply -f ./openshift/quarkusapp/dc-quarkusapp.yml
oc apply -f ./openshift/quarkusapp/service-quarkusapp.yml
oc apply -f ./openshift/quarkusapp/route-quarkusapp.yml

## ターミナルからトピック送信
oc exec -n demo-pj -it demo-cluster-kafka-0 -- bin/kafka-console-producer.sh --bootstrap-server demo-cluster-kafka-brokers:9092 --topic daytrader.inventory.outboxevent

## Debeziumからのトピック
{"schema":{"type":"struct","fields":[{"type":"struct","fields":[{"type":"string","optional":true,"field":"ID"},{"type":"string","optional":true,"field":"AGGREGATETYPE"},{"type":"string","optional":true,"field":"AGGREGATEID"},{"type":"string","optional":true,"field":"TYPE"},{"type":"string","optional":true,"field":"PAYLOAD"}],"optional":true,"name":"daytrader.inventory.outboxevent.Value","field":"before"},{"type":"struct","fields":[{"type":"string","optional":true,"field":"ID"},{"type":"string","optional":true,"field":"AGGREGATETYPE"},{"type":"string","optional":true,"field":"AGGREGATEID"},{"type":"string","optional":true,"field":"TYPE"},{"type":"string","optional":true,"field":"PAYLOAD"}],"optional":true,"name":"daytrader.inventory.outboxevent.Value","field":"after"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"version"},{"type":"string","optional":false,"field":"connector"},{"type":"string","optional":false,"field":"name"},{"type":"int64","optional":false,"field":"ts_ms"},{"type":"string","optional":true,"name":"io.debezium.data.Enum","version":1,"parameters":{"allowed":"true,last,false"},"default":"false","field":"snapshot"},{"type":"string","optional":false,"field":"db"},{"type":"string","optional":true,"field":"table"},{"type":"int64","optional":false,"field":"server_id"},{"type":"string","optional":true,"field":"gtid"},{"type":"string","optional":false,"field":"file"},{"type":"int64","optional":false,"field":"pos"},{"type":"int32","optional":false,"field":"row"},{"type":"int64","optional":true,"field":"thread"},{"type":"string","optional":true,"field":"query"}],"optional":false,"name":"io.debezium.connector.mysql.Source","field":"source"},{"type":"string","optional":false,"field":"op"},{"type":"int64","optional":true,"field":"ts_ms"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"id"},{"type":"int64","optional":false,"field":"total_order"},{"type":"int64","optional":false,"field":"data_collection_order"}],"optional":true,"field":"transaction"}],"optional":false,"name":"daytrader.inventory.outboxevent.Envelope"},"payload":{"before":null,"after":{"ID":"9efa6b1b-d0d0-4bae-be0b-3c7fd0eac38f","AGGREGATETYPE":"Order","AGGREGATEID":"75001","TYPE":"OrderCreated","PAYLOAD":"{\"id\":75001,\"type\":\"buy\",\"openDate\":1638515751061,\"symbol\":\"s:0\",\"quantity\":100.0,\"price\":241.69,\"orderFee\":24.95,\"accountId\":0}"},"source":{"version":"1.2.0.Final","connector":"mysql","name":"daytrader","ts_ms":1638515751000,"snapshot":"false","db":"inventory","table":"outboxevent","server_id":223344,"gtid":null,"file":"mysql-bin.000003","pos":143130771,"row":0,"thread":8,"query":null},"op":"c","ts_ms":1638515751418,"transaction":null}}

{"payload":{"after":{"ID":"9efa6b1b-d0d0-4bae-be0b-3c7fd0eac38f","AGGREGATETYPE":"Order","AGGREGATEID":"75001","TYPE":"OrderCreated","PAYLOAD":"{\"id\":75001,\"type\":\"buy\",\"openDate\":1638515751061,\"symbol\":\"s:0\",\"quantity\":100.0,\"price\":241.69,\"orderFee\":24.95,\"accountId\":0}"}}}

{"id":75001,"type":"buy","openDate":1638515751061,"symbol":"s:0","quantity":100.0,"price":241.69,"orderFee":24.95,"accountId":0}
{"id":75001,"type":"buy","orderItemName":"Lemon","openDate":1638515751061,"symbol":"s:0","quantity":100.0,"price":241.69,"orderFee":24.95,"accountId":0}

{"orderId":1,"orderType":"E","orderItemName":"Lime","quantity":100,"price":50,"shipmentAddress":"541-428 Nulla Avenue","zipCode":"4286","totalAmount":5000,"deliveryFee":200,"stateCode":"12345","stateName":"12345"}

{"orderType":"E","orderItemName":"Lime","quantity":100,"price":50,"shipmentAddress":"541-428 Nulla Avenue","zipCode":"4286"}



以下は備忘
#### コンパイル（ローカルで）
mvn clean compile package -DskipTests

#### ビルド（初回のみ）
oc new-build --binary --name=quarkusapp -l app=modern-app -n daytrader

#### docker（初回のみ？）
oc patch bc/quarkusapp -p "{\"spec\":{\"strategy\":{\"dockerStrategy\":{\"dockerfilePath\":\"src/main/docker/Dockerfile.jvm\"}}}}" -n daytrader

#### ビルド
oc start-build quarkusapp --from-dir=. --follow -n daytrader

#### アプリスタート
oc new-app --image-stream=quarkusapp

oc expose service 