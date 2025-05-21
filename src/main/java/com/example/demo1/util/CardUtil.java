package com.example.demo1.util;

import org.springframework.stereotype.Component;

@Component
public class CardUtil {

    public static String maskCard(String cardNumber){
        if (cardNumber == null || cardNumber.length()< 4)
            return cardNumber;
        return cardNumber.replaceAll("\\d(?=\\d{4})","*");

    }
}
