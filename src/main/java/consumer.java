import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class consumer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties properties = new Properties();
		
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.put(ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG,"true");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG,"Consumers_01");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,30000);
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<Integer, String> kafkaConsumer = new KafkaConsumer<Integer, String>(properties);
		kafkaConsumer.subscribe(Collections.singletonList("event"));
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
			
			System.out.println("--------------------------------");
			ConsumerRecords<Integer, String> consumerRecords = kafkaConsumer.poll(Duration.ofMillis(10));
			consumerRecords.forEach(consumer -> {
			System.out.println("[Key : " + consumer.key() + " ,Value : " + consumer.value() + "offset : " + consumer.offset());
			});
		}, 1000, 1000, TimeUnit.MILLISECONDS);
	
	}
	
	
}
