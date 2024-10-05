package isuru.kafka_zookeeper.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    // creating custom topics
    @Bean
    public NewTopic newTopic() {
        // input order: name of the topic, numOfPartitions, replicationFactor
        return new NewTopic("topic-3", 5, (short) 1);
    }


}
