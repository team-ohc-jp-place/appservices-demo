package com.redhat.workshop.demo;

import com.redhat.workshop.util.JsonPOJODeserializer;
import com.redhat.workshop.util.JsonPOJOSerializer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.Deserializer;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.StreamsBuilder;

import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Map;
import java.util.HashMap;

import javax.inject.Inject;

import org.kie.kogito.rules.RuleUnit;
import org.kie.kogito.rules.RuleUnitInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class EarthOrderTopology{ 

    private static final String INCOMING_TOPIC = "incoming-topic"; //入力トピック名
    private static final String OUTCOMING_TOPIC = "outcoming-topic"; //出力トピック名

    private static final Logger LOGGER = LoggerFactory.getLogger(EarthOrderTopology.class);


    @Inject
    RuleUnit<EarthOrderUnit> alertRuleUnit; //RuleUnit EarthOrderUnit.java 参照


    @Produces
    public Topology monitor() {

        LOGGER.info("イベント処理開始");

        //トピックからのデータの取得
        Map<String, Object> serdeProps = new HashMap<>();
        final Serializer<EarthOrder> earthOrderSerializer = new JsonPOJOSerializer<>();
        serdeProps.put("JsonPOJOClass", EarthOrder.class);
        earthOrderSerializer.configure(serdeProps, false);

        final Deserializer<EarthOrder> earthOrderDeserializer = new JsonPOJODeserializer<>();
        serdeProps.put("JsonPOJOClass", EarthOrder.class);
        earthOrderDeserializer.configure(serdeProps, false);

        final Serde<EarthOrder> earthOrderSerde = Serdes.serdeFrom(earthOrderSerializer, earthOrderDeserializer);
        //ここまで

        //ルールユニットの宣言
        //Setup Drools RuleUnit 
        EarthOrderUnit earthOrderUnit = new EarthOrderUnit();
        RuleUnitInstance<EarthOrderUnit> alertsvcInstance = alertRuleUnit.createInstance(earthOrderUnit);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, EarthOrder> windowedharvestcnt = builder.stream(
            INCOMING_TOPIC, /* input topic */ //入力トピックを指定
            Consumed.with(
                Serdes.String(), /* key serde */
                earthOrderSerde   /* value serde */
            )
        )
        //.peek((key, value) -> System.out.println("topic get desuyo!! -> " + value))
        .map(
            (key, value) -> {
                earthOrderUnit.getEventStream().append(value); //ファクトの連携
                LOGGER.info("value: '{}'", value.getOrderType());
                alertsvcInstance.fire(); //ルールの実行
                return new KeyValue<>(key,value);
            }
        );

        LOGGER.info("earthOrderSerde: '{}'", earthOrderSerde);
        windowedharvestcnt.to(OUTCOMING_TOPIC, Produced.with(Serdes.String(),  earthOrderSerde));

        return builder.build();

    }

}