package com.ctvit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;

public class KafkaConsumerOld {
	
	private ConsumerConnector consumer;
	
	private static final String topic = "girl1";
	
	public KafkaConsumerOld(){
		init();
	}
	
	private void init(){
		Properties props = new Properties();
        props.put("zookeeper.connect", "47.93.21.57:2181,47.93.21.57:2182,47.93.21.57:2183");
        props.put("group.id", "test");
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "2000");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ConsumerConfig config = new ConsumerConfig(props);       		
        consumer = Consumer.createJavaConsumerConnector(config);
	}
	
	public void consume(){ 
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 1);
        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
        Map<String, List<KafkaStream<String, String>>> consumerMap =  consumer.createMessageStreams(topicCountMap,keyDecoder,valueDecoder);
        KafkaStream<String, String> stream = consumerMap.get(topic).get(0);
        ConsumerIterator<String, String> it = stream.iterator();
        while (it.hasNext()) {
        	System.out.println(it.next().message());
        }
	}
	
	public static void main(String[] args) {
		KafkaConsumerOld consumer = new KafkaConsumerOld();
		consumer.consume();
	}

}
