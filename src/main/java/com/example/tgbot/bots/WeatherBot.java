package com.example.tgbot.bots;

import com.example.tgbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    private final Map<Long, Boolean> waitingCity =  new HashMap<>();
    private WeatherService weatherService;

    public WeatherBot(WeatherService weatherService) {
        super("5808239245:AAHIHwXB950OD-7l2ko__aV6nDoA0SXsQ1A");
        this.weatherService = weatherService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()&&update.getMessage().hasText()){
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getFrom().getUserName() != null ? update.getMessage().getFrom().getUserName() : "little spy";
            if(message.equals("/start"))
            {
                String text = "Hello, "+name;
                sendText(chatId, text);
                sendText(chatId, "I can tell you the weather forecast, just send me /weather");
            }
            else{
                if(message.equals("/weather")){
                    sendText(chatId, "Send me the name of the city");
                    waitingCity.put(chatId, true);
                } else if (Boolean.TRUE.equals(waitingCity.get(chatId))) {
                    weatherService.sendWeather(chatId, message, this);
                    waitingCity.put(chatId, false);
                }
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
        return "adelmnv_bot";
    }
}
