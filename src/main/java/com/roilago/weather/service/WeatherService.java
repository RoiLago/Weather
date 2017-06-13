package com.roilago.weather.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roilago.weather.configuration.CitiesConfiguration;
import com.roilago.weather.object.CityWeather;
import com.roilago.weather.object.Weather;
import com.roilago.weather.utils.WeatherUtils;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

@Service
public class WeatherService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private OpenWeatherMap owm;
	private CitiesConfiguration citiesConfig;

	private final byte forecastDays = 4;

	@Autowired
	public void setOpenWeatherMap(OpenWeatherMap owm) {
		this.owm = owm;
	}

	@Autowired
	public void setCitiesConfig(CitiesConfiguration citiesConfig) {
		this.citiesConfig = citiesConfig;
	}

	public List<CityWeather> getWeatherList() {
		List<CityWeather> cityWeatherList = new ArrayList<CityWeather>();
		List<String> cities = citiesConfig.getNames();
		for (String city : cities) {
			CityWeather cityWeather = new CityWeather();
			cityWeather.setCityName(city);

			try {
				CurrentWeather cwd = owm.currentWeatherByCityName(city);
				if (cwd != null && cwd.hasDateTime()) {
					Weather weather = WeatherUtils.buildWeatherFromCurrent(cwd);
					cityWeather.addWeather(weather);
				}
				DailyForecast forecast = owm.dailyForecastByCityName(city, forecastDays);
				if (forecast != null && forecast.hasForecastCount()) {
					for (int i = 1; i < forecast.getForecastCount(); i++) {
						Weather weather = WeatherUtils.buildWeatherFromForecast(forecast.getForecastInstance(i));
						cityWeather.addWeather(weather);
					}
				}
				if (cityWeather.getWeatherList().size() != 0) {
					cityWeatherList.add(cityWeather);
				}
			} catch (IOException | JSONException e) {
				log.error(e.getMessage());
			}

		}

		return cityWeatherList;
	}

}