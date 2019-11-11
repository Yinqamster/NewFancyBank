/**
* @author Group 5
* @description  the interface for mainly user system
* @system user
*/
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.BankController;
import controller.UserController;
import model.Loan;
import utils.Config;
import utils.ErrCode;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;

public class UserInterface extends JFrame{
	
	UserController userController = UserController.getInstance();
	
	public UserInterface(String username) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);
		

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("User   System");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JButton back = new JButton("");
		back.setIcon(new ImageIcon(Config.ROOT + "back.png"));
		back.setBounds(6, 6, 35, 35);
		contentPanel.add(back);
		
		JButton logout = new JButton("");
		logout.setIcon(new ImageIcon(Config.ROOT + "logout.png"));
		logout.setBounds(452, 6, 35, 35);
		contentPanel.add(logout);
		
		JPanel namePanel = new JPanel();
		namePanel.setBounds(50, 165, 400, 20);
		namePanel.setLayout(new GridLayout(1, 2, 5, 5));
		JLabel uname = new JLabel("Username: " + username);
		uname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JLabel tname = new JLabel("True Name: " + userController.getTruenameByUsername(username));
		tname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		namePanel.add(uname);
		namePanel.add(tname);
		

		List<String> checkingAccounts = userController.getAccountList(username, Config.CHECKINGACCOUNT);
		List<String> savingAccounts = userController.getAccountList(username, Config.SAVINGACCOUNT);
		
		boolean hasOperation = savingAccounts.size()==0&&checkingAccounts.size()==0 ? false : true;
		int rows = savingAccounts.size() + checkingAccounts.size();
		rows += hasOperation ? 4 : 2;
		int panelHeight = 25 * rows;
		
		
		JPanel accountPanel = new JPanel();
		accountPanel.setBounds(50, 190, 400, panelHeight);
		accountPanel.setLayout(new GridLayout(rows, 2, 5, 5));
		accountPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));  
		
		JLabel cacc = new JLabel("Checking Account:");
		cacc.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JButton createCacc = new JButton("Create");
		accountPanel.add(cacc);
		accountPanel.add(createCacc);
		
		for(int i = 0; i < checkingAccounts.size(); i++) {
			JLabel accNumL = new JLabel("Account number: ");
			JLabel accNum = new JLabel(checkingAccounts.get(i));
			accountPanel.add(accNumL);
			accountPanel.add(accNum);
		}
		
		JLabel sacc = new JLabel("Saving Account:");
		sacc.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JButton createSacc = new JButton("Create");
		accountPanel.add(sacc);
		accountPanel.add(createSacc);
		
		for(int i = 0; i < savingAccounts.size(); i++) {
			JLabel accNumL = new JLabel("Account number: ");
			JLabel accNum = new JLabel(String.valueOf(savingAccounts.get(i)));
			accountPanel.add(accNumL);
			accountPanel.add(accNum);
		}
		
		JButton deposit = new JButton("Deposit");
		JButton withdraw = new JButton("Withdraw");
		JButton transfer = new JButton("Transfer");
		JButton showDetail = new JButton("Show Details");
		if(hasOperation) {
			accountPanel.add(deposit);
			accountPanel.add(withdraw);
			accountPanel.add(transfer);
			accountPanel.add(showDetail);
		}


		List<String> securityAccount = userController.getAccountList(username, Config.SECURITYACCOUNT);
		boolean hasSecurityAccount = securityAccount == null || securityAccount.size()==0 ? false : true;
		//only for test
//		hasSecurityAccount = true;
		int securityRows = securityAccount.size();
		securityRows += hasSecurityAccount ? 2 : 1;
		int securityPanelHeight = 25 * securityRows;

		JPanel securityAccountPanel = new JPanel();
		securityAccountPanel.setBounds(50, accountPanel.getY() + accountPanel.getHeight() + 5, 400, securityPanelHeight);
		securityAccountPanel.setLayout(new GridLayout(securityRows, 2, 5, 5));
		securityAccountPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

		JLabel seacc = new JLabel("Security Account:");
		seacc.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JButton createSeacc = new JButton("Create");
		securityAccountPanel.add(seacc);
		securityAccountPanel.add(createSeacc);

		for(int i = 0; i < securityAccount.size(); i++) {
			JLabel accNumL = new JLabel("Account number: ");
			JLabel accNum = new JLabel(securityAccount.get(i));
			securityAccountPanel.add(accNumL);
			securityAccountPanel.add(accNum);
		}

//		JButton buy = new JButton("Buy");
//		JButton sell = new JButton("Sell");
		JButton allStocks = new JButton("Stocks Market");
		JButton showStockDetail = new JButton("Show Details");

		if(hasSecurityAccount) {
//			securityAccountPanel.add(buy);
//			securityAccountPanel.add(sell);
			securityAccountPanel.add(allStocks);
			securityAccountPanel.add(showStockDetail);
		}


		Map<String,Loan> loanList = userController.getLoanList(username);
		boolean hasLoan = loanList.size() == 0 ? false : true;
		int loanRows = hasLoan ? 2 + loanList.size() : 1;
		int loanPanelHeight = 25 * loanRows;
		int YBegin = securityAccountPanel.getY()+securityAccountPanel.getHeight() + 5;
		
		JPanel loanPanel = new JPanel();
		loanPanel.setBounds(50, YBegin, 400, loanPanelHeight);
		loanPanel.setLayout(new GridLayout(loanRows, 3, 5, 5));
		loanPanel.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));  
		
		JLabel LoanLabel = new JLabel("Loan");
		LoanLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JButton takeLoan = new JButton("Take Loan");
		JLabel emptyLable = new JLabel();
		loanPanel.add(LoanLabel);
		loanPanel.add(emptyLable);
		loanPanel.add(takeLoan);
		
		JLabel loanName = new JLabel("Name");
		JLabel loanNumber = new JLabel("Number");
		JLabel loanOperation = new JLabel("Operation");
		
		if(hasLoan){
			loanPanel.add(loanName);
			loanPanel.add(loanNumber);
			loanPanel.add(loanOperation);
			for(Loan loan : loanList.values()) {
				JLabel na = new JLabel(loan.getName());
				JLabel nu = new JLabel(String.valueOf(loan.getNumber()));
				loanPanel.add(na);
				loanPanel.add(nu);
				JButton oper = new JButton("Pay");
				oper.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						UserInterface.this.dispose();
						new TakeLoan(username, loan.getName());
					}
				});
				loanPanel.add(oper);
			}
		}
		
		
		
		contentPanel.add(namePanel);
		contentPanel.add(accountPanel);
		contentPanel.add(securityAccountPanel);
		contentPanel.add(loanPanel);
		contentPanel.add(titlePanel);
		contentPanel.add(background);
		getContentPane().add(contentPanel);
		
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = loanPanel.getY() + loanPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM User System" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new Login(Config.USER);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.logout(username);
				if(res == ErrCode.OK) {
					UserInterface.this.dispose();
					new Login(Config.USER);
				}
				else {
					//this shouldn't happen
					System.out.println("Logout Error");
				}
			}
		});
		
		createCacc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.createAccount(username, Config.CHECKINGACCOUNT, Config.DEFAULTCURRENCY, BankController.getBank().getOpenAccountFee());
				if(res == ErrCode.OK) {
					UserInterface.this.dispose();
					new UserInterface(username);
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
		
		createSacc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.createAccount(username, Config.SAVINGACCOUNT, Config.DEFAULTCURRENCY, BankController.getBank().getOpenAccountFee());
				if(res == ErrCode.OK) {
					UserInterface.this.dispose();
					new UserInterface(username);
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

		createSeacc.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.createAccount(username, Config.SECURITYACCOUNT, Config.DEFAULTCURRENCY, BankController.getBank().getOpenAccountFee());
				if(res == ErrCode.OK) {
					UserInterface.this.dispose();
					new UserInterface(username);
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
		
		deposit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new Transact(username, Config.DEPOSIT);
			}
		});
		
		withdraw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new Transact(username, Config.WITHDRAW);
			}
		});
		
		transfer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new Transact(username, Config.TRANSFEROUT);
			}
		});
		
		showDetail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new AccountDetail(username, "");
				
			}
		});

//		buy.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				UserInterface.this.dispose();
//				new StockTransaction(username, Config.BUY);
//			}
//		});
//
//		sell.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				UserInterface.this.dispose();
//				new StockTransaction(username, Config.SELL);
//			}
//		});

		allStocks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterface.this.dispose();
				new StockMarket(username, Config.USER);
			}
		});

		showStockDetail.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserInterface.this.dispose();
				new SecurityAccountDetail(username, "");
			}
		});
		
		takeLoan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UserInterface.this.dispose();
				new TakeLoan(username, "");
				
			}
		});
	}
}
