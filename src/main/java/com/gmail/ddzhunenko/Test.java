package com.gmail.ddzhunenko;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Test {

    public void sendAudio(String url, long chatId) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .followRedirects(false)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Referer", url)
                .build();
        Response response = client.newCall(request).execute();

        String fileURL = response.header("Location");

        // sending file
        String token = new Data().getToken();
        //String chatId = "488337158";

        TelegramBot bot = TelegramBotAdapter.build(token);
        bot.sendAudio(chatId, fileURL, 100, "", "", null, null);
        response.body().toString();
    }

}
