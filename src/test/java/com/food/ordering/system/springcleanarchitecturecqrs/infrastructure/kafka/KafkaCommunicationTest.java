package com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka;

import com.food.ordering.system.springcleanarchitecturecqrs.infrastructure.kafka.config.KafkaTestConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ContextConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ContextConfiguration(classes = KafkaTestConfig.class)
@EmbeddedKafka(partitions = 1, topics = { "test-topic" })
public class KafkaCommunicationTest {

    @Autowired
    private KafkaTemplate<String, String> testKafkaTemplate;

    private final CountDownLatch latch = new CountDownLatch(1);
    private String receivedMessage;

    @KafkaListener(topics = "test-topic", groupId = "test-group", containerFactory = "testKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, String> record) {
        receivedMessage = record.value();
        latch.countDown();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Test
    public void testKafkaCommunication() throws InterruptedException {
        String message = "Hello, Kafka!";
        testKafkaTemplate.send("test-topic", message);

        boolean messageReceived = latch.await(10, TimeUnit.SECONDS);
        assertTrue(messageReceived, "Message not received within timeout");
        assertEquals(message, receivedMessage, "Received message does not match sent message");
    }
}