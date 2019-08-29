package database;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

// In this class, the database operations are done
public class DatabaseOperation {
    private long customerId;
    private long creditId;
    private long monthlyPaymentId;

    public long getCustomerId() {
        return customerId;
    }

    public long getCreditId() {
        return creditId;
    }

    public long getMonthlyPaymentId() {
        return monthlyPaymentId;
    }

    //this is for implementation of insert to customer
    public void insertToCustomer(ResultSet rs, PreparedStatement ps, String name, String lastname, LocalDate birthday) throws SQLException {
        ps.setString(1, name);
        ps.setString(2, lastname);
        ps.setDate(3, Date.valueOf(birthday));
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        if(rs.next()){
            this.customerId =rs.getLong(1);
        }
    }

    //this is for implementation of insert to credit
    public void insertToCredit(ResultSet rs,PreparedStatement ps, long cutomerId, BigDecimal homePrice, BigDecimal initialPayment, BigDecimal creditAmount, BigDecimal interestAmount, LocalDate firstPaymentDate, LocalDate lastPaymentDate) throws SQLException {
        ps.setLong(1, cutomerId);
        ps.setBigDecimal(2, homePrice);
        ps.setBigDecimal(3, initialPayment);
        ps.setBigDecimal(4, creditAmount);
        ps.setBigDecimal(5, interestAmount);
        ps.setDate(6, Date.valueOf(firstPaymentDate));
        ps.setDate(7, Date.valueOf(lastPaymentDate));
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        if ( rs.next()){
            this.creditId=rs.getLong(1);
        }
    }

    //this is for implementation of insert to monthly payment
    public void insertToMonthlyPayment(ResultSet rs, PreparedStatement ps, long creditId, LocalDate paymentDate, BigDecimal baseAmount, BigDecimal interestAmount, BigDecimal totalAmount) throws SQLException{
        ps.setLong(1, creditId);
        ps.setDate(2,Date.valueOf(paymentDate));
        ps.setBigDecimal(3,baseAmount);
        ps.setBigDecimal(4,interestAmount);
        ps.setBigDecimal(5,totalAmount);
        ps.executeUpdate();
        rs= ps.getGeneratedKeys();
        if (rs.next()){
            this.monthlyPaymentId= rs.getLong(1);
        }
    }

    //this method is sql statement for inserting to customer table
    public PreparedStatement getCustomer(Connection connection) throws SQLException {
        String sql = "insert into customer values(CUSTOMER_SEQ.nextval, ?, ?, ? )";
        String generatedColumns[] = { "ID" };
        PreparedStatement ps = connection.prepareStatement(sql,generatedColumns);
        return ps;
    }

    //this method is sql statement for inserting to credit table
    public PreparedStatement getCredit(Connection connection) throws SQLException {
        String sql = "insert into credit(ID, CUSTOMER_ID, HOME_PRICE, INITIAL_PAYMENT, CREDIT_AMOUNT, INTEREST_AMOUNT, FIRST_PAYMENT_DATE, LAST_PAYMENT_DATE) " +
                "values(CREDIT_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)";
        String generatedColumns[] = { "ID" };
        PreparedStatement ps = connection.prepareStatement(sql, generatedColumns);

        return ps;
    }

    //this method is sql statement for inserting to monthly payment table
    public PreparedStatement getMonthlyPayment(Connection connection) throws SQLException{
        String sql ="insert into monthly_payment (ID,CREDIT_ID,PAYMENT_DATE,BASE_AMOUNT, INTEREST_AMOUNT, TOTAL_AMOUNT) values(MONTHLY_PAYMENT_SEQ.nextval,?,?,?,?,?)";
        String generatedColumns[] = { "ID" };
        PreparedStatement ps =connection.prepareStatement(sql,generatedColumns);
        return ps;
    }
}
