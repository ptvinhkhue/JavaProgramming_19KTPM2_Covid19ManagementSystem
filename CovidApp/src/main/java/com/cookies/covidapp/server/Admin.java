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
    
    public static ArrayList<Integer> getManagerHistoryID(int managerID) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_manager where managerID = " + managerID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("historyID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getManagerHistoryInfo(int managerID, String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_manager where managerID = " + managerID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static void lockManager(int managerID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "update acc_manager "
                    + "set locked = " + 1 + " where managerID = " + managerID;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void unlockManager(int managerID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "update acc_manager "
                    + "set locked = " + 0 + " where managerID =" + managerID;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
