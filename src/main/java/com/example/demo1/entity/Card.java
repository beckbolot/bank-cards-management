package com.example.demo1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "user")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "card_number")
    @NotNull
    private String cardNumber;

    @Column(name = "expire_date")
    @NotNull
    private Timestamp expireDate;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;




}
