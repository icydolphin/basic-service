package com.ctvit;

import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducer {
	
	private  Producer<String,String> producer;
	
	private static final String TOPIC = "girl1";
	
	
	public KafkaProducer(){
		init();
	}
	
	private void init(){
		Properties props = new Properties();
        props.put("bootstrap.servers", "47.93.21.57:9092,47.93.21.57:9093,47.93.21.57:9094");//服务器ip:端口号，集群用逗号分隔
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("request.required.acks","0");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String,String>(props);
        
	}
	
	public void send(String msg){
		ProducerRecord<String,String> producerRecord = new ProducerRecord<String,String>(TOPIC, 0, "1111", msg);
		producer.send(producerRecord);
	}
	public static void main(String[] args) {
		KafkaProducer kafkaProducer = new KafkaProducer();
		kafkaProducer.send("xinpeilu");
		kafkaProducer.send("successsuccesssuccess");
	}

}
