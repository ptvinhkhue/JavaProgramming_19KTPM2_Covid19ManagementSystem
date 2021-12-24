package com.cookies.covidapp.server;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author ptvin
 */
public class Manager extends CovidAccount {

    /*---Constructor---*/
    public Manager(String username, String password) {
        super(username, password);
    }

    public Manager() {
    }

    public Manager(String username) {
        super(username);
    }
    
    public static int getID() {
        try {
            DataQuery db = new DataQuery();
            String sql = "select managerID from acc_manager where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            return db.rs.getInt("managerID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    /*---User Management---*/
    public static void createUser(String username, String fullname, String personalID, int YoB, int addressID, int status, int placeID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "insert into acc_user (username, fullname, personalID, addressID, status, placeID, debt, loggedIn) values ('"
                    + username + "','" + fullname + "','" + personalID + "'," + addressID + "," + status + "," + placeID + "," + 0 + "," + 0 + ")";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> displayUserList(String field) {
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

    public static String displayUserDetail(int userID, String field) {
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

    public static void searchUserByName(String key) {
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

    public static void updateUserInfo(int userID, String fullname, String personalID, int YoB, int addressID) {
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

    public static void updateUserStatus(int userID, int status, ArrayList<Integer> arr) {
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

    public static void updateUserPlace(int userID, int placeID) {
        try {
            DataQuery db = new DataQuery();

            String sql = "select * from acc_user where userID = " + userID;
            db.rs = db.stm.executeQuery(sql);
            db.rs.next();
            if (db.rs.getInt("placeID") != placeID) {
                int oldPlaceID, newCurrent;
                oldPlaceID = db.rs.getInt("placeID");
                
                // update current
                sql = "select current from place where placeID = " + oldPlaceID;
                db.rsSub = db.stm.executeQuery(sql);
                db.rsSub.next();
                newCurrent = db.rsSub.getInt("current") - 1;

                sql = "update place "
                        + "set current = " + newCurrent + " where placeID =" + oldPlaceID;
                db.stm.executeUpdate(sql);

                sql = "select current from place where placeID = " + placeID;
                db.rsSub = db.stm.executeQuery(sql);
                db.rsSub.next();
                newCurrent = db.rsSub.getInt("current") + 1;

                sql = "update place "
                        + "set current = " + newCurrent + " where placeID =" + placeID;
                db.stm.executeUpdate(sql);
                
                // update user data
                sql = "update acc_user "
                        + "set placeID = " + placeID + " where userID =" + userID;
                db.stm.executeUpdate(sql);
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

    public static ArrayList<String> displayNecessityList(String field) {
        ArrayList<String> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from neccessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getString(field));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
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
        
        //ArrayList<Integer> arr = new ArrayList<Integer>();
        Manager.updateUserPlace(1, 2);
    }
    
}
