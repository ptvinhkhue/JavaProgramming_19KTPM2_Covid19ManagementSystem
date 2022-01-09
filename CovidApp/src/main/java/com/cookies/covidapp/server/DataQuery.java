package com.cookies.covidapp.server;

import java.sql.*;

/**
 *
 * @author ptvin
 */
public class DataQuery {
    public Connection con = null;
    public Statement stm = null;
    public ResultSet rs = null;
    public ResultSet rsSub = null;
    
    public DataQuery() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/covid_management", "root", "tdht27092001");
            stm = con.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Unable to load driver class.");
            System.exit(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
