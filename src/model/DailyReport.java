/**
* @author Group 5
* @description  This is the data structure for daily report
*/
package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DailyReport {

	private int userNumber;
	private int openAccountNum;
	private int transactionNum;
	//currency name, number
	private Map<String, BigDecimal> currencyIn;
	//currency name, number
	private Map<String, BigDecimal> currencyOut;
	//currency name, number
	private Map<String, BigDecimal> serviceCharge;
	private List<Transaction> transactions;
	
	public DailyReport() {
		userNumber = 0;
		openAccountNum = 0;
		transactionNum = 0;
		currencyIn = new HashMap<String, BigDecimal>();
		currencyOut = new HashMap<String, BigDecimal>();
		serviceCharge = new HashMap<String, BigDecimal>();
		transactions = new ArrayList<Transaction>();
	}
	
	public int getOpenAccountNum() {
		return this.openAccountNum;
	}
	
	public void setOpenAccountNum(int n) {
		this.openAccountNum = n;
	}
	
	public int getTransactionNum() {
		return this.transactionNum;
	}
	
	public void setTransactionNum(int n) {
		this.transactionNum = n;
	}
	
	public int getUserNumber() {
		return this.userNumber;
	}
	
	public void setUserNumber(int n) {
		this.userNumber = n;
	}
	
	public Map<String, BigDecimal> getCurrencyIn() {
		return this.currencyIn;
	}
	
	public Map<String, BigDecimal> getCurrencyOut() {
		return this.currencyOut;
	}
	
	public Map<String, BigDecimal> getServiceCharge() {
		return this.serviceCharge;
	}
	
	public List<Transaction> getTransactions() {
		return this.transactions;
	}
	
	
	
}
