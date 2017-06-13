package com.roilago.weather.object;

import java.util.ArrayList;
import java.util.List;

public class CityWeather {

	public String cityName;

	public List<Weather> weatherList = new ArrayList<Weather>();

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Weather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}

	public void addWeather(Weather weather) {
		weatherList.add(weather);
	}
}
