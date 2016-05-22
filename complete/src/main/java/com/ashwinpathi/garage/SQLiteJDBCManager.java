package com.ashwinpathi.garage;
import java.sql.*;

public class SQLiteJDBCManager
{
    public static void main( String args[] )
    {
        SQLiteJDBCManager sqLiteJDBC = new SQLiteJDBCManager();
    }

    public SQLiteJDBCManager() {
        initializeSqliteDB();
    }

    private void initializeSqliteDB() {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:/tmp/test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE DOOR_OPEN_TIMES " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " STATE           TEXT    NOT NULL, " +
                    " TIME            INT     NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}