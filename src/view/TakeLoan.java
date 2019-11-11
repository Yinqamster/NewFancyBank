/**
* @author Group 5
* @description  the interface for taking loan
* @system user
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import model.Currency;
import model.Loan;
import utils.Config;
import utils.ErrCode;

import javax.swing.JComboBox;

public class TakeLoan extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public TakeLoan(String username, String loanName) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		int rows = loanName.isEmpty()||loanName==null ? 6 : 8;
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
		
		JLabel name = new JLabel("Name*: ");
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(name);
		name.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField lName = new JTextField(10);
		lName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lName);
		
		JLabel collateral = new JLabel("Collateral*：");
		collateral.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(collateral);
		collateral.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField colla = new JTextField(10);
		colla.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(colla);
		
		JLabel currency = new JLabel("Currency*：");
		currency.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(currency);
		currency.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> cur = new JComboBox<String>();
		if(BankController.getBank().getCurrencyList().size() > 0) {
			for(Map.Entry<String, Currency> c : BankController.getBank().getCurrencyList().entrySet()) {
//				if(c.getValue().getStatus() == Config.ENABLE){
					cur.addItem(c.getKey());
//				}
			}
		}
		panel.add(cur);
		
		JLabel amount = new JLabel("Amount*: ");
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(amount);
		amount.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField num = new JTextField(10);
		num.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(num);
		
		JLabel dueDate = new JLabel("Due Date*: ");
		dueDate.setHorizontalAlignment(SwingConstants.RIGHT);
		dueDate.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField date = new JTextField(3);
		date.setHorizontalAlignment(SwingConstants.LEFT);
		date.setToolTipText("mm/dd/yyyy");
		panel.add(dueDate);
		panel.add(date);

		JButton confirm = new JButton("Confirm");
		confirm.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton pay = new JButton("Pay");
		pay.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
		
		JLabel status = new JLabel("Status");
		status.setHorizontalAlignment(SwingConstants.RIGHT);
		status.setFont(new Font("Helvetica",Font.PLAIN,15));
		JLabel sta = new JLabel();
		sta.setHorizontalAlignment(SwingConstants.LEFT);
		
		List<String> checkingAccounts = userController.getAccountList(username, Config.CHECKINGACCOUNT);
		List<String> savingAccounts = userController.getAccountList(username, Config.SAVINGACCOUNT);
		
		JLabel payAccount = new JLabel("Pay Account*：");
		payAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		payAccount.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> payAcc = new JComboBox<String>();
		for(String c : checkingAccounts) {
			payAcc.addItem(c);
		}
		for(String c : savingAccounts) {
			payAcc.addItem(c);
		}
		
		
		
		if(!loanName.isEmpty() && !loanName.equals(null)) {
			title.setText("Pay For Loan");
			Loan loan = userController.getLoanList(username).get(loanName);
			lName.setText(loanName);
			lName.setEditable(false);
			colla.setText(loan.getCollateral());
			colla.setEditable(false);
			cur.setSelectedItem(loan.getCurrency());
			cur.setEditable(false);
			num.setText(String.valueOf(loan.getNumber()));
			num.setEditable(false);
			date.setText(loan.getDueDate().getMonth()+"/"+loan.getDueDate().getDay()+"/"+loan.getDueDate().getYear());
			date.setEditable(false);
			panel.add(status);
			if(loan.getStatus() == Config.PROCESSING) {
				sta.setText("Processing");
			}
			else if(loan.getStatus() == Config.PASSED) {
				sta.setText("Passed");
			}
			else if(loan.getStatus() == Config.PAIED) {
				sta.setText("Paied");
			}
			panel.add(sta);
			panel.add(payAccount);
			panel.add(payAcc);
			panel.add(pay);
		}
		else {
			title.setText("Take  Loan");
			panel.add(confirm);
		}
		
		panel.add(cancel);
		
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
		
		this.setTitle( "Bank ATM Loan" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		confirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = lName.getText();
				String currency = cur.getSelectedItem().toString();
				String number = num.getText();
				String collat = colla.getText();
				String ddate = date.getText();
				int res = userController.takeLoan(username, name, collat, currency, number, ddate);
				
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			        		ErrCode.errCodeToStr(res), "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
			        
					TakeLoan.this.dispose();
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
		
		pay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = userController.payForLoan(username, loanName, payAcc.getSelectedItem() == null ? null : payAcc.getSelectedItem().toString());
				if(res == ErrCode.OK) {
					TakeLoan.this.dispose();
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

//
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				TakeLoan.this.dispose();
				new UserInterface(username);
			}
		});
	}
}
