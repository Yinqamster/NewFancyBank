/**
* @author Group 5
* @description  the interface for daily report
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
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import controller.BankController;
import model.DailyReport;
import model.Transaction;
import utils.Config;
import utils.ErrCode;

public class GetDailyReport extends JFrame{

	public BankController managerController = BankController.getInstance();
	
	public GetDailyReport() {
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		
		JLabel background = new JLabel();
		ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
		background.setIcon(bg);
		background.setBounds(0, 0, 1000, 150);

		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Daily  Report");
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
		logout.setBounds(939, 6, 35, 35);
		contentPanel.add(logout);
		
		DailyReport dailyReport = managerController.generateReport();
		
		
		int infoPanelRows = 1;
		JPanel infoPanel = new JPanel();
		infoPanel.setBounds(50, 180, 900, 25*infoPanelRows);
		infoPanel.setLayout(new GridLayout(infoPanelRows, 6, 0, 5));
		
		JLabel openAccountNumber = new JLabel("# Of Accounts: ");
		openAccountNumber.setFont(new Font("Helvetica", Font.PLAIN, 15));
		openAccountNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel oan = new JLabel(String.valueOf(dailyReport.getOpenAccountNum()));
		oan.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(openAccountNumber);
		infoPanel.add(oan);
		
		JLabel userLabel = new JLabel("# Of Users: ");
		userLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel usernum = new JLabel(String.valueOf(dailyReport.getUserNumber()));
		usernum.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(userLabel);
		infoPanel.add(usernum);
		
		JLabel trannumLabel = new JLabel("# Of Transactions: ");
		trannumLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		trannumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel trannum = new JLabel(String.valueOf(dailyReport.getTransactionNum()));
		trannum.setFont(new Font("Helvetica", Font.PLAIN, 15));
		infoPanel.add(trannumLabel);
		infoPanel.add(trannum);
		
		
		JPanel currencyInLabelPanel = new JPanel();
		currencyInLabelPanel.setBounds(50, infoPanel.getY() + infoPanel.getHeight() + 20, 900, 25);
		currencyInLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel currencyIn = new JLabel("Currency In: ");
		currencyIn.setFont(new Font("Helvetica", Font.PLAIN, 15));
		currencyInLabelPanel.add(currencyIn);
		
		Map<String, BigDecimal> currencyInList = dailyReport.getCurrencyIn();
		int currencyInPanelRows = currencyInList.size() == 0 ? 0 : currencyInList.size() + 1;
		JScrollPane currencyInScrollPanel = new JScrollPane();
		int currencyInScrollPaneHeight = 25*currencyInPanelRows > 50 ? 50 : 25*currencyInPanelRows;
		currencyInScrollPanel.setBounds(50, currencyInLabelPanel.getY() + currencyInLabelPanel.getHeight() + 5, 900, currencyInScrollPaneHeight);
		
		if(currencyInList.size() != 0) {
			JPanel currencyInPanel = new JPanel();
			currencyInPanel.setLayout(new GridLayout(currencyInPanelRows, 2, 10, 5));
		
			JLabel currency = new JLabel("Currency: ");
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel amount = new JLabel("Amount: ");
			amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
			currencyInPanel.add(currency);
			currencyInPanel.add(amount);
			for(Map.Entry<String, BigDecimal> b : currencyInList.entrySet()) {
				JLabel curr = new JLabel(b.getKey());
				JLabel num = new JLabel(String.valueOf(b.getValue()));
				currencyInPanel.add(curr);
				currencyInPanel.add(num);
			}
			currencyInScrollPanel.setViewportView(currencyInPanel);
		}
		
		JPanel currencyOutLabelPanel = new JPanel();
		currencyOutLabelPanel.setBounds(50, currencyInScrollPanel.getY() + currencyInScrollPanel.getHeight() + 20, 900, 25);
		currencyOutLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel currencyOut = new JLabel("Currency Out: ");
		currencyOut.setFont(new Font("Helvetica", Font.PLAIN, 15));
		currencyOutLabelPanel.add(currencyOut);
		
		Map<String, BigDecimal> currencyOutList = dailyReport.getCurrencyOut();
		int currencyOutPanelRows = currencyOutList.size() == 0 ? 0 : currencyOutList.size() + 1;
		JScrollPane currencyOutScrollPanel = new JScrollPane();
		int currencyOutScrollPaneHeight = 25*currencyOutPanelRows > 50 ? 50 : 25*currencyOutPanelRows;
		currencyOutScrollPanel.setBounds(50, currencyOutLabelPanel.getY() + currencyOutLabelPanel.getHeight() + 5, 900, currencyOutScrollPaneHeight);
		
		if(currencyOutList.size() != 0) {
			JPanel currencyOutPanel = new JPanel();
			currencyOutPanel.setLayout(new GridLayout(currencyOutPanelRows, 2, 10, 5));
		
			JLabel currency = new JLabel("Currency: ");
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel amount = new JLabel("Amount: ");
			amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
			currencyOutPanel.add(currency);
			currencyOutPanel.add(amount);
			for(Map.Entry<String, BigDecimal> b : currencyOutList.entrySet()) {
				JLabel curr = new JLabel(b.getKey());
				JLabel num = new JLabel(String.valueOf(b.getValue()));
				currencyOutPanel.add(curr);
				currencyOutPanel.add(num);
			}
			currencyOutScrollPanel.setViewportView(currencyOutPanel);
		}
		
		JPanel serviceChargeLabelPanel = new JPanel();
		serviceChargeLabelPanel.setBounds(50, currencyOutScrollPanel.getY() + currencyOutScrollPanel.getHeight() + 20, 900, 25);
		serviceChargeLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel serviceCharge = new JLabel("Service Charge: ");
		serviceCharge.setFont(new Font("Helvetica", Font.PLAIN, 15));
		serviceChargeLabelPanel.add(serviceCharge);
		
		Map<String, BigDecimal> serviceChargeList = dailyReport.getServiceCharge();
		int serviceChargePanelRows = serviceChargeList.size() == 0 ? 0 : serviceChargeList.size() + 1;
		JScrollPane serviceChargeScrollPanel = new JScrollPane();
		int serviceChargeScrollPaneHeight = 25*serviceChargePanelRows > 50 ? 50 : 25*serviceChargePanelRows;
		serviceChargeScrollPanel.setBounds(50, serviceChargeLabelPanel.getY() + serviceChargeLabelPanel.getHeight() + 5, 900, serviceChargeScrollPaneHeight);
		
		if(serviceChargeList.size() != 0) {
			JPanel serviceChargePanel = new JPanel();
			serviceChargePanel.setLayout(new GridLayout(serviceChargePanelRows, 2, 10, 5));
		
			JLabel currency = new JLabel("Currency: ");
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel amount = new JLabel("Amount: ");
			amount.setFont(new Font("Helvetica", Font.PLAIN, 15));
			serviceChargePanel.add(currency);
			serviceChargePanel.add(amount);
			for(Map.Entry<String, BigDecimal> b : serviceChargeList.entrySet()) {
				JLabel curr = new JLabel(b.getKey());
				JLabel num = new JLabel(String.valueOf(b.getValue()));
				serviceChargePanel.add(curr);
				serviceChargePanel.add(num);
			}
			serviceChargeScrollPanel.setViewportView(serviceChargePanel);
		}
		
		JPanel transactionLabelPanel = new JPanel();
		transactionLabelPanel.setBounds(50, serviceChargeScrollPanel.getY() + serviceChargeScrollPanel.getHeight() + 20, 900, 25);
		transactionLabelPanel.setLayout(new GridLayout(1, 1, 10, 5));
		JLabel transaction = new JLabel("Transaction: ");
		transaction.setFont(new Font("Helvetica", Font.PLAIN, 15));
		transactionLabelPanel.add(transaction);
		
		List<Transaction> transactionList = dailyReport.getTransactions();
		int transactionPanelRows = dailyReport.getTransactionNum() == 0 ? 0 : dailyReport.getTransactionNum() + 1;
		JScrollPane transactionScrollPanel = new JScrollPane();
		int transactionScrollPaneHeight = 30*transactionPanelRows > 150 ? 150 : 30*transactionPanelRows;
		transactionScrollPanel.setBounds(50, transactionLabelPanel.getY() + transactionLabelPanel.getHeight() + 5, 900, transactionScrollPaneHeight);

		if(transactionList != null && transactionList.size() != 0) {
			JPanel transactionPanel = new JPanel();
			transactionPanel.setLayout(new GridLayout(transactionPanelRows, 10, 10, 5));
			
			JLabel username = new JLabel("Username");
			username.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel transactionID = new JLabel("ID");
			transactionID.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel transactionType = new JLabel("Type");
			transactionType.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel currency = new JLabel("Currency");
			currency.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel from = new JLabel("From");
			from.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel to = new JLabel("To");
			to.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel num = new JLabel("Amount");
			num.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel serviceFee = new JLabel("Fee");
			serviceFee.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel remarks = new JLabel("Remark");
			remarks.setFont(new Font("Helvetica", Font.PLAIN, 15));
			JLabel date = new JLabel("Time");
			date.setFont(new Font("Helvetica", Font.PLAIN, 15));
			
			JLabel operation = new JLabel("Operation: ");
			operation.setFont(new Font("Helvetica", Font.PLAIN, 15));
			transactionPanel.add(username);
			transactionPanel.add(transactionID);
			transactionPanel.add(transactionType);
			transactionPanel.add(currency);
			transactionPanel.add(from);
			transactionPanel.add(to);
			transactionPanel.add(num);
			transactionPanel.add(serviceFee);
			transactionPanel.add(remarks);
			transactionPanel.add(date);
			for(Transaction t : transactionList) {
				transactionPanel.add(new JLabel(t.getUsername()));
				transactionPanel.add(new JLabel(t.getTransactionId()));
				transactionPanel.add(new JLabel(t.getTransactionTypeStr()));
				transactionPanel.add(new JLabel(t.getCurrency()));
				switch (t.getTransactionType()) {
				case Config.DEPOSIT:
				case Config.OPENACCOUNT:
					transactionPanel.add(new JLabel("Cash"));
					transactionPanel.add(new JLabel(t.getToAccountNumber()));
					break;
				case Config.WITHDRAW:
					transactionPanel.add(new JLabel(t.getFromAccountNumber()));
					transactionPanel.add(new JLabel("Cash"));
					break;
				case Config.TRANSFEROUT:
				case Config.RECEIVE:
				case Config.BUY:
				case Config.SELL:
					transactionPanel.add(new JLabel(t.getFromAccountNumber()));
					transactionPanel.add(new JLabel(t.getToAccountNumber()));
					break;
				case Config.PAYFORLOAN:
					transactionPanel.add(new JLabel(t.getFromAccountNumber()));
					transactionPanel.add(new JLabel("Loan"));
				break;
				case Config.SAVINGACCOUNTINTEREST:
					transactionPanel.add(new JLabel("Bank"));
					transactionPanel.add(new JLabel(t.getToAccountNumber()));
					break;

				default:
					transactionPanel.add(new JLabel());
					transactionPanel.add(new JLabel());
					break;
				}
				transactionPanel.add(new JLabel(String.valueOf(t.getNum())));
				transactionPanel.add(new JLabel(String.valueOf(t.getServiceCharge())));
				transactionPanel.add(new JLabel(t.getRemarks()));
				transactionPanel.add(new JLabel(t.getDate().toTimeString()));
			}
			transactionScrollPanel.setViewportView(transactionPanel);
		}
		
		
		
		contentPanel.add(titlePanel);
		contentPanel.add(infoPanel);
		contentPanel.add(currencyInLabelPanel);
		contentPanel.add(currencyInScrollPanel);
		contentPanel.add(currencyOutLabelPanel);
		contentPanel.add(currencyOutScrollPanel);
		contentPanel.add(serviceChargeLabelPanel);
		contentPanel.add(serviceChargeScrollPanel);
		contentPanel.add(transactionLabelPanel);
		contentPanel.add(transactionScrollPanel);
		contentPanel.add(background);
		
		getContentPane().add(contentPanel);
		
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		int totalWidth = 1000;
		int totalHeight = transactionScrollPanel.getY() + transactionScrollPanel.getHeight() + 50;
		totalHeight = totalHeight > 800 ? totalHeight : 800;
		int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
		int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;
		
		this.setTitle( "Bank ATM Daily Report" );
		this.setResizable(false);
		this.setSize(totalWidth, totalHeight);
		this.setLocation(locationX, locationY); 
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE); 
		this.setVisible( true );
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GetDailyReport.this.dispose();
				new ManagerInterface(Config.MANAGERUSERNAME);
			}
		});
		
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int res = managerController.logout(Config.MANAGERUSERNAME);
				if(res == ErrCode.OK) {
					GetDailyReport.this.dispose();
					new Login(Config.MANAGER);
				}
				else {
					System.out.println("Logout Error");
				}
			}
		});
	}
}
