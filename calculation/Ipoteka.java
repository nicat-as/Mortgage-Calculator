package calculation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.StringJoiner;

public class Ipoteka {
    private BigDecimal amount;
    private LocalDate birthdate;
    private long year;
    private final double PERCENTAGE;
    private BigDecimal rate;
    private BigDecimal payment;
    private BigDecimal principal;
    private BigDecimal interest;
    private BigDecimal totalInterest;
    private BigDecimal balance;
    private LocalDate paymentDate;


    public Ipoteka(BigDecimal amount, long year, double percentage) {
        this.amount = amount;
        this.balance = amount;
        this.year = year;
        this.PERCENTAGE = percentage;
        this.paymentDate = LocalDate.now();
    }

    //This is for getting date for payment
    public void setPaymentDate() {
        this.paymentDate = this.paymentDate.plusMonths(1);
    }

    //this method calculates monthly payment to bank
    public void setPayment() {
        BigDecimal rate = BigDecimal.ZERO;
        rate = rate.add(BigDecimal.valueOf(PERCENTAGE)
                .divide(BigDecimal.valueOf(1200), 7, RoundingMode.HALF_UP));
        this.rate = rate;
        long nper = year * 12;

        BigDecimal paymentPerMonth = BigDecimal.ZERO;
        paymentPerMonth = paymentPerMonth.add(amount
                .multiply(rate
                        .multiply(BigDecimal.valueOf(1)
                                .add(rate)
                                .pow((int) nper))
                        .divide(BigDecimal.valueOf(1)
                                .add(rate)
                                .pow((int) nper)
                                .subtract(BigDecimal.valueOf(1)), new MathContext(10))));

        this.payment = paymentPerMonth.stripTrailingZeros();
    }

    //this method calculates how many amount have to be paid
    public void setBalance() {
        BigDecimal balance = this.balance.subtract(this.principal);
        this.balance = balance;
    }

    //this method calculates bank's interest amount monthly
    public void setInterest() {
        BigDecimal interest = this.balance.multiply(this.rate);
        this.interest = interest;
    }

    //monthly payment minus interest amount is principle
    public void setPrincipal() {
        this.principal = this.payment.subtract(this.interest);
        BigDecimal dif = this.principal.subtract(this.principal.setScale(2, RoundingMode.FLOOR));
    }

    // this calculates total amount which bank takes
    public BigDecimal getTotalInterest() {
        setPayment();
        this.totalInterest=getPayment().multiply(BigDecimal.valueOf(this.year).multiply(BigDecimal.valueOf(12))).subtract(this.amount);
        return this.totalInterest;
    }

    public BigDecimal getPayment() {
        return payment.setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getPrincipal() {
        return principal.setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getInterest() {
        return interest.setScale(2, RoundingMode.FLOOR);
    }

    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.FLOOR);
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    //this method is for calculation of payment, interest, principle, balance
    public void calculate() {
        setPaymentDate();
        setPayment();
        setInterest();
        setPrincipal();
        setBalance();
    }

    @Override
    public String toString() {
        return new StringJoiner(" | ", "|", "|")
                .add(paymentDate.toString())
                .add("payment : " + payment.setScale(2, RoundingMode.FLOOR) + " AZN")
                .add("principal : " + principal.setScale(2, RoundingMode.FLOOR) + " AZN")
                .add("interest : " + interest.setScale(2, RoundingMode.FLOOR) + " AZN")
                .add("balance : " + balance.setScale(2, RoundingMode.FLOOR) + " AZN")
                .toString();
    }
}
