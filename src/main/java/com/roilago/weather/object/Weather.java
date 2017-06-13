package com.roilago.weather.object;

import java.util.Date;

public class Weather {

	public Date dateTime;

	public Float temperature = Float.valueOf(0);

	public Float pressure = Float.valueOf(0);

	public Float humidity = Float.valueOf(0);

	public Float windSpeed = Float.valueOf(0);

	public Float windDegree = Float.valueOf(0);

	public Float rain = Float.valueOf(0);

	public Float clouds = Float.valueOf(0);

	public String icon;

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Float getTemperature() {
		return temperature;
	}

	public void setTemperature(Float temperature) {
		this.temperature = temperature;
	}

	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	public Float getHumidity() {
		return humidity;
	}

	public void setHumidity(Float humidity) {
		this.humidity = humidity;
	}

	public Float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Float getWindDegree() {
		return windDegree;
	}

	public void setWindDegree(Float windDegree) {
		this.windDegree = windDegree;
	}

	public Float getRain() {
		return rain;
	}

	public void setRain(Float rain) {
		this.rain = rain;
	}

	public Float getClouds() {
		return clouds;
	}

	public void setClouds(Float clouds) {
		this.clouds = clouds;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
