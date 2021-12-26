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

    /*---Get---*/
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
    public static ArrayList<String> getUserStringList(String field) {
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

    public static ArrayList<Integer> searchUserByName(String key) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where fullname like '%" + key + "%'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("userID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ret;
    }

    public static int existedAddress(String address) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from address";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                String str = db.rs.getString("ward") + ", " + db.rs.getString("district") + ", " + db.rs.getString("province");
                if (str == null ? address == null : str.equals(address)) {
                    return db.rs.getInt("addressID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int existedPlace(String place) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (db.rs.getString("name") == null ? place == null : db.rs.getString("name").equals(place)) {
                    return db.rs.getInt("placeID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int existedUser(String personalID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (db.rs.getString("personalID") == null ? personalID == null : db.rs.getString("personalID").equals(personalID)) {
                    return db.rs.getInt("userID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void createUser(String fullname, String personalID, int yob, int addressID, int status, int placeID) {
        try {
            DataQuery db = new DataQuery();

            // update current
            String sql = "select current from place where placeID = " + placeID;
            db.rs = db.stm.executeQuery(sql);
            db.rs.next();
            int newCurrent = db.rs.getInt("current") + 1;

            sql = "update place "
                    + "set current = " + newCurrent + " where placeID =" + placeID;
            db.stm.executeUpdate(sql);
            
            // insert new User
            sql = "insert into acc_covid (username) values ('" + personalID + "')";
            db.stm.executeUpdate(sql);

            sql = "insert into acc_user (username, fullname, personalID, yob, addressID, status, placeID, debt, loggedIn) values ('"
                    + personalID + "','" + fullname + "','" + personalID + "'," + yob + "," + addressID + "," + status + "," + placeID + "," + 0 + "," + 0 + ")";
            db.stm.executeUpdate(sql);
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
            String sql = "select * from necessity";
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
