/**
* @author Group 5
* @description  This is checking account, extends from account
*/
package model;

import java.math.BigDecimal;
import java.util.Map;

public class CheckingAccount extends Account{
	public CheckingAccount() {
		super();
	}
	public CheckingAccount(String accountNumber, Map<String, BigDecimal> balance, Map<String, Transaction> transactionDetails){
		super.setAccountNumber(accountNumber);
		super.setBalance(balance);
		super.setTransactionDetails(transactionDetails);
	}
}
