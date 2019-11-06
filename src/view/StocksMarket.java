package view;

import controller.BankController;
import controller.UserController;
import model.Stock;
import utils.Config;
import utils.ErrCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class StocksMarket extends JFrame {

    public UserController userController = UserController.getInstance();
    public BankController managerController = BankController.getInstance();

    public StocksMarket(String username, String identity) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        JLabel background = new JLabel();
        ImageIcon bg=new ImageIcon(Config.ROOT + "login_background.png");
        background.setIcon(bg);
        background.setBounds(0, 0, 500, 150);

        JPanel titlePanel = new JPanel();
        JLabel title = new JLabel("Stock Market");
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



        Map<String, Stock> allStocks = BankController.getBank().getStockMap();
        int rows = allStocks == null || allStocks.size() == 0 ? 1 : allStocks.size() + 1;
        int scrollPaneHeight = rows * 30 > 300 ? 300 : rows * 30;
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 180, 400, scrollPaneHeight);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(rows, 3));

        JLabel companyLabel = new JLabel("Company");
        companyLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel unitPriceLabel = new JLabel("Unit Price");
        unitPriceLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));
        JLabel operationLabel = new JLabel("Operation");
        operationLabel.setFont(new Font("Helvetica", Font.PLAIN, 15));

        panel.add(companyLabel);
        panel.add(unitPriceLabel);
        panel.add(operationLabel);

        if(allStocks != null && allStocks.size() != 0) {
            for(Stock s : allStocks.values()) {
                String companyName = s.getCompany();
                panel.add(new JLabel(companyName));
                panel.add(new JLabel(String.valueOf(s.getUnitPrice())));
                JButton operationButton = new JButton();
                if(identity.equals(Config.USER)) {
                    operationButton.setText("Buy");
                }
                else if(identity.equals(Config.MANAGER)) {
                    operationButton.setText("Edit");
                }
                panel.add(operationButton);
                operationButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StocksMarket.this.dispose();
                        if(identity.equals(Config.USER)) {
                            new StockTransaction(username, Config.BUY, companyName);
                        }
                        else if(identity.equals(Config.MANAGER)) {
                            new StockManagement(companyName);
                        }


                    }
                });
            }
        }

        scrollPane.setViewportView(panel);


        JPanel addStockPanel = new JPanel(new GridLayout(1,4));
        addStockPanel.setBounds(200, scrollPane.getY()+scrollPane.getHeight() + 20, 100, 25);
        JButton addStock = new JButton("Add");
        addStock.setFont(new Font("Helvetica", Font.PLAIN,15));
        addStockPanel.add(addStock);


        contentPanel.add(titlePanel);
        contentPanel.add(scrollPane);
        if(identity.equals(Config.MANAGER)) {
            contentPanel.add(addStockPanel);
        }
        contentPanel.add(background);

        getContentPane().add(contentPanel);

        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        int totalWidth = 500;
        int totalHeight = scrollPane.getY() + scrollPane.getHeight() + 60;
        totalHeight = totalHeight > 500 ? totalHeight : 500;
        int locationX = (int)screenSize.getWidth()/2 - totalWidth/2;
        int locationY = (int)screenSize.getHeight()/2 - totalHeight/2;

        this.setTitle( "Bank ATM Stock Market" );
        this.setResizable(false);
        this.setSize(totalWidth, totalHeight);
        this.setLocation(locationX, locationY);
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        this.setVisible( true );



        addStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StocksMarket.this.dispose();
                new StockManagement("");
            }
        });

        back.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                StocksMarket.this.dispose();
                if(identity.equals(Config.USER)) {
                    new UserInterface(username);
                }
                else if(identity.equals(Config.MANAGER)) {
                    new ManagerInterface(Config.MANAGERUSERNAME);
                }
            }
        });

        logout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                int res = -1;
                if(identity.equals(Config.USER)) {
                    res = userController.logout(username);
                }
                else if(identity.equals(Config.MANAGER)) {
                    res = managerController.logout(Config.MANAGERUSERNAME);
                }
                if(res == ErrCode.OK) {
                    StocksMarket.this.dispose();
                    new Login(identity);
                }
                else {
                    System.out.println("Logout Error");
                }
            }
        });
    }
}
