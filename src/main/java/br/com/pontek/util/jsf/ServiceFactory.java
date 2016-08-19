package br.com.pontek.util.jsf;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class ServiceFactory implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ServiceFactory.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) throws BeansException {
		return ServiceFactory.applicationContext.getBean(beanName);
	}
}
