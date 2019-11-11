/**
* @author Group 5
* @description  This is the configuration of the project.
*/
package utils;

import java.math.BigDecimal;

public class Config {

	public static final String USER = "User";
	public static final String MANAGER = "Manager";
	
	public static final String MANAGERUSERNAME = "Admin";
	public static final String MANAGERPASSWORD = "admin";
	
	//account type
	public static final int CHECKINGACCOUNT = 1;
	public static final int SAVINGACCOUNT = 2;
	public static final int SECURITYACCOUNT = 3;
	
	//log status
	public static final int NOTLOGGEDIN = 1;
	public static final int LOGGEDIN = 2;
	
	//loan status
	public static final int PROCESSING = 1;
	public static final int PASSED = 2;
	public static final int PAIED = 3;
	
	//transaction type
	public static final int DEPOSIT = 1;
	public static final int WITHDRAW = 2;
	public static final int TRANSFEROUT = 3;
	public static final int RECEIVE = 4;
	public static final int OPENACCOUNT = 5;
	public static final int CLOSEACCOUNT = 6;
	public static final int PAYFORLOAN = 7;
	public static final int SAVINGACCOUNTINTEREST = 8;
	//stock transaction type
	public static final int BUY = 9;
	public static final int SELL = 10;
	
	
	//default config
	public static final String DEFAULTCURRENCY = "USD";
	public static final BigDecimal DEFAULTSERVICECHARGERATE = new BigDecimal("0.01");
	public static final BigDecimal DEFAULTINTERESTSFORSAVINGACCOUNT = new BigDecimal("0.03");
	public static final BigDecimal DEFAULTINTERESTFORLOAN = new BigDecimal("0.1");
	public static final BigDecimal DEFAULTBALANCEFORINTEREST = new BigDecimal("500");
	public static final BigDecimal DEFAULTOPENACCOUNTFEE = new BigDecimal("10");
	public static final BigDecimal DEFAULTCLOSEACCOUNTFEE = new BigDecimal("50");
	// for security account and stock transaction
	public static final BigDecimal DEFAULTSECURITYTHRESHOLD = new BigDecimal("500");
	public static final BigDecimal DEFAULTSTOCKCHARGERATE = new BigDecimal("0.05");


	
	//number length
	public static final int ACCOUNTNUMBERLENGTH = 12;
	public static final int TRANSACTIONIDLENGTH = 10;
	
	
	//currency status
	public static final int DISABLE = 1;
	public static final int ENABLE = 2;
	
	//sort
	public static final int SORTBYID = 1;
	public static final int SORTBYLOANSIZE = 2;
	public static final int SORTBYACCOUNTSIZE = 3;
	//sort order
	public static final int DESC = 1;
	public static final int ASC = 2;
	
	
	//sex
	public static  final int MALE = 1;
	public static  final int FEMALE = 2;

	public static final int DECIMALDIGITS = 2;
	
	//root path
	//if use IDE, change to ./src/img/
	//if use command line, change to ./img/
	public static final String ROOT = "./src/img/";
	
}
