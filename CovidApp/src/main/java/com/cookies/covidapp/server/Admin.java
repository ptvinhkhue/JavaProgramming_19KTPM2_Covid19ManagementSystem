/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp.server;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
    
    /*---Get Data from Database---*/
    public static ArrayList<Integer> getIntField(String table, String field) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from " + table;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getStringField(String table, String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from " + table;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
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
    
    public static int existedManager(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_manager";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (db.rs.getString("username") == null ? username == null : db.rs.getString("username").equals(username)) {
                    return db.rs.getInt("managerID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public static void createManager(String username, String password) {
        try {
            DataQuery db = new DataQuery();
            String hassPass = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                    
            String sql = "insert into acc_covid (username, password, type) values ('" + username + "', '" + hassPass + "', " + 2 + ")";
            db.stm.executeUpdate(sql);
            
            sql = "insert into acc_manager (username, locked) values ('" + username + "', " + 0 + ")";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*---Place Management---*/
    public static ArrayList<Integer> getPlaceIntList(String field) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getPlaceStringList(String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static String getPlaceDetail(int placeID, String field) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place where placeID = " + placeID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getString(field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static int existedPlace(String name) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (db.rs.getString("name") == null ? name == null : db.rs.getString("name").equals(name)) {
                    return db.rs.getInt("placeID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public static void createPlace(String name, int capacity, int current) {
        try {
            // create place
            DataQuery db = new DataQuery();
            String sql = "insert into place (name, capacity, current) values ('"
                    + name + "'," + capacity + ", " + current + ")";
            db.stm.executeUpdate(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlace(int placeID, String name, int capacity, int current) {
        try {
            // update place
            DataQuery db = new DataQuery();
            String sql = "update place "
                    + "set name ='" + name + "', capacity =" + capacity + ", current =" + current + " where placeID =" + placeID;
            db.stm.executeUpdate(sql);
            
            // handle history
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean occupiedPlace(int placeID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select count(*) as total from acc_user where placeID =" + placeID;
            db.rs = db.stm.executeQuery(sql);
            
            // check occupied
            if (db.rs.next()) {
                //System.out.println(db.rs.getInt("total"));
                if (db.rs.getInt("total") > 0) return true;
                else return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static boolean historicPlace(int placeID) {
        try {
            DataQuery db = new DataQuery();
            boolean historic = false;
            
            // check old history
            String sql = "select count(*) as total from history_place where placeOldID =" + placeID;
            db.rs = db.stm.executeQuery(sql);
            if (db.rs.next()) {
                //System.out.println(db.rs.getInt("total"));
                if (db.rs.getInt("total") > 0) historic = true;
            }
            
            // check new history
            sql = "select count(*) as total from history_place where placeNewID =" + placeID;
            db.rs = db.stm.executeQuery(sql);
            if (db.rs.next()) {
                //System.out.println(db.rs.getInt("total"));
                if (db.rs.getInt("total") > 0) historic = true;
            }
            
            if (historic) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static void deletePlace(int placeID) {
        try {
            // delete history
            DataQuery db = new DataQuery();
            String sql = "delete from place where placeID =" + placeID;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}