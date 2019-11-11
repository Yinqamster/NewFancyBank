/**
* @author Group 5
* @description  This is the data structure for transaction
*/
package model;

import java.math.BigDecimal;

import utils.Config;
import utils.UtilFunction;

public class Transaction {
	private int userID;
	private int transactionType;
	private int status;
	private Date date;
	private String fromAccountNumber;
	private String toAccountNumber;
	private String currency;
	private String username;
	private String transactionID;
	private String remarks;
	// for security account, num is the number of stocks purchase/sold
	private BigDecimal num;
	private BigDecimal serviceCharge;
	// for security account, balance is transaction amount (num * unit price)
	private BigDecimal balance;
	
	public Transaction() {
		this.transactionID = UtilFunction.generateTransactionID();
	}
	
	public Transaction(String username, int userID, String currency, BigDecimal num, BigDecimal serviceCharge, BigDecimal balance,
			Date date, String remarks, int transactionType) {
		this();
		this.username = username;
		this.userID = userID;
		this.currency = currency;
		this.num = num;
		this.serviceCharge = serviceCharge;
		this.balance = balance;
		this.date = date;
		this.remarks = remarks;
		this.transactionType = transactionType;
		
	}
	
	public Transaction(String username, int userID, String currency, BigDecimal num, BigDecimal serviceCharge, BigDecimal balance, 
			Date date, String remarks, int transactionType, String from, String to) {
		this(username, userID, currency, num, serviceCharge, balance, date, remarks, transactionType);
		this.fromAccountNumber = from;
		this.toAccountNumber = to;
	}

	public Transaction(String transactionID, String username, int userID, String currency, BigDecimal num, BigDecimal serviceCharge, BigDecimal balance,
					   Date date, String remarks, int transactionType, String from, String to) {
		this.username = username;
		this.userID = userID;
		this.currency = currency;
		this.num = num;
		this.serviceCharge = serviceCharge;
		this.balance = balance;
		this.date = date;
		this.remarks = remarks;
		this.transactionType = transactionType;
		this.transactionID = transactionID;
		this.fromAccountNumber = from;
		this.toAccountNumber = to;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getTransactionType(){
		return this.transactionType;
	}
	
	public String getTransactionTypeStr(){
		switch (this.transactionType) {
			case Config.DEPOSIT:
				return "Deposit";
			case Config.WITHDRAW:
				return "Withdraw";
			case Config.TRANSFEROUT:
				return "Transfer Out";
			case Config.RECEIVE:
				return "Receive";
			case Config.OPENACCOUNT:
				return "Open Account";
			case Config.CLOSEACCOUNT:
				return "Close Account";
			case Config.PAYFORLOAN:
				return "Pay For Loan";
			case Config.SAVINGACCOUNTINTEREST:
				return "Saving Account Interest";
			case Config.BUY:
				return "Stock Buy";
			case Config.SELL:
				return "Stock Sell";
			default:
				return "";
		}
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getTransactionId() {
		return this.transactionID;
	}
	
	public String getFromAccountNumber() {
		return this.fromAccountNumber;
	}
	
	public BigDecimal getServiceCharge() {
		return this.serviceCharge;
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public String getRemarks(){
		return this.remarks;
	}
	
	public void setFromAccountNumber(String from) {
		this.fromAccountNumber = from;
	}
	
	public String getToAccountNumber() {
		return this.toAccountNumber;
	}
	
	public void setToAccountNumber(String to) {
		this.toAccountNumber = to;
	}
	
	public BigDecimal getNum() {
		return this.num;
	}
	
	public void setNum(BigDecimal num) {
		this.num = num;
	}
}
