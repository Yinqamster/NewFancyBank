/**
* @author Group 5
* @description  This is the data structure for person
*/
package model;

public class Person {
	private Name name;
	private PhoneNumber phoneNumber;
	private String email;
	private Date dob;
	private int sex;
	private int ID;
	
	public Person() {}
	public Person(Name name, long phoneNumber, String email, Date dob, int sex) {
		this.name = name;
		this.phoneNumber = new PhoneNumber(phoneNumber);
		this.email = email;
		this.dob = dob;
		this.sex = sex;
	}
	
	public Name getName() {
		return this.name;
	}
	
	public void setName(Name name) {
		this.name = name;
	}
	
	public int getSex() {
		return this.sex;
	}
	
	public  void setSex(int sex) {
		this.sex = sex;
	}
	
	public long getPhoneNumber() {
		return phoneNumber.getPhoneNumber();
	}
	
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = new PhoneNumber(phoneNumber);
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDob() {
		return this.dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
}
