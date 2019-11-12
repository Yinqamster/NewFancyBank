/**
* @author Group 5
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

	//Stock
	public static final int STOCKEXIST = 501;
	public static final int STOCKNOTEXIST = 502;
	public static final int ILLEGALINPUT = 503;
	public static final int NOTENOUGHSTOCK = 504;
	public static final int HAVESOLDSTOCK = 505;

	// Database
	public static final int DATABASECONNECTIONFAIL = 601;
    public static final int INITDATAFAIL = 602;
	public static final int GETUSERLISTFAIL = 603;
	public static final int GETACCOUNTMAPFAIL = 604;
	public static final int GETBALANCEMAPFAIL = 605;
	public static final int GETTRANSACNTIONFAIL = 606;
	public static final int GETHOLDINGSTOCKFAIL = 607;
	public static final int GETSTOCKMARKETFAIL = 608;
    public static final int GETMANAGERBALANCEFAIL = 609;
    public static final int INSERTNAMEFAIL = 610;
    public static final int INSERTUSERFAIL = 611;
    public static final int UPDATEBALANCEFAIL = 612;
    public static final int ADDTRANSACTIONFAIL = 613;
	public static final int INSERTACCOUNTFAIL = 614;
	public static final int INSERTLOANFAIL = 615;
	public static final int UPDATELOANSTATUSFAIL = 616;
	public static final int INSERTHOLDINGSTOCKFAIL = 617;
	
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
			case STOCKEXIST:
				errString = "Cannot Add an Existed Stock";
				break;
			case STOCKNOTEXIST:
				errString = "Cannot Find This Stock";
				break;
			case ILLEGALINPUT:
				errString = "Illegal Input";
				break;
			case DATABASECONNECTIONFAIL:
				errString = "Fail to Connect Database";
				break;
			case INITDATAFAIL:
				errString = "Fail to Init Data from Database";
				break;
			case GETUSERLISTFAIL:
				errString = "Fail to Get UserList";
				break;
			case GETACCOUNTMAPFAIL:
				errString = "Fail to Get AccountMap";
				break;
			case GETBALANCEMAPFAIL:
				errString = "Fail to Get BalanceMap";
				break;
			case GETTRANSACNTIONFAIL:
				errString = "Fail to Get TrasanctionMap";
				break;
			case GETHOLDINGSTOCKFAIL:
				errString = "Fail to Get HoldingStock";
				break;
			case GETSTOCKMARKETFAIL:
				errString = "Fail to Get Stock Market Map";
				break;
            case GETMANAGERBALANCEFAIL:
                errString = "Fail to Get Manager's Balance Map";
                break;
            case INSERTNAMEFAIL:
                errString = "Fail to Insert a Name to DB";
                break;
            case INSERTUSERFAIL:
                errString = "Fail to Insert a User to DB";
                break;
            case UPDATEBALANCEFAIL:
                errString = "Fail to Update Balance";
                break;
            case ADDTRANSACTIONFAIL:
                errString = "Fail to Add Transaction to DB";
                break;
			case INSERTACCOUNTFAIL:
				errString = "Fail to Add Account to DB";
				break;
			case INSERTLOANFAIL:
				errString = "Fail to Add Loan to DB";
				break;
			case UPDATELOANSTATUSFAIL:
				errString = "Fail to Update Loan Status";
				break;
			case INSERTHOLDINGSTOCKFAIL:
				errString = "Fail to Insert Holding Stock";
				break;
			case HAVESOLDSTOCK:
				errString = "Have sold stock";
				break;
			default:
				errString = "Error. Please try later.";
				break;
		}
		return errString;
	}
}
