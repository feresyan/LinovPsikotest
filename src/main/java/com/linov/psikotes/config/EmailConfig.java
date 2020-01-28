package com.linov.psikotes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class EmailConfig {
	 @Bean(name="emailConfigBean")
	    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration(ResourceLoader resourceLoader) {
	        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
	        bean.setTemplateLoaderPath("classpath:/email/");
	        return bean;
	    }
}
