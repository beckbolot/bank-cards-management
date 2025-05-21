package com.example.demo1.repository;

import com.example.demo1.dto.CardFilter;
import com.example.demo1.entity.Card;
import com.example.demo1.entity.QCard;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo1.entity.QCard.*;

@Component
@RequiredArgsConstructor
public class FilterCardRepositoryImpl implements FilterCardRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Card> findAllByFilter(CardFilter cardFilter) {
        Predicate predicate = QPredicates.builder()
                .add(cardFilter.cardNumber(), card.cardNumber::containsIgnoreCase)
                .add(cardFilter.cardStatus(), card.cardStatus::eq)
                .add(cardFilter.expireDate(), card.expireDate::before)
                .add(cardFilter.balance(), card.balance::gt)
                .build();
        return jpaQueryFactory
                .select(card)
                .from(card)
                .where(predicate)
                .fetch();
    }
}
