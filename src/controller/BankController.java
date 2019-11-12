/**
* @author Group 5
* @description  This is the controller for bank. It is the back end for manager system. Add three new method named "addNewStock"
 * "modifyStockPrice" and "deleteStock"
*/

package controller;

import java.math.BigDecimal;
import java.util.*;

import db.operation.Operations;
import model.*;
import model.Currency;
import model.Date;
import utils.Config;
import utils.ErrCode;
import utils.UtilFunction;

public class BankController implements SystemInterface{
	
	private static BankController instance;
	
	private static Bank bank;
	
	private BankController() {
		// TODO Auto-generated constructor stub
	}
	
	public static BankController getInstance() {  
	    if (instance == null) {  
	        instance = new BankController();  
	    }
	    return instance;
	}
	
	public static Bank getBank() {
		if(bank == null) {
			bank = new Bank();
		}
		return bank;
	}
	
	public static Bank initBank() {
		if(bank == null) {
			bank = new Bank();
		}
//		handInterest();
		return bank;
	}

	@Override
	public int register(Name name, int sex, String phoneNum, String email, String birthday, String password, String cPassword) {
		return ErrCode.OK;
	}

	@Override
	public int login(String username, String password) {
		// TODO Auto-generated method stub
		if(UtilFunction.checkName(username, Config.MANAGER) != ErrCode.OK) {
			return UtilFunction.checkName(username, Config.MANAGER);
		}
		if(UtilFunction.checkPassword(password) != ErrCode.OK) {
			return UtilFunction.checkPassword(password);
		}
		
		if (!password.equals(bank.getPassword())) {
			return ErrCode.WRONGPASSWORD;
		}
		return ErrCode.OK;
	}

	@Override
	public int logout(String username) {
		// TODO Auto-generated method stub
		if(UtilFunction.checkName(username, Config.MANAGER) != ErrCode.OK) {
			return UtilFunction.checkName(username, Config.MANAGER);
		}
		return ErrCode.OK;
	}
	
	public Map<String, BigDecimal> getBalance() {
		return bank.getBalance();
	}
	
	//check customer
	//select users by some conditions
	public List<User> getUsersByCondition(String username, int sortBy, int sortOrder) {
		List<User> res = new ArrayList<User>();
		if(username != null && !username.isEmpty()) {
			if(bank.getUserList().containsKey(username)){
				res.add(bank.getUserList().get(username));
			}
		}
		else {
			for(User user : bank.getUserList().values()){
				res.add(user);
			}
		}
		
		res.sort(new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				// TODO Auto-generated method stub
				if(sortBy == Config.SORTBYID) {
					if(sortOrder == Config.ASC) {
						if(o1.getID() < o2.getID()) {
							return -1;
						}
						else if(o1.getID() == o2.getID()) {
							return 0;
						}
						else if(o1.getID() > o2.getID()) {
							return 1;
						}
					}
					else if(sortOrder == Config.DESC) {
						if(o1.getID() < o2.getID()) {
							return 1;
						}
						else if(o1.getID() == o2.getID()) {
							return 0;
						}
						else if(o1.getID() > o2.getID()) {
							return -1;
						}
					}
				}
				else if(sortBy == Config.SORTBYLOANSIZE) {
					int o1LoanSize = o1.getLoanList() == null ? 0 : o1.getLoanList().size();
					int o2LoanSize = o2.getLoanList() == null ? 0 : o2.getLoanList().size();
					if(sortOrder == Config.ASC) {
						
						if(o1LoanSize < o2LoanSize) {
							return -1;
						}
						else if(o1LoanSize == o2LoanSize) {
							return 0;
						}
						else if(o1LoanSize > o2LoanSize) {
							return 1;
						}
					}
					else if(sortOrder == Config.DESC) {
						if(o1LoanSize < o2LoanSize) {
							return 1;
						}
						else if(o1LoanSize == o2LoanSize) {
							return 0;
						}
						else if(o1LoanSize > o2LoanSize) {
							return -1;
						}
					}
				}
				else if(sortBy == Config.SORTBYACCOUNTSIZE) {
					int o1AccountSize = o1.getAccounts() == null ? 0 : o1.getAccounts().size();
					int o2AccountSize = o2.getAccounts() == null ? 0 : o2.getAccounts().size();
					if(sortOrder == Config.ASC) {
						
						if(o1AccountSize < o2AccountSize) {
							return -1;
						}
						else if(o1AccountSize == o2AccountSize) {
							return 0;
						}
						else if(o1AccountSize > o2AccountSize) {
							return 1;
						}
					}
					else if(sortOrder == Config.DESC) {
						if(o1AccountSize < o2AccountSize) {
							return 1;
						}
						else if(o1AccountSize == o2AccountSize) {
							return 0;
						}
						else if(o1AccountSize > o2AccountSize) {
							return -1;
						}
					}
				}
				return 0;
			}
			
		});
		
		return res;
	}
	
	
	public int passLoan(String username, String loanName) {
		if(!bank.getUserList().containsKey(username)) {
			return ErrCode.USERNAMENOTEXISTS;
		}
		if(bank.getUserList().get(username).getLoanList() == null 
				|| !bank.getUserList().get(username).getLoanList().containsKey(loanName)) {
			return ErrCode.LOANNAMENOTEXIST;
		}
		if(bank.getUserList().get(username).getLoanList().get(loanName).getStatus() != Config.PROCESSING) {
			return ErrCode.LOANCANNOTBEPASSED;
		}
		bank.getUserList().get(username).getLoanList().get(loanName).setStatus(Config.PASSED);

		// update db
		Loan loan = bank.getUserList().get(username).getLoanList().get(loanName);
		Operations.updateLoanStatus(loan);

		return ErrCode.OK;
	}
	
	
	public DailyReport generateReport() {
		DailyReport dr = new DailyReport();
		dr.setUserNumber(bank.getUserList() == null ? 0 : bank.getUserList().size());
		dr.setOpenAccountNum(bank.getAccountList() == null ? 0 : bank.getAccountList().size());
		dr.setTransactionNum(bank.getTransactionIdList() == null ? 0 : bank.getTransactionIdList().size());

		if(bank.getUserList() != null) {
			for(User user: bank.getUserList().values()) {
				if(user.getAccounts() != null) {
					for(Account account : user.getAccounts().values()) {
						if(account.getTransactionDetails() != null) {
							for(Transaction transaction : account.getTransactionDetails().values()) {
								switch (transaction.getTransactionType()) {
								case Config.DEPOSIT:
									if(dr.getCurrencyIn().containsKey(transaction.getCurrency())) {
										dr.getCurrencyIn().put(transaction.getCurrency(), dr.getCurrencyIn().get(transaction.getCurrency()).add(transaction.getNum()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
									}
									else {
										dr.getCurrencyIn().put(transaction.getCurrency(), transaction.getNum());
									}
									break;
									
								case Config.WITHDRAW:
									if(dr.getCurrencyOut().containsKey(transaction.getCurrency())) {
										dr.getCurrencyOut().put(transaction.getCurrency(), dr.getCurrencyOut().get(transaction.getCurrency()).add(transaction.getNum()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
									}
									else {
										dr.getCurrencyOut().put(transaction.getCurrency(), transaction.getNum());
									}
									break;

								default:
									break;
								}
								if(dr.getServiceCharge().containsKey(transaction.getCurrency())) {
									dr.getServiceCharge().put(transaction.getCurrency(), dr.getServiceCharge().get(transaction.getCurrency()).add(transaction.getServiceCharge()).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
								}
								else {
									dr.getServiceCharge().put(transaction.getCurrency(), transaction.getServiceCharge());
								}
								dr.getTransactions().add(transaction);
							}
						}
					}
				}
			}
		}

		dr.getTransactions().sort(new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				// TODO Auto-generated method stub
				Date d1 = o1.getDate();
				Date d2 = o2.getDate();
				if(d1.compareTo(d2) > 0) {
					return -1;
				}
				else if(d1.compareTo(d1) == 0) {
					return 0;
				}
				else {
					return -1;
				}
			}
		});
		
		return dr;
	}
	
	
	
	//set configuration
	public int setCurrencyStatus(String currency, int status) {
		if(bank.getCurrencyList().containsKey(currency)) {
			bank.getCurrencyList().get(currency).setStatus(status);
		}
		return ErrCode.OK;
	}
	
	public int editCurrency(String currency, String scRate, String ifsAcc, String ifLoan, String bfInterest) {
		if(currency.isEmpty() || currency == null) {
			return ErrCode.CURRENCYISNULL;
		}
		if(!UtilFunction.isNumber(scRate) || !UtilFunction.isNumber(ifsAcc) || !UtilFunction.isNumber(ifLoan) || !UtilFunction.isNumber(bfInterest)){
			return ErrCode.INPUTNOTANUMBER;
		}
		Currency c;
		CurrencyConfig config = new CurrencyConfig(new BigDecimal(scRate), new BigDecimal(ifsAcc),
				new BigDecimal(ifLoan), new BigDecimal(bfInterest));
		if(bank.getCurrencyList().containsKey(currency)){
			c = bank.getCurrencyList().get(currency);
		}
		else {
			c = new Currency();
		}
		c.setConfig(config);
		bank.getCurrencyList().put(currency, c);
		bank.getBalance().put(currency, new BigDecimal("0"));
		return ErrCode.OK;
	}
	
	public int deleteCurrency(String currency) {
		if(bank.getCurrencyList().containsKey(currency)) {
			bank.getCurrencyList().remove(currency);
		}
		return ErrCode.OK;
	}
	
	public int saveConfig(String open, String close, String stock, String threshold) {
		if(!UtilFunction.isNumber(open) || !UtilFunction.isNumber(close) || !UtilFunction.isNumber(stock) ||
			!UtilFunction.isNumber(threshold)) {
			return ErrCode.INPUTNOTANUMBER;
		}
		bank.setOpenAccountFee(new BigDecimal(open));
		bank.setCloseAccountFee(new BigDecimal(close));
		bank.setStockTransactionFee(new BigDecimal(stock));
		bank.setSecurityAccountThreshold(new BigDecimal(threshold));
		return ErrCode.OK;
	}
	
	
	//hand interests for saving accounts
	public int handInterest() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
// 
//        Date time = calendar.getTime();         // execute at 12：00：00
// 
//        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                System.out.println("begin to hand interests");
                if(bank.getUserList().size() > 0) {
	                for(User user : bank.getUserList().values()) {
	                	if(user.getAccounts() != null && user.getAccounts().size() > 0) {
		                	for(Account account: user.getAccounts().values()) {
		                		if(account instanceof SavingAccount) {
		                			Map<String, BigDecimal> balanceList = account.getBalance();
		                			for(Map.Entry<String, BigDecimal> balance: balanceList.entrySet()) {
		                				BigDecimal balanceForInterest = bank.getCurrencyList().get(balance.getKey()).getConfig().getBalanceForInterest();
		                				if(balance.getValue().compareTo(balanceForInterest) >= 0) {
		                					BigDecimal interestsRate = bank.getCurrencyList().get(balance.getKey()).getConfig().getInterestsForSavingAccount();
		                					BigDecimal interests = balance.getValue().multiply(interestsRate).divide(new BigDecimal("365"), Config.DECIMALDIGITS, BigDecimal.ROUND_FLOOR);
		                					Transaction t = new Transaction(user.getName().getNickName(), user.getID(), balance.getKey(), interests, BigDecimal.ZERO, balance.getValue().add(interests), UtilFunction.now(), null, Config.SAVINGACCOUNTINTEREST, "", account.getAccountNumber());
		                					balance.setValue(balance.getValue().add(interests).setScale(Config.DECIMALDIGITS, BigDecimal.ROUND_CEILING));
		                					account.addTransactionDetails(t);

		                					// update db
											BigDecimal newUserBalance = balance.getValue();
											String currency = balance.getKey();
											Operations.balanceUpdateDB(account.getAccountNumber(),newUserBalance,currency,false);
											Operations.addTransactionToDB(t);
		                				}
		                			}
		                			account.setBalance(balanceList);
		                		}
		                		user.addAccount(account.getAccountNumber(), account);
		                	}
		                	bank.addUser(user.getName().getNickName(), user);
	                	}
	                }
                }
//            }
//        }, time, 1000 * 60 * 60 * 24);//execute per day
        
        return ErrCode.OK;
    }

	public int addNewStock(String company, String unitPriceStr) {
		BigDecimal unitPrice;
		try {
			unitPrice = new BigDecimal(unitPriceStr);
		} catch (NumberFormatException e) {
			return ErrCode.INPUTNOTANUMBER;
		}
		Stock stock = new Stock(company, unitPrice);
		boolean successful = bank.addStock(stock);
		if(!successful)
			return ErrCode.STOCKEXIST;

		//update db
		Operations.updateOrInsertStock(stock,0);

		return ErrCode.OK;
	}

	public int modifyStockPrice(String company, String modifyPriceStr) {
		BigDecimal modifyPrice;
		try {
			modifyPrice = new BigDecimal(modifyPriceStr);
		} catch (NumberFormatException e) {
			return ErrCode.INPUTNOTANUMBER;
		}
		if(modifyPrice.compareTo(new BigDecimal("0")) == -1)
			return ErrCode.ILLEGALINPUT;
		Map<String, Stock> stockMap = bank.getStockMap();
		if(!stockMap.containsKey(company))
			return ErrCode.STOCKNOTEXIST;
		int soldCount = stockMap.get(company).getSoldCount();
		Stock temp = new Stock(company, modifyPrice,soldCount);
		stockMap.put(company, temp);

		//update db

		Operations.updateOrInsertStock(temp, soldCount);

		return ErrCode.OK;
	}

	public int deleteStock(String company) {
		Map<String, Stock> stockMap = bank.getStockMap();
		Stock stock = stockMap.get(company);
		if(stock == null) {
			return ErrCode.STOCKNOTEXIST;
		}
		if(stock.getSoldCount() > 0)  {
			return ErrCode.HAVESOLDSTOCK;
		}
		stockMap.remove(company);

		// update db
		Operations.deleteStock(company);

		return ErrCode.OK;
	}
}
