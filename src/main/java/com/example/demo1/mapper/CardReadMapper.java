package com.example.demo1.mapper;

import com.example.demo1.dto.CardReadDto;
import com.example.demo1.dto.UserReadDto;
import com.example.demo1.entity.Card;
import com.example.demo1.util.CardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardReadMapper implements Mapper<Card, CardReadDto>{

    private final UserReadMapper userReadMapper;
    @Override
    public CardReadDto map(Card object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        return new CardReadDto(
                object.getId(),
                CardUtil.maskCard(object.getCardNumber()),
                object.getExpireDate(),
                object.getCardStatus(),
                object.getBalance(),
                user
        );
    }
}
