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

import utils.ErrCode;

public class Tools {
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
    
}