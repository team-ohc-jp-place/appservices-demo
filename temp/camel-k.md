# デモ

## まずは何があるのかを説明

## レガシーアプリのデモ
一つ流してみる

画面のデータの説明

CDCのログ
ログから次に必要な部分のデータを見せる

## レガシーアプリからkafkaのデモ

### ターミナル
oc exec -n demo-pj -it earth-cluster-kafka-0 -- bin/kafka-console-consumer.sh --topic user1.earth.dbo.Orders --bootstrap-server earth-cluster-kafka-brokers:9092


# Camel-k

## Input
Kafka Not Secured Source を選ぶ

### topic name
user1.earth.dbo.Orders

### Brokers
earth-cluster-kafka-bootstrap.demo-pj.svc:9092

## atlasmap

### Uri
atlasmap:file:/etc/camel/resources/order-mapping.adm

## Output
Kafka Not Secured Sink を選ぶ

### topic name
incoming-topic

### Brokers
earth-cluster-kafka-bootstrap.demo-pj.svc:9092

## Deploy
kamel run ./openshift/camel-k/order.yaml --resource file:./openshift/camel-k/order-mapping.adm -n demo-pj

### ターミナル
oc exec -n demo-pj -it earth-cluster-kafka-0 -- bin/kafka-console-consumer.sh --topic incoming-topic --bootstrap-server earth-cluster-kafka-brokers:9092

## DM

### まずは10件動かして画面で見せる

### すぐ裏でビルドスタート

### デシジョンテーブルを見せる
### 無料のしきい値変更を見せる

### もう一回10件



### ターミナル

oc exec -n demo-pj -it earth-cluster-kafka-0 -- bin/kafka-console-consumer.sh --topic outcoming-topic --bootstrap-server earth-cluster-kafka-brokers:9092