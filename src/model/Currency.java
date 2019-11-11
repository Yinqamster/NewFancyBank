/**
* @author Group 5
* @description  This is the data structure for currency
*/
package model;

public class Currency {

	private String currencyName;
	private int status;
	private CurrencyConfig config;
	
	public Currency(){
	}
	
	public Currency(String name) {
		this.currencyName = name;
	}
	
	public Currency(String name, CurrencyConfig currencyConfig) {
		this(name);
		this.config = currencyConfig;
	}
	
	public  String getCurrencyName() {
		return this.currencyName;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public CurrencyConfig getConfig() {
		return this.config;
	}
	
	public void setConfig(CurrencyConfig config) {
		this.config = config;
	}
}
