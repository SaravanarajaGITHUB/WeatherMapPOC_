package com.saravana.weathermappoc.pojo;

import java.util.List;

public class DailyForecasts {

    private City city;
    private String country;
    private List<WeatherInfo> list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<WeatherInfo> getList() {
        return list;
    }

    public void setList(List<WeatherInfo> list) {
        this.list = list;
    }
}
