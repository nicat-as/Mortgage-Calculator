# Mortgage Calculator

This app gets mortgage application and  calculates mortgage and insert values to the database.

# Running application
Firstly, let's look at the **`calculation`** folder. In this folder, we have 2 file:
 - `Ipoteka.java`
 - `LoanInfo.java`

In `ipoteka.java` , calculates monthly payment, monthly interest amount, monthly principle and balance.  `LoanInfo.java` is for getting application information from user. This is implemented by `setApplication()` method. 

Second folder is **`database`** folder . In this folder, there are two files:
- `DatabaseOperation.java`
- `JdbcUtill.java`

`DatabaseOperation.java` is for inserting database. `JdbcUtill.java` is for connecting to database. In here, you have to only change database configurations:

    String username="your_username";
    String password ="your_password";
    //if you use oracle database, change only ip address and port
    String jdbcUrl ="jdbc:oracle:thin:@//your_ip:your_port/ORCL";
    // If using oracle, keep it
    String jdbcDriver="oracle.jdbc.driver.OracleDriver";

Following folder is **`exceptions`**. there is  **`valueIncompatible`**  class that check amount limits.
**`sql`** folder contains `*.sql` scripts that is for creating tables in database.
In the end, there is our `Main.java` that we are going to run application and get results. For running application use your java IDE (Intellij IDEA, NetBeans, Eclipse).  

     
