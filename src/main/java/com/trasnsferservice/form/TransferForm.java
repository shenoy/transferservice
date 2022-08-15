package com.trasnsferservice.form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TransferForm {
    Long sourceAccountNumber;
    Long destinationAccountNumber;
    BigDecimal Amount;
}
