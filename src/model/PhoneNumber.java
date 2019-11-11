/**
* @author Group 5
* @description  This is the data structure for phone number
*/
package model;

public class PhoneNumber {

	private String region;
	private int regionCode;
	private long phoneNumber;
	private int type;
	
	public PhoneNumber() {
		
	}
	
	public PhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public long getPhoneNumber() {
		return this.phoneNumber;
	}
}
