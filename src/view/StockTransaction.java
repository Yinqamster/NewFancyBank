package view;

import controller.BankController;
import controller.UserController;
import model.Stock;
import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StockTransaction extends JFrame {

    UserController userController = UserController.getInstance();

    public StockTransaction(String username, int type, String str) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        int rows = type == Config.BUY ? 5 : 6;
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
        JLabel title = new JLabel();
        title.setFont(new Font("Helvetica",Font.PLAIN,35));
        titlePanel.add(title);
        titlePanel.setOpaque(false);
        titlePanel.setBounds(50, 80, 400, 50);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JLabel uname = new JLabel(username);
        uname.setFont(new Font("Helvetica",Font.PLAIN,15));
        panel.add(usernameLabel);
        panel.add(uname);

        //TODO get stock by stock id
        Stock stock;
        if(type == Config.BUY) {
            stock = BankController.getBank().getStockList().get(companyName);
        }
        else {
            stock = userController.getStock(username, transactionID);
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
        JLabel company = new JLabel(stock.getCompany());
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
        panel.add(savingAccountLabel);
        panel.add(accountList);

        JLabel sizeLabel = new JLabel("Quantity");
        sizeLabel.setFont(new Font("Helvetica",Font.PLAIN,15));
        JTextField size = new JTextField();
        if(type == Config.SELL) {
            //TODO add stock size
//            size.setText();
        }









        contentPanel.add(titlePanel);
//        contentPanel.add(scrollPane);
        contentPanel.add(background);

        getContentPane().add(contentPanel);

        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        int totalWidth = 500;
        int totalHeight = panel.getY() + panel.getHeight() + 50;
        totalHeight = totalHeight > 500 ? totalHeight : 500;
        int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
        int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;

        this.setTitle( "Bank ATM Stock Market" );
        this.setResizable(false);
        this.setSize(totalWidth, totalHeight);
        this.setLocation(locationX, locationY);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setVisible( true );
    }
}
