package com.example.demo1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card){
        this.cards.add(card);
        card.setUser(this);
    }
}
