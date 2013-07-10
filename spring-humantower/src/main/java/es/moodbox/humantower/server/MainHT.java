package es.moodbox.humantower.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import es.moodbox.humantower.spring.cfg.SpringConfig;

public class MainHT {
	
	private static final Logger LOG = LoggerFactory.getLogger(MainHT.class);
	
	/**
	 * It starts the app context with our programmatic config
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOG.debug("Starting app context");
		@SuppressWarnings("resource")
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		ctx.registerShutdownHook();
	}
}
