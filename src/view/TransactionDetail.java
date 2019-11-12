/**
* @author Group 5
* @description  the interface for transaction details
* @system user, manager
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import model.Transaction;
import utils.Config;
import utils.ErrCode;

public class TransactionDetail extends JFrame{

	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public TransactionDetail(String username, String accountNumber, Transaction t, String identity) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(40, 180, 400, 500);
		panel.setLayout(new GridLayout(10, 2, 10, 15));
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Transaction Detail");
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
		
		JLabel transactionType = new JLabel("Transaction Type:");
		transactionType.setHorizontalAlignment(SwingConstants.RIGHT);
		transactionType.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(transactionType);
		JLabel type = new JLabel(t.getTransactionTypeStr());
		type.setHorizontalAlignment(SwingConstants.LEFT);
		type.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(type);
		
		JLabel transactionID = new JLabel("Transaction ID:");
		transactionID.setHorizontalAlignment(SwingConstants.RIGHT);
		transactionID.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(transactionID);
		JLabel id = new JLabel(t.getTransactionId());
		id.setHorizontalAlignment(SwingConstants.LEFT);
		id.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(id);
		
		JLabel fromAccountNumber = new JLabel();
		fromAccountNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		fromAccountNumber.setFont(new Font("Helvetica",Font.PLAIN,15));
		JLabel fac = new JLabel();
		fac.setHorizontalAlignment(SwingConstants.LEFT);
		fac.setFont(new Font("Helvetica",Font.PLAIN,15));
		
		JLabel toAccountNumber = new JLabel();
		toAccountNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		toAccountNumber.setFont(new Font("Helvetica",Font.PLAIN,15));
		JLabel tac = new JLabel();
		tac.setHorizontalAlignment(SwingConstants.LEFT);
		tac.setFont(new Font("Helvetica",Font.PLAIN,15));
		
		
		if(t.getTransactionType() == Config.TRANSFEROUT || t.getTransactionType() == Config.RECEIVE) {
			//to from 
			fromAccountNumber.setText("From Account Number:");
			panel.add(fromAccountNumber);
			fac.setText(t.getFromAccountNumber());
			panel.add(fac);
			toAccountNumber.setText("To Account Number:");
			panel.add(toAccountNumber);
			tac.setText(t.getToAccountNumber());
			panel.add(tac);
		}
		else if(t.getTransactionType() == Config.DEPOSIT || t.getTransactionType() == Config.OPENACCOUNT || t.getTransactionType() == Config.SAVINGACCOUNTINTEREST) {
			//to
			toAccountNumber.setText("Account Number:");
			panel.add(toAccountNumber);
			tac.setText(t.getToAccountNumber());
			panel.add(tac);
		}
		else if(t.getTransactionType() == Config.WITHDRAW || t.getTransactionType() == Config.PAYFORLOAN) {
			//from
			fromAccountNumber.setText("Account Number:");
			panel.add(fromAccountNumber);
			fac.setText(t.getFromAccountNumber());
			panel.add(fac);
		}
		
		JLabel currency = new JLabel("Currency:");
		currency.setHorizontalAlignment(SwingConstants.RIGHT);
		currency.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(currency);
		JLabel cur = new JLabel(t.getCurrency());
		cur.setHorizontalAlignment(SwingConstants.LEFT);
		cur.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(cur);
		
		JLabel amount = new JLabel("Amount:");
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		amount.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(amount);
		JLabel num = new JLabel(String.valueOf(t.getNum()));
		num.setHorizontalAlignment(SwingConstants.LEFT);
		num.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(num);
		
		JLabel serviceCharge = new JLabel("Service Charge:");
		serviceCharge.setHorizontalAlignment(SwingConstants.RIGHT);
		serviceCharge.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(serviceCharge);
		JLabel scNum = new JLabel(String.valueOf(t.getServiceCharge()));
		scNum.setHorizontalAlignment(SwingConstants.LEFT);
		scNum.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(scNum);
		
		JLabel balance = new JLabel("Balance:");
		balance.setHorizontalAlignment(SwingConstants.RIGHT);
		balance.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(balance);
		JLabel bal = new JLabel(String.valueOf(t.getBalance()));
		bal.setHorizontalAlignment(SwingConstants.LEFT);
		bal.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(bal);
		
		JLabel date = new JLabel("Time:");
		date.setHorizontalAlignment(SwingConstants.RIGHT);
		date.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(date);
		JLabel time = new JLabel(t.getDate().toTimeString());
		time.setHorizontalAlignment(SwingConstants.LEFT);
		time.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(time);
		
		JLabel remarks = new JLabel("Remark:");
		remarks.setHorizontalAlignment(SwingConstants.RIGHT);
		remarks.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(remarks);
		JLabel remark = new JLabel(t.getRemarks());
		remark.setHorizontalAlignment(SwingConstants.LEFT);
		remark.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(remark);
		
		contentPanel.add(titlePanel);
		contentPanel.add(panel);
		contentPanel.add(background);
		
		this.add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = panel.getY() + panel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Transaction Details" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		
		
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TransactionDetail.this.dispose();
				if(identity.equals(Config.MANAGER)) {
					new UserDetail(username, accountNumber);
				}
				else if(identity.equals(Config.USER)) {
					new AccountDetail(username, accountNumber);
				}
				
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.logout(username);
				if(res == ErrCode.OK) {
					TransactionDetail.this.dispose();
					new Login(identity);
				}
				else {
					//this shouldn't happen
					System.out.println("Logout Error");
				}
			}
		});
		

	}
}
