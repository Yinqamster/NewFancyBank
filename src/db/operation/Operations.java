/*
 * @Author: Group 5
 * @Description: This class includes some database operation
 */
package db.operation;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.*;
import model.Date;
import utils.*;
import view.Transact;

public class Operations {
    // databse file path
    public static final String url = "jdbc:sqlite:src/db/atm.db";
    public static final String JDBCName = "org.sqlite.JDBC";

    // call this method firstly
    public static boolean testDBConnection() {
        if (getConnection() != null) {
            System.out.println("Connect to DB successfully");
            return true;
        } else return false;
    }

    public static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName(JDBCName);
            c = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(ErrCode.errCodeToStr(601));
        }
        return c;
    }

    // initiate data from database to memory
//    public static void initDataFromDB(){
//        // get connection
//        Connection c = getConnection();
//        try {
//
//        }catch (SQLException e){
//            System.err.println(ErrCode.errCodeToStr(602));
//        }
//    }

    // =============================== load data from DB =======================================
    public static HashMap<String, User> getUserListFromDB() {
        Connection c = getConnection();
        HashMap<String, User> userList = new HashMap<>();

        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM userList;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                int userID = rs.getInt("userID");
                String userName = rs.getString("nickName");
                String mname = rs.getString("middleName");
                Name name;
                if (mname == null) {
                    name = new Name(rs.getString("firstName"), rs.getString("lastName"), rs.getString("nickName"));
                } else {
                    name = new Name(rs.getString("firstName"), rs.getString("middleName"),
                            rs.getString("lastName"), rs.getString("nickName"));
                }
                int sex = rs.getInt("sex");
                long phoneNumber = rs.getLong("phoneNumber");
                String email = rs.getString("email");
                Date dob = UtilFunction.stringToDate(rs.getString("dob"));
                String password = rs.getString("password");
                User user = new User(name, sex, phoneNumber, email, dob, password);

                // add accounts for this user
                user.setAccounts(getAccountsByUserName(userName));

                //add loans for this user
                user.setLoanList(getLoanMap(userID));

                // add to userList
                userList.put(userName, user);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETUSERLISTFAIL));
        }
        return userList;
    }

    public static Map<String, Account> getAccountsByUserName(String userName) {
        int userID = getUserIDFromDB(userName);
        Connection c = getConnection();
        Map<String, Account> accountList = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Accounts WHERE userID=" + userID + ";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String accountNumber = rs.getString("accountNumber");
                int accountType = rs.getInt("accountType");

                // get balance map for this account
                Map<String, BigDecimal> balanceMap = getBalanceMap(accountNumber);

                // add transactions for this account
                Map<String, Transaction> transactionMap = getTransactionMap(accountNumber);

                // new acount
                Account account;
                switch (accountType) {
                    case 1:
                        account = new CheckingAccount(accountNumber, balanceMap, transactionMap);
                        break;
                    case 2:
                        account = new SavingAccount(accountNumber, balanceMap, transactionMap);
                        break;
                    case 3: {
                        account = new SecurityAccount(accountNumber, getHoldingStock(accountNumber), transactionMap);
                        break;
                    }
                    default:
                        account = new Account(accountNumber, balanceMap, transactionMap);
                }
                // add to accountList
                accountList.put(accountNumber, account);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETACCOUNTMAPFAIL));
        }
        return accountList;
    }

    public static HashMap<String, Currency> getCurrencyListFromDB() {
        Connection c = getConnection();
        HashMap<String, Currency> currencyList = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Currency WHERE status=2;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                BigDecimal serviceChargeRate = rs.getBigDecimal("serviceChargeRate");
                BigDecimal interestsForSavingAccount = rs.getBigDecimal("interestsForSavingAccount");
                BigDecimal interestsForLoan = rs.getBigDecimal("interestsForLoan");
                BigDecimal balanceForInterest = rs.getBigDecimal("balanceForInterest");
                CurrencyConfig currencyConfig = new CurrencyConfig(
                        serviceChargeRate,
                        interestsForSavingAccount,
                        interestsForLoan,
                        balanceForInterest);
                String currencyName = rs.getString("currencyName");
                int status = rs.getInt("status");

                Currency currency = new Currency(currencyName, currencyConfig);
                // add to currencyList
                currencyList.put(currencyName, currency);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return currencyList;
    }

    public static int getUserIDFromDB(String userName) {
        int userID = -1;
        Connection c = getConnection();
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM Names WHERE nickName='" + userName + "';";
            ResultSet rs = stmt.executeQuery(sql);
            userID = rs.getInt("userID");
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            return userID;
        } finally {
            return userID;
        }
    }

    public static Map<String, BigDecimal> getBalanceMap(String accountNumber) {
        Connection c = getConnection();
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM balanceList WHERE accountNumber='" + accountNumber + "';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String currencyName = rs.getString("currency");
                BigDecimal amount = rs.getBigDecimal("amount");

                // add to balanceMap
                balanceMap.put(currencyName, amount);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETBALANCEMAPFAIL));
        }
        return balanceMap;
    }

    public static Map<String, Transaction> getTransactionMap(String accountNumber) {
        Connection c = getConnection();
        Map<String, Transaction> transactionMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM transacitonList WHERE (fromAccountNumber='" + accountNumber + "'AND (transactionType<>4 AND transactionType<>9)) OR (toAccountNumber='" + accountNumber + "' AND (transactionType<>3 AND transactionType<>10));";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String transactionID = rs.getString("transactionID");
                String username = rs.getString("nickName");
                int userID = rs.getInt("userID");
                String currency = rs.getString("currency");
                BigDecimal num = rs.getBigDecimal("num");
                BigDecimal serviceCharge = rs.getBigDecimal("serviceCharge");
                BigDecimal balance = rs.getBigDecimal("balance");
                Date datetime = UtilFunction.stringToTime(rs.getString("date"));
                String remarks = rs.getString("remarks");
                int transactionType = rs.getInt("transactionType");
                String from = rs.getString("fromAccountNumber");
                String to = rs.getString("toAccountNumber");

                Transaction transaction = new Transaction(transactionID, username, userID, currency, num, serviceCharge, balance, datetime, remarks, transactionType, from, to);

                // add to transactionMap
                transactionMap.put(transactionID, transaction);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETTRANSACNTIONFAIL));
        }
        return transactionMap;
    }

    public static Map<String, Loan> getLoanMap(int userID) {
        Connection c = getConnection();
        Map<String, Loan> LoanMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Loans WHERE userID=" + userID + " AND status<>3;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String name = rs.getString("name");
                String collateral = rs.getString("collateral");
                String currency = rs.getString("currency");
                BigDecimal number = rs.getBigDecimal("number");
                Date startDate = UtilFunction.stringToDate(rs.getString("startDate"));
                Date dueDate = UtilFunction.stringToDate(rs.getString("dueDate"));
                int status = rs.getInt("status");

                // new Loan
                Loan loan = new Loan(name, collateral, currency, number, startDate, dueDate, status);

                // add to transactionMap
                LoanMap.put(name, loan);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return LoanMap;
    }

    public static Map<String, String> getAccountMapFromDB() {
        Connection c = getConnection();
        Map<String, String> AccountMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM accountList;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String accountNumber = rs.getString("accountNumber");
                String userName = rs.getString("nickname");

                // add to AccountMap
                AccountMap.put(accountNumber, userName);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return AccountMap;
    }

    public static List<String> getTransactionIdList() {
        Connection c = getConnection();
        List<String> TransactionIdList = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Transactions;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String ID = rs.getString("transactionID");
                // add to transactionMap
                TransactionIdList.add(ID);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return TransactionIdList;
    }

    public static Map<String, HoldingStock> getHoldingStock(String accountNumber) {
        Connection c = getConnection();
        Map<String, HoldingStock> holdingStockHashMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM HoldingStocks WHERE accountNumber='" + accountNumber + "' AND number>0;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String stockRecordID = rs.getString("stockRecordID");
                String company = rs.getString("company");
                BigDecimal buyInPrice = rs.getBigDecimal("buyInPrice");
                BigDecimal number = rs.getBigDecimal("number");

                HoldingStock holdingStock = new HoldingStock(company, buyInPrice, number);

                // add to holdingStockHashMap
                holdingStockHashMap.put(stockRecordID, holdingStock);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETHOLDINGSTOCKFAIL));
        }
        return holdingStockHashMap;
    }

    public static Map<String, Stock> getStockMapFromDB() {
        Connection c = getConnection();
        Map<String, Stock> stockMarketMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Stocks;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String company = rs.getString("company");
                BigDecimal unitPrice = rs.getBigDecimal("unitPrice");
                int soldCount = rs.getInt("soldCount");

                Stock stock = new Stock(company, unitPrice, soldCount);

                // add to stockMarketMap
                stockMarketMap.put(company, stock);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETSTOCKMARKETFAIL));
        }
        return stockMarketMap;
    }

    public static Map<String, BigDecimal> getManagerBalanceMapFromDB() {
        Connection c = getConnection();
        Map<String, BigDecimal> ManagerBalanceMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Balances WHERE isManager=1;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String currency = rs.getString("currency");
                BigDecimal amount = rs.getBigDecimal("amount");

                // add to ManagerBalanceMap
                ManagerBalanceMap.put(currency, amount);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETMANAGERBALANCEFAIL));
        }
        return ManagerBalanceMap;
    }

    // ============================ insert/update data to DB ==================================

    public static void addUserToDB(User user) {
        // prepare data
        int userID = user.getID();
        Name name = user.getName();
        String nickName = name.getNickName();
        String firstName = name.getFirstName();
        String middleName = name.getMiddleName();
        String lastName = name.getLastName();

        String sql = "INSERT INTO Names(nickName,firstName,middleName,lastName,userID) VALUES(?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nickName);
            pstmt.setString(2, firstName);
            pstmt.setString(3, middleName);
            pstmt.setString(4, lastName);
            pstmt.setInt(5, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.INSERTNAMEFAIL));
        }

        int sex = user.getSex();
        Long phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String dob = user.getDob().toDayString();
        String password = user.getPassword();

        sql = "INSERT INTO Users(userID,sex,phoneNumber,email,dob,password) VALUES(?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userID);
            pstmt.setInt(2, sex);
            pstmt.setLong(3, phoneNumber);
            pstmt.setString(4, email);
            pstmt.setString(5, dob);
            pstmt.setString(6, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.INSERTUSERFAIL));
        }
        System.out.println("Add user to DB succeed.");
    }

    public static void deposit(String accountNumber, BigDecimal newUserBalance, BigDecimal newManagerBalance, String currency, Transaction transaction) {
        // update balance
        // user balance
        balanceUpdateDB(accountNumber, newUserBalance, currency, false);

        //manager balance
        balanceUpdateDB("", newManagerBalance, currency, true);

        // add transaction
        addTransactionToDB(transaction);
    }

    public static void balanceUpdateDB(String accountNumber, BigDecimal newBalance, String currency, boolean isManager) {
        if (!isManager) {
            // user balance
            String sql = "INSERT OR REPLACE INTO Balances(currency, amount, accountNumber , isManager) VALUES(?,?,?,0);";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currency);
                pstmt.setBigDecimal(2, newBalance);
                pstmt.setString(3, accountNumber);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(ErrCode.errCodeToStr(ErrCode.UPDATEBALANCEFAIL));
            }
        } else {
            //manager balance
            String sql = "INSERT OR REPLACE INTO Balances(currency, amount, accountNumber , isManager) VALUES(?,?,?,1);";

            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, currency);
                pstmt.setBigDecimal(2, newBalance);
                pstmt.setString(3, "");
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println(ErrCode.errCodeToStr(ErrCode.UPDATEBALANCEFAIL));
            }
        }
    }

    public static void addTransactionToDB(Transaction transaction) {
        // prepare data
        String transactionID = transaction.getTransactionId();
        int transactionType = transaction.getTransactionType();
        String date = transaction.getDate().toTimeString();
        String fromAccountNumber = transaction.getFromAccountNumber();
        String toAccountNumber = transaction.getToAccountNumber();
        String currency = transaction.getCurrency();
        String remarks = transaction.getRemarks();
        BigDecimal num = transaction.getNum();
        BigDecimal serviceCharge = transaction.getServiceCharge();
        BigDecimal balance = transaction.getBalance();

        String sql = "INSERT INTO Transactions(transactionID,transactionType,date,fromAccountNumber,toAccountNumber,currency,remarks,num,serviceCharge,balance) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transactionID);
            pstmt.setInt(2, transactionType);
            pstmt.setString(3, date);
            pstmt.setString(4, fromAccountNumber);
            pstmt.setString(5, toAccountNumber);
            pstmt.setString(6, currency);
            pstmt.setString(7, remarks);
            pstmt.setBigDecimal(8, num);
            pstmt.setBigDecimal(9, serviceCharge);
            pstmt.setBigDecimal(10, balance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.ADDTRANSACTIONFAIL));
        }
    }

    public static void withdraw(String accountNumber, BigDecimal newUserBalance, BigDecimal newManagerBalance, String currency, Transaction transaction) {
        // update balance
        // user balance
        balanceUpdateDB(accountNumber, newUserBalance, currency, false);

        //manager balance
        balanceUpdateDB("", newManagerBalance, currency, true);

        // add transaction
        addTransactionToDB(transaction);
    }

    public static void sendMoney(String accountNumber, BigDecimal newUserBalance, BigDecimal newManagerBalance, String currency, Transaction transaction) {
        // update balance
        // user balance
        balanceUpdateDB(accountNumber, newUserBalance, currency, false);

        //manager balance
        balanceUpdateDB("", newManagerBalance, currency, true);

        // add transaction
        addTransactionToDB(transaction);
    }

    public static void receiveMoney(String accountNumber, BigDecimal newUserBalance, BigDecimal newManagerBalance, String currency, Transaction transaction) {
        // update balance
        // user balance
        balanceUpdateDB(accountNumber, newUserBalance, currency, false);

        //manager balance
        balanceUpdateDB("", newManagerBalance, currency, true);

        // add transaction
        addTransactionToDB(transaction);
    }

    public static void addAccountToDB(Account account, User user, int accountType, Transaction t, BigDecimal newUserBalance, BigDecimal newManagerBalance, String currency) {
        // prepare dataManager
        String accountNumber = account.getAccountNumber();
        int userID = user.getID();

        String sql = "INSERT INTO Accounts(accountNumber,accountType,userID) VALUES(?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, accountNumber);
            pstmt.setInt(2, accountType);
            pstmt.setInt(3, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.INSERTACCOUNTFAIL));
        }
        System.out.println("Add Account to DB succeed.");

        // user balance
        balanceUpdateDB(accountNumber, newUserBalance, currency, false);

        //manager balance
        balanceUpdateDB("", newManagerBalance, currency, true);

        // add transaction
        addTransactionToDB(t);
    }

    public static void addLoanToDB(Loan loan, User user){
        // prepare dataManager
        String loanName = loan.getName();
        String currency = loan.getCurrency();
        BigDecimal number = loan.getNumber();
        String startDate = loan.getStartDate().toDayString();
        String dueDate = loan.getDueDate().toDayString();
        String collateral = loan.getCollateral();
        int status = loan.getStatus();
        int userID = user.getID();

        String sql = "INSERT INTO Loans(name, currency, number, startDate, dueDate, collateral, status, userID) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loanName);
            pstmt.setString(2, currency);
            pstmt.setBigDecimal(3, number);
            pstmt.setString(4, startDate);
            pstmt.setString(5, dueDate);
            pstmt.setString(6, collateral);
            pstmt.setInt(7, status);
            pstmt.setInt(8, userID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fail to add loan");
        }
        System.out.println("Add Loan to DB succeed.");
    }

    public static void payLoan(Loan loan, User user, String payAccountNumber, BigDecimal newUserBalance, BigDecimal newManagerBalance){
        int status = Config.PAIED;
        int userID = user.getID();
        String currency = loan.getCurrency();

        // update loan status
        // user balance
        String sql = "UPDATE Loans SET status=? WHERE userID=? AND currency=?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setInt(2, userID);
            pstmt.setString(3, currency);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.UPDATELOANSTATUSFAIL));
        }

        // update balance for certain account
        balanceUpdateDB(payAccountNumber,newUserBalance,currency,false);

        // update balance for manager
        balanceUpdateDB("",newManagerBalance,currency,true);

    }

    public static void buyStock(HoldingStock holdingStock, String stockRecordID, String currency, SavingAccount savingAccount, SecurityAccount securityAccount,Transaction tSaving, Transaction tSecurity, BigDecimal newManagerBalance){
        // add holdingStock
        String company = holdingStock.getCompanyName();
        BigDecimal buyInPrice = holdingStock.getBuyInPirce();
        BigDecimal number = holdingStock.getNumber();
        String securityAccountNumber = securityAccount.getAccountNumber();

        String sql = "INSERT INTO HoldingStocks(stockRecordID, company, buyInPrice, number, accountNumber) VALUES(?,?,?,?,?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockRecordID);
            pstmt.setString(2, company);
            pstmt.setBigDecimal(3, buyInPrice);
            pstmt.setBigDecimal(4, number);
            pstmt.setString(5, securityAccountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(ErrCode.errCodeToStr(ErrCode.INSERTHOLDINGSTOCKFAIL));
        }

        //update stock sold count
        updatestockSoldCount(company,number);

        // update balance for saving account
        String savingAccountNumber = savingAccount.getAccountNumber();
        BigDecimal newUserBalance = savingAccount.getBalance().get(currency);
        balanceUpdateDB(savingAccountNumber,newUserBalance,currency,false);

        // add transaction for saving account
        addTransactionToDB(tSaving);
        // add transaction for security account
        addTransactionToDB(tSecurity);

        // update balance for manager
        balanceUpdateDB("",newManagerBalance,currency,true);
    }

    public static void sellStock(HoldingStock holdingStock, String stockRecordID, String currency, SavingAccount savingAccount, SecurityAccount securityAccount,Transaction tSaving, Transaction tSecurity, BigDecimal newManagerBalance){
        // update holdingStock
        String company = holdingStock.getCompanyName();
        BigDecimal number = holdingStock.getNumber();

        String sql = "UPDATE HoldingStocks SET number=? WHERE stockRecordID=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, number);
            pstmt.setString(2, stockRecordID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fail to update holding stock");
        }

        if(number.equals(new BigDecimal("0"))){
            deleteHoldingStock(stockRecordID);
        }

        //update stock sold count
        updatestockSoldCount(company,number.multiply(new BigDecimal(-1)).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));

        // update balance for saving account
        String savingAccountNumber = savingAccount.getAccountNumber();
        BigDecimal newUserBalance = savingAccount.getBalance().get(currency);
        balanceUpdateDB(savingAccountNumber,newUserBalance,currency,false);

        // add transaction for saving account
        addTransactionToDB(tSaving);
        // add transaction for security account
        addTransactionToDB(tSecurity);

        // update balance for manager
        balanceUpdateDB("",newManagerBalance,currency,true);
    }

    public static void updatestockSoldCount(String company, BigDecimal number){
        String sql = "UPDATE Stocks SET soldCount=soldCount+? WHERE company=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBigDecimal(1, number);
            pstmt.setString(2, company);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update sold stock count fail.");
        }
    }

    public static void updateLoanStatus(Loan loan){
        int status = loan.getStatus();
        String loanName = loan.getName();
        String sql = "UPDATE Loans SET status=? WHERE name=? AND status=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, status);
            pstmt.setString(2, loanName);
            pstmt.setInt(3, Config.PROCESSING);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Update sold Loan Status Fail.");
        }
    }

    public static void updateOrInsertStock(Stock stock, int soldCount){
        String company = stock.getCompany();
        BigDecimal unitPrice = stock.getUnitPrice();

        String sql = "INSERT OR REPLACE INTO Stocks(company, unitPrice, soldCount) VALUES(?,?,?);";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company);
            pstmt.setBigDecimal(2, unitPrice);
            pstmt.setInt(3, soldCount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fail to update or insert stock");
        }
    }

    public static void deleteHoldingStock(String stockRecordID){
        String sql = "DELETE FROM HoldingStocks WHERE stockRecordID=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, stockRecordID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fail to delete holding stock");
        }
    }

    public static void deleteStock(String company){
        String sql = "DELETE FROM Stocks WHERE company=?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, company);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Fail to delete stock");
        }

    }
}