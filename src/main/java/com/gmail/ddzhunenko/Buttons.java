package com.gmail.ddzhunenko;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Buttons {

    private Handles handles = new Handles();

    public InlineKeyboardMarkup kbSongList(String songName, int i) throws IOException {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(); //return`s keyboard object
        List<List<InlineKeyboardButton>> kbList = new ArrayList<>(); // list of rows of keyboards
        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(new InlineKeyboardButton().setText("<").setCallbackData("prev"));
        row1.add(new InlineKeyboardButton().setText("Page: " + i / 10 + "").setCallbackData("size"));
        row1.add(new InlineKeyboardButton().setText(">").setCallbackData("next"));

        Map<Integer, Map.Entry> songs = handles.getSongList(songName);

        for (int j = i - 9; j < i + 1; j++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText(songs.get(j).getValue().toString()).setCallbackData(songs.get(j).getKey().toString()));
            kbList.add(row);
        }

        kbList.add(row1);
        inlineKeyboardMarkup.setKeyboard(kbList);

        return inlineKeyboardMarkup;
    }
}
