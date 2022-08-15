package com.trasnsferservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long accountNumber;
    BigDecimal balance= BigDecimal.valueOf(0.0);

    public Account(Long accountNumber, BigDecimal balance){
        this.accountNumber=accountNumber;
        this.balance = balance;
    }

}
