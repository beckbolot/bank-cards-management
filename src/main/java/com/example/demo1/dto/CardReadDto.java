package com.example.demo1.dto;

import com.example.demo1.entity.Card;
import com.example.demo1.entity.CardStatus;
import com.example.demo1.entity.User;
import com.example.demo1.mapper.Mapper;
import lombok.Value;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
public class CardReadDto{

    Integer id;
    String cardNumber;
    Timestamp expiryDate;
    CardStatus cardStatus;
    BigDecimal balance;
    UserReadDto user;

}
