/**
* @author Group 5
* @description  the interface for customer checking
* @system manager
*/
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.BankController;
import controller.UserController;
import model.User;
import utils.Config;
import utils.ErrCode;
import javax.swing.SwingConstants;

public class CheckCustomer extends JFrame{
	
	public UserController userController = UserController.getInstance();
	public BankController managerController = BankController.getInstance();
	
	public CheckCustomer(List<User> customerList){
	
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 1000, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Check Customer");
		title.setFont(new Font("Helvetica",Font.PLAIN,35));
		titlePanel.add(title);
		titlePanel.setOpaque(false);
		titlePanel.setBounds(300, 80, 400, 50);
		
		JButton back = new JButton("");
		back.setIcon(new ImageIcon(Config.ROOT + "back.png"));
		back.setBounds(6, 6, 35, 35);
		contentPanel.add(back);
		
		JButton logout = new JButton("");
		logout.setIcon(new ImageIcon(Config.ROOT + "logout.png"));
		logout.setBounds(936, 6, 35, 35);
		contentPanel.add(logout);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(50, 180, 900, 30);
		searchPanel.setLayout(new GridLayout(1, 7, 10, 5));
		
		JLabel uname = new JLabel("Username: ");
		uname.setHorizontalAlignment(SwingConstants.RIGHT);
		uname.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JTextField name = new JTextField();
		name.setFont(new Font("Helvetica", Font.PLAIN, 15));
		searchPanel.add(uname);
		searchPanel.add(name);
		JLabel sortBy = new JLabel("Sort By: ");
		sortBy.setHorizontalAlignment(SwingConstants.RIGHT);
		sortBy.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JComboBox<String> sortCondition = new JComboBox<String>();
		sortCondition.setFont(new Font("Helvetica", Font.PLAIN, 15));
		sortCondition.addItem("ID");
		sortCondition.addItem("# of Loan");
		sortCondition.addItem("# of Acc");
		searchPanel.add(sortBy);
		searchPanel.add(sortCondition);
		JLabel sort = new JLabel("Sort: ");
		sort.setHorizontalAlignment(SwingConstants.RIGHT);
		sort.setFont(new Font("Helvetica", Font.PLAIN, 15));
		JComboBox<String> sortOrder = new JComboBox<String>();
		sortOrder.setFont(new Font("Helvetica", Font.PLAIN, 15));
		sortOrder.addItem("Desc");
		sortOrder.addItem("Asc");
		searchPanel.add(sort);
		searchPanel.add(sortOrder);
		JButton search = new JButton("Search");
		searchPanel.add(search);
		
		
		JPanel userPanel = new JPanel();
		int userPanelRows = customerList == null || customerList.size() == 0 ? 0 : customerList.size() + 1;
		JScrollPane scrollPane = new JScrollPane();
		int scrollPaneHeight = 30*userPanelRows > 240 ? 240 : 30*userPanelRows;
		scrollPane.setBounds(50, 240, 900, scrollPaneHeight);

		
		
		userPanel.setLayout(new GridLayout(userPanelRows, 6, 0, 0));
		if(customerList.size() != 0) {
			JLabel IDLabel = new JLabel("ID: ");
			IDLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(IDLabel);
			
			JLabel usernameLabel = new JLabel("Username: ");
			usernameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(usernameLabel);
			
			JLabel truenameLabel = new JLabel("True Name: ");
			truenameLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(truenameLabel);
			
			JLabel accountNumLabel = new JLabel("Number of Account: ");
			accountNumLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(accountNumLabel);
			
			JLabel loanNumLabel = new JLabel("Number of Loan: ");
			loanNumLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(loanNumLabel);
			
			JLabel operationLabel = new JLabel("Oeration: ");
			operationLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
			userPanel.add(operationLabel);
			
			for(User user : customerList) {
				JLabel ID = new JLabel(String.valueOf(user.getID()));
				ID.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(ID);
				
				JLabel username = new JLabel(user.getName().getNickName());
				username.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(username);
				
				JLabel truename = new JLabel(user.getName().getFullName());
				truename.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(truename);
				
				JLabel accountNum = new JLabel(String.valueOf(user.getAccounts().size()));
				if(user.getAccounts() == null) {
					accountNum.setText("0");
				}
				else {
					accountNum.setText(String.valueOf(user.getAccounts().size()));
				}
				accountNum.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(accountNum);
				
				JLabel loanNum = new JLabel();
				if(user.getLoanList() == null) {
					loanNum.setText("0");
				}
				else {
					loanNum.setText(String.valueOf(user.getLoanList().size()));
				}
				loanNum.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(loanNum);
				
				JButton operation = new JButton("Detail>>");
				operation.setFont(new Font("Helvetica", Font.PLAIN, 15));
				userPanel.add(operation);
				
				operation.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						CheckCustomer.this.dispose();
						new UserDetail(user.getName().getNickName(), null);
					}
				});
			}
		}
		
		scrollPane.setViewportView(userPanel);
		
		contentPanel.add(titlePanel);
		contentPanel.add(searchPanel);
		contentPanel.add(scrollPane);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 1000;
		int totalHeight = scrollPane.getY() + scrollPane.getHeight() + 50;
		totalHeight = totalHeight > 500 ? totalHeight : 500;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Check Customer" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int sc = Config.SORTBYID;
				int so = Config.DESC;
				switch (sortCondition.getSelectedItem().toString()) {
				case "ID":
					sc = Config.SORTBYID;
					break;
				case "# of Loan":
					sc = Config.SORTBYLOANSIZE;
					break;
				case "# of Acc":
					sc = Config.SORTBYACCOUNTSIZE;
					break;

				default:
					break;
				}
				switch (sortOrder.getSelectedItem().toString()) {
				case "Asc":
					so = Config.ASC;
					break;
				case "Desc":
					so = Config.DESC;
					break;

				default:
					break;
				}
				
				CheckCustomer.this.dispose();
				new CheckCustomer(managerController.getUsersByCondition(name.getText(), sc, so));
			}
		});
		
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CheckCustomer.this.dispose();
				new ManagerInterface(Config.MANAGERUSERNAME);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.logout(Config.MANAGERUSERNAME);
				if(res == ErrCode.OK) {
					CheckCustomer.this.dispose();
					new Login(Config.MANAGER);
				}
				else {
					System.out.println("Logout Error");
				}
			}
		});
		
	}
}
