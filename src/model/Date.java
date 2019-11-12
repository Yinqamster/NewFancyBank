/**
* @author Group 5
* @description  This is the data structure for date
*/
package model;

public class Date implements Comparable<Date>{
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	public Date(){}
	
	public Date(int month, int day, int year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public Date(int month, int day, int year, int hour, int minute, int second) {
		this(month, day, year);
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMinute() {
		return this.minute;
	}
	
	public int getSecond() {
		return this.second;
	}
	
	
	public String toDayString() {
		String string = "" + month + "/" + day + "/" + year;
		return string;
	}
	
	public String toTimeString() {
		String string = "" + month + "/" + day + "/" + year +" " + hour + ":" + minute + ":" +second;
		return string;
	}

	@Override
	public int compareTo(Date o) {
		// TODO Auto-generated method stub
		if(year > o.getYear() 
				||(year == o.getYear() && month > o.getMonth())
				||(year == o.getYear() && month == o.getMonth() && day > o.getDay())
				||(year == o.getYear() && month == o.getMonth() && day == o.getDay() && hour > o.getHour())
				||(year == o.getYear() && month == o.getMonth() && day == o.getDay() && hour == o.getHour() && minute > o.getMinute())
				||(year == o.getYear() && month == o.getMonth() && day == o.getDay() && hour == o.getHour() && minute == o.getMinute() && second > o.getSecond())) {
			return 1;
		}
		else if(year == o.getYear() && month == o.getMonth() && day == o.getDay() && hour == o.getHour() && minute == o.getMinute() && second == o.getSecond()) {
			return 0;
		}
		else {
			return -1;
		}
	}
	
}
