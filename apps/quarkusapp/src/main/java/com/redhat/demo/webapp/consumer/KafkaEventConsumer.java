package com.redhat.demo.webapp.consumer;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class KafkaEventConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaEventConsumer.class);

    @Inject
    OrderEventHandler orderEventHandler;

    @Incoming("orders")
    @Transactional
    public CompletionStage<Void> onMessage(KafkaRecord<String, String> message) throws IOException {
        return CompletableFuture.runAsync(() -> {
                LOG.info(message.getPayload());
                orderEventHandler.onOrderEvent(message.getPayload());
        });
    }
}
