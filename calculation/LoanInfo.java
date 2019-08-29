package calculation;

import exceptions.ValueIncompatible;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LoanInfo {

    private BigDecimal homeAmount;
    private BigDecimal creditAmount;
    private BigDecimal initialPayment;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private LocalDate firstPayment;
    private LocalDate lastPayment;
    private double percentage;
    private long year;
    private final int MAX_TIME = 25;
    private final int PENSION_AGE = 65;

    private Scanner in = new Scanner(System.in);

    public void setApplication() throws ValueIncompatible {
        System.out.println("--Mortgage application--");
        System.out.print("First name: ");
        this.firstname = in.nextLine();

        System.out.print("Last name: ");
        this.lastname = in.nextLine();

        System.out.print("Birthday (day-month-year): ");
        String[] birthdate = in.nextLine().split("-");
        this.birthdate = LocalDate.of(Integer.parseInt(birthdate[2]), Integer.parseInt(birthdate[1]), Integer.parseInt(birthdate[0]));

        //Taking home price. If home price's 70% is more than 150.000, it asks again.
        boolean isPrice = false;
        while (!isPrice) {
            System.out.print("Home price : ");
            BigDecimal homePrice = in.nextBigDecimal();
            this.creditAmount = homePrice.multiply(new BigDecimal("0.7"));
            if (this.creditAmount.compareTo(BigDecimal.valueOf(150000)) != 1) {
                this.homeAmount = homePrice;
                this.initialPayment=this.homeAmount.subtract(this.creditAmount);
                isPrice = true;
            } else {
                System.out.println("Home price is more than giveable credit ");
                //throw new ValueIncompatible("Home price is more than giveable credit : ", homePrice);
            }
        }

        //It shows how many years can be take with time() method
        System.out.print("You can select " + time() + " or less than:");
        in.nextLine();
        this.year = in.nextLong();

        this.firstPayment=LocalDate.now().plusMonths(1);
        this.lastPayment=this.firstPayment.plusYears(this.year);


        //It's percentage for per year. Max acceptable percentage is 8%
        System.out.print("Enter the percentage per year (max 8%): ");
        double temp = in.nextDouble();
        if (temp <= 8) {
            this.percentage = temp;
        } else {
            throw new ValueIncompatible("Percentage is higher than expected: ", BigDecimal.valueOf(temp));
        }

        System.out.println("--Application accepted--");
    }

    //it checks max mortgage time with pension age
    public long time() {
        long age = ChronoUnit.YEARS.between(this.birthdate, LocalDate.now());
        if (age + MAX_TIME > PENSION_AGE && PENSION_AGE - age > 0) {
            return PENSION_AGE - age;
        } else {
            return MAX_TIME;
        }
    }

    public BigDecimal getHomeAmount() {
        return homeAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount.setScale(2,RoundingMode.FLOOR);
    }

    public BigDecimal getInitialPayment() {
        return initialPayment.setScale(2,RoundingMode.FLOOR);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public long getYear() {
        return year;
    }

    public double getPercentage() {
        return percentage;
    }

    public LocalDate getFirstPayment() {
        return firstPayment;
    }

    public LocalDate getLastPayment() {
        return lastPayment;
    }

    /* public void infoTable(){
        ipoteka= new calculation.Ipoteka(this.amount,this.year,this.percentage);
        int c = 1;
        BigDecimal sum=BigDecimal.ZERO;
        BigDecimal sumPrinciple= BigDecimal.ZERO;
        BigDecimal sumInterest = BigDecimal.ZERO;
        System.out.println("| Mortgage payment table |");
        while (c <= this.year*12) {
            ipoteka.setPaymentDate();
            ipoteka.setPayment();
            ipoteka.setInterest();
            ipoteka.setPrincipal();
            ipoteka.setBalance();
            sum=sum.add(ipoteka.getPayment());
            sumPrinciple =sumPrinciple.add(ipoteka.getPrincipal());
            sumInterest=sumInterest.add(ipoteka.getInterest()) ;
            System.out.println(c+") "+ipoteka);
            c++;
        }
        System.out.println("Payment summary : "+sum.setScale(2, RoundingMode.FLOOR));
        System.out.println("Principle summary : "+sumPrinciple.setScale(2,RoundingMode.FLOOR));
        System.out.println("Interest summary : "+sumInterest.setScale(2,RoundingMode.FLOOR));
    }*/

}
