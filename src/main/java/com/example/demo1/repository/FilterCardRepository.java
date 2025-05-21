package com.example.demo1.repository;

import com.example.demo1.dto.CardFilter;
import com.example.demo1.entity.Card;

import java.util.List;

public interface FilterCardRepository {

    List<Card> findAllByFilter(CardFilter cardFilter);
}
