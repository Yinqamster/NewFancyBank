/**
* @author Group 5
* @description  the interface for account detail
* @system user
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.BankController;
import controller.UserController;
import model.Account;
import model.CheckingAccount;
import model.SavingAccount;
import model.Transaction;
import utils.Config;
import utils.ErrCode;

public class AccountDetail extends JFrame{
	
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public AccountDetail(String username, String accountNumber) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Account  Detail");
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
		
		int infoPanelRows = 3;
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(40, 180, 400, 25*infoPanelRows);
		infoPanel.setLayout(new GridLayout(infoPanelRows, 2, 10, 5));
		
		JLabel uname = new JLabel("Username: " + username);
		uname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JLabel tname = new JLabel("True Name: " + userController.getTruenameByUsername(username));
		tname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(uname);
		infoPanel.add(tname);
		
		List<String> checkingAccounts = userController.getAccountList(username, Config.CHECKINGACCOUNT);
		List<String> savingAccounts = userController.getAccountList(username, Config.SAVINGACCOUNT);
		
		JLabel account = new JLabel("Account*：");
		account.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> accountList = new JComboBox<String>();
		for(String c : checkingAccounts) {
			accountList.addItem(c);
		}
		for(String c : savingAccounts) {
			accountList.addItem(c);
		}
		if(!accountNumber.isEmpty() && accountNumber != null) {
			accountList.setSelectedItem(accountNumber);
		}
		infoPanel.add(account);
		infoPanel.add(accountList);
		
		JLabel accountType = new JLabel("Account Type:");
		accountType.setFont(new Font("Helvetica", Font.PLAIN, 15));
		Account acc = userController.getAccountDetail(username, accountList.getSelectedItem().toString());
		JLabel aTypeLabel = new JLabel();
		if(acc instanceof CheckingAccount) {
			aTypeLabel.setText("Checking Account");
		}
		else if(acc instanceof SavingAccount) {
			aTypeLabel.setText("Saving Account");
		}
		aTypeLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(accountType);
		infoPanel.add(aTypeLabel);
		
		
		JPanel balanceLabelPanel = new JPanel();
		balanceLabelPanel.setBounds(40, infoPanel.getY()+infoPanel.getHeight()+20, 400, 25);
		balanceLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel balance = new JLabel("Balance: ");
		balance.setFont(new Font("Helvetica", Font.PLAIN, 15));
		balanceLabelPanel.add(balance);
		
		Map<String, BigDecimal> balanceList = userController.getAccountDetail(username, accountList.getSelectedItem().toString()).getBalance();
		int balancePanelRows = balanceList.size() == 0 ? 0 : balanceList.size() + 1;
		JScrollPane balanceScrollPanel = new JScrollPane();
		int balanceScrollPaneHeight = 25*balancePanelRows > 100 ? 100 : 25*balancePanelRows;
		balanceScrollPanel.setBounds(40, balanceLabelPanel.getY()+balanceLabelPanel.getHeight()+5, 400, balanceScrollPaneHeight);
		
		if(balanceList.size() != 0) {
			JPanel balancePanel = new JPanel();
			balancePanel.setLayout(new GridLayout(balancePanelRows, 2, 10, 5));
		
			JLabel currency = new JLabel("Currency: ");
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel amount = new JLabel("Amount: ");
			amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
			balancePanel.add(currency);
			balancePanel.add(amount);
			for(Map.Entry<String, BigDecimal> b : balanceList.entrySet()) {
				JLabel curr = new JLabel(b.getKey());
				JLabel num = new JLabel(String.valueOf(b.getValue()));
				balancePanel.add(curr);
				balancePanel.add(num);
			}
			balanceScrollPanel.setViewportView(balancePanel);
		}
		
		
		
		JPanel transactionLabelPanel = new JPanel();
		transactionLabelPanel.setBounds(40, balanceScrollPanel.getY()+balanceScrollPanel.getHeight()+20, 400, 25);
		transactionLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel transaction = new JLabel("Transaction: ");
		transaction.setFont(new Font("Helvetica", Font.PLAIN, 15));
		transactionLabelPanel.add(transaction);
		
		Map<String, Transaction> transactionList = userController.getAccountDetail(username, accountList.getSelectedItem().toString()).getTransactionDetails();
		int transactionPanelRows = transactionList.size() == 0 ? 0 : transactionList.size() + 1;
		JScrollPane transactionScrollPanel = new JScrollPane();
		int transactionScrollPaneHeight = 30*transactionPanelRows > 150 ? 150 : 30*transactionPanelRows;
		transactionScrollPanel.setBounds(40, transactionLabelPanel.getY()+transactionLabelPanel.getHeight()+5, 400, transactionScrollPaneHeight);
		
		if(transactionList.size() != 0) {
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
						AccountDetail.this.dispose();
						new TransactionDetail(username, accountList.getSelectedItem().toString(), t, Config.USER);
					}
				});
			}
			transactionScrollPanel.setViewportView(transactionPanel);
		}
		
		
		
		
		
		contentPanel.add(titlePanel);
		contentPanel.add(infoPanel);
		contentPanel.add(balanceLabelPanel);
		contentPanel.add(balanceScrollPanel);
		contentPanel.add(transactionLabelPanel);
		contentPanel.add(transactionScrollPanel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = transactionScrollPanel.getY() + transactionScrollPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Account Details" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AccountDetail.this.dispose();
				new UserInterface(username);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.logout(username);
				if(res == ErrCode.OK) {
					AccountDetail.this.dispose();
					new Login(Config.USER);
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
				AccountDetail.this.dispose();
				new AccountDetail(username, selectedAccNum);
			}
		});
		
	}
}
