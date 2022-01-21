# local でkafkaを立てる

# フォルダ指定
cd ./strimzi-all-in-one

# 起動
docker-compose up

# トピック追加
docker-compose exec kafka bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic daytrader.inventory.outboxevent

docker-compose exec kafka bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic incoming-topic

docker-compose exec kafka bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic outgoing-topic

# producer
docker-compose exec kafka bin/kafka-console-producer.sh --topic daytrader.inventory.outboxevent --bootstrap-server localhost:9092

docker-compose exec kafka bin/kafka-console-producer.sh --topic incoming-topic --bootstrap-server localhost:9092

# consumer
docker-compose exec kafka bin/kafka-console-consumer.sh --topic <topic>  --bootstrap-server localhost:9092

docker-compose exec kafka bin/kafka-console-consumer.sh --topic outcoming-topic --bootstrap-server localhost:9092

# topic delete
docker-compose exec kafka bin/kafka-topics.sh --delete --bootstrap-server localhost:9092 --topic incoming-topic 