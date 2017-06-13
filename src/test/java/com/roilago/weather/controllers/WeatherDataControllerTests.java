package com.roilago.weather.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.roilago.weather.object.CityWeather;
import com.roilago.weather.service.WeatherService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherDataControllerTests {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private WeatherService weatherServiceMock;

	private final String cityName = "Pontevedra";

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private void buildCorrectResponse() throws MalformedURLException, IOException, JSONException {
		CityWeather cityWeather = new CityWeather();
		cityWeather.setCityName(cityName);
		List<CityWeather> cityWeatherList = new ArrayList<CityWeather>();
		cityWeatherList.add(cityWeather);

		when(weatherServiceMock.getWeatherList()).thenReturn(cityWeatherList);
	}

	@Test
	public void correctResponseTest() throws Exception {
		buildCorrectResponse();

		this.mockMvc.perform(get("/cityWeatherList")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.cityWeatherList[0].cityName", is(cityName)));
	}

	@Test
	public void emptyResponseTest() throws Exception {
		this.mockMvc.perform(get("/cityWeatherList")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.cityWeatherList", is(empty())));
	}
}
