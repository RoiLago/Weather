package com.roilago.weather.utils;

import com.roilago.weather.object.Weather;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast.Forecast;

public class WeatherUtils {

	public static Weather buildWeatherFromCurrent(CurrentWeather currentWeather) {
		Weather weather = new Weather();

		weather.setDateTime(currentWeather.getDateTime());
		if (currentWeather.hasMainInstance()) {
			weather.setTemperature(currentWeather.getMainInstance().getTemperature());
			weather.setPressure(currentWeather.getMainInstance().getPressure());
			weather.setHumidity(currentWeather.getMainInstance().getHumidity());
		}
		if (currentWeather.hasWindInstance()) {
			weather.setWindSpeed(getValidNumber(currentWeather.getWindInstance().getWindSpeed()));
			weather.setWindDegree(getValidNumber(currentWeather.getWindInstance().getWindDegree()));
		}
		if (currentWeather.hasRainInstance()) {
			weather.setRain(getValidNumber(currentWeather.getRainInstance().getRain()));
		}
		if (currentWeather.hasCloudsInstance()) {
			weather.setClouds(getValidNumber(currentWeather.getCloudsInstance().getPercentageOfClouds()));
		}
		if (currentWeather.hasWeatherInstance() && currentWeather.getWeatherCount() > 0) {
			weather.setIcon("https://openweathermap.org/img/w/"
					+ currentWeather.getWeatherInstance(0).getWeatherIconName() + ".png");
		}

		return weather;
	}

	public static Weather buildWeatherFromForecast(Forecast forecast) {
		Weather weather = new Weather();

		weather.setDateTime(forecast.getDateTime());
		if (forecast.getTemperatureInstance() != null) {
			weather.setTemperature(forecast.getTemperatureInstance().getDayTemperature());
		}
		weather.setPressure(getValidNumber(forecast.getPressure()));
		weather.setHumidity(getValidNumber(forecast.getHumidity()));
		weather.setWindSpeed(getValidNumber(forecast.getWindSpeed()));
		weather.setWindDegree(getValidNumber(forecast.getWindDegree()));
		weather.setRain(getValidNumber(forecast.getRain()));
		weather.setClouds(getValidNumber(forecast.getPercentageOfClouds()));
		if (forecast.hasWeatherInstance() && forecast.getWeatherCount() > 0) {
			weather.setIcon(
					"https://openweathermap.org/img/w/" + forecast.getWeatherInstance(0).getWeatherIconName() + ".png");
		}

		return weather;
	}

	private static Float getValidNumber(Float value) {
		if (value.isNaN()) {
			return Float.valueOf(0);
		} else {
			return value;
		}
	}
}