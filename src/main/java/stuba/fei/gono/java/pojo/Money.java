package stuba.fei.gono.java.pojo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/***
 * Class representing withdraw amount in defined currency (only EUR for DOMESTIC) and with precision.
 */
@Data
public class Money {

    @NotNull(message = "CURRENCY_INVALID")
    private Currency currency;
    private double amount;
}
