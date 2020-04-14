package stuba.fei.gono.java.pojo;

import lombok.Data;
import stuba.fei.gono.java.blocking.validation.annotations.MaxAmount;

import javax.validation.constraints.NotNull;

@Data
public class Money {

    @NotNull
    private Currency currency;
    @MaxAmount(message = "FIELD_INVALID")
    private double amount;
}
