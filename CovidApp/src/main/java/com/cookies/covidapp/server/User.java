package com.cookies.covidapp.server;

import Necessity.Necessity;
import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class User extends CovidAccount {

    public User(String username, String password) {
        super(username, password);
    }

    public User(String username) {
        super(username);
    }
    
    public static boolean checkFirstTime(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select loggedIn from acc_user where username = " + username;
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            if (db.rs.getInt("loggedIn") == 0) return true;
            else return false;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void setPassword(String username, String password) {

        try {
            String passHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            DataQuery db = new DataQuery();
            String sql = "update acc_covid "
                        + "set password = '" + passHash + "' where username = '" + username + "'";
            db.stm.executeUpdate(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setLoggedIn(String username) {

        try {
            int loggedIn = 1;
            DataQuery db = new DataQuery();
            String sql = "update acc_user "
                        + "set loggedIn = '" + loggedIn + "' where username = '" + username + "'";
            db.stm.executeUpdate(sql);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> getUserIntList(String field) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt(field));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String getUserDetail(int userID, String field) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getString(field);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String getUserAddress(int userID) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);
            while (db.rs.next()) {
                sql = "select * from address where addressID = " + db.rs.getInt("addressID");
                db.rs = db.stm.executeQuery(sql);

                while (db.rs.next()) {
                    ret = "Address: " + db.rs.getString("ward") + ", " + db.rs.getString("district") + ", " + db.rs.getString("province");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static String getUserPlace(int userID) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                sql = "select * from place where placeID = " + db.rs.getInt("PlaceID");
                db.rs = db.stm.executeQuery(sql);
                while (db.rs.next()) {
                    ret = "Place: " + db.rs.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static String getFullPlace(int placeID) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place where placeID = " + placeID;
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            ret = db.rs.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static ArrayList<Integer> getUserRelation(int userID) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from relation where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("relatedID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static int getID() {
        try {
            DataQuery db = new DataQuery();
            String sql = "select userID from acc_user where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            while (db.rs.next()) {
                return db.rs.getInt("userID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static ArrayList<String> displayStatusHistory(int userID) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_status where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {  
                    ret.add("F" + db.rs.getString("statusOld") + " -> F" + db.rs.getString("statusNew"));    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getTimeStatusHistory(int userID) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_status where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {  
                    ret.add(db.rs.getString("datetime"));    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getOldPlaceList(int userID) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_place where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {  
                    ret.add("Old: " + getFullPlace(db.rs.getInt("placeOldID")) + " | " + db.rs.getString("datetime"));    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getNewPlaceList(int userID) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_place where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {  
                    ret.add("New: " +getFullPlace(db.rs.getInt("placeNewID")));    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public void displayNecessityHistory(int userID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from order join order_detail on order.orderID = order_detail.orderID where userID =" + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.print("Order: " + db.rs.getInt("orderID") + ". ");
                System.out.println("Sum: " + db.rs.getInt("sum"));
                System.out.print("Necessity: " + db.rs.getString("nesscityID") + ". ");
                System.out.println("Amount: " + db.rs.getString("amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Integer> getOrderIntList(int userID) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from covid_management.order where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("orderID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getOrderInfo(int userID, String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from covid_management.order where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if ("orderID".equals(field))
                {
                    ret.add("Order " + db.rs.getInt("orderID"));
                }
                else if ("sum".equals(field))
                {
                    ret.add("Total: " + db.rs.getInt("sum"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getSumOrder() {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from covid_management.order";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add("Total: " +db.rs.getInt("sum"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getOrderDetail(int orderID, String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from order_detail where orderID = " + orderID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if ("necessityID".equals(field))
                {
                    ret.add(getNecessityDetail(db.rs.getInt("necessityID"), "name"));
                }
                else if ("amount".equals(field))
                {
                    ret.add("Price: " + getNecessityDetail(db.rs.getInt("necessityID"), "price") + " | Amount: " +  db.rs.getInt("amount"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static void setDebt(int money) {
        try {
            DataQuery db = new DataQuery();
            int newDebt = getDebt() - money;
            String sql = "update acc_user "
                    + "set debt = " + newDebt + " where username = "  + username;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void setDebtOrder(int money) {
        try {
            DataQuery db = new DataQuery();
            int newDebt = getDebt() + money;
            String sql = "update acc_user "
                    + "set debt = " + newDebt + " where username = "  + username;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getDebt() {
        int ret = 0;
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            while (db.rs.next()) {
                ret = db.rs.getInt("debt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static void tranfer(int money) {
        try {
            DataQuery db = new DataQuery();
            int newBalance = getCurrentBalance() - money;
            String sql = "update acc_bank "
                    + "set balance = " + newBalance + " where personalID = "  + username;
            db.stm.executeUpdate(sql);
            setDebt(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getCurrentBalance() {
        int ret = 0;
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_bank where personalID = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getInt("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static ArrayList<String> displayNecessityList(String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if ("price".equals(field)) {
                    ret.add(db.rs.getString(field) + "VNĐ");
                } else {
                    ret.add(db.rs.getString(field));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static String getNecessityDetail(int necessityID, String field) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity where necessityID = " + necessityID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if ("price".equals(field)) {
                    ret = db.rs.getString(field) + "VNĐ";
                } else {
                    ret = db.rs.getString(field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static ArrayList<Integer> searchNecessityByName(String key) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity where name like '%" + key + "%'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("necessityID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ret;
    }

    public int getNecessityID(String name) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (name.equals(db.rs.getString("name"))) {
                    return db.rs.getInt("necessityID");
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public static void createOrder(int userID, int sum) {
        try {
            DataQuery db = new DataQuery();
            String sql = "insert into covid_management.order (userID,sum) values (?,?)";
            PreparedStatement pstmt = db.con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, sum);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buyNecessity(int userID, ArrayList<Necessity> cart) {
        try {
            
            int orderID = 0;
            DataQuery db = new DataQuery();
            String sql = "select * from covid_management.order where userID = " + userID + " order by orderID desc limit 1";
            db.rs = db.stm.executeQuery(sql);
            
            while (db.rs.next()) {
                orderID = db.rs.getInt("orderID");
            }
            PreparedStatement pstmt;
            for (int i = 0; i < cart.size(); i++) {
                sql = "insert into order_detail(orderID,necessityID,amount) values(?,?,?)";
                pstmt = db.con.prepareStatement(sql);
                pstmt.setInt(1, orderID);
                pstmt.setInt(2, cart.get(i).NID);
                pstmt.setInt(3, cart.get(i).amount);
                pstmt.execute();
            }
            //n.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
