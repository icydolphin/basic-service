package com.ctvit.loader;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextLoader implements ApplicationContextAware {
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
		SpringContextLoader.context = paramApplicationContext;
	}
	
	public static ApplicationContext getContext(){
		return context;
	}
}
