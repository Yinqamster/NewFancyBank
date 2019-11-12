/**
 * @author Group 5
 * @description  the interface for security account
 * @system user
 */

package view;

import controller.BankController;
import controller.UserController;
import model.HoldingStock;
import model.SecurityAccount;
import utils.Config;
import utils.ErrCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;

public class SecurityAccountDetail extends JFrame {

    public UserController userController = UserController.getInstance();

    public SecurityAccountDetail(String username, String accNum) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        JLabel background = new JLabel();
        ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
        background.setIcon(bg);
        background.setBounds(0, 0, 500, 150);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Security Account Detail");
        title.setFont(new Font("Helvetica", Font.PLAIN,35));
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

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(2, 2));
        infoPanel.setBounds(40, 180, 400, 50);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Helvetica", Font.PLAIN,15));
        JLabel uname = new JLabel(username);
        uname.setFont(new Font("Helvetica", Font.PLAIN,15));
        infoPanel.add(usernameLabel);
        infoPanel.add(uname);

        JLabel accountLabel = new JLabel("Account:");
        accountLabel.setFont(new Font("Helvetica", Font.PLAIN,15));
        JComboBox<String> account = new JComboBox<String>();
        List<String> securityAccount = userController.getAccountList(username, Config.SECURITYACCOUNT);
        for(String s : securityAccount) {
            account.addItem(s);
        }
        if(accNum != null && !accNum.isEmpty()) {
            account.setSelectedItem(accNum);
        }
        account.setFont(new Font("Helvetica", Font.PLAIN,15));
        infoPanel.add(accountLabel);
        infoPanel.add(account);


        String accountNum = account.getSelectedItem().toString();
        Map<String, HoldingStock> allStocks = ((SecurityAccount)userController.getAccountDetail(username, accountNum)).getStockList();
        int rows = allStocks == null || allStocks.size() == 0 ? 1 : allStocks.size() + 1;
        int scrollPaneHeight = rows * 30 > 300 ? 300 : rows * 30 + 10;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, infoPanel.getY() + infoPanel.getHeight() + 20, 400, scrollPaneHeight);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 5, 5,5));

        JLabel IDLabel = new JLabel("ID");
        IDLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel companyLabel = new JLabel("Company");
        companyLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel currentPriceLabel = new JLabel("Current Price");
        currentPriceLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel buyInPriceLabel = new JLabel("Buy Price");
        buyInPriceLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel operationLabel = new JLabel("Operation");
        operationLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));

        panel.add(IDLabel);
        panel.add(companyLabel);
        panel.add(amountLabel);
        panel.add(currentPriceLabel);
        panel.add(buyInPriceLabel);
        panel.add(operationLabel);

        if(allStocks != null && allStocks.size() != 0) {
            for(Map.Entry<String, HoldingStock> s : allStocks.entrySet()) {
                String tranID = s.getKey();
                panel.add(new JLabel(tranID));
                panel.add(new JLabel(s.getValue().getCompanyName()));
                panel.add(new JLabel(String.valueOf(s.getValue().getNumber())));
                panel.add(new JLabel(String.valueOf(BankController.getBank().getStockMap().get(s.getValue().getCompanyName()).getUnitPrice())));
                panel.add(new JLabel(String.valueOf(s.getValue().getBuyInPirce())));
                JButton operationButton = new JButton("Sell");
                panel.add(operationButton);
                operationButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SecurityAccountDetail.this.dispose();
                        new StockTransaction(username, Config.SELL, tranID, "", account.getSelectedItem().toString());
                    }
                });
            }
        }

        scrollPane.setViewportView(panel);


        contentPanel.add(titlePanel);
        contentPanel.add(infoPanel);
        contentPanel.add(scrollPane);
        contentPanel.add(background);

        getContentPane().add(contentPanel);

        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        int totalWidth = 500;
        int totalHeight = scrollPane.getY() + scrollPane.getHeight() + 50;
        totalHeight = totalHeight > 500 ? totalHeight : 500;
        int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
        int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;

        this.setTitle( "Bank ATM Account Detail" );
        this.setResizable(false);
        this.setSize(totalWidth, totalHeight);
        this.setLocation(locationX, locationY);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setVisible( true );


        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SecurityAccountDetail.this.dispose();
                new SecurityAccountDetail(username, account.getSelectedItem().toString());
            }
        });

        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                SecurityAccountDetail.this.dispose();
                new UserInterface(username);
            }
        });

        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int res = userController.logout(username);
                if(res == ErrCode.OK) {
                    SecurityAccountDetail.this.dispose();
                    new Login(Config.USER);
                }
                else {
                    System.out.println("Logout Error");
                }
            }
        });
    }
}
