package db.operation;

import java.sql.*;

public class DBTest {
    public static void main( String args[] )
    {
        String url = "jdbc:sqlite:src/db/atm.db";
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection(url);
            DatabaseMetaData meta = c.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
