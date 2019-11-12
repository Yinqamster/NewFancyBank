/**
* @author Group 5
* @description  the interface for balance checking
* @system manager
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.BankController;
import controller.UserController;
import utils.Config;
import utils.ErrCode;

public class CheckBalance extends JFrame{
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public CheckBalance() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Bank Asset");
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
		
		Map<String, BigDecimal> balanceList = managerController.getBalance();
		int rows = balanceList.size() + 1;
		JScrollPane scrollPanel = new JScrollPane();
		int scrollPaneHeight = 30*rows > 150 ? 150 : 30*rows;
		scrollPanel.setBounds(40, 180, 400, scrollPaneHeight);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows, 2, 10, 5));
		
		JLabel currency = new JLabel("Currency");
		currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JLabel amount = new JLabel("Amount");
		amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
		panel.add(currency);
		panel.add(amount);
		
		for(Map.Entry<String, BigDecimal> b : balanceList.entrySet()) {
			JLabel cur = new JLabel(b.getKey());
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel am = new JLabel(String.valueOf(b.getValue()));
			amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
			panel.add(cur);
			panel.add(am);
		}
		
		scrollPanel.setViewportView(panel);
		
		contentPanel.add(titlePanel);
		contentPanel.add(scrollPanel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = scrollPanel.getY() + scrollPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Bank Asset" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CheckBalance.this.dispose();
				new ManagerInterface(Config.MANAGERUSERNAME);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.logout(Config.MANAGERUSERNAME);
				if(res == ErrCode.OK) {
					CheckBalance.this.dispose();
					new Login(Config.MANAGER);
				}
				else {
					System.out.println("Logout Error");
				}
			}
		});
	}
}
