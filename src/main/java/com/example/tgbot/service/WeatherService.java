package com.example.tgbot.service;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    public String sendWeather(String city){
        return city+" weather";
    }
}
