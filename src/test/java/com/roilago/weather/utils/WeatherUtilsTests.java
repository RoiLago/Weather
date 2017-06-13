package com.roilago.weather.utils;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.roilago.weather.object.Weather;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.CurrentWeather.Main;
import net.aksingh.owmjapis.DailyForecast.Forecast;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherUtilsTests {

	private final float testFloat = 1F;

	@Test
	public void currentWeatherTest() {
		CurrentWeather currentWeather = mock(CurrentWeather.class);

		Date date = new Date();
		when(currentWeather.getDateTime()).thenReturn(date);

		Main main = mock(Main.class);
		when(main.getTemperature()).thenReturn(Float.valueOf(testFloat));
		when(currentWeather.hasMainInstance()).thenReturn(true);
		when(currentWeather.getMainInstance()).thenReturn(main);
		Weather weather = WeatherUtils.buildWeatherFromCurrent(currentWeather);

		assertTrue(weather.getDateTime().equals(date));
		assertTrue(weather.getTemperature().equals(testFloat));
	}

	@Test
	public void correctForecastWeatherTest() {
		Forecast forecast = mock(Forecast.class);

		Date date = new Date();
		when(forecast.getDateTime()).thenReturn(date);
		when(forecast.getPressure()).thenReturn(testFloat);
		Weather weather = WeatherUtils.buildWeatherFromForecast(forecast);

		assertTrue(weather.getDateTime().equals(date));
		assertTrue(weather.getPressure().equals(testFloat));
	}

	@Test
	public void nanForecastWeatherTest() {
		Forecast forecast = mock(Forecast.class);

		Date date = new Date();
		when(forecast.getDateTime()).thenReturn(date);
		when(forecast.getPressure()).thenReturn(Float.NaN);
		Weather weather = WeatherUtils.buildWeatherFromForecast(forecast);

		assertTrue(weather.getDateTime().equals(date));
		assertTrue(weather.getPressure().equals(0F));
	}
}