package com.example.tgbot.bots;

import com.example.tgbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;


public class WeatherBot extends TelegramLongPollingBot {
    private final Map<Long, Boolean> waitingCity =  new HashMap<>();

    public WeatherBot(String botToken) {
        super(botToken);

    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&update.getMessage().hasText()){
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if(message.equals("/weather")){
                sendText(chatId, "Type city");
                waitingCity.put(chatId, true);
            } else if (Boolean.TRUE.equals(waitingCity.get(chatId))) {
                sendText(chatId, "weather in " + message);
                waitingCity.put(chatId, false);
            }
        }
    }


    public void sendText(Long who, String what){
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
    @Override
    public String getBotUsername() {
        return "your bot username";
    }
}
