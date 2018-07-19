package com.ctvit;

import java.io.InputStream;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

public class DitributeLocker {
	
	private static DitributeLocker ditributeLocker = new DitributeLocker();
	
	private static ZkClient zkClient;
	
	private static String nodePath;
	
	private DitributeLocker(){
		InputStream in = DitributeLocker.class.getClassLoader().getResourceAsStream("zookeeper.properties");
		Properties properties = new Properties();
		try{
			properties.load(in);		
		}catch(Exception e){
			e.printStackTrace();
		}
		String serverIpAndPort = properties.getProperty("zookeeper");
		zkClient = new ZkClient(serverIpAndPort);
		nodePath = properties.getProperty("node");
	}
	
	public static DitributeLocker getInstance(){
		return ditributeLocker;
	}
	
	public  boolean getExclusiveLock(Class Useclass){
		boolean canUse = false;
		try{
			zkClient.create(nodePath+"/"+Useclass.getName(), "1", CreateMode.EPHEMERAL);
			canUse = true;
		}catch(Exception e){
			System.out.println("占用失败");
		}
		return canUse;
	}

}
