package com.example.demo1.service;

import com.example.demo1.dto.CardCreateEditDto;
import com.example.demo1.dto.CardReadDto;
import com.example.demo1.dto.TransferRequestDto;
import com.example.demo1.dto.UserReadDto;
import com.example.demo1.entity.Card;
import com.example.demo1.entity.CardStatus;
import com.example.demo1.entity.Role;
import com.example.demo1.entity.User;
import com.example.demo1.mapper.CardCreateEditMapper;
import com.example.demo1.mapper.CardReadMapper;
import com.example.demo1.repository.CardRepository;
import com.example.demo1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private  CardRepository cardRepository;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  CardReadMapper cardReadMapper;
    @Mock
    private  CardCreateEditMapper cardCreateEditMapper;
    @InjectMocks
    private CardService cardService;

    private Card card;
    private User user;
    private CardReadDto cardReadDto;
    private UserReadDto userReadDto;
    private CardCreateEditDto cardCreateEditDto;



    @BeforeEach
    void setup(){
        user = new User(1,"1@gmail.com","pass1","First1","Last1","AC1111", LocalDate.now(), Role.USER, Collections.emptyList());
        card = new Card(1,"1111111111",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),user);



        userReadDto = new UserReadDto(1,"1@gmail.com","First1","Last1","AC1111", LocalDate.now(), Role.USER);
        cardReadDto = new CardReadDto(1,"1111111111",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),userReadDto);

        cardCreateEditDto = new CardCreateEditDto("1111111111",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),user.getId());



    }

    @Test
    void findAll() {

        List<Card> cards = List.of(card);

        given(cardRepository.findAll()).willReturn(cards);
        given(cardReadMapper.map(card)).willReturn(cardReadDto);

        List<CardReadDto> cardReadDtoLists = cardService.findAll();

        assertThat(cardReadDtoLists).hasSize(1);

        assertThat(cardReadDtoLists.get(0).getCardNumber()).isEqualTo("1111111111");

        verify(cardRepository).findAll();
        verify(cardReadMapper,times(1)).map(any(Card.class));
    }



    @Test
    void findById() {
        given(cardRepository.findById(1)).willReturn(Optional.of(card));
        given(cardReadMapper.map(card)).willReturn(cardReadDto);

        cardReadDto = cardService.findById(1).orElseThrow();

        assertThat(cardReadDto).isNotNull();
        assertThat(cardReadDto.getCardNumber()).isEqualTo("1111111111");

    }

    @Test
    void create() {

        given(cardCreateEditMapper.map(cardCreateEditDto)).willReturn(card);
        given(cardRepository.save(card)).willReturn(card);
        given(cardReadMapper.map(card)).willReturn(cardReadDto);

        CardReadDto result = cardService.create(cardCreateEditDto);

        assertNotNull(result);
        assertEquals("1111111111",result.getCardNumber());

        verify(cardCreateEditMapper).map(cardCreateEditDto);
        verify(cardRepository).save(card);
        verify(cardReadMapper).map(card);
    }


    @Test
    void update() {
        Card existingCard = new Card(1,"1111111111",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),user);

        Card updatedCard = new Card(1,"222222222",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),user);


        CardReadDto cardReadDto1 = new CardReadDto(1,"222222222",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),userReadDto);

        given(cardRepository.findById(1)).willReturn(Optional.of(existingCard));
        given(cardCreateEditMapper.map(cardCreateEditDto, existingCard)).willReturn(updatedCard);
        given(cardRepository.saveAndFlush(updatedCard)).willReturn(updatedCard);
        given(cardReadMapper.map(updatedCard)).willReturn(cardReadDto1);

        Optional<CardReadDto> result = cardService.update(1, cardCreateEditDto);

        assertTrue(result.isPresent());
        assertEquals("222222222", result.get().getCardNumber());
        verify(cardRepository).findById(1);
        verify(cardCreateEditMapper).map(cardCreateEditDto, existingCard);
        verify(cardRepository).saveAndFlush(updatedCard);
        verify(cardReadMapper).map(updatedCard);
    }

    @Test
    void delete() {

        given(cardRepository.findById(1)).willReturn(Optional.of(card));
        willDoNothing().given(cardRepository).delete(card);


        boolean result = cardService.delete(1);

        assertTrue(result);
        verify(cardRepository).findById(1);
        verify(cardRepository).delete(card);
    }


    @Test
    void transferMoney() {

        TransferRequestDto transferRequestDto = new TransferRequestDto("1234 5678 9876 5432", "2345 6789 8765 4321", BigDecimal.valueOf(50));
        Card fromCard = new Card(1,"1234 5678 9876 5432",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(200),user);
        Card toCard = new Card(2,"2345 6789 8765 4321",Timestamp.valueOf("2029-01-01 00:00:00"),
                CardStatus.ACTIVE,new BigDecimal(100),user);

        given(cardRepository.findCardByCardNumber(transferRequestDto.getFromCardNumber())).willReturn(Optional.of(fromCard));
        given(cardRepository.findCardByCardNumber(transferRequestDto.getToCardNumber())).willReturn(Optional.of(toCard));
        willReturn(fromCard).given(cardRepository).save(fromCard);
        willReturn(toCard).given(cardRepository).save(toCard);


        cardService.transferMoney(transferRequestDto);


        assertEquals(BigDecimal.valueOf(150), fromCard.getBalance());
        assertEquals(BigDecimal.valueOf(150), toCard.getBalance());
        verify(cardRepository).save(fromCard);
        verify(cardRepository).save(toCard);
    }

}