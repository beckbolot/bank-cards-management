package com.example.demo1.security;

import com.example.demo1.entity.Card;
import com.example.demo1.exception.CardNotFoundException;
import com.example.demo1.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component("accessCard")
public class AccessCard {

    @Autowired
    private CardRepository cardRepository;

    public boolean hasAccessToCard(Authentication authentication,Integer cardId){
        User user = (User) authentication.getPrincipal();
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with id: " + cardId + "not found"));

        return card.getUser().getUsername().equals(user.getUsername());
    }

}
