package exceptions;

import java.math.BigDecimal;

public class ValueIncompatible extends MortgageException {

    private String message;
    private BigDecimal value;

    public ValueIncompatible(String message, BigDecimal value) {
        this.message=message;
        this.value= value;
    }

    @Override
    public String getMessage() {
        return this.message+this.value;
    }
}
