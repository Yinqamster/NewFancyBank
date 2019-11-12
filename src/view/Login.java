/**
* @author Group 5
* @description  the interface for login
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;
import utils.Config;
import utils.ErrCode;

public class Login extends JFrame{
	
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public Login(String identity) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 200, 400, 100);
		panel.setLayout(new GridLayout(2, 2, 20, 15));
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);
		
		JButton back = new JButton("");
		back.setIcon(new ImageIcon(Config.ROOT + "back.png"));
		back.setBounds(6, 6, 35, 35);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel(identity + "   Login");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JLabel username = new JLabel("Username:");
		username.setHorizontalAlignment(SwingConstants.RIGHT);
		username.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField uname = new JTextField(10);
		panel.add(username);
		panel.add(uname);
		
		JLabel password = new JLabel("Password:");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("Helvetica",Font.PLAIN,15));
		JPasswordField pwd = new JPasswordField(10);
		panel.add(password);
		panel.add(pwd);
		
		JButton login = new JButton("Login");
		login.setSize(200, 40);
		login.setLocation(150, 330);
		login.setFont(new Font("Helvetica",Font.PLAIN,15));
		contentPanel.add(login);
		
		JButton register = new JButton("Register");
		register.setFont(new Font("Helvetica",Font.PLAIN,15));
		register.setSize(200, 40);
		register.setLocation(150, 390);
		if(identity == Config.USER) {
			contentPanel.add(register);
		}
		
		contentPanel.add(back);
		contentPanel.add(titlePanel);
		contentPanel.add(panel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 500;
		int totalHeight = contentPanel.getY() + contentPanel.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		
		
		this.setTitle( "Bank ATM Login" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Login.this.dispose();
				new Register(identity);
			}
		});
		
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String username = uname.getText();
				String password = String.valueOf(pwd.getPassword());
				int res = -1;
				if(identity == Config.USER) {
					res = userController.login(username, password);
					if(res == ErrCode.OK) {
						Login.this.dispose();
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
				else if(identity == Config.MANAGER) {
					res = managerController.login(username, password);
					if(res == ErrCode.OK) {
						Login.this.dispose();
						new ManagerInterface(username);
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
			}
		});

		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Login.this.dispose();
				new ChooseIdentity();
			}
		});
	}
}
