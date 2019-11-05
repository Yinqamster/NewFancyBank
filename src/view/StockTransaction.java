package view;

import utils.Config;

import javax.swing.*;
import java.awt.*;

public class StockTransaction extends JFrame {

    public StockTransaction(String username, int type, String str) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);

        int rows = type == Config.BUY ? 8 : 7;
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
