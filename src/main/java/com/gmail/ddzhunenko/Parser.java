package com.gmail.ddzhunenko;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public int parsePageNumber(String str) {

        String regex = "(Page)\\W\\W(\\d)*";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);

        String result = m.find() ? m.group() : null;
        return Integer.parseInt(result.replaceAll(("\\D"), " ").trim());
    }

    public static void main(String[] args) {


            System.out.println(new Parser().parsePageNumber("Page: 10"));

        }

}