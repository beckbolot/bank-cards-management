package com.example.demo1.service;

import com.example.demo1.dto.CardCreateEditDto;
import com.example.demo1.dto.CardReadDto;
import com.example.demo1.dto.TransferRequestDto;
import com.example.demo1.entity.Card;
import com.example.demo1.entity.CardStatus;
import com.example.demo1.entity.QCard;
import com.example.demo1.entity.User;
import com.example.demo1.exception.CardNotFoundException;
import com.example.demo1.exception.InsufficientFundsException;
import com.example.demo1.exception.UserNotFoundException;
import com.example.demo1.mapper.CardCreateEditMapper;
import com.example.demo1.mapper.CardReadMapper;
import com.example.demo1.repository.CardRepository;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.util.CardUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardReadMapper cardReadMapper;
    private final CardCreateEditMapper cardCreateEditMapper;


    public Page<CardReadDto> findAll(Pageable pageable){
        return cardRepository.findAll(pageable)
                .map(cardReadMapper::map);
    }


    public List<CardReadDto> findAll(){
        return cardRepository.findAll().stream()
                .map(cardReadMapper::map)
                .collect(Collectors.toList());
    }

    public Optional<CardReadDto> findById(Integer id){
        return cardRepository.findById(id).map(cardReadMapper::map);
    }

    @Transactional
    public CardReadDto create(CardCreateEditDto cardCreateEditDto){
        return Optional.of(cardCreateEditDto)
                .map(cardCreateEditMapper::map)
                .map(cardRepository::save)
                .map(cardReadMapper::map)
                .orElseThrow();
    }
    @Transactional
    public Optional<CardReadDto> update(Integer id,CardCreateEditDto cardCreateEditDto){
        return cardRepository.findById(id)
                .map(entity->cardCreateEditMapper.map(cardCreateEditDto,entity))
                .map(cardRepository::saveAndFlush)
                .map(cardReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id){
        return cardRepository.findById(id)
                .map(entity->{
                    cardRepository.delete(entity);
                    cardRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public void transferMoney(TransferRequestDto dto){

        String maskedFromCard = CardUtil.maskCard(dto.getFromCardNumber());
        String maskedToCard = CardUtil.maskCard(dto.getToCardNumber());
        Card fromCard = cardRepository.findCardByCardNumber(dto.getFromCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Card with: " + maskedFromCard + " not found"));

        Card toCard = cardRepository.findCardByCardNumber(dto.getToCardNumber())
                .orElseThrow(() -> new CardNotFoundException("Card with: " + maskedToCard + " not found"));

        if (fromCard.getBalance().compareTo(dto.getAmount()) < 0){
            throw new InsufficientFundsException("Insufficient funds in the " + maskedFromCard);
        }

        fromCard.setBalance(fromCard.getBalance().subtract(dto.getAmount()));
        toCard.setBalance(toCard.getBalance().add(dto.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);
    }

    @Transactional
    public CardReadDto blockCard(Integer cardId){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with: " + cardId + " not found"));
        card.setCardStatus(CardStatus.BLOCKED);
        cardRepository.saveAndFlush(card);
        return cardReadMapper.map(card);
    }

    @Transactional
    public CardReadDto activateCard(Integer cardId){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with: " + cardId + " not found"));
        card.setCardStatus(CardStatus.ACTIVE);
        cardRepository.saveAndFlush(card);
        return cardReadMapper.map(card);
    }

    @Transactional
    public String requestCardBlocking(Integer cardId, String username){
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CardNotFoundException("Card with: " + cardId + " not found"));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with: " + username + " not found"));

        if (user.getRole().equals("ADMIN")){
            card.setCardStatus(CardStatus.BLOCKED);
            cardRepository.save(card);
            return "Card blocked successfully";
        }else {
            return "You are not authorized block card";
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Predicate buildPredicate(String  cardNumber, Timestamp expiryDate,CardStatus cardStatus,BigDecimal balance,Integer userId){
        QCard card = QCard.card;
        BooleanBuilder builder = new BooleanBuilder();

        if (cardNumber !=null){
            builder.and(card.cardNumber.eq(cardNumber));
        }
        if (expiryDate !=null){
            builder.and(card.expireDate.before(expiryDate));
        }
        if (cardStatus !=null){
            builder.and(card.cardStatus.eq(cardStatus));
        }

        if (balance !=null){
            builder.and(card.balance.eq(balance));
        }
        if (userId !=null){
            builder.and(card.id.eq(userId));
        }

        return builder;
    }




}
