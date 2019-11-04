/**
* @author Qi Yin
* @ID U31787103
* @description  This is the error code of the project.
*/
package utils;

public class ErrCode {

	public static final int ERROR = 0;
	public static final int OK = 1;
	
	//register
	public static final int MISSFIRSTNAME = 101;
	public static final int MISSMIDDLENAME = 102;
	public static final int MISSLASTNAME = 103;
	public static final int MISSUSERNAME = 104;
	public static final int USERNAMEEXISTS = 10401;
	public static final int USERNAMENOTEXISTS = 10402;
	public static final int MISSPHONENUMBER = 105;
	public static final int PHONENUMBERNOTNUMBER = 10501;
	public static final int MISSEMAIL = 106;
	public static final int WRONGEMAILFORMAT = 10601;
	public static final int MISSDATE = 107;
	public static final int WRONGDATEFORMAT = 10701;
	public static final int WRONGDATEMONTH = 10702;
	public static final int WRONGDATEDAY = 10703;
	public static final int WRONGDATEYEAR = 10704;
	public static final int MISSPASSWORD = 108;
	public static final int WRONGPASSWORD = 10801;
	public static final int MISSCOMFIRMPASSWORD = 10802;
	public static final int WRONGCOMFIRMPASSWORD = 10803;
	
	//transaction
	public static final int NOENOUGHMONEY = 201;
	public static final int NOSUCHCURRENCY = 202;
	public static final int NOSUCHACCOUNT = 203;
	public static final int AMOUNTNOTANUMBER = 204;
	public static final int MISSAMOUNT = 20401;
	
	//loan
	public static final int LOANNAMEEMPTY = 301;
	public static final int LOANNAMEEXIST = 30101;
	public static final int LOANNAMENOTEXIST = 30102;
	public static final int COLLATERALEMPTY = 302;
	public static final int LOANNUMBEREMPTY = 303;
	public static final int LOANCANNOTBEPAIED = 304;
	public static final int LOANCANNOTBEPASSED = 305;
	
	//set config
	public static final int CURRENCYISNULL = 401;
	public static final int INPUTNOTANUMBER = 402;
	
	
	public static String errCodeToStr(int errCode) {
		String errString = "";
		switch (errCode) {
		case ERROR:
			errString = "Error. Please try later.";
			break;
		case OK:
			errString = "Success!";
			break;
		case MISSFIRSTNAME:
			errString = "First name cannot be empty.";
			break;
		case MISSMIDDLENAME:
			errString = "Middle name cannot be empty.";
			break;
		case MISSLASTNAME:
			errString = "Last name cannot be empty.";
			break;
		case MISSUSERNAME:
			errString = "User name cannot be empty.";
			break;
		case USERNAMEEXISTS:
			errString = "User name exists, please try another.";
			break;
		case USERNAMENOTEXISTS:
			errString = "User name doesn't exist.";
			break;
		case MISSPHONENUMBER:
			errString = "Phone number cannot be empty.";
			break;
		case PHONENUMBERNOTNUMBER:
			errString = "Not numbers. Please input valid numbers.";
			break;
		case MISSEMAIL:
			errString = "Email cannot be empty.";
			break;
		case WRONGEMAILFORMAT:
			errString = "Wrong email format. Please input a valid email address.";
			break;
		case MISSDATE:
			errString = "Date cannot be empty.";
			break;
		case WRONGDATEFORMAT:
			errString = "Wrong date format. Please input mm/dd/yyyy";
			break;
		case WRONGDATEMONTH:
			errString = "Wrong month. Please input a number between 1 and 12";
			break;
		case WRONGDATEDAY:
			errString = "Wrong day. Please input a valid day.";
			break;
		case WRONGDATEYEAR:
			errString = "Wrong year. Please input a valid year.";
			break;
		case MISSPASSWORD:
			errString = "Password cannot be empty.";
			break;
		case WRONGPASSWORD:
			errString = "Wrong password.";
			break;
		case MISSCOMFIRMPASSWORD:
			errString = "Comfirm password cannot be empty.";
			break;
		case WRONGCOMFIRMPASSWORD:
			errString = "Password and confirm password don't match.";
			break;
		case NOENOUGHMONEY:
			errString = "No enough money.";
			break;
		case NOSUCHCURRENCY:
			errString = "No such currency";
			break;
		case NOSUCHACCOUNT:
			errString = "No such account";
			break;
		case AMOUNTNOTANUMBER:
			errString = "Amount is not a number. Please input a valid number.";
			break;
		case MISSAMOUNT:
			errString = "Amount cannot be empty.";
			break;
		case LOANNAMEEMPTY:
			errString = "Loan name cannot be empty.";
			break;
		case LOANNAMEEXIST:
			errString = "Loan name exists. Please try another";
			break;
		case LOANNAMENOTEXIST:
			errString = "Loan name doesn't exist";
			break;
		case COLLATERALEMPTY:
			errString = "Collateral cannot be empty.";
			break;
		case LOANNUMBEREMPTY:
			errString = "Loan amount cannot be empty.";
			break;
		case LOANCANNOTBEPAIED:
			errString = "Loan cannot be paied. Please check status.";
			break;
		case LOANCANNOTBEPASSED:
			errString = "Loan cannot be passed. Please check status.";
			break;
		case CURRENCYISNULL:
			errString = "Currency cannot be empty.";
			break;
		case INPUTNOTANUMBER:
			errString = "Not a Number. Please input a valid number.";
			break;

		default:
			errString = "Error. Please try later.";
			break;
		}
		return errString;
	}
}
