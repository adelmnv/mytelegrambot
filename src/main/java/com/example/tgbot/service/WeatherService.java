package com.example.tgbot.service;

import com.example.tgbot.bots.WeatherBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

public class WeatherService {

    private final WebClient webClient;
    public WeatherService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void sendWeather(Long chatId, String city, WeatherBot weatherBot){
        weatherBot.sendText(chatId,city+" weather");
    }
}
