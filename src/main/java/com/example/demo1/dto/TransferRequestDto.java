package com.example.demo1.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class TransferRequestDto {

    String fromCardNumber;
    String toCardNumber;
    BigDecimal amount;
}
