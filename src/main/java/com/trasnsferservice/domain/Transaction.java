package com.trasnsferservice.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long sourceAccountNumber;
    Long destinationAccountNumber;
    BigDecimal Amount;

    public Transaction(Long sourceAccountNumber, Long destinationAccountNumber, BigDecimal amount) {
        this.sourceAccountNumber = sourceAccountNumber;
        this.destinationAccountNumber = destinationAccountNumber;
        Amount = amount;
    }
}
