package com.cookies.covidapp.server;

import Necessity.Necessity;
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

    ArrayList<Necessity> n;

    public User(String username, String password) {
        super(username, password);
        n = new ArrayList<>();
    }

    public void displayUserInformation(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user join address on acc_user.addressID = address.addressID where acc_user.username =" + username;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.println("ID: " + db.rs.getInt("userID"));
                System.out.println("Username: " + db.rs.getString("username"));
                System.out.println("Fullname: " + db.rs.getString("fullname"));
                System.out.println("Personal ID: " + db.rs.getString("personalID"));
                System.out.println("Year: " + db.rs.getInt("year"));
                System.out.println("Address: " + db.rs.getInt("ward") + ", " + db.rs.getInt("district") + ", " + db.rs.getInt("province"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void displayManagedHistory(int userID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from history_covid where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.println("ID: " + db.rs.getInt("historyID"));
                System.out.println("Old status: " + db.rs.getString("statusOld"));
                System.out.println("New status: " + db.rs.getString("statusNew"));
                //System.out.println("Old place: " + db.rs.getInt("placeOld"));
                //System.out.println("New place: " + db.rs.getString("placeNew"));
                //System.out.println("Date: " + db.rs.getDate("datetime"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void displayDebt(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select debt from acc_user where username = " + username;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.println("Debt: " + db.rs.getInt("debt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayNecessityList() {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.print("ID: " + db.rs.getInt("necessityID"));
                System.out.print(", Name: " + db.rs.getString("name"));
                System.out.print(", Limit amout: " + db.rs.getInt("limitAmount"));
                System.out.print(", Limit time: " + db.rs.getDate("limitTime"));
                System.out.print(", Price: " + db.rs.getInt("price") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchNecessityByName(String key) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity where name like '%" + key + "%'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.print("ID: " + db.rs.getInt("necessityID"));
                System.out.print(", Name: " + db.rs.getString("name"));
                System.out.print(", Limit amout: " + db.rs.getInt("limitAmount"));
                System.out.print(", Limit time: " + db.rs.getDate("limitTime"));
                System.out.println(", Price: " + db.rs.getInt("price") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseyNecessity() {
        Necessity necessity = new Necessity();
        n.add(necessity);
    }

    public void buyNecessity(int userID) {
        try {
            int sum = 0;
            for (int i = 0; i < n.size(); i++) {
                sum += n.get(i).amount * n.get(i).price;
            }
            DataQuery db = new DataQuery();
            String sql = "insert into order(userID,sum) values(?,?)";
            PreparedStatement pstmt = db.con.prepareStatement(sql);
            pstmt.setInt(1, userID);
            pstmt.setInt(2, sum);
            pstmt.execute();

            int orderID = 0;
            sql = "select * from order where userID = " + userID + "order by orderID desc limit 1";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                orderID = db.rs.getInt("orderID");
            }
            for (int i = 0; i < n.size(); i++) {
                sql = "insert into order_detail(orderID,neccessityID,amount) values(?,?,?)";
                pstmt = db.con.prepareStatement(sql);
                pstmt.setInt(1, orderID);
                pstmt.setInt(2, n.get(i).NID);
                pstmt.setInt(3, n.get(i).amount);
                pstmt.execute();
            }
            n.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
