/**
 * @author Group 5
 * @description  the interface for stock management
 * @system manager
 */

package view;

import controller.BankController;
import model.Stock;
import utils.Config;
import utils.ErrCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockManagement extends JFrame {

    public BankController managerController = BankController.getInstance();

    public StockManagement(String companyName) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        JLabel background = new JLabel();
        ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
        background.setIcon(bg);
        background.setBounds(0, 0, 500, 150);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Manage Stock");
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

        JPanel panel = new JPanel();
        panel.setBounds(40, 180, 400, 200);
        panel.setLayout(new GridLayout(4, 2, 10, 15));

        JLabel companyLabel = new JLabel("Company Name:");
        companyLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        companyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField company = new JTextField();
        company.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(companyLabel);
        panel.add(company);

        JLabel unitPriceLabel = new JLabel("Unit Price:");
        unitPriceLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        unitPriceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField unitPrice = new JTextField();
        unitPrice.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(unitPriceLabel);
        panel.add(unitPrice);


        JLabel soldCountLabel = new JLabel("Sold Out:");
        soldCountLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        soldCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel soldCount = new JLabel();
        soldCount.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(soldCountLabel);
        panel.add(soldCount);

        if(companyName != null && !companyName.isEmpty()) {
            company.setText(companyName);
            company.setEditable(false);
            Stock stock = BankController.getBank().getStockMap().get(companyName);
            soldCount.setText(String.valueOf(stock.getSoldCount()));
            unitPrice.setText(String.valueOf(stock.getUnitPrice()));
        }
        else {
            soldCount.setText("0");
        }

        JButton ok = new JButton("OK");
        ok.setFont(new Font("Helvetica",Font.PLAIN,15));
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(ok);
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

        this.setTitle( "Bank ATM Stock Management" );
        this.setResizable(false);
        this.setSize(totalWidth, totalHeight);
        this.setLocation(locationX, locationY);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setVisible( true );

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = -1;
                if(companyName != null && !companyName.isEmpty()) {
                    res = managerController.modifyStockPrice(companyName, unitPrice.getText());
                }
                else {
                    res = managerController.addNewStock(company.getText(), unitPrice.getText());
                }
                if(res == ErrCode.OK) {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(null,
                            ErrCode.errCodeToStr(res), "Message",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                            options,
                            options[0]);

                    StockManagement.this.dispose();
                    new StockMarket("", Config.MANAGER);
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
                StockManagement.this.dispose();
                new StockMarket("", Config.MANAGER);
            }
        });

        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                StockManagement.this.dispose();
                new StockMarket("", Config.MANAGER);
            }
        });

        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int res = managerController.logout(Config.MANAGERUSERNAME);
                if(res == ErrCode.OK) {
                    StockManagement.this.dispose();
                    new Login(Config.MANAGER);
                }
                else {
                    System.out.println("Logout Error");
                }
            }
        });
    }
}
