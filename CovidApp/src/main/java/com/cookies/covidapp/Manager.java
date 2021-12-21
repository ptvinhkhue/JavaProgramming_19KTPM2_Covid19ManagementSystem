package com.cookies.covidapp;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ptvin
 */
public class Manager extends CovidAccount {

    /*---Constructor---*/
    Manager(String username, String password) {
        super(username, password);
    }

    Manager(String username) {
        super(username);
    }

    /*---User Management---*/
    public void createUser(String username, String fullname, String personalID, int YoB, int addressID, int status, int placeID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "insert into acc_user (username, fullname, personalID, addressID, status, placeID, debt, loggedIn) values ('"
                    + username + "','" + fullname + "','" + personalID + "'," + addressID + "," + status + "," + placeID + "," + 0 + "," + 0 + ")";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> displayUserList(String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString(field));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public String displayUserDetail(int userID, String field) {
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

    public void searchUserByName(String key) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where fullname like '%" + key + "%'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.print("ID: " + db.rs.getInt("userID"));
                System.out.print("Username: " + db.rs.getString("username"));
                System.out.print("Fullname: " + db.rs.getString("fullname") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserInfo(int userID, String fullname, String personalID, int YoB, int addressID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "update acc_user"
                    + "set fullname ='" + fullname + "', personalID ='" + personalID
                    + "', yob =" + YoB + ", addressID =" + addressID + "where userID =" + userID;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserStatus(int userID, int status, ArrayList<Integer> arr) {
        try {
            DataQuery db = new DataQuery();

            String sql = "select * from acc_user where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);
            db.rs.next();
            if (db.rs.getInt("status") > status) {

                sql = "update acc_user "
                        + "set status = " + status + " where userID =" + userID;
                db.stm.executeUpdate(sql);
                arr.add(userID);

                // handle relation
                sql = "select * from relation where userID =" + userID;
                db.rsSub = db.stm.executeQuery(sql);
                while (db.rsSub.next()) {
                    if (!arr.contains(db.rsSub.getInt("relatedID"))) {
                        updateUserStatus(db.rsSub.getInt("relatedID"), status + 1, arr);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*---Necessity Management---*/
    public void createNecessity(String name, int limitAmount, Date limitTime, int price) {
        try {
            DataQuery db = new DataQuery();
            String sql = "insert into necessity (name, limitAmount, limitTime, price) values ('"
                    + name + "'," + limitAmount + "','" + limitTime + "'," + price + ")";
            db.stm.executeUpdate(sql);
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

    public void displayNecessityDetail(int necessityID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity where necessityID =" + necessityID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.println("ID: " + db.rs.getInt("necessityID"));
                System.out.println(", Name: " + db.rs.getString("name"));
                System.out.println(", Limit amout: " + db.rs.getInt("limitAmount"));
                System.out.println(", Limit time: " + db.rs.getDate("limitTime"));
                System.out.println(", Price: " + db.rs.getInt("price"));
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

    public void updateNecessity(int necessityID, String name, int limitAmount, Date limitTime, int price) {
        try {
            DataQuery db = new DataQuery();
            String sql = "update necessity"
                    + "set name ='" + name + "', limitAmount =" + limitAmount + ", limitTime =" + limitTime + ", price =" + price
                    + "where userID =" + necessityID;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        Manager m = new Manager("abc");
        ArrayList<Integer> arr = new ArrayList<Integer>();
        m.updateUserStatus(3, 1, arr);
    }

}
