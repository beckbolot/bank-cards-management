package com.example.demo1.dto;

import com.example.demo1.entity.CardStatus;

import java.math.BigInteger;
import java.sql.Timestamp;

public record CardFilter(
        String cardNumber,
        Timestamp expireDate,
        CardStatus cardStatus,
        BigInteger balance
        ) {
}
