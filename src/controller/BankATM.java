/**
* @author Qi Yin
* @ID U31787103
* @description  This is the entrance of the whole project.
*/
package controller;

import view.ChooseIdentity;

public class BankATM {

	public static void main(String args[]) {
		BankController.initBank();
		ChooseIdentity cr = new ChooseIdentity();
	}
}
