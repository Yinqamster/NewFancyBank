/**
 * @author Group
 * @description This is the data structure for bank
 */
package model;

import java.math.BigDecimal;
import java.util.*;

import db.operation.Operations;

import utils.Config;


public class Bank {

    private String username = Config.MANAGERUSERNAME;
    private String password = Config.MANAGERPASSWORD;
    private Map<String, BigDecimal> balance;
    private BigDecimal closeAccountFee;
    private BigDecimal openAccountFee;
    // for security account and stock transaction
    private BigDecimal stockTransactionFee;
    private BigDecimal securityAccountThreshold;

    //userId, user
    private Map<String, User> userList;
    //currency name, currrency
    private Map<String, Currency> currencyList;
    //account number, user name
    private Map<String, String> accountList;
    //transaction id
    private List<String> transactionIdList;
    //stocks
    private Map<String, Stock> stockMap;

    public Bank() {
        balance = Operations.getManagerBalanceMapFromDB();
        userList = Operations.getUserListFromDB(); // get userList from database, including accouts and loans for every user
        currencyList = Operations.getCurrencyListFromDB(); // get currencyList from database
        accountList = Operations.getAccountMapFromDB();
        transactionIdList = Operations.getTransactionIdList();
        // added for security account and stock
        stockMap = Operations.getStockMapFromDB();

        // make sure every currency can be managed
        if(balance.size() != currencyList.size()){
            Set<String> currencyNameList = currencyList.keySet();
            for(String name : currencyNameList){
                if(balance.get(name)==null){
                    balance.put(name, new BigDecimal("0"));
                }
            }
        }

        //fees
        openAccountFee = Config.DEFAULTOPENACCOUNTFEE;
        closeAccountFee = Config.DEFAULTCLOSEACCOUNTFEE;
        // for security account and stock transaction
        stockTransactionFee = Config.DEFAULTSTOCKCHARGERATE;
        securityAccountThreshold = Config.DEFAULTSECURITYTHRESHOLD;

//		CurrencyConfig currencyConfig  = new CurrencyConfig(
//				Config.DEFAULTSERVICECHARGERATE,
//				Config.DEFAULTINTERESTFORLOAN,
//				Config.DEFAULTINTERESTSFORSAVINGACCOUNT,
//				Config.DEFAULTBALANCEFORINTEREST);
//		currencyList.put(Config.DEFAULTCURRENCY, new Currency(Config.DEFAULTCURRENCY, currencyConfig));
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }


    public Map<String, BigDecimal> getBalance() {
        return this.balance;
    }

    public void setBalance(Map<String, BigDecimal> balance) {
        this.balance = balance;
    }

    public BigDecimal getOpenAccountFee() {
        return this.openAccountFee;
    }

    public void setOpenAccountFee(BigDecimal open) {
        this.openAccountFee = open;
    }

    public BigDecimal getCloseAccountFee() {
        return this.closeAccountFee;
    }

    public void setCloseAccountFee(BigDecimal close) {
        this.closeAccountFee = close;
    }

    public BigDecimal getStockTransactionFee() {
        return stockTransactionFee;
    }

    public void setStockTransactionFee(BigDecimal stockTransactionFee) {
        this.stockTransactionFee = stockTransactionFee;
    }

    public BigDecimal getSecurityAccountThreshold() {
        return securityAccountThreshold;
    }

    public void setSecurityAccountThreshold(BigDecimal securityAccountThreshold) {
        this.securityAccountThreshold = securityAccountThreshold;
    }

    public Map<String, User> getUserList() {
        return this.userList;
    }

    public void addUser(String username, User user) {
        userList.put(username, user);
    }

    public Map<String, Currency> getCurrencyList() {
        return this.currencyList;
    }

    public void addCurrency(String currencyName, Currency currency) {
        currencyList.put(currencyName, currency);
    }

    public Map<String, String> getAccountList() {
        return this.accountList;
    }

    public void addAccount(String accountNumber, String username) {
        this.accountList.put(accountNumber, username);
    }

    public List<String> getTransactionIdList() {
        return this.transactionIdList;
    }

    public void addTransactionId(String transactionId) {
        this.transactionIdList.add(transactionId);
    }

    public boolean addStock(Stock stock) {
        if (stockMap.containsKey(stock.getCompany()))
            return false;
        else
            stockMap.put(stock.getCompany(), stock);
        return true;
    }

    public Map<String, Stock> getStockMap() {
        return stockMap;
    }

    public void setStockMap(Map<String, Stock> stockMap) {
        this.stockMap = stockMap;
    }
}
