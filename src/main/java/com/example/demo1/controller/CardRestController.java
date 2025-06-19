package com.example.demo1.controller;

import com.example.demo1.dto.CardCreateEditDto;
import com.example.demo1.dto.CardReadDto;
import com.example.demo1.dto.PageResponse;
import com.example.demo1.dto.TransferRequestDto;
import com.example.demo1.entity.Card;
import com.example.demo1.entity.CardStatus;
import com.example.demo1.exception.TransferRequestException;
import com.example.demo1.mapper.CardReadMapper;
import com.example.demo1.repository.CardRepository;
import com.example.demo1.service.CardService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Tag(name = "Card controller",description = "Operation related to Cards")
public class CardRestController {

    private final CardService cardService;
    private final CardRepository cardRepository;
    private final CardReadMapper cardReadMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Getting all cards with help of special created object PageResponse that contains meta data about page")
    public PageResponse<CardReadDto> findAll(Pageable pageable){
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<CardReadDto> page = cardService.findAll(pageRequest);

        return PageResponse.of(page);
    }


    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Getting all cards by filtering card number, card status etc. ")
    public ResponseEntity<List<CardReadDto>> findAllByFilter(@RequestParam(required = false) String cardNumber,
                                                             @RequestParam(required = false) Timestamp expiryDate,
                                                             @RequestParam(required = false) CardStatus cardStatus,
                                                             @RequestParam(required = false) BigDecimal balance,
                                                             @RequestParam(required = false) Integer userId){
        PageRequest pageRequest = PageRequest.of(0, 20);

        Predicate predicate = cardService.buildPredicate(cardNumber, expiryDate, cardStatus, balance,userId);

        Page<Card> pageCards = cardRepository.findAll(predicate, pageRequest);

        List<CardReadDto> filteredCardDto = pageCards.stream().map(cardReadMapper::map).collect(Collectors.toList());
        return ResponseEntity.ok(filteredCardDto);

    }

    @GetMapping("/allCards")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Getting all cards as list")
    public ResponseEntity<List<CardReadDto>> findAll(){
        return ResponseEntity.ok().body(cardService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER') and @accessCard.hasAccessToCard(authentication,#id) or hasAuthority('ADMIN')")
    @Operation(summary = "Only card owner can see his own card information")
    public ResponseEntity<CardReadDto> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(cardService.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/block/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Blocking or cancelling card , only ADMIN can block card")
    public ResponseEntity<CardReadDto> blockCard(@PathVariable("id") Integer cardId){
        return ResponseEntity.ok(cardService.blockCard(cardId));
    }

    @PostMapping("/active/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Activating card , only ADMIN can activate cards ")
    public ResponseEntity<CardReadDto> activateCardCard(@PathVariable("id") Integer cardId){
        return ResponseEntity.ok(cardService.activateCard(cardId));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Creating new card, only ADMIN can do this")
    public ResponseEntity<CardReadDto> create(@RequestBody @Validated CardCreateEditDto card){
        return ResponseEntity.ok(cardService.create(card));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Updating card information")
    public ResponseEntity<CardReadDto> update(@PathVariable("id") Integer id,@RequestBody @Validated CardCreateEditDto card){
        return ResponseEntity.ok().body(cardService.update(id,card).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Deleting card from database")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return cardService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/request/block")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "As user has no authority blocking card, user can request ADMIN to block the card")
    public ResponseEntity<String> requestCardBlocking(@RequestParam Integer cardId, @RequestParam String username){
        String response = cardService.requestCardBlocking(cardId, username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @Operation(summary = "User can transfer funds between his cards or transfer to another person card")
    public ResponseEntity<String> transfer(@RequestBody TransferRequestDto dto){
        try{
            cardService.transferMoney(dto);
            return ResponseEntity.ok("Transfer successfully completed!!!");
        }catch (TransferRequestException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }







}
