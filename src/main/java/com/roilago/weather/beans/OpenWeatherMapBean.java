package com.roilago.weather.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.roilago.weather.configuration.ApiConfiguration;

import net.aksingh.owmjapis.OpenWeatherMap;
import net.aksingh.owmjapis.OpenWeatherMap.Language;
import net.aksingh.owmjapis.OpenWeatherMap.Units;

@Component
public class OpenWeatherMapBean {

	private ApiConfiguration apiConfig;

	@Autowired
	public void setApiConfig(ApiConfiguration apiConfig) {
		this.apiConfig = apiConfig;
	}

	@Bean
	public OpenWeatherMap openWeatherMap() {
		OpenWeatherMap owm = new OpenWeatherMap(apiConfig.getKey());
		owm.setUnits(Units.METRIC);
		owm.setLang(Language.SPANISH);
		return owm;
	}
}
