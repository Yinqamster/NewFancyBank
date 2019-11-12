/**
* @author Group 5
* @description  This is the data structure for the configuration of currency
*/
package model;

import java.math.BigDecimal;

public class CurrencyConfig {

	private BigDecimal serviceChargeRate;
	//per 365 days
	private BigDecimal interestsForSavingAccount;
	//per 365 days
	private BigDecimal interestsForLoan;
	//the lowest balance that can get interest for saving account
	private BigDecimal balanceForInterest;
	
	public CurrencyConfig() {
		
	}
	
	public CurrencyConfig(BigDecimal serviceChargeRate, BigDecimal interestsForSavingAccount,
			BigDecimal interestsForLoan, BigDecimal balanceForInterest) {
		this.serviceChargeRate = serviceChargeRate;
		this.interestsForLoan = interestsForLoan;
		this.interestsForSavingAccount = interestsForSavingAccount;
		this.balanceForInterest = balanceForInterest;
	}
	
	public BigDecimal getServiceChargeRate() {
		return this.serviceChargeRate;
	}
	
	public void setServiceChargeRate(BigDecimal serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}
	
	public BigDecimal getInterestsForSavingAccount() {
		return this.interestsForSavingAccount;
	}
	
	public void setInterestsForSavingAccount(BigDecimal interestsForSavingAccount) {
		this.interestsForSavingAccount = interestsForSavingAccount;
	}
	
	public BigDecimal getInterestsForLoan() {
		return this.interestsForLoan;
	}
	
	public void setInterestForLoan(BigDecimal interestsForLoan) {
		this.interestsForLoan = interestsForLoan;
	}
	
	public BigDecimal getBalanceForInterest() {
		return this.balanceForInterest;
	}
	
	public void setBalanceForInterest(BigDecimal balanceForInterest) {
		this.balanceForInterest = balanceForInterest;
	}
}
