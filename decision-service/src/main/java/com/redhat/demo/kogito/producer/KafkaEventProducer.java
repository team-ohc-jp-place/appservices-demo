package com.redhat.demo.kogito.producer;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
//import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class KafkaEventProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaEventProducer.class);

    //@Outgoing("webapp")
    @Transactional
    public void onProduceEvent(JsonNode payload) {

        final String outmessage = payload.toString();

        // 接続時の設定値を Properties インスタンスとして構築する
        Properties properties = new Properties();
        // 接続先 Kafka ノード
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Producer を構築する
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());

        // トピックを指定してメッセージを送信する
        producer.send(new ProducerRecord<String, String>("outgoing-topic", outmessage));
        producer.close();
    }

}