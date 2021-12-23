/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp;

/**
 *
 * @author ptvin
 */
public class Admin extends CovidAccount {
    
    public Admin(String username, String password) {
        super(username, password);
    }
    
    public void signup() {
        try {
            DataQuery db = new DataQuery();
            String sql1 = "insert into acc_covid values ('" + username + "','" + password + "'," + 1 + ")";
            String sql2 = "insert into acc_admin (username) values ('" + username + "'";
            db.stm.executeUpdate(sql1);
            db.stm.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void createManager(String username, String password) {
        try {
            DataQuery db = new DataQuery();
            String sql1 = "insert into acc_covid values ('" + username + "','" + password + "'," + 2 + ")";
            String sql2 = "insert into acc_manager (username) values ('" + username + "'";
            db.stm.executeUpdate(sql1);
            db.stm.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
