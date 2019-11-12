/**
* @author Group 5
* @description  the interface for registration
* @system user
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Name;
import utils.Config;
import utils.ErrCode;

import javax.swing.SwingConstants;

import controller.BankController;
import controller.UserController;

import java.awt.GridLayout;
import java.awt.Toolkit;

public class Register extends JFrame {
	
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();

	public Register(String identity) {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(40, 180, 400, 500);
		panel.setLayout(new GridLayout(11, 2, 10, 15));
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 500, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel(identity + "   Register");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(50, 80, 400, 50);
		
		JLabel firstName = new JLabel("First Name*:");
		firstName.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(firstName);
		firstName.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField fname = new JTextField(10);
		fname.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(fname);
		
		JLabel middleName = new JLabel("Middle Name：");
		middleName.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(middleName);
		middleName.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField mname = new JTextField(10);
		mname.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(mname);
		
		JLabel lastName = new JLabel("Last Name*:");
		lastName.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lastName);
		lastName.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField lname = new JTextField(10);
		lname.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lname);
		
		JLabel username = new JLabel("Username*:");
		username.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(username);
		username.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField uname = new JTextField(10);
		uname.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(uname);
		
		JLabel sex = new JLabel("Gender*:");
		sex.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(sex);
		sex.setFont(new Font("Helvetica",Font.PLAIN,15));
		JComboBox<String> se = new JComboBox<String>();
		se.addItem("Female");
		se.addItem("Male");
		se.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(se);
		
		JLabel phoneNumber = new JLabel("Phone Number*:");
		phoneNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(phoneNumber);
		phoneNumber.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField pnumber = new JTextField(10);
		pnumber.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(pnumber);
		
		JLabel email = new JLabel("Email*:");
		email.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(email);
		email.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField em = new JTextField(10);
		em.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(em);
		
		JLabel birthday = new JLabel("Birthday*:");
		birthday.setHorizontalAlignment(SwingConstants.RIGHT);
		birthday.setFont(new Font("Helvetica",Font.PLAIN,15));
		JTextField date = new JTextField(3);
		date.setHorizontalAlignment(SwingConstants.LEFT);
		date.setToolTipText("mm/dd/yyyy");
		panel.add(birthday);
		panel.add(date);
		
		JLabel password = new JLabel("Password*:");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("Helvetica",Font.PLAIN,15));
		JPasswordField pwd = new JPasswordField(10);
		pwd.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(password);
		panel.add(pwd);

		JLabel confirmPassword = new JLabel("Confirm Password*:");
		confirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		confirmPassword.setFont(new Font("Helvetica",Font.PLAIN,15));
		JPasswordField cpwd = new JPasswordField(10);
		cpwd.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(confirmPassword);
		panel.add(cpwd);

		JButton register = new JButton("Register");
		register.setFont(new Font("Helvetica",Font.PLAIN,15));
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
		panel.add(register);
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
		
		this.setTitle( "Bank ATM Register" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Name name = new Name(fname.getText(), mname.getText(), lname.getText(), uname.getText());
				String phoneNum = pnumber.getText();
				String email = em.getText();
				String birthday = date.getText();
				String password = String.valueOf(pwd.getPassword());
				String cPassword = String.valueOf(cpwd.getPassword());
				int sex = se.getSelectedItem().equals("Female") ? Config.FEMALE : Config.MALE;
				int res = -1;
				if(identity == Config.USER) {
					res = userController.register(name, sex, phoneNum, email, birthday, password, cPassword);
					
				}
//				else if(identity == Config.MANAGER) {
//					res = managerController.register(name, phoneNum, email, birthday, password, cPassword);
//				}
				
				if(res == ErrCode.OK) {
					Object[] options = {"OK"};
			        JOptionPane.showOptionDialog(null,  
			        		ErrCode.errCodeToStr(res), "Message",  
			                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,   
			                options,   
			                options[0]); 
			        
					Register.this.dispose();
					new Login(identity);
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
				Register.this.dispose();
				new Login(identity);
			}
		});
	}

}
