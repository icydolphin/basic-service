package com.ctvit.propertyreader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 系统配置文件读取类
 * @author xinpeilu
 *
 */
public class PropertyReader {
	
	private static PropertyReader propertyReader;
	
	private Properties properties;
	
	private static String systemConfigPath = PropertyReader.class.getClassLoader().getResource("conf.properties").getPath();
	
	private PropertyReader(){
		try{
			properties = new Properties();
			properties.load(new FileInputStream(new File(systemConfigPath)));			
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 获取filePath的.properties文件的属性
	 * @param filePath
	 */
	public PropertyReader(String filePath){
		try{
			properties = new Properties();
			properties.load(new FileInputStream(new File(filePath)));			
		}catch(Exception e){
			
		}
	}
	/**
	 * 获取系统配置属性
	 * @return
	 */
	public static PropertyReader getInstance(){
		synchronized (PropertyReader.class) {		
			if(propertyReader==null){
				propertyReader = new PropertyReader();
			}			
		}
		return propertyReader;
	}
	
	/**
	 * 获取指定key的值
	 * @param key
	 * @return
	 */
	public String get(String key){
		return (String)properties.get(key);
	}
	
	/**
	 * 获取配置文件中的所有属性
	 * @return
	 */
	public Map<String,String> getAllParams(){
		Map<String,String> paramMap = new HashMap<String,String>();
		Enumeration  e = properties.propertyNames();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = properties.getProperty(key);
			paramMap.put(key, value);
		}	
		return paramMap;
	}
	
	public static void main(String[] args) {
		PropertyReader pr = PropertyReader.getInstance();
		System.out.println(pr.get("port"));
	}

}
