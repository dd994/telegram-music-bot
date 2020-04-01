package com.gmail.ddzhunenko;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.LocalDateTime;

public class Bot extends TelegramLongPollingBot {

    private Data data = new Data();
    private Test test = new Test();
    private Buttons buttons = new Buttons();
    private Handles handles = new Handles();
    private Parser parser = new Parser();

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message message = update.getMessage();
        try {
            if (update.hasMessage()) {

                System.out.println("New message: " + message.getText() + " ChatId: " + message.getChatId() + " " + LocalDateTime.now());
                execute(sendMessage.setChatId(message.getChatId()).setText(message.getText()).setReplyMarkup(buttons.kbSongList(message.getText(), 10)));
            } else if (update.hasCallbackQuery()) {

                String data = update.getCallbackQuery().getData();
                String text = update.getCallbackQuery().getMessage().getText();
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                int messageId = update.getCallbackQuery().getMessage().getMessageId();
                int page = parser.parsePageNumber(update.getCallbackQuery().toString());

                switch (data) {

                    case "next":
                        try {
                            if (((handles.listSize(text) - page*10)) > 10) {
                                System.out.println("CallbackQuery: " + text + ", page " + page);
                                execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText(("Downloading...")).setShowAlert(false));
                                execute(new EditMessageReplyMarkup().setChatId(chatId).setMessageId(messageId).setReplyMarkup(buttons.kbSongList(text, (page + 1) * 10)));
                                break;
                            } else {
                                execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText("Its the last page").setShowAlert(false));
                                execute(new EditMessageReplyMarkup().setChatId(chatId).setMessageId(messageId).setReplyMarkup(buttons.kbSongList(text,  10)));
                                break;
                            }

                        } catch (TelegramApiException | IOException e) {
                            e.printStackTrace();
                        }
                    case "prev":
                        if (page != 1) {
                            System.out.println("CallbackQuery: " + text + ", page " + page);
                            execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText(("Downloading...")).setShowAlert(false));
                            execute(new EditMessageReplyMarkup().setChatId(chatId).setMessageId(messageId).setReplyMarkup(buttons.kbSongList(text, (page - 1) * 10)));

                            break;
                        } else {
                            System.out.println("first " + (int) (Math.floor(handles.listSize(text) / 10)));
                            System.out.println("size " + handles.listSize(text));
                            execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText(("Downloading...")).setShowAlert(false));
                            execute(new EditMessageReplyMarkup().setChatId(chatId).setMessageId(messageId).setReplyMarkup(buttons.kbSongList(text, (int) (Math.floor(handles.listSize(text) / 10)) * 10)));
                        }
                    case "size":
                        execute(new AnswerCallbackQuery().setCallbackQueryId(update.getCallbackQuery().getId()).setText((handles.listSize(text)-handles.listSize(text)%10)+" songs found").setShowAlert(false));

                    default:
                        System.out.println("CallbackQuery: send audio from switch");
                        System.out.println("CallbackQuery: " + text + ", page " + page);
                        test.sendAudio("https://m.z1.fm/download/" + update.getCallbackQuery().getData(), update.getCallbackQuery().getMessage().getChatId());
                        System.out.println("CallbackQuery: " + update.getCallbackQuery().getData());
                }
            }
        } catch (IOException | TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return data.getName();
    }

    @Override
    public String getBotToken() {
        return data.getToken();
    }
}
