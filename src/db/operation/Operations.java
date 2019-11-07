/*
 * @Author: your name
 * @Date: 2019-11-05 20:14:51
 * @LastEditTime: 2019-11-05 20:54:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: \NewFancyBank\src\db\operation\Tools.java
 */
package db.operation;

import java.sql.*;
import java.util.HashMap;

import model.*;
import model.Date;
import utils.*;

public class Operations {
    // databse file path
    public static final String url = "jdbc:sqlite:src/db/atm.db";
    public static final String JDBCName = "org.sqlite.JDBC";

    // call this method firstly
    public static boolean testDBConnection(){
        if(getConnection() != null) return true;
        else return false;
    }

    public static Connection getConnection(){
        Connection c = null;
        try {
            Class.forName(JDBCName);
            c = DriverManager.getConnection(url);
        } catch ( Exception e ) {
            System.err.println(ErrCode.errCodeToStr(601));
        }
        return c;
    }

    // initiate data from database to memory
//    public static void initDataFromDB(){
//        // get connection
//        Connection c = getConnection();
//        try {
//
//        }catch (SQLException e){
//            System.err.println(ErrCode.errCodeToStr(602));
//        }
//    }

    public static HashMap<String, User> getUserListFromDB(){
        Connection c = getConnection();
        HashMap<String, User> userList = new HashMap<>();

        try {
            Statement stmt = c.createStatement();
            String sql = "SELECT * FROM userList;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // parse data
                String userName = rs.getString("nickName");
                String mname = rs.getString("middleName");
                Name name;
                if(mname == null){
                    name = new Name(rs.getString("firstName"), rs.getString("lastName"), rs.getString("nickName"));
                }
                else {
                    name = new Name(rs.getString("firstName"), rs.getString("middleName"),
                            rs.getString("lastName"), rs.getString("nickName"));
                }
                int sex = rs.getInt("sex");
                long phoneNumber = rs.getLong("phoneNumber");
                String email = rs.getString("email");
                Date dob = UtilFunction.stringToDate(rs.getString("dob"));
                String password = rs.getString("password");
                User user = new User(name, sex, phoneNumber, email, dob, password);

                // add to userList
                userList.put(userName, user);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e){
            System.err.println(ErrCode.errCodeToStr(602));
        }
        return userList;
    }

    public static int getUserIDFromDB(String userName){
        int userID = -1;
        Connection c = getConnection();
        Statement stmt = null;
        try {
            stmt = c.createStatement();
            String sql = "SELECT * FROM Names WHERE nickName='" + userName +"';";
            ResultSet rs = stmt.executeQuery(sql);
            userID = rs.getInt("userID");
            rs.close();
            stmt.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return userID;
        }
    }
}