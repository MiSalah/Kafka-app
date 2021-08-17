import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class producer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties properties =  new Properties();
		
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
		properties.put(ProducerConfig.CLIENT_ID_CONFIG,"producer1");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer"); 
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
		Random random = new Random();
		
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{		
			String key = String.valueOf(random.nextInt(1000));
			String value = String.valueOf(random.nextDouble()*9999);
			kafkaProducer.send(new ProducerRecord<String, String>("event",key,value),(metadata,ex)->{
				System.out.println("Sending event message : \n");
				System.out.println("Key : " +key + ", value : " +value + "\n");
				System.out.println("Partition : " + metadata.partition()+ "\n");
				System.out.println("Offset : " + metadata.offset()+ "\n");
				System.out.println("\n------------------------------");
			});
		}, 1000, 1000,TimeUnit.MILLISECONDS);
	}
}
