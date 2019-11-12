/**
* @author Group 5
* @description  This is the data structure for loan
*/
package model;

import java.math.BigDecimal;

public class Loan {
	String name;
	String currency;
	BigDecimal number;
	Date startDate;
	Date dueDate;
	String collateral;
	int status;
	
	public Loan() {
		
	}
	
	public Loan(String name, String collateral, String currency, BigDecimal number, Date startDate, Date dueDate, int status) {
		this.name = name;
		this.collateral = collateral;
		this.currency = currency;
		this.number = number;
		this.startDate = startDate;
		this.dueDate = dueDate;
		this.status = status;
	}
	
	public String getName() {
		return this.name;
	}
	
	public BigDecimal getNumber() {
		return this.number;
	}
	
	public String getCollateral() {
		return this.collateral;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	
	public Date getDueDate() {
		return this.dueDate;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
