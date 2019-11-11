/**
* @author Group 5
* @description  the interface for mainly manager system
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.BankController;
import utils.Config;
import utils.ErrCode;

import javax.swing.JButton;

public class ManagerInterface extends JFrame{
	
	BankController managerController = BankController.getInstance();

	public ManagerInterface(String username){
		JPanel panel = new JPanel();
		panel.setLayout(null);
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		
		JButton back = new JButton("");
		back.setIcon(new ImageIcon(Config.ROOT + "back.png"));
		back.setBounds(6, 6, 35, 35);
		panel.add(back);
		
		JButton logout = new JButton("");
		logout.setIcon(new ImageIcon(Config.ROOT + "logout.png"));
		logout.setBounds(452, 6, 35, 35);
		panel.add(logout);
		
		JLabel background = new JLabel();
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Manager   System");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JPanel operationPanel = new JPanel(new GridLayout(6, 1, 100, 15));
		operationPanel.setSize(200, 290);
		operationPanel.setLocation(150, 180);
		
		JButton checkBalance = new JButton("Check Balance");
		checkBalance.setFont(new Font("Helvetica", Font.PLAIN, 15));
		checkBalance.setIcon(new ImageIcon(Config.ROOT + "balance.png"));
		operationPanel.add(checkBalance);
		JButton checkCustomer = new JButton("Check Customer");
		checkCustomer.setFont(new Font("Helvetica", Font.PLAIN, 15));
		checkCustomer.setIcon(new ImageIcon(Config.ROOT + "user.png"));
		operationPanel.add(checkCustomer);
		JButton getDailyReport = new JButton("Get Daily Report");
		getDailyReport.setIcon(new ImageIcon(Config.ROOT + "report.png"));
		getDailyReport.setFont(new Font("Helvetica", Font.PLAIN, 15));
		operationPanel.add(getDailyReport);
		JButton setConfig = new JButton("Set Config");
		setConfig.setIcon(new ImageIcon(Config.ROOT + "setting.png"));
		setConfig.setFont(new Font("Helvetica", Font.PLAIN, 15));
		operationPanel.add(setConfig);
		JButton handInterest = new JButton("Hand Interest");
		handInterest.setIcon(new ImageIcon(Config.ROOT + "interest.png"));
		handInterest.setFont(new Font("Helvetica", Font.PLAIN, 15));
		operationPanel.add(handInterest);
		JButton manageStock = new JButton("Manage Stock");
		manageStock.setIcon(new ImageIcon(Config.ROOT + "stocks.png"));
		manageStock.setFont(new Font("Helvetica", Font.PLAIN, 15));
		operationPanel.add(manageStock);
		
		panel.add(titlePanel);
		panel.add(operationPanel);
		panel.add(background);
		
		
		getContentPane().add(panel);
		
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = operationPanel.getY() + operationPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Manager System" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerInterface.this.dispose();
				new Login(Config.MANAGER);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.logout(username);
				if(res == ErrCode.OK) {
					ManagerInterface.this.dispose();
					new Login(Config.MANAGER);
				}
				else {
					//this shouldn't happen
					System.out.println("Logout Error");
				}
			}
		});
		
		checkBalance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerInterface.this.dispose();
				new CheckBalance();
			}
		});
		
		checkCustomer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerInterface.this.dispose();
				new CheckCustomer(managerController.getUsersByCondition(null, Config.SORTBYID, Config.DESC));
			}
		});
		
		getDailyReport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerInterface.this.dispose();
				new GetDailyReport();
			}
		});
		
		setConfig.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManagerInterface.this.dispose();
				new SetConfig();
			}
		});
		
		handInterest.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.handInterest();
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			                "Successful!", "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
				}
				
			}
		});

		manageStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerInterface.this.dispose();
				new StockMarket("", Config.MANAGER);
			}
		});
	}
}
