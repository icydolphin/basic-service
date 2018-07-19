package com.ctvit;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaConsumer {
	
	private Consumer<String, String> consumer;
	
	private final String TOPIC = "girl1";
	
	public KafkaConsumer(){
		init();
	}
	
	private void init(){
		Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.93.21.57:9092,47.93.21.57:9093,47.93.21.57:9094");//服务器ip:端口号，集群用逗号分隔
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer(props);
        consumer.subscribe(Arrays.asList(TOPIC));
	}
	
	public void consume(){
		while(true){
			ConsumerRecords<String, String> records = consumer.poll(100);
            if (records.count() > 0) {
                for (ConsumerRecord<String, String> record : records) {
                	System.out.println("从kafka接收到的消息是："+record.value());
                }
            }
		}
	}
	
	public static void main(String[] args) {
		KafkaConsumer kafkaConsumer = new KafkaConsumer();
		kafkaConsumer.consume();
	}

}
