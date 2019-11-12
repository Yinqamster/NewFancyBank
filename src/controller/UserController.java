/**
* @author Group 5
* @description  This is the controller for user. It is the back end for user system. Add new method named "getHoldingStock",
 * "sellStock", "buyStock"
*/
package controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.operation.Operations;
import model.*;
import utils.Config;
import utils.ErrCode;
import utils.UtilFunction;

public class UserController implements SystemInterface{
	
	public BankController bankController = BankController.getInstance();
	public Bank bank = BankController.getBank();
	
	private static UserController instance;
	
	private UserController() {
		// TODO Auto-generated constructor stub
	}
	
	public static UserController getInstance() {  
	    if (instance == null) {  
	        instance = new UserController();  
	    }
	    return instance;
	}

	@Override
	public int register(Name name, int sex, String phoneNum, String email, String birthday, String password, String cPassword) {
		if(UtilFunction.checkName(name, Config.USER) != ErrCode.OK) {
			return UtilFunction.checkName(name, Config.USER);
		}
		if(UtilFunction.checkPhoneNumber(phoneNum) != ErrCode.OK) {
			return UtilFunction.checkPhoneNumber(phoneNum);
		}
		if(UtilFunction.checkEmail(email) != ErrCode.OK) {
			return UtilFunction.checkEmail(email);
		}
		if(UtilFunction.checkDate(birthday) != ErrCode.OK) {
			return UtilFunction.checkDate(birthday);
		}
		if(UtilFunction.checkPassword(password, cPassword) != ErrCode.OK) {
			return UtilFunction.checkPassword(password, cPassword);
		}
		User user = new User(name, sex, (long)Long.parseLong(phoneNum), email, UtilFunction.stringToDate(birthday), password);
		bank.addUser(user.getName().getNickName(), user);

		// add user to db
		Operations.addUserToDB(user);

//		UtilFunction.printUsers();
		return ErrCode.OK;
	}

	@Override
	public int login(String username, String password) {
		if(UtilFunction.checkName(username, Config.USER) != ErrCode.OK) {
			return UtilFunction.checkName(username, Config.USER);
		}
		if(UtilFunction.checkPassword(password) != ErrCode.OK) {
			return UtilFunction.checkPassword(password);
		}
		
		User user = bank.getUserList().get(username);
		if (!password.equals(user.getPassword())) {
			return ErrCode.WRONGPASSWORD;
		}
		user.setStatus(Config.LOGGEDIN);
		bank.addUser(username, user);
		return ErrCode.OK;
	}

	@Override
	public int logout(String username) {
		// TODO Auto-generated method stub
		if(UtilFunction.checkName(username, Config.USER) != ErrCode.OK) {
			return UtilFunction.checkName(username, Config.USER);
		}
		User user = bank.getUserList().get(username);
		user.setStatus(Config.NOTLOGGEDIN);
		bank.addUser(username, user);
		return ErrCode.OK;
	}
	
	public List<String> getAccountList(String username, int accountType) {
		List<String> accountList = new ArrayList<String>();
		User user = bank.getUserList().get(username);
		for(Account a: user.getAccounts().values()){
			if((accountType == Config.SAVINGACCOUNT && a instanceof SavingAccount)
					|| accountType == Config.CHECKINGACCOUNT && a instanceof CheckingAccount
					|| accountType == Config.SECURITYACCOUNT && a instanceof SecurityAccount) {
				accountList.add(a.getAccountNumber());
			}
		}
		return accountList;
	}
	
//	public List<String> getAccountList(String username) {
//		List<String> accountList = new ArrayList<String>();
//		User user = bank.getUserList().get(username);
//		for(Account a: user.getAccounts().values()){
//			accountList.add(a.getAccountNumber());
//		}
//		return accountList;
//	}
	
	public Account getAccountDetail(String username, String accountNumber) {
		User user = bank.getUserList().get(username);
		Account account = user.getAccounts().get(accountNumber);
		return account;
	}

	//transact
	public int deposit(String username, int accountType, String accountNumber, String amount, String currency, String remarks){
		if(!bank.getCurrencyList().containsKey(currency)) {
			return ErrCode.NOSUCHCURRENCY;
		}
		CurrencyConfig currencyConfig = bank.getCurrencyList().get(currency).getConfig();
		User user = bank.getUserList().get(username);
		if(!bank.getAccountList().containsKey(accountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}
		Account account = user.getAccounts().get(accountNumber);
		if((accountType == Config.SAVINGACCOUNT && !(account instanceof SavingAccount))
				|| (accountType == Config.CHECKINGACCOUNT && !(account instanceof CheckingAccount))){
			System.out.println("Wrong account type and account number");
			return ErrCode.ERROR;
		}
		if(amount.isEmpty() || amount == null) {
			return ErrCode.MISSAMOUNT;
		}
		if(!UtilFunction.isNumber(amount)) {
			return ErrCode.AMOUNTNOTANUMBER;
		}
		BigDecimal number = new BigDecimal(amount);
		Map<String, BigDecimal> balanceList = account.getBalance();
		BigDecimal oldBalance = new BigDecimal("0");
		if(balanceList.containsKey(currency)){
			oldBalance = balanceList.get(currency);
		}
		BigDecimal serviceCharge = number.multiply(currencyConfig.getServiceChargeRate()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		BigDecimal newBalance = oldBalance.add(number.subtract(serviceCharge)).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		bank.getBalance().put(currency, bank.getBalance().get(currency).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		BigDecimal newManagerBalance = bank.getBalance().get(currency);
		balanceList.put(currency, newBalance);
		account.setBalance(balanceList);
		
		Transaction t = new Transaction(username, user.getID(), currency, number, serviceCharge, newBalance, UtilFunction.now(), remarks, Config.DEPOSIT, "", accountNumber);
		account.addTransactionDetails(t);
		user.getAccounts().put(accountNumber, account);
		bank.addUser(username, user);

		// update DB
		Operations.deposit(accountNumber,newBalance,newManagerBalance,currency,t);

		return ErrCode.OK;
	}
	
	public int withdraw(String username, int accountType, String accountNumber, String amount, String currency, String remarks) {
		if(!bank.getCurrencyList().containsKey(currency)) {
			return ErrCode.NOSUCHCURRENCY;
		}
		CurrencyConfig currencyConfig = bank.getCurrencyList().get(currency).getConfig();
		User user = bank.getUserList().get(username);
		if(!bank.getAccountList().containsKey(accountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}
		Account account = user.getAccounts().get(accountNumber);
		if((accountType == Config.SAVINGACCOUNT && !(account instanceof SavingAccount))
				|| (accountType == Config.CHECKINGACCOUNT && !(account instanceof CheckingAccount))){
			System.out.println("Wrong account type and account number");
			return ErrCode.ERROR;
		}
		if(amount.isEmpty() || amount == null) {
			return ErrCode.MISSAMOUNT;
		}
		if(!UtilFunction.isNumber(amount)) {
			return ErrCode.AMOUNTNOTANUMBER;
		}
		BigDecimal number = new BigDecimal(amount);
		Map<String, BigDecimal> balanceList = account.getBalance();
		
		if(!balanceList.containsKey(currency)){
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal oldBalance = balanceList.get(currency);
		BigDecimal serviceCharge = number.multiply(currencyConfig.getServiceChargeRate()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		
		//balance must bigger than withdraw number add service charge
		if(oldBalance.compareTo(number.add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING)) < 0) {
			return ErrCode.NOENOUGHMONEY;
		}
		
		BigDecimal newBalance = oldBalance.subtract(number).subtract(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		bank.getBalance().put(currency, bank.getBalance().get(currency).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		BigDecimal newManagerBalance = bank.getBalance().get(currency);
		balanceList.put(currency, newBalance);
		account.setBalance(balanceList);
		Transaction t = new Transaction(username, user.getID(), currency, number, serviceCharge, newBalance, UtilFunction.now(), remarks, Config.WITHDRAW, accountNumber, "");
		account.addTransactionDetails(t);
		user.getAccounts().put(accountNumber, account);
		bank.addUser(username, user);

		// update DB
		Operations.withdraw(accountNumber,newBalance,newManagerBalance,currency,t);

		return ErrCode.OK;
	}
	
	public int transfer(String username, int accountType, String fromAccountNumber, 
			String toAccountNumber, String amount, String currency, String remarks) {
		if(!bank.getCurrencyList().containsKey(currency)) {
			return ErrCode.NOSUCHCURRENCY;
		}
		CurrencyConfig currencyConfig = bank.getCurrencyList().get(currency).getConfig();
		User user = bank.getUserList().get(username);
		if(!bank.getAccountList().containsKey(toAccountNumber) || !bank.getAccountList().containsKey(fromAccountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}
		
		//from account
		Account fromAccount = user.getAccounts().get(fromAccountNumber);
		if((accountType == Config.SAVINGACCOUNT && !(fromAccount instanceof SavingAccount))
				|| (accountType == Config.CHECKINGACCOUNT && !(fromAccount instanceof CheckingAccount))){
			System.out.println("Wrong account type and account number");
			return ErrCode.ERROR;
		}
		if(amount.isEmpty() || amount == null) {
			return ErrCode.MISSAMOUNT;
		}
		if(!UtilFunction.isNumber(amount)) {
			return ErrCode.AMOUNTNOTANUMBER;
		}
		BigDecimal number = new BigDecimal(amount);
		Map<String, BigDecimal> fromBalanceList = fromAccount.getBalance();
		if(!fromBalanceList.containsKey(currency)){
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal oldBalance = fromBalanceList.get(currency);
		BigDecimal serviceCharge = fromAccount instanceof SavingAccount ? new BigDecimal("0") : number.multiply(currencyConfig.getServiceChargeRate()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		if(oldBalance.compareTo(number.add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING)) < 0) {
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal newBalance = oldBalance.subtract(number).subtract(serviceCharge);
		bank.getBalance().put(currency, bank.getBalance().get(currency).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		BigDecimal newManagerBalance = bank.getBalance().get(currency);
		fromBalanceList.put(currency, newBalance);
		fromAccount.setBalance(fromBalanceList);
		Transaction t = new Transaction(username, user.getID(), currency, number, serviceCharge, newBalance, UtilFunction.now(), remarks, Config.TRANSFEROUT, fromAccountNumber, toAccountNumber);
		fromAccount.addTransactionDetails(t);
		user.getAccounts().put(fromAccountNumber, fromAccount);
		bank.addUser(username, user);

		// update DB
		Operations.sendMoney(fromAccountNumber,newBalance,newManagerBalance,currency,t);
		
		//to account
		User toUser = bank.getUserList().get(bank.getAccountList().get(toAccountNumber));
		Account toAccount = toUser.getAccounts().get(toAccountNumber);
		Map<String, BigDecimal> toBalanceList = toAccount.getBalance();
		BigDecimal toOldBalance = new BigDecimal("0");
		if(toBalanceList.containsKey(currency)){
			toOldBalance = toBalanceList.get(currency);
		}
		BigDecimal toNewBalance = toOldBalance.add(number);
		toBalanceList.put(currency, toNewBalance);
		toAccount.setBalance(toBalanceList);
		Transaction toT = new Transaction(toUser.getName().getNickName(), toUser.getID(), currency, number, BigDecimal.ZERO, toNewBalance, UtilFunction.now(), remarks, Config.RECEIVE, fromAccountNumber, toAccountNumber);
		toAccount.addTransactionDetails(toT);
		toUser.getAccounts().put(toAccountNumber, toAccount);
		bank.addUser(toUser.getName().getNickName(), toUser);

		// update DB
		Operations.receiveMoney(toAccountNumber,toNewBalance,newManagerBalance,currency,toT);
		
		return ErrCode.OK;
	}
	
	
	//default deposit some money when open account
	// for security account, number is 0
	public int createAccount(String username, int accountType, String currency, BigDecimal number) {
		Account account = new Account();
		if(accountType == Config.CHECKINGACCOUNT) {
			account = new CheckingAccount();
		}
		else if(accountType == Config.SAVINGACCOUNT){
			account = new SavingAccount();
		}
		// added for security account
		else if (accountType == Config.SECURITYACCOUNT) {
			User user = bank.getUserList().get(username);
			BigDecimal total = new BigDecimal("0");
			Map<String, Account> accounts = user.getAccounts();
			for (Map.Entry<String, Account> entry: accounts.entrySet()) {
				if (entry.getValue() instanceof SavingAccount) {
					Map<String, BigDecimal> balance = entry.getValue().getBalance();
					if (balance.containsKey("USD")) {
						total = total.add(balance.get("USD"));
					}
				}
			}
			if (total.compareTo(bank.getSecurityAccountThreshold()) >= 0) {
				account = new SecurityAccount();

				String accountNumber = UtilFunction.generateAccountNumber();
				bank.addAccount(accountNumber, username);
				account.setAccountNumber(accountNumber);

				BigDecimal serviceCharge = bank.getOpenAccountFee();
				bank.getBalance().put(currency, bank.getBalance().get(currency).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
				BigDecimal newManagerBalance = bank.getBalance().get(currency);

				Transaction t = new Transaction(username, user.getID(), currency, new BigDecimal("0"), serviceCharge, new BigDecimal("0"), UtilFunction.now(), null, Config.OPENACCOUNT, "", accountNumber);
				account.addTransactionDetails(t);
				user.getAccounts().put(accountNumber, account);
				bank.addUser(username, user);

				// update DB
				Operations.addAccountToDB(account,user,accountType,t, new BigDecimal("0"), newManagerBalance, currency);

				return ErrCode.OK;
			}
			else {
				return ErrCode.NOENOUGHMONEY;
			}
		}
//		account.setAccountType(accountType);
		String accountNumber = UtilFunction.generateAccountNumber();
		bank.addAccount(accountNumber, username);
		account.setAccountNumber(accountNumber);
		User user = bank.getUserList().get(username);
		
		if(!bank.getCurrencyList().containsKey(currency)) {
			return ErrCode.NOSUCHCURRENCY;
		}
		
		Map<String, BigDecimal> balanceList = account.getBalance();
		BigDecimal oldBalance = new BigDecimal("0");
		BigDecimal serviceCharge = bank.getOpenAccountFee();
		if(number.compareTo(serviceCharge) < 0) {
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal newBalance = oldBalance.add(number.subtract(serviceCharge)).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		bank.getBalance().put(currency, bank.getBalance().get(currency).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		BigDecimal newManagerBalance = bank.getBalance().get(currency);
		balanceList.put(currency, newBalance);
		account.setBalance(balanceList);
		
		Transaction t = new Transaction(username, user.getID(), currency, number, serviceCharge, newBalance, UtilFunction.now(), null, Config.OPENACCOUNT, "", accountNumber);
		account.addTransactionDetails(t);
		user.getAccounts().put(accountNumber, account);
		bank.addUser(username, user);

		// update DB
		Operations.addAccountToDB(account,user,accountType,t, newBalance,newManagerBalance, currency);

		return ErrCode.OK;
	}
	
	public int closeAccount(String username, int accountType, String accountNumber) {
		User user = bank.getUserList().get(username);
		Account account = user.getAccounts().get(accountNumber);
		Map<String, BigDecimal> balanceList = account.getBalance();
		BigDecimal serviceCharge = bank.getCloseAccountFee();
		if(!balanceList.containsKey(Config.DEFAULTCURRENCY) 
				|| balanceList.get(Config.DEFAULTCURRENCY).compareTo(serviceCharge) < 0) {
			return ErrCode.NOENOUGHMONEY;
		}
		bank.getBalance().put(Config.DEFAULTCURRENCY, bank.getBalance().get(Config.DEFAULTCURRENCY).add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		user.getAccounts().remove(accountNumber);
		bank.getAccountList().remove(accountNumber);
		bank.addUser(username, user);
		return ErrCode.OK;
	}
	
	//get user's true name by user name
	public String getTruenameByUsername(String username) {
		String name = "";
		Name uname = bank.getUserList().get(username).getName();
		name += uname.getFirstName();
		if(!uname.getMiddleName().isEmpty() && uname.getMiddleName() != null) {
			name += " " + uname.getMiddleName();
		}
		name += " " + uname.getLastName();
		return name;
	}
	
	
	//loan
	public Map<String, Loan> getLoanList(String username) {
		return bank.getUserList().get(username).getLoanList();
	}
	
	public int takeLoan(String username, String name, String collateral, String currency, String number, String dueDate) {
		User user = bank.getUserList().get(username);
		
		if(name.isEmpty() || name == null) {
			return ErrCode.LOANNAMEEMPTY;
		}
		if(user.getLoanList().containsKey(name)) {
			return ErrCode.LOANNAMEEXIST;
		}
		if(collateral.isEmpty() || collateral == null) {
			return ErrCode.COLLATERALEMPTY;
		}
		if(number.isEmpty() || number == null || !UtilFunction.isNumber(number)) {
			return ErrCode.LOANNUMBEREMPTY;
		}
		if(UtilFunction.checkDate(dueDate) != ErrCode.OK) {
			return UtilFunction.checkDate(dueDate);
		}
		
		Date startDate = UtilFunction.now();
		Date endDate = UtilFunction.stringToDate(dueDate);
		Loan loan = new Loan(name, collateral, currency, new BigDecimal(number), startDate, endDate, Config.PROCESSING);
		user.addLoan(loan);
		bank.addUser(username, user);

        // update db
        Operations.addLoanToDB(loan,user);
		
		return ErrCode.OK;
	}
	
	public int payForLoan(String username, String loanName, String accountNumber) {
		User user = bank.getUserList().get(username);
		if(loanName == null || loanName.isEmpty()) {
			return ErrCode.LOANNAMEEMPTY;
		}
		if(!user.getLoanList().containsKey(loanName)) {
			return ErrCode.LOANNAMENOTEXIST;
		}
		if(user.getLoanList().get(loanName).getStatus() == Config.PAIED 
				|| user.getLoanList().get(loanName).getStatus() == Config.PROCESSING) {
			return ErrCode.LOANCANNOTBEPAIED;
		}
		Loan loan = user.getLoanList().get(loanName);
		if(!user.getAccounts().containsKey(accountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}
		int days = UtilFunction.calculateTimeDifference(loan.getStartDate(), UtilFunction.now()) + 1;
		BigDecimal interestRate = bank.getCurrencyList().get(loan.getCurrency()).getConfig().getInterestsForLoan();
		BigDecimal interestsForLoan = loan.getNumber().multiply(interestRate)
				.multiply(new BigDecimal(days))
				.divide(new BigDecimal("365"), Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		BigDecimal oldBalance = user.getAccounts().get(accountNumber).getBalance().get(loan.getCurrency());
		if(!user.getAccounts().get(accountNumber).getBalance().containsKey(loan.getCurrency())
				|| oldBalance.compareTo(loan.getNumber().add(interestsForLoan).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING))<0) {
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal newBalance = oldBalance.subtract(loan.getNumber()).subtract(interestsForLoan).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		user.getAccounts().get(accountNumber).getBalance().put(loan.getCurrency(), newBalance);
		Transaction transaction = new Transaction(username, user.getID(), loan.getCurrency(), loan.getNumber(), interestsForLoan, newBalance, UtilFunction.now(), null, Config.PAYFORLOAN, accountNumber, "");
		user.getAccounts().get(accountNumber).addTransactionDetails(transaction);
		bank.getBalance().put(loan.getCurrency(), bank.getBalance().get(loan.getCurrency()).add(interestsForLoan).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		BigDecimal newManagerBalance = bank.getBalance().get(loan.getCurrency());
		loan.setStatus(Config.PAIED);
		user.getLoanList().put(loanName, loan);

		// update db
        Operations.payLoan(loan,user,accountNumber,newBalance,newManagerBalance);

		return ErrCode.OK;
	}

	/* The following method is added for stock purchase transaction */
	public int buyStock(String username, String companyName, String count, String savingAccountNumber, String securityAccountNumber) {
        String currency = "USD";
	    User user = bank.getUserList().get(username);
		if(!bank.getAccountList().containsKey(savingAccountNumber) || !bank.getAccountList().containsKey(securityAccountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}
		if(!UtilFunction.isInteger(count)) {
			return ErrCode.AMOUNTNOTANUMBER;
        }

		int num = Integer.parseInt(count);
		// get stock info
		Stock stock = bank.getStockMap().get(companyName);
		if(num <= 0) {
			return ErrCode.MISSAMOUNT;
		}
		BigDecimal purchaseAmount = stock.getUnitPrice().multiply(BigDecimal.valueOf(num)).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);

		// check and deduct money from saving account
		SavingAccount savingAccount = (SavingAccount) user.getAccounts().get(savingAccountNumber);
		Map<String, BigDecimal> fromBalanceList = savingAccount.getBalance();
		if(!fromBalanceList.containsKey("USD")){
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal oldBalance = fromBalanceList.get("USD");
		BigDecimal serviceCharge = purchaseAmount.multiply(bank.getStockTransactionFee()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		if(oldBalance.compareTo(purchaseAmount.add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING)) < 0) {
			return ErrCode.NOENOUGHMONEY;
		}
		BigDecimal newBalance = oldBalance.subtract(purchaseAmount).subtract(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		bank.getBalance().put("USD", bank.getBalance().get("USD").add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
        BigDecimal newManagerBalance = bank.getBalance().get(currency);
		fromBalanceList.put("USD", newBalance);
		savingAccount.setBalance(fromBalanceList);

		Transaction transactionSaving = new Transaction(username, user.getID(), "USD", new BigDecimal(count), serviceCharge, newBalance, UtilFunction.now(), companyName, Config.BUY, savingAccountNumber, securityAccountNumber);
		savingAccount.addTransactionDetails(transactionSaving);
		user.getAccounts().put(savingAccountNumber, savingAccount);

		// put stock purchased into security account
		HoldingStock stockRecord = new HoldingStock(companyName, stock.getUnitPrice(), new BigDecimal(count));
		SecurityAccount securityAccount = (SecurityAccount) user.getAccounts().get(securityAccountNumber);
		String stockRecordId = UtilFunction.generateTransactionID();
		securityAccount.getStockList().put(stockRecordId, stockRecord);

		Transaction transactionSecurity = new Transaction(username, user.getID(), "USD", new BigDecimal(count), new BigDecimal("0"), purchaseAmount, UtilFunction.now(), companyName, Config.BUY, savingAccountNumber, securityAccountNumber);
		securityAccount.addTransactionDetails(transactionSecurity);
		user.getAccounts().put(securityAccountNumber, securityAccount);

		// update db
        Operations.buyStock(stockRecord,stockRecordId,currency,savingAccount,securityAccount,transactionSaving,transactionSecurity,newManagerBalance);

		bank.addUser(username, user);
		return ErrCode.OK;
	}
	/* End */


	/* The following method is added for stock selling transaction */
	public int sellStock(String username, String stockRecordId, String count, String securityAccountNumber, String savingAccountNumber) {
        String currency = "USD";
	    User user = bank.getUserList().get(username);
		if(!bank.getAccountList().containsKey(savingAccountNumber) || !bank.getAccountList().containsKey(securityAccountNumber)) {
			return ErrCode.NOSUCHACCOUNT;
		}

		if(!UtilFunction.isInteger(count)) {
			return ErrCode.AMOUNTNOTANUMBER;
		}

		SecurityAccount securityAccount = (SecurityAccount) user.getAccounts().get(securityAccountNumber);
		SavingAccount savingAccount = (SavingAccount) user.getAccounts().get(savingAccountNumber);

		// *************** error here: count = "" ****************
		BigDecimal sellCount = new BigDecimal(count);
		// *******************************************************

		// get transaction and stock info
		HoldingStock stockRecord = securityAccount.getStockList().get(stockRecordId);
		if (stockRecord.getNumber().compareTo(sellCount) < 0) {
			return ErrCode.NOTENOUGHSTOCK;
		}

		// return money to saving account
		BigDecimal returnAmount = sellCount.multiply(stockRecord.getBuyInPirce()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		BigDecimal serviceCharge = returnAmount.multiply(bank.getStockTransactionFee()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		BigDecimal oldBalance = savingAccount.getBalance().get("USD");
		bank.getBalance().put("USD", bank.getBalance().get("USD").add(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
        BigDecimal newManagerBalance = bank.getBalance().get(currency);
		BigDecimal newBalance = oldBalance.add(returnAmount).subtract(serviceCharge).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING);
		savingAccount.getBalance().put("USD", newBalance);

		Transaction transactionSaving = new Transaction(username, user.getID(), "USD", sellCount, serviceCharge, newBalance,
				UtilFunction.now(), stockRecord.getCompanyName(), Config.SELL, securityAccountNumber, savingAccountNumber);
		savingAccount.addTransactionDetails(transactionSaving);
		user.getAccounts().put(savingAccountNumber, savingAccount);

		// update stock records in security account
		stockRecord.setNumber(stockRecord.getNumber().subtract(sellCount).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		if (stockRecord.getNumber().compareTo(new BigDecimal("0")) == 0) {
			securityAccount.getStockList().remove(stockRecordId);
		}
		else {
			securityAccount.getStockList().put(stockRecordId, stockRecord);
		}

		Transaction transactionSecurity = new Transaction(username, user.getID(), "USD", sellCount, new BigDecimal("0"), returnAmount,
				UtilFunction.now(), stockRecord.getCompanyName(), Config.SELL, securityAccountNumber, savingAccountNumber);
		securityAccount.addTransactionDetails(transactionSecurity);
		user.getAccounts().put(securityAccountNumber, securityAccount);

		bank.addUser(username, user);

		// update db
        Operations.sellStock(stockRecord,stockRecordId,stockRecordId,savingAccount,securityAccount,transactionSaving,transactionSecurity,newManagerBalance);

		return ErrCode.OK;
	}

	public HoldingStock getHoldingStock(String username, String stockRecordID, String securityAccountNumber) {
	    if(username == null || username.isEmpty() || stockRecordID == null || stockRecordID.isEmpty()) {
	        System.out.println("Wrong parameter!");
	        return null;
        }
        if(bank.getUserList() == null || !bank.getUserList().containsKey(username)) {
            System.out.println("Wrong parameter!");
            return null;
        }
        SecurityAccount account = (SecurityAccount) getAccountDetail(username, securityAccountNumber);
        if(!account.getStockList().containsKey(stockRecordID)) {
            System.out.println("Wrong parameter!");
            return null;
        }
        return account.getStockList().get(stockRecordID);
    }
}
