package com.example.demo1.dto;

import com.example.demo1.entity.CardStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
@FieldNameConstants
public class CardCreateEditDto {

    @NotNull
    String cardNumber;

    @Future
    Timestamp expiryDate;

    @NotNull
    CardStatus cardStatus;

    BigDecimal balance;

    @Positive
    Integer userId;

}
