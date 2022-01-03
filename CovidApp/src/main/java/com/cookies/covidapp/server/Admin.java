/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp.server;

import java.util.ArrayList;

/**
 *
 * @author ptvin
 */
public class Admin extends CovidAccount {
    
     /*---Constructor---*/
    public Admin(String username, String password) {
        super(username, password);
    }

    public Admin() {
    }

    public Admin(String username) {
        super(username);
    }
    
    /*---Manager Management---*/
    
    public static ArrayList<Integer> getManagerIDList() {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_manager";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("managerID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getManagerNameList() {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_manager";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString("username"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String getManagerDetail(int managerID, String field) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_manager where managerID = " + managerID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getString(field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<Integer> getManagerHistory(int managerID) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_manager where managerID = " + managerID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("activity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
