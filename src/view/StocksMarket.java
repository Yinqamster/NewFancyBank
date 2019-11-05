package view;

import controller.BankController;
import controller.UserController;
import model.Stock;
import utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class StocksMarket extends JFrame {

    public UserController userController = UserController.getInstance();
    public BankController managerController = BankController.getInstance();

    public StocksMarket(String username) {
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

        Set<Stock> allStocks = BankController.getBank().getStockList();
        int rows = allStocks == null || allStocks.size() == 0 ? 1 : allStocks.size() + 1;
        int scrollPaneHeight = rows * 25 > 300 ? 300 : rows * 25;
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
            for(Stock s : allStocks) {
                String companyName = s.getCompany();
                panel.add(new JLabel(companyName));
                panel.add(new JLabel(String.valueOf(s.getUnitPrice())));
                JButton buyButton = new JButton("Buy");
                panel.add(buyButton);
                buyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StocksMarket.this.dispose();
                        new StockTransaction(username, Config.BUY, companyName);
                    }
                });
            }
        }



        scrollPane.setViewportView(panel);



        contentPanel.add(titlePanel);
        contentPanel.add(scrollPane);
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
