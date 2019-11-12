/**
* @author Group 5
* @description  the interface for transacting
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import model.CheckingAccount;
import model.Currency;
import model.SavingAccount;
import utils.Config;
import utils.ErrCode;

public class Transact extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public Transact(String username, int transactionType) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		int rows = transactionType == Config.TRANSFEROUT ? 8 : 7;
		JPanel panel = new JPanel();
		panel.setBounds(40, 180, 400, 50*rows);
		panel.setLayout(new GridLayout(rows, 2, 10, 15));
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel();
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JLabel uname = new JLabel("Username: " + username);
		uname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JLabel tname = new JLabel("True Name: " + userController.getTruenameByUsername(username));
		tname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		panel.add(uname);
		panel.add(tname);
		
		List<String> checkingAccounts = userController.getAccountList(username, Config.CHECKINGACCOUNT);
		List<String> savingAccounts = userController.getAccountList(username, Config.SAVINGACCOUNT);
		
		JLabel fromAccount = new JLabel("From Account*：");
		fromAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		fromAccount.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> fromAccountList = new JComboBox<String>();
		
		JLabel toAccount = new JLabel("To Account*：");
		toAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		toAccount.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> toAccountList = new JComboBox<String>();
		
		for(String c : checkingAccounts) {
			fromAccountList.addItem(c);
			toAccountList.addItem(c);
		}
		for(String c : savingAccounts) {
			fromAccountList.addItem(c);
			toAccountList.addItem(c);
		}
		
		JLabel currency = new JLabel("Currency*：");
		currency.setHorizontalAlignment(SwingConstants.RIGHT);
		currency.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> cur = new JComboBox<String>();
		if(BankController.getBank().getCurrencyList().size() > 0) {
			for(Map.Entry<String, Currency> c : BankController.getBank().getCurrencyList().entrySet()) {
//				if(c.getValue().getStatus() == Config.ENABLE){
					cur.addItem(c.getKey());
//				}
			}
		}
		
		JLabel amount = new JLabel("Amount*: ");
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		amount.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField num = new JTextField(10);
		num.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel remarks = new JLabel("Remarks: ");
		remarks.setHorizontalAlignment(SwingConstants.RIGHT);
		remarks.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField rem = new JTextField(10);
		rem.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel balance = new JLabel("Balance: ");
		balance.setHorizontalAlignment(SwingConstants.RIGHT);
		balance.setFont(new Font("Helvetica",Font.PLAIN,15));
		JLabel bal = new JLabel();
		bal.setHorizontalAlignment(SwingConstants.LEFT);

		JButton confirm = new JButton("Confirm");
		confirm.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
		
		
		if(transactionType == Config.DEPOSIT) {
			title.setText("Deposit");
			panel.add(toAccount);
			panel.add(toAccountList);
			panel.add(currency);
			panel.add(cur);
			panel.add(amount);
			panel.add(num);
			BigDecimal balan = userController.getAccountDetail(username, toAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
			bal.setText(String.valueOf(balan == null ? 0 : balan));
			panel.add(balance);
			panel.add(bal);
			panel.add(remarks);
			panel.add(rem);
			panel.add(confirm);
			panel.add(cancel);
		}
		else if(transactionType == Config.WITHDRAW) {
			title.setText("Withdraw");
			panel.add(fromAccount);
			panel.add(fromAccountList);
			panel.add(currency);
			panel.add(cur);
			panel.add(amount);
			panel.add(num);
			BigDecimal balan = userController.getAccountDetail(username, fromAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
			bal.setText(String.valueOf(balan == null ? 0 : balan));
			panel.add(balance);
			panel.add(bal);
			panel.add(remarks);
			panel.add(rem);
			panel.add(confirm);
			panel.add(cancel);
		}
		else if(transactionType == Config.TRANSFEROUT) {
			title.setText("Transafer");
			if(fromAccountList.getSelectedItem() != null &&
					userController.getAccountDetail(username, String.valueOf(fromAccountList.getSelectedItem())) != null &&
					userController.getAccountDetail(username, String.valueOf(fromAccountList.getSelectedItem())) instanceof CheckingAccount) {
				toAccountList.setEditable(true);
			}
			else {
				toAccountList.setEditable(false);
			}
			panel.add(toAccount);
			panel.add(toAccountList);
			panel.add(currency);
			panel.add(cur);
			panel.add(amount);
			panel.add(num);
			panel.add(fromAccount);
			panel.add(fromAccountList);
			BigDecimal balan = userController.getAccountDetail(username, fromAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
			bal.setText(String.valueOf(balan == null ? 0 : balan));
			panel.add(balance);
			panel.add(bal);
			panel.add(remarks);
			panel.add(rem);
			panel.add(confirm);
			panel.add(cancel);
		}
		
		
		contentPanel.add(titlePanel);
		contentPanel.add(panel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = panel.getY() + panel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Transact" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		fromAccountList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(transactionType == Config.WITHDRAW || transactionType == Config.TRANSFEROUT) {
					if(transactionType == Config.TRANSFEROUT) {
						if(fromAccountList.getSelectedItem() != null &&
								userController.getAccountDetail(username, String.valueOf(fromAccountList.getSelectedItem())) != null &&
								userController.getAccountDetail(username, String.valueOf(fromAccountList.getSelectedItem())) instanceof CheckingAccount) {
							toAccountList.setEditable(true);
						}
						else {
							toAccountList.setEditable(false);
						}
					}
					BigDecimal balan = userController.getAccountDetail(username, fromAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
					bal.setText(String.valueOf(balan == null ? 0 : balan));
					panel.revalidate();
					panel.repaint();
				}
			}
		});
		
		cur.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(transactionType == Config.WITHDRAW || transactionType == Config.TRANSFEROUT) {
					BigDecimal balan = userController.getAccountDetail(username, fromAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
					bal.setText(String.valueOf(balan == null ? 0 : balan));
				}
				else if(transactionType == Config.DEPOSIT) {
					BigDecimal balan = userController.getAccountDetail(username, toAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
					bal.setText(String.valueOf(balan == null ? 0 : balan));
					
				}
				panel.revalidate();
				panel.repaint();
			}
		});
		
		toAccountList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(transactionType == Config.DEPOSIT) {
					BigDecimal balan = userController.getAccountDetail(username, toAccountList.getSelectedItem().toString()).getBalance().get(cur.getSelectedItem().toString());
					bal.setText(String.valueOf(balan == null ? 0 : balan));
					panel.revalidate();
					panel.repaint();
				}
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String toAccountNumber = toAccountList.getSelectedItem().toString();
				String fromAccountNumber = fromAccountList.getSelectedItem().toString();
				String currency = cur.getSelectedItem().toString();
				String amount = num.getText();
				String remark = rem.getText();
				int res = -1;
				int accountType = 0;

				if(transactionType == Config.DEPOSIT) {
					if(userController.getAccountDetail(username, toAccountNumber) instanceof CheckingAccount) {
						accountType = Config.CHECKINGACCOUNT;
					}
					else if(userController.getAccountDetail(username, toAccountNumber) instanceof SavingAccount) {
						accountType = Config.SAVINGACCOUNT;
					}
				}
				else {
					if(userController.getAccountDetail(username, fromAccountNumber) instanceof CheckingAccount) {
						accountType = Config.CHECKINGACCOUNT;
					}
					else if(userController.getAccountDetail(username, fromAccountNumber) instanceof SavingAccount) {
						accountType = Config.SAVINGACCOUNT;
					}
				}
				
				if(transactionType == Config.DEPOSIT) {
					res = userController.deposit(username, accountType, toAccountNumber, amount, currency, remark);
				}
				else if(transactionType == Config.WITHDRAW) {
					res = userController.withdraw(username, accountType, fromAccountNumber, amount, currency, remark);
				}
				else if(transactionType == Config.TRANSFEROUT) {
					res = userController.transfer(username, accountType, fromAccountNumber, toAccountNumber, amount, currency, remark);
				}
				
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			        		ErrCode.errCodeToStr(res), "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
			        
					Transact.this.dispose();
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

		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Transact.this.dispose();
				new UserInterface(username);
			}
		});
	}
}
