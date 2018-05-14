package com.ctvit.propertyreader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
/**
 * ϵͳ�����ļ���ȡ��
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
	 * ��ȡfilePath��.properties�ļ�������
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
	 * ��ȡϵͳ��������
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
	
	public String get(String key){
		return (String)properties.get(key);
	}
	
	public static void main(String[] args) {
		PropertyReader pr = PropertyReader.getInstance();
		System.out.println(pr.get("port"));
	}

}
