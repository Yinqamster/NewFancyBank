/**
* @author Group 5
* @description  the interface for currency configuration
* @system manager
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import model.Bank;
import utils.Config;
import utils.ErrCode;

public class CurrencyConfig extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();
	public Bank bank = BankController.getBank();

	public CurrencyConfig(String currency) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Currency   Configuration");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JPanel panel = new JPanel();
		panel.setBounds(40, 180, 400, 300);
		panel.setLayout(new GridLayout(6, 2, 10, 15));
		
		JLabel name = new JLabel("Name*:");
		name.setHorizontalAlignment(SwingConstants.RIGHT);
		name.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(name);
		JTextField n = new JTextField(10);
		n.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(n);
		
		JLabel serviceChargeRate = new JLabel("Service Fee Rate*:");
		serviceChargeRate.setHorizontalAlignment(SwingConstants.RIGHT);
		serviceChargeRate.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(serviceChargeRate);
		JTextField scr = new JTextField(10);
		scr.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(scr);
		
		JLabel interestsForSavingAccount = new JLabel("Saving Acc Interest Rate*:");
		interestsForSavingAccount.setHorizontalAlignment(SwingConstants.RIGHT);
		interestsForSavingAccount.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(interestsForSavingAccount);
		JTextField ifsa = new JTextField(10);
		ifsa.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(ifsa);
		
		JLabel interestsForLoan = new JLabel("Loan Interest Rate*:");
		interestsForLoan.setHorizontalAlignment(SwingConstants.RIGHT);
		interestsForLoan.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(interestsForLoan);
		JTextField ifl = new JTextField(10);
		ifl.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(ifl);
		
		JLabel balanceForInterest = new JLabel("Lowest Balance For Interest*:");
		balanceForInterest.setHorizontalAlignment(SwingConstants.RIGHT);
		balanceForInterest.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(balanceForInterest);
		JTextField bfi = new JTextField(10);
		bfi.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(bfi);
		
		if(!currency.isEmpty() && currency != null && bank.getCurrencyList().containsKey(currency)) {
			model.CurrencyConfig currencyConfig = bank.getCurrencyList().get(currency).getConfig();
			n.setText(currency);
			n.setEditable(false);;
			scr.setText(String.valueOf(currencyConfig.getServiceChargeRate()));
			ifsa.setText(String.valueOf(currencyConfig.getInterestsForSavingAccount()));
			ifl.setText(String.valueOf(currencyConfig.getInterestsForLoan()));
			bfi.setText(String.valueOf(currencyConfig.getBalanceForInterest()));
		}

		JButton save = new JButton("Save");
		save.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(save);
		panel.add(cancel);
		
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
		
		this.setTitle( "Bank ATM Currency Config" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = n.getText();
				String scRate = scr.getText();
				String ifsAccount = ifsa.getText();
				String ifLoan = ifl.getText();
				String bfInterest = bfi.getText();
				int res = managerController.editCurrency(name, scRate, ifsAccount, ifLoan, bfInterest);
					
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			        		ErrCode.errCodeToStr(res), "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
			        
					CurrencyConfig.this.dispose();
					new SetConfig();
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
				CurrencyConfig.this.dispose();
				new SetConfig();
			}
		});
	}
}
