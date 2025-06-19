package com.example.demo1.repository;

import com.example.demo1.entity.Card;
import com.example.demo1.entity.CardStatus;
import com.example.demo1.entity.User;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Integer>, QuerydslPredicateExecutor<Card>, FilterCardRepository {

    Optional<Card> findCardByCardNumber(String cardNumber);

    List<Card> findAllByExpireDateBefore(Timestamp expiryDate, Sort sort);

    List<Card> findAllBy(String sortCriteria,Pageable pageable);

    Slice<Card> findAllBy(Pageable pageable);







}
