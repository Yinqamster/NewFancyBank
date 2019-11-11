/**
* @author Group 5
* @description  This interface defines functions that user and manager have commonly.
*/
package controller;

import model.Name;

public interface SystemInterface {
	public int register(Name name, int sex, String phoneNum, String email, String birthday, String password, String cPassword);
	public int login(String username, String password);
	public int logout(String username);
	
}
