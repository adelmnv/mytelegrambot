package com.example.tgbot.service;

import com.example.tgbot.bots.WeatherBot;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public class WeatherService {

    private final WebClient openWeatherClient;
    private final String apiKey = "3ee1f26e5f4dea1fbfe1eb03204bffaf";

    public WeatherService(WebClient openWeatherClient) {
        this.openWeatherClient = openWeatherClient;
    }

    public void sendWeather(Long chatId, String city, WeatherBot weatherBot){
        weatherBot.sendText(chatId,fetchWeatherByCityName(city));
    }
    private String fetchWeatherByCityName(String city){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            JsonNode jsonNode = openWeatherClient.get().uri(uriBuilder -> uriBuilder
                            .path("weather")
                            .queryParam("q", city)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();


            if(jsonNode!=null) {
                stringBuilder.append("Weather in ").append(jsonNode.get("name").asText()).append("\t");
                stringBuilder.append(jsonNode.get("main").get("temp").asDouble()).append("°C\n");
                stringBuilder.append(jsonNode.get("weather").get(0).get("main").asText());
            }
            else{
                stringBuilder.append("There is no such city");
            }
        }
        catch(WebClientResponseException e){
            stringBuilder.append("There is no such city");
        }
        return stringBuilder.toString();
    }
}
