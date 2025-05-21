package com.example.demo1.mapper;

import com.example.demo1.dto.CardCreateEditDto;
import com.example.demo1.entity.Card;
import com.example.demo1.entity.User;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.util.CardUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardCreateEditMapper implements Mapper<CardCreateEditDto, Card>{

    private final UserRepository userRepository;
    @Override
    public Card map(CardCreateEditDto object) {

        Card card = new Card();
        copy(object,card);

        return card;
    }

    @Override
    public Card map(CardCreateEditDto fromObject, Card toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CardCreateEditDto object,Card card){
        card.setCardNumber(object.getCardNumber());
        card.setExpireDate(object.getExpiryDate());
        card.setCardStatus(object.getCardStatus());
        card.setBalance(object.getBalance());
        card.setUser(getUser(object.getUserId()));
    }

    private User getUser(Integer userId){
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
