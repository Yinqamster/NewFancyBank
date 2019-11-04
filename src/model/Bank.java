/**
* @author Qi Yin
* @ID U31787103
* @description  This is the data structure for bank
*/
package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Config;


public class Bank{

	private String username = Config.MANAGERUSERNAME;
	private String password = Config.MANAGERPASSWORD;
	private Map<String, BigDecimal> balance;
	private BigDecimal closeAccountFee;
	private BigDecimal openAccountFee;
	
	//userId, user
	private Map<String, User> userList;
	//currency name, currrency
	private Map<String, Currency> currencyList;
	//account number, user name
	private Map<String, String> accountList;
	//transaction id
	private List<String> transactionIdList;
	
	public Bank(){
		balance = new HashMap<String, BigDecimal>();
		userList = new HashMap<String, User>();
		currencyList = new HashMap<String, Currency>();
		accountList = new HashMap<String, String>();
		transactionIdList = new ArrayList<String>();
		
		balance.put(Config.DEFAULTCURRENCY, new BigDecimal("0"));
		openAccountFee = Config.DEFAULTOPENACCOUNTFEE;
		closeAccountFee = Config.DEFAULTCLOSEACCOUNTFEE;
		
		CurrencyConfig currencyConfig  = new CurrencyConfig(
				Config.DEFAULTSERVICECHARGERATE,
				Config.DEFAULTINTERESTFORLOAN,
				Config.DEFAULTINTERESTSFORSAVINGACCOUNT,
				Config.DEFAULTBALANCEFORINTEREST);
		currencyList.put(Config.DEFAULTCURRENCY, new Currency(Config.DEFAULTCURRENCY, currencyConfig));
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
	
}
