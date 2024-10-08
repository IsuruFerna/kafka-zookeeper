package isuru.kafka_demo.service;

import isuru.kafka_demo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    // to communicate with kafka server
    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {

        // when setting a topic, kafka uses default settings to do that.
        // therefor it creates a topic with 1 partition, which may reduce performance when communicating with multiple consumers
        // we also can create custom topics, which we have created in config/KafkaProducerConfig
        CompletableFuture<SendResult<String, Object>> future = template.send("topic-4", message);

        // handle result asynchronously
        future.whenComplete((result, ex) -> {
            // this is how we get metadata(partition)
            // result.getRecordMetadata().partition();

           if(ex == null) {
               System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
           } else {
               System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
           }
        });
    }

    public void sendEventsToTopic(Customer customer) {

        try {
            CompletableFuture<SendResult<String, Object>> future = template.send("topic-customer", customer);
            future.whenComplete((result, ex) -> {

                if(ex == null) {
                    System.out.println("Sent message=[" + customer.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" + customer.toString() + "] due to : " + ex.getMessage());
                }
            });
        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }
    }
}
