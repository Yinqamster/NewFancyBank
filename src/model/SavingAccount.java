/**
* @author Group 5
* @description  This is saving account, extends from account
*/
package model;

import java.math.BigDecimal;
import java.util.Map;

public class SavingAccount extends Account{
	public SavingAccount() {
		super();
	}
	public SavingAccount(String accountNumber, Map<String, BigDecimal> balance, Map<String, Transaction> transactionDetails){
		super.setAccountNumber(accountNumber);
		super.setBalance(balance);
		super.setTransactionDetails(transactionDetails);
	}
}
