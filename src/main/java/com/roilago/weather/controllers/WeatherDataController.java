package com.roilago.weather.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roilago.weather.service.WeatherService;

@RestController
public class WeatherDataController {

	private WeatherService weatherService;

	@Autowired
	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@RequestMapping("/cityWeatherList")
	public Map<String, Object> getWeatherListData() {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("cityWeatherList", weatherService.getWeatherList());

		return model;
	}

}