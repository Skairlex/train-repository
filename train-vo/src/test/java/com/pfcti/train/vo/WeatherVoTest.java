package com.pfcti.train.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class WeatherVoTest {

   /* @Test
    public void MapperBuilderTest(){
        String json="{\"coord\":{\"lon\":-94.04,\"lat\":33.44},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":292.23,\"feels_like\":291.39,\"temp_min\":292.02,\"temp_max\":292.73,\"pressure\":1021,\"humidity\":46},\"visibility\":10000,\"wind\":{\"speed\":3.13,\"deg\":255,\"gust\":5.36},\"clouds\":{\"all\":0},\"dt\":1696785312,\"sys\":{\"type\":2,\"id\":62880,\"country\":\"US\",\"sunrise\":1696767309,\"sunset\":1696809124},\"timezone\":-18000,\"id\":4133367,\"name\":\"Texarkana\",\"cod\":200}";
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseApiWeather miObjeto= null;
        // Convertir el string JSON en un objeto Java
        try {
            miObjeto= objectMapper.readValue(json, ResponseApiWeather.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(miObjeto);

    }/
}
