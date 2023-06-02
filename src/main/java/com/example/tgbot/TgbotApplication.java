package com.example.tgbot;

import com.example.tgbot.bots.WeatherBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TgbotApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(TgbotApplication.class, args);

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new WeatherBot("6057562054:AAGWMDqM2Uuv6JFlfeQZvS0g5JZwBidilgs"));
    }

}
