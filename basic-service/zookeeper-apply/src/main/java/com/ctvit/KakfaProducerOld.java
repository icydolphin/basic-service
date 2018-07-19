package com.ctvit;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KakfaProducerOld {
	
	private Producer<String,String> producer;
	
	private static final String topic = "girl1";
	
	public KakfaProducerOld(){
		init();
	}
	//初始化配置
	public void init(){
		Properties props = new Properties();
		props.put("metadata.broker.list", "47.93.21.57:9092,47.93.21.57:9093,47.93.21.57:9094");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("key.serializer.class", "kafka.serializer.StringEncoder");
		//props.put("partitioner.class", "com.ctvit.mobileinf.util.kafka.SimplePartitioner");
		props.put("request.required.acks","0");
		producer = new Producer<String, String>(new ProducerConfig(props));
	}
	
	public void send(String msg){

		producer.send(new KeyedMessage<String, String>(topic, "0" , msg));
	}
	public static void main(String[] args) {
		KakfaProducerOld kakfaProducer = new KakfaProducerOld();
		kakfaProducer.send("1242343");
		kakfaProducer.send("fdsfsdfsd");
	}
	
	

}
