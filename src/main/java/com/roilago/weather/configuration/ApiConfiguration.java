package com.roilago.weather.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("api")
public class ApiConfiguration {

	private String key;

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}