/**
* @author Group 5
* @description  This is the entrance of the whole project.
*/
package controller;

import db.operation.Operations;
import view.ChooseIdentity;

public class BankATM {

	public static void main(String[] args) {
		Operations.testDBConnection();
		BankController.initBank();
		ChooseIdentity cr = new ChooseIdentity();
	}
}
