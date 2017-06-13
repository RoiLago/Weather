package com.roilago.weather.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.roilago.weather.configuration.CitiesConfiguration;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.DailyForecast.Forecast;
import net.aksingh.owmjapis.OpenWeatherMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherServiceTests {

	@MockBean
	private OpenWeatherMap openWeatherMap;

	@MockBean
	private CitiesConfiguration citiesConfiguration;

	@Autowired
	private WeatherService weatherService;

	private final String cityName = "Pontevedra";

	@Test
	public void emptyListTest() throws Exception {
		assertTrue(weatherService.getWeatherList().isEmpty());
	}

	@Test
	public void invalidWeatherTest() throws Exception {
		ArrayList<String> cityNames = new ArrayList<String>();
		cityNames.add(cityName);
		when(citiesConfiguration.getNames()).thenReturn(cityNames);

		assertTrue(weatherService.getWeatherList().isEmpty());
	}

	private void configureCities() {
		ArrayList<String> cityNames = new ArrayList<String>();
		cityNames.add(cityName);
		when(citiesConfiguration.getNames()).thenReturn(cityNames);
	}

	private void buildCurrentWeather(Date date) throws IOException, JSONException {
		CurrentWeather cw = mock(CurrentWeather.class);
		when(cw.hasDateTime()).thenReturn(true);
		when(cw.getDateTime()).thenReturn(date);
		when(openWeatherMap.currentWeatherByCityName(anyString())).thenReturn(cw);
	}

	@Test
	public void validCurrentInvalidForecastTest() throws IOException, JSONException {
		configureCities();
		Date date = new Date();
		buildCurrentWeather(date);

		assertFalse(weatherService.getWeatherList().isEmpty());
		assertTrue(weatherService.getWeatherList().get(0).getWeatherList().get(0).getDateTime().equals(date));
	}

	private void buildForecastWeather(Date date) throws IOException, JSONException {
		DailyForecast dailyForecast = mock(DailyForecast.class);
		when(dailyForecast.hasForecastCount()).thenReturn(true);
		when(dailyForecast.getForecastCount()).thenReturn(2);
		Forecast forecast = mock(Forecast.class);
		when(forecast.getDateTime()).thenReturn(date);
		when(dailyForecast.getForecastInstance(1)).thenReturn(forecast);
		when(openWeatherMap.dailyForecastByCityName(anyString(), anyByte())).thenReturn(dailyForecast);
	}

	@Test
	public void validForecastInvalidCurrentTest() throws Exception {
		configureCities();
		Date date = new Date();
		buildForecastWeather(date);

		assertFalse(weatherService.getWeatherList().isEmpty());
		assertTrue(weatherService.getWeatherList().get(0).getWeatherList().get(0).getDateTime().equals(date));
	}

	@Test
	public void validCurrentValidForecastTest() throws Exception {
		configureCities();
		Date date0 = new Date();
		buildCurrentWeather(date0);
		Date date1 = new Date();
		buildForecastWeather(date1);

		assertFalse(weatherService.getWeatherList().isEmpty());
		assertTrue(weatherService.getWeatherList().get(0).getWeatherList().get(0).getDateTime().equals(date0));
		assertTrue(weatherService.getWeatherList().get(0).getWeatherList().get(1).getDateTime().equals(date1));
	}
}