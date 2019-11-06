package view;

import controller.BankController;
import controller.UserController;
import model.HoldingStock;
import model.Stock;
import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

public class StockTransaction extends JFrame {

    UserController userController = UserController.getInstance();

    //str: company name is type is buy, transaction id if type is sell
    public StockTransaction(String username, int type, String str, String accNum) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        int rows = type == Config.BUY ? 6 : 9;
        String companyName = "";
        String transactionID = "";
        if(type == Config.BUY) {
            companyName = str;
        }
        else if(type == Config.SELL) {
            transactionID = str;
        }
        JPanel panel = new JPanel();
        panel.setBounds(40, 180, 400, 50*rows);
        panel.setLayout(new GridLayout(rows, 2, 10, 15));

        JLabel background = new JLabel();
        ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
        background.setIcon(bg);
        background.setBounds(0, 0, 500, 150);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Stock Transaction");
        title.setFont(new Font("Helvetica",Font.PLAIN,35));
        titlePanel.add(title);
        titlePanel.setOpaque(false);
        titlePanel.setBounds(50, 80, 400, 50);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel uname = new JLabel(username);
        uname.setFont(new Font("Helvetica",Font.PLAIN,15));

        //TODO get stock by stock id
        Stock stock;
        HoldingStock holdingStock;
        String cName;
        String cPrice;
        String s = "0";
        String biP = "0";

        if(type == Config.BUY) {
            stock = BankController.getBank().getStockMap().get(companyName);
            cName = stock.getCompany();
            cPrice = String.valueOf(stock.getUnitPrice());
        }
        else {
//            holdingStock = userController.getHoldingStock(username, transactionID);
            holdingStock = new HoldingStock("", BigDecimal.ZERO, BigDecimal.ZERO);
            stock = BankController.getBank().getStockMap().get(holdingStock.getCompanyName());
            cName = holdingStock.getCompanyName();
            cPrice = String.valueOf(stock.getUnitPrice());
            s = String.valueOf(holdingStock.getNumber());
            biP = String.valueOf(holdingStock.getBuyInPirce());
        }

        JLabel tranIDLabel = new JLabel("Transaction ID: ");
        tranIDLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel tranID = new JLabel();
        tranID.setFont(new Font("Helvetica",Font.PLAIN,15));
        if(type == Config.SELL) {
            panel.add(tranIDLabel);
            panel.add(tranID);
        }


        JLabel companyLabel = new JLabel("Company: ");
        companyLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel company = new JLabel(cName);
        company.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(companyLabel);
        panel.add(company);

        List<String> savingAccounts = userController.getAccountList(username, Config.SAVINGACCOUNT);
        JLabel savingAccountLabel = new JLabel("Saving Account");
        savingAccountLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JComboBox<String> accountList = new JComboBox<String>();
        for(String c : savingAccounts) {
            accountList.addItem(c);
        }
        if(accNum != null && !accNum.isEmpty()) {
            accountList.setSelectedItem(accNum);
        }
        panel.add(savingAccountLabel);
        panel.add(accountList);

        JLabel priceLabel = new JLabel("Current Price");
        priceLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel price = new JLabel(cPrice);
        price.setFont(new Font("Helvetica",Font.PLAIN,15));

        JLabel sizeLabel = new JLabel("Quantity");
        sizeLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JTextField size = new JTextField();
        if(type == Config.SELL) {
            size.setText(s);
        }

        JLabel sellSizeLabel = new JLabel("Sell");
        sellSizeLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JTextField sellSize = new JTextField();

        JLabel buyInPriceLabel = new JLabel("Buy In Price");
        buyInPriceLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel buyInPrice = new JLabel(biP);
        buyInPrice.setFont(new Font("Helvetica",Font.PLAIN,15));

        JButton ok = new JButton("OK");
        ok.setFont(new Font("Helvetica",Font.PLAIN,15));
        JButton cancel = new JButton("Cancel");
        cancel.setFont(new Font("Helvetica",Font.PLAIN,15));

        panel.add(usernameLabel);
        panel.add(uname);
        if(type == Config.SELL) {
            panel.add(tranIDLabel);
            panel.add(tranID);
        }
        panel.add(companyLabel);
        panel.add(company);
        panel.add(priceLabel);
        panel.add(price);
        if(type == Config.SELL) {
            panel.add(buyInPriceLabel);
            panel.add(buyInPrice);
        }
        panel.add(savingAccountLabel);
        panel.add(accountList);
        panel.add(sizeLabel);
        panel.add(size);
        if(type == Config.SELL) {
            panel.add(sellSizeLabel);
            panel.add(sellSize);
        }
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

        this.setTitle( "Bank ATM Stock Transaction" );
        this.setResizable(false);
        this.setSize(totalWidth, totalHeight);
        this.setLocation(locationX, locationY);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setVisible( true );

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO invoke user controller
            }
        });


        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockTransaction.this.dispose();
                new StockMarket(username, Config.USER);
            }
        });

        accountList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockTransaction.this.dispose();
                new StockTransaction(username, type, str, accountList.getSelectedItem().toString());
            }
        });
    }
}
