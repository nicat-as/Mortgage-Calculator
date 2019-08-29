import calculation.Ipoteka;
import calculation.LoanInfo;
import exceptions.ValueIncompatible;
import database.DatabaseOperation;
import database.JdbcUtill;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static long customerId;
    static long creditId;
    static long paymentId;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LoanInfo info = new LoanInfo();
        JdbcUtill u = new JdbcUtill();
        DatabaseOperation db = new DatabaseOperation();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isContinue = false;

        try {
            while (!isContinue) {
                //Getting information from customer
                info.setApplication();

                //Calculate mortgage
                Ipoteka ipoteka = new Ipoteka(info.getCreditAmount(), info.getYear(), info.getPercentage());

                //Get connection from database
                connection = u.getConnection();
                System.out.println("Connected!");

                //Inserting information to customer table
                ps = db.getCustomer(connection);
                db.insertToCustomer(rs, ps, info.getFirstname(), info.getLastname(), info.getBirthdate());
                customerId = db.getCustomerId();

                //Inserting information to credit table
                ps = db.getCredit(connection);
                db.insertToCredit(rs, ps, customerId, info.getHomeAmount(), info.getInitialPayment(), info.getCreditAmount(), ipoteka.getTotalInterest(), info.getFirstPayment(), info.getLastPayment());
                creditId = db.getCreditId();

                //Calculating monthly mortgage and inserting to monthly payment table
                ps = db.getMonthlyPayment(connection);
                int c = 0;
                while (c < info.getYear() * 12) {
                    ipoteka.calculate();
                    db.insertToMonthlyPayment(rs, ps, creditId, ipoteka.getPaymentDate(), ipoteka.getPrincipal(), ipoteka.getInterest(), ipoteka.getPayment());
                    c++;
                }

                //Completed!!
                System.out.println("Inserted!\n");

                System.out.print("Do you want to commit? (Y or N): ");
                String word = in.nextLine();
                if (word.equalsIgnoreCase("y")) {
                    u.commit(connection);
                } else if (word.equalsIgnoreCase("n")) {
                    u.rollback(connection);
                }

                System.out.print("Do you want to continue? (Y or N): ");
                String con = in.nextLine();
                if (con.equalsIgnoreCase("y")) {
                    System.out.println();
                } else if (con.equalsIgnoreCase("n")) {
                    isContinue = true;
                }
            }
        } catch (SQLException | ValueIncompatible e) {
            u.rollback(connection);
            e.printStackTrace();
        } finally {
            u.closeResultSet(rs);
            u.closePreparedStatement(ps);
            u.closeConnection(connection);
        }


    }
}
