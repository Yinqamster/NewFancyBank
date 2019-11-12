/**
* @author Group 5
* @description  the interface for user details
* @system manager
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import model.*;
import utils.Config;
import utils.ErrCode;

public class UserDetail extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public UserDetail(String username, String accountNumber) {
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 1000, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("User  Detail");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(300, 80, 400, 50);
		
		JButton back = new JButton("");
		back.setIcon(new ImageIcon(Config.ROOT + "back.png"));
		back.setBounds(6, 6, 35, 35);
		contentPanel.add(back);
		
		JButton logout = new JButton("");
		logout.setIcon(new ImageIcon(Config.ROOT + "logout.png"));
		logout.setBounds(939, 6, 35, 35);
		contentPanel.add(logout);
		
		int infoPanelRows = 2;
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(50, 180, 900, 25*infoPanelRows);
		infoPanel.setLayout(new GridLayout(infoPanelRows, 8, 0, 5));
		
		User user = BankController.getBank().getUserList().get(username);
		Map<String, Account> accounts = user.getAccounts();
		Map<String, Loan> loans = user.getLoanList();
//		Map<String, Stock> stocks = user.getStockList();
		
		
		JLabel IDLabel = new JLabel("ID: ");
		IDLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		IDLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel ID = new JLabel(String.valueOf(user.getID()));
		ID.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(IDLabel);
		infoPanel.add(ID);
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel uname = new JLabel(username);
		uname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(usernameLabel);
		infoPanel.add(uname);
		
		JLabel truenameLabel = new JLabel("True Name: ");
		truenameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		truenameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel tname = new JLabel(user.getName().getFullName());
		tname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(truenameLabel);
		infoPanel.add(tname);
		
		JLabel sexLabel = new JLabel("Gender: ");
		sexLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		sexLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel sex = new JLabel(user.getSex() == Config.FEMALE ? "Female" : "Male");
		sex.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(sexLabel);
		infoPanel.add(sex);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel pnum = new JLabel(String.valueOf(user.getPhoneNumber()));
		pnum.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(phoneLabel);
		infoPanel.add(pnum);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel email = new JLabel(user.getEmail());
		email.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(emailLabel);
		infoPanel.add(email);
		
		JLabel dobLabel = new JLabel("Birthday: ");
		dobLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		dobLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel dob = new JLabel(user.getDob().toDayString());
		dob.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(dobLabel);
		infoPanel.add(dob);
		
		JLabel oweLabel = new JLabel("Owe: ");
		oweLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		oweLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		boolean oweFlag = false;
		for (Loan loan : loans.values()) {
			if(loan.getStatus() == Config.PASSED) {
				oweFlag = true;
			}
		}
		JLabel owe = new JLabel(oweFlag ? "Yes" : "No");
		owe.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(oweLabel);
		infoPanel.add(owe);
		
		

		JPanel accountTitlePanel = new JPanel();
		accountTitlePanel.setBounds(50, infoPanel.getY() + infoPanel.getHeight() + 20, 900, 60);
		accountTitlePanel.setLayout(new GridLayout(1, 4, 20, 5));
		
		JLabel account = new JLabel("Number：");
		account.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> accountList = new JComboBox<String>();
		for(Account c : accounts.values()) {
			accountList.addItem(c.getAccountNumber());
		}
		if(accountNumber != null && !accountNumber.isEmpty()) {
			accountList.setSelectedItem(accountNumber);
		}
		accountTitlePanel.add(account);
		accountTitlePanel.add(accountList);
		
		Account selectedAccount = new Account();
		if(accountList.getSelectedItem() != null) {
			selectedAccount = userController.getAccountDetail(username, accountList.getSelectedItem().toString());
		}
		
		JLabel accountType = new JLabel("Type:");
		accountType.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JLabel aTypeLabel = new JLabel();
		if(selectedAccount instanceof CheckingAccount) {
			aTypeLabel.setText("Checking Account");
		}
		else if(selectedAccount instanceof SavingAccount) {
			aTypeLabel.setText("Saving Account");
		}
		else if(selectedAccount instanceof  SecurityAccount) {
			aTypeLabel.setText("Security Account");
		}
		aTypeLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		accountTitlePanel.add(accountType);
		accountTitlePanel.add(aTypeLabel);

		JPanel balanceLabelPanel = new JPanel();
		balanceLabelPanel.setBounds(50, accountTitlePanel.getY() + accountTitlePanel.getHeight() + 20, 900, 25);
		balanceLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel balance = new JLabel("Balance: ");
		balance.setFont(new Font("Helvetica", Font.PLAIN, 15));
		balanceLabelPanel.add(balance);
		JScrollPane balanceScrollPanel = new JScrollPane();

		JPanel stockLabelPanel = new JPanel();
		stockLabelPanel.setBounds(50, accountTitlePanel.getY() + accountTitlePanel.getHeight() + 20, 900, 25);
		stockLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel stock = new JLabel("Stock: ");
		stock.setFont(new Font("Helvetica", Font.PLAIN, 15));
		stockLabelPanel.add(stock);
		JScrollPane stockScrollPanel = new JScrollPane();

		int transactionLabelPanelHeight = 0;

		if(selectedAccount instanceof CheckingAccount || selectedAccount instanceof SavingAccount) {
			Map<String, BigDecimal> balanceList = selectedAccount.getBalance();
			int balancePanelRows = balanceList == null || balanceList.size() == 0 ? 0 : balanceList.size() + 1;

			int balanceScrollPaneHeight = 25 * balancePanelRows > 50 ? 50 : 25 * balancePanelRows;
			balanceScrollPanel.setBounds(50, balanceLabelPanel.getY() + balanceLabelPanel.getHeight() + 5, 900, balanceScrollPaneHeight);

			if (balanceList != null && balanceList.size() != 0) {
				JPanel balancePanel = new JPanel();
				balancePanel.setLayout(new GridLayout(balancePanelRows, 2, 10, 5));

				JLabel currency = new JLabel("Currency: ");
				currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
				JLabel amount = new JLabel("Amount: ");
				amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
				balancePanel.add(currency);
				balancePanel.add(amount);
				for (Map.Entry<String, BigDecimal> b : balanceList.entrySet()) {
					JLabel curr = new JLabel(b.getKey());
					JLabel num = new JLabel(String.valueOf(b.getValue()));
					balancePanel.add(curr);
					balancePanel.add(num);
				}
				balanceScrollPanel.setViewportView(balancePanel);
			}
			transactionLabelPanelHeight = balanceScrollPanel.getY() + balanceScrollPanel.getHeight() + 20;
		}
		else if(selectedAccount instanceof SecurityAccount) {
			Map<String, HoldingStock> stockList = ((SecurityAccount) selectedAccount).getStockList();
			int stockPanelRows = stockList == null || stockList.size() == 0 ? 0 : stockList.size() + 1;

			int stockScrollPaneHeight = 25 * stockPanelRows > 50 ? 50 : 25 * stockPanelRows;
			stockScrollPanel.setBounds(50, stockLabelPanel.getY() + stockLabelPanel.getHeight() + 5, 900, stockScrollPaneHeight);

			if (stockList != null && stockList.size() != 0) {
				JPanel stockPanel = new JPanel();
				stockPanel.setLayout(new GridLayout(stockPanelRows, 4, 10, 5));

				JLabel tranID = new JLabel("ID: ");
				tranID.setFont(new Font("Helvetica", Font.PLAIN, 15));
				JLabel company = new JLabel("Company: ");
				company.setFont(new Font("Helvetica", Font.PLAIN, 15));
				JLabel amount = new JLabel("Amount: ");
				amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
				JLabel buyInPrice = new JLabel("Buy Price: ");
				buyInPrice.setFont(new Font("Helvetica", Font.PLAIN, 15));
				stockPanel.add(tranID);
				stockPanel.add(company);
				stockPanel.add(amount);
				stockPanel.add(buyInPrice);
				for(Map.Entry<String, HoldingStock> hs : stockList.entrySet()) {
					stockPanel.add(new JLabel(hs.getKey()));
					stockPanel.add(new JLabel(hs.getValue().getCompanyName()));
					stockPanel.add(new JLabel(String.valueOf(hs.getValue().getNumber())));
					stockPanel.add(new JLabel(String.valueOf(hs.getValue().getBuyInPirce())));
				}
				stockScrollPanel.setViewportView(stockPanel);
			}
			transactionLabelPanelHeight = stockScrollPanel.getY() + stockScrollPanel.getHeight() + 20;
		}
		else {
			transactionLabelPanelHeight = accountTitlePanel.getY() + accountTitlePanel.getHeight() + 20;
		}


		JPanel transactionLabelPanel = new JPanel();

		transactionLabelPanel.setBounds(50, transactionLabelPanelHeight, 900, 25);
		transactionLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel transaction = new JLabel("Transaction: ");
		transaction.setFont(new Font("Helvetica", Font.PLAIN, 15));
		transactionLabelPanel.add(transaction);
		
		Map<String, Transaction> transactionList = selectedAccount.getTransactionDetails();
		int transactionPanelRows = transactionList == null || transactionList.size() == 0 ? 0 : transactionList.size() + 1;
		JScrollPane transactionScrollPanel = new JScrollPane();
		int transactionScrollPaneHeight = 30*transactionPanelRows > 90 ? 90 : 30*transactionPanelRows;
		transactionScrollPanel.setBounds(50, transactionLabelPanel.getY() + transactionLabelPanel.getHeight() + 5, 900, transactionScrollPaneHeight);
		
		if(transactionList != null && transactionList.size() != 0) {
			JPanel transactionPanel = new JPanel();
			transactionPanel.setLayout(new GridLayout(transactionPanelRows, 3, 10, 5));
			
			JLabel transactionType = new JLabel("Type: ");
			transactionType.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel transactionID = new JLabel("ID: ");
			transactionID.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel operation = new JLabel("Operation: ");
			operation.setFont(new Font("Helvetica", Font.PLAIN, 15));
			transactionPanel.add(transactionType);
			transactionPanel.add(transactionID);
			transactionPanel.add(operation);
			for(Transaction t : transactionList.values()) {
				JLabel type = new JLabel(t.getTransactionTypeStr());
				JLabel id = new JLabel(t.getTransactionId());
				JButton view = new JButton("View Detail");
				transactionPanel.add(type);
				transactionPanel.add(id);
				transactionPanel.add(view);
				view.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						UserDetail.this.dispose();
						new TransactionDetail(username, accountList.getSelectedItem().toString(), t, Config.MANAGER);
					}
				});
			}
			transactionScrollPanel.setViewportView(transactionPanel);
		}
		
		
		JPanel loanLabelPanel = new JPanel();
		loanLabelPanel.setBounds(50, transactionScrollPanel.getY() + transactionScrollPanel.getHeight() + 20, 900, 25);
		loanLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel loan = new JLabel("Loan: ");
		loan.setFont(new Font("Helvetica", Font.PLAIN, 15));
		loanLabelPanel.add(loan);
		
		int loanRows = loans == null || loans.size() == 0 ? 0 : loans.size() + 1;
		JScrollPane loanScrollPanel = new JScrollPane();
		int loanScrollPaneHeight = 30*loanRows > 90 ? 90 : 30*loanRows;
		loanScrollPanel.setBounds(50, loanLabelPanel.getY() + loanLabelPanel.getHeight() + 5, 900, loanScrollPaneHeight);

		if(loans != null || loans.size() != 0) {
			JPanel loanPanel = new JPanel();
			loanPanel.setLayout(new GridLayout(loanRows, 8, 0, 5));
			
			JLabel loanName = new JLabel("Name");
			loanName.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanNumber = new JLabel("Number");
			loanNumber.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanCurrency = new JLabel("Currency");
			loanCurrency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanStartDate = new JLabel("Start");
			loanStartDate.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanEndDate = new JLabel("End");
			loanEndDate.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanCollateral = new JLabel("Collateral");
			loanCollateral.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanOperation = new JLabel("Operation");
			loanOperation.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel loanStatus = new JLabel("Status");
			loanStatus.setFont(new Font("Helvetica", Font.PLAIN, 15));
			
			loanPanel.add(loanName);
			loanPanel.add(loanNumber);
			loanPanel.add(loanCurrency);
			loanPanel.add(loanCollateral);
			loanPanel.add(loanStartDate);
			loanPanel.add(loanEndDate);
			loanPanel.add(loanStatus);
			loanPanel.add(loanOperation);
			
			for(Loan l : loans.values()) {
				loanPanel.add(new JLabel(l.getName()));
				loanPanel.add(new JLabel(String.valueOf(l.getNumber())));
				loanPanel.add(new JLabel(l.getCurrency()));
				loanPanel.add(new JLabel(l.getCollateral()));
				loanPanel.add(new JLabel(l.getStartDate().toDayString()));
				loanPanel.add(new JLabel(l.getDueDate().toDayString()));
				String st = "";
				switch (l.getStatus()) {
				case Config.PROCESSING:
					st = "processing";
					break;
				case Config.PASSED:
					st = "passed";
					break;
				case Config.PAIED:
					st = "paied";
					break;
				default:
					break;
				}
				loanPanel.add(new JLabel(st));
				JButton oper = new JButton("PASS");
				if(l.getStatus() == Config.PROCESSING) {
					loanPanel.add(oper);
					oper.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int res = managerController.passLoan(username, l.getName());
							if(res == ErrCode.OK) {
								UserDetail.this.dispose();
								new UserDetail(username, null);
							}
							else {
								Object[] options = {"OK"};
						        JOptionPane.showOptionDialog(null,  
						                ErrCode.errCodeToStr(res), "Error",  
						                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null,   
						                options,   
						                options[0]); 
							}
							
						}
					});
					
				}
				else {
					loanPanel.add(new Label());
				}
				
			}
			loanScrollPanel.setViewportView(loanPanel);
		}




		contentPanel.add(titlePanel);
		contentPanel.add(infoPanel);
		contentPanel.add(accountTitlePanel);
		if(selectedAccount instanceof CheckingAccount || selectedAccount instanceof  SavingAccount) {
			contentPanel.add(balanceLabelPanel);
			contentPanel.add(balanceScrollPanel);
		}
		else if(selectedAccount instanceof SecurityAccount) {
			contentPanel.add(stockLabelPanel);
			contentPanel.add(stockScrollPanel);
		}
		contentPanel.add(transactionLabelPanel);
		contentPanel.add(transactionScrollPanel);
		contentPanel.add(loanLabelPanel);
		contentPanel.add(loanScrollPanel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 1000;
		int totalHeight = transactionScrollPanel.getY() + transactionScrollPanel.getHeight() + 50;
		totalHeight = totalHeight > 800 ? totalHeight : 800;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM User Details" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserDetail.this.dispose();
				new CheckCustomer(managerController.getUsersByCondition(null, Config.SORTBYID, Config.DESC));
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.logout(Config.MANAGERUSERNAME);
				if(res == ErrCode.OK) {
					UserDetail.this.dispose();
					new Login(Config.MANAGER);
				}
				else {
					System.out.println("Logout Error");
				}
			}
		});
		
		accountList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedAccNum = accountList.getSelectedItem().toString();
				UserDetail.this.dispose();
				new UserDetail(username, selectedAccNum);
			}
		});
		
	}
}
