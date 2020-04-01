package com.gmail.ddzhunenko;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Handles {

    public Map getSongList(String songName) throws IOException {

        Map<Integer, Map.Entry> songkeys = new HashMap<>();

        String url = "https://m.z1.fm/mp3/search?keywords=";

        Document doc = getDataFromUrlConnect(url + songName);
        Document doc1 = getDataFromUrlConnect(url + songName + "&page=2&_pjax=true");
        Document doc2 = getDataFromUrlConnect(url + songName + "&page=3&_pjax=true");
        Document doc3 = getDataFromUrlConnect(url + songName + "&page=4&_pjax=true");
        Document doc4 = getDataFromUrlConnect(url + songName + "&page=5&_pjax=true");
        Document doc5 = getDataFromUrlConnect(url + songName + "&page=6&_pjax=true");

        for (int i = 1; i < getSongs(doc).size()
                + getSongs(doc1).size()
                + getSongs(doc2).size()
                + getSongs(doc3).size()
                + getSongs(doc4).size()
                + getSongs(doc5).size() + 1; i++) {
            songkeys.put(i, null);
        }

        int i = 0;

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc1).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc2).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc3).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc4).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        for (Map.Entry<String, String> stringStringEntry : getSongs(doc5).entrySet()) {
            i++;
            songkeys.put(i, stringStringEntry);
        }

        return songkeys;
    }

    public int listSize(String str) throws IOException {
        return getSongList(str).size();
    }

    // get DOM doc from http connection
    public static Document getDataFromUrlConnect(String url) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
    }

    // list of songs
    public Map<String, String> getSongs(Document doc) {

        Map<String, String> songList = new HashMap<>();
        final String[] result = {""};

        Elements songs = doc.getElementsByAttributeValue("class", "tracks-item");
        songs.forEach(element -> {
            Element li = element.child(0);
            String url = li.attr("data-id");
            result[0] = result[0] + url;
        });

        songs.forEach(element -> songList.put(element.attr("data-id"), element.attr("data-artist") + " - " + element.attr("data-title")));

        return songList.entrySet().stream()
                .filter(stringStringEntry -> stringStringEntry.getValue().toCharArray().length < 50)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    public static void main(String[] args) throws IOException {
        //System.out.println(new Handles().getSongs(getDataFromUrlConnect("https://m.z1.fm/mp3/search?keywords=lady+gaga")));
        //System.out.println(new Handles().getSongList("eminem").size());
        System.out.println((int)(Math.floor((new Handles().listSize("eminem"))/10)));
    }
}

