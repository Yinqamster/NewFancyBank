/*
 * @Author: your name
 * @Date: 2019-11-05 20:14:51
 * @LastEditTime: 2019-11-07 23:27:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \NewFancyBank\src\db\operation\Tools.java
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

public class Operations {
    // databse file path
    public static final String url = "jdbc:sqlite:src/db/atm.db";
    public static final String JDBCName = "org.sqlite.JDBC";

    // call this method firstly
    public static boolean testDBConnection(){
        if(getConnection() != null) {
            System.out.println("Connect to DB successfully");
            return true;}
        else return false;
    }

    public static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName(JDBCName);
            c = DriverManager.getConnection(url);
        } catch ( Exception e ) {
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

    public static HashMap<String, User> getUserListFromDB(){
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
                if(mname == null){
                    name = new Name(rs.getString("firstName"), rs.getString("lastName"), rs.getString("nickName"));
                }
                else {
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
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETUSERLISTFAIL));
        }
        return userList;
    }

    public static Map<String, Account> getAccountsByUserName(String userName){
        int userID = getUserIDFromDB(userName);
        Connection c = getConnection();
        Map<String, Account> accountList = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Accounts WHERE userID="+userID+";";
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
                    case 1: account = new CheckingAccount(accountNumber, balanceMap, transactionMap);break;
                    case 2: account = new SavingAccount(accountNumber, balanceMap, transactionMap);break;
                    case 3: {
                        account = new SecurityAccount(accountNumber, getHoldingStock(accountNumber), transactionMap);
                        break;
                    }
                    default: account = new Account(accountNumber, balanceMap, transactionMap);
                }
                // add to accountList
                accountList.put(accountNumber, account);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETACCOUNTMAPFAIL));
        }
        return accountList;
    }

    public static HashMap<String, Currency> getCurrencyListFromDB(){
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
                CurrencyConfig currencyConfig  = new CurrencyConfig(
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
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return currencyList;
    }

    public static int getUserIDFromDB(String userName){
        int userID = -1;
        Connection c = getConnection();
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM Names WHERE nickName='" + userName +"';";
            ResultSet rs = stmt.executeQuery(sql);
            userID = rs.getInt("userID");
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return userID;
        }
    }

    public static Map<String, BigDecimal> getBalanceMap(String accountNumber){
        Connection c = getConnection();
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM balanceList WHERE accountNumber='"+accountNumber+"';";
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
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETBALANCEMAPFAIL));
        }
        return balanceMap;
    }

    public static Map<String, Transaction> getTransactionMap(String accountNumber){
        Connection c = getConnection();
        Map<String, Transaction> transactionMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM transactionList WHERE accountNumber='"+accountNumber+"';";
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

                Transaction transaction = new Transaction(transactionID,username,userID,currency,num,serviceCharge,balance,datetime,remarks,transactionType,from,to);

                // add to transactionMap
                transactionMap.put(transactionID, transaction);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETTRANSACNTIONFAIL));
        }
        return transactionMap;
    }

    public static Map<String, Loan> getLoanMap(int userID){
        Connection c = getConnection();
        Map<String, Loan> LoanMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM Loans WHERE userID="+userID+";";
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
                Loan loan = new Loan(name,collateral,currency,number,startDate,dueDate,status);

                // add to transactionMap
                LoanMap.put(name, loan);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return LoanMap;
    }

    public static Map<String,String> getAccountMapFromDB(){
        Connection c = getConnection();
        Map<String,String> AccountMap = new HashMap<>();
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
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return AccountMap;
    }

    public static List<String> getTransactionIdList(){
        Connection c = getConnection();
        List<String> TransactionIdList = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM transactionList;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String ID = rs.getString("transactionID");
                // add to transactionMap
                TransactionIdList.add(ID);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return TransactionIdList;
    }

    public static Map<String, HoldingStock> getHoldingStock(String accountNumber){
        Connection c = getConnection();
        Map<String, HoldingStock> holdingStockHashMap = new HashMap<>();
        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM HoldingStocks WHERE accountNumber='"+accountNumber+"';";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String company = rs.getString("company");
                BigDecimal buyInPrice = rs.getBigDecimal("buyInPrice");
                BigDecimal number = rs.getBigDecimal("number");

                HoldingStock holdingStock = new HoldingStock(company,buyInPrice,number);

                // add to holdingStockHashMap
                holdingStockHashMap.put(company, holdingStock);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETHOLDINGSTOCKFAIL));
        }
        return holdingStockHashMap;
    }

    public static Map<String, Stock> getStockMapFromDB(){
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

                Stock stock = new Stock(company,unitPrice,soldCount);

                // add to stockMarketMap
                stockMarketMap.put(company, stock);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(ErrCode.GETSTOCKMARKETFAIL));
        }
        return stockMarketMap;
    }
}