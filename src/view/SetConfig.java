/**
* @author Group 5
* @description  the interface for setting
* @system manager
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.BankController;
import controller.UserController;
import model.Bank;
import model.Currency;
import utils.Config;
import utils.ErrCode;
import javax.swing.ScrollPaneConstants;

public class SetConfig extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();
	public Bank bank = BankController.getBank();

	public SetConfig() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Set  Configuration");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JPanel panel = new JPanel();
		panel.setBounds(40, 180, 400, 175);
		panel.setLayout(new GridLayout(5, 2, 10, 15));
		
		JLabel openAccountFee = new JLabel("Open Account Fee*:");
//		openAccountFee.setHorizontalAlignment(SwingConstants.RIGHT);
		openAccountFee.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(openAccountFee);
		JTextField oaf = new JTextField(10);
		oaf.setText(String.valueOf(bank.getOpenAccountFee()));
//		oaf.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(oaf);
		
		JLabel closeAccountFee = new JLabel("Close Account Fee*：");
//		closeAccountFee.setHorizontalAlignment(SwingConstants.RIGHT);
		closeAccountFee.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(closeAccountFee);
		JTextField caf = new JTextField(10);
		caf.setText(String.valueOf(bank.getCloseAccountFee()));
//		caf.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(caf);

		JLabel purchaseStockFee = new JLabel("Purchase Stock Fee*：");
		purchaseStockFee.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(purchaseStockFee);
		JTextField psf = new JTextField(10);
		psf.setText(String.valueOf(bank.getStockTransactionFee()));
		panel.add(psf);

		JLabel securityThreshold = new JLabel("Security Threshold*：");
		securityThreshold.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(securityThreshold);
		JTextField st = new JTextField(10);
		st.setText(String.valueOf(bank.getSecurityAccountThreshold()));
		panel.add(st);




		
		JLabel currency = new JLabel("Currency");
//		currency.setHorizontalAlignment(SwingConstants.RIGHT);
		currency.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(currency);
		JButton addCurrency = new JButton("Add");
//		addCurrency.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(addCurrency);
		
		int rows = bank.getCurrencyList().size()+1;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		int height = rows * 35 > 100 ? 100 : rows * 35;
		scrollPane.setBounds(40, panel.getY()+panel.getHeight()+5, 400, height);
		
		JPanel currencyPanel = new JPanel();
		currencyPanel.setLayout(new GridLayout(rows, 2, 10, 5));
		currencyPanel.setSize(400, rows * 35);
		
		JLabel name = new JLabel("Name");
		name.setFont(new Font("Helvetica",Font.PLAIN,15));
		currencyPanel.add(name);
//		JLabel enptyLable1 = new JLabel();
//		currencyPanel.add(enptyLable1);
		JLabel operation = new JLabel("Operation");
		operation.setFont(new Font("Helvetica",Font.PLAIN,15));
		currencyPanel.add(operation);
//		JLabel enptyLable2 = new JLabel();
//		currencyPanel.add(enptyLable2);
		
		
		for(Map.Entry<String,Currency> c : bank.getCurrencyList().entrySet()) {
			JLabel currencyName = new JLabel(c.getKey());
			currencyName.setFont(new Font("Helvetica",Font.PLAIN,15));
			currencyPanel.add(currencyName);
			
			JButton enable = new JButton("Enable");
			enable.setFont(new Font("Helvetica",Font.PLAIN,15));
			JButton disable = new JButton("Disable");
			disable.setFont(new Font("Helvetica",Font.PLAIN,15));
			JButton edit = new JButton("Edit");
			edit.setFont(new Font("Helvetica",Font.PLAIN,15));
			JButton delete = new JButton("Delete");
			delete.setFont(new Font("Helvetica",Font.PLAIN,15));
//			currencyPanel.add(c.getValue().getStatus() == Config.ENABLE ? disable : enable);
			currencyPanel.add(edit);
//			currencyPanel.add(delete);
			
			enable.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					managerController.setCurrencyStatus(c.getKey(), Config.ENABLE);
					SetConfig.this.dispose();
					new SetConfig();
				}
			});
			
			disable.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					managerController.setCurrencyStatus(c.getKey(), Config.DISABLE);
					SetConfig.this.dispose();
					new SetConfig();
				}
			});
			
			edit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					SetConfig.this.dispose();
					new CurrencyConfig(c.getKey());
					
				}
			});
			
			delete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					managerController.deleteCurrency(c.getKey());
					SetConfig.this.dispose();
					new SetConfig();
				}
			});
			
		}
		
		scrollPane.setViewportView(currencyPanel);
		

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 10, 15));
		buttonPanel.setBounds(40, scrollPane.getY()+scrollPane.getHeight()+20, 400, 25);
		JButton save = new JButton("Save");
		save.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
		buttonPanel.add(save);
		buttonPanel.add(cancel);
		
		contentPanel.add(titlePanel);
		contentPanel.add(panel);
		contentPanel.add(scrollPane);
		contentPanel.add(buttonPanel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = buttonPanel.getY() + buttonPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Set Config" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		addCurrency.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SetConfig.this.dispose();
				new CurrencyConfig("");
			}
		});
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO save config add psf and st
				int res = managerController.saveConfig(oaf.getText(), caf.getText(), psf.getText(), st.getText());
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			        		ErrCode.errCodeToStr(res), "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
			        
					SetConfig.this.dispose();
					new ManagerInterface(Config.MANAGERUSERNAME);
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
				SetConfig.this.dispose();
				new ManagerInterface(Config.MANAGERUSERNAME);
			}
		});
	}
}
