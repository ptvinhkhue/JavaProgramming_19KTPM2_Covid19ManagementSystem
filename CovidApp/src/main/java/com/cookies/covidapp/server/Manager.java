package com.cookies.covidapp.server;

import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ptvin
 */
public class Manager extends CovidAccount {
    
    static long millis = System.currentTimeMillis();
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

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
    
    /*---Authentication---*/
    public static boolean locked(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_manager where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            
            if (db.rs.next()) {
                if (db.rs.getInt("locked") == 1) return true;
                else return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }

    /*---User Management---*/
    public static int getUserID(String username) {
        int ret = 0;

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            
            if (db.rs.next()) {
                ret = db.rs.getInt("userID");
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
    
    public static String getFullAddress(int addressID) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from address where addressID = " + addressID;
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            ret = db.rs.getString("ward") + ", " + db.rs.getString("district") + ", " + db.rs.getString("province");
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
    
    public static ArrayList<Integer> sortSearchedUser(String key, String citeria) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        try {
            DataQuery db = new DataQuery();
            
            // select citeria
            if ("ID".equals(citeria)) {
                citeria = "userID";
            }
            else if ("Name".equals(citeria)) {
                citeria = "fullname";
            }
            else if ("Age".equals(citeria)) {
                citeria = "yob";
            }
            else if ("Status".equals(citeria)) {
                citeria = "status";
            }
            else if ("Place".equals(citeria)) {
                citeria = "placeID";
            }
            
            String sql = "select * from acc_user where fullname like '%" + key + "%'"
                    + "order by " + citeria + " ASC";
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
    
    public static boolean overloadedPlace(String name) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from place where name = '" + name + "'";
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            if (db.rs.getInt("current") == db.rs.getInt("capacity")) return true;
            else return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
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
            sql = "insert into acc_covid (username, type) values ('" + personalID + "', " + 3 + ")";
            db.stm.executeUpdate(sql);

            sql = "insert into acc_user (username, fullname, personalID, yob, addressID, status, placeID, debt, loggedIn) values ('"
                    + personalID + "','" + fullname + "','" + personalID + "'," + yob + "," + addressID + "," + status + "," + placeID + "," + 0 + "," + 0 + ")";
            db.stm.executeUpdate(sql);
            
            // insert user's bak account
            sql = "insert into acc_bank (personalID, balance, type) values ('" + personalID + "', " + 5000000 + ", " + 2 + ")";
            db.stm.executeUpdate(sql);
            
            sql = "insert into acc_normal (bankID) values ('" + personalID + "')";
            db.stm.executeUpdate(sql);
            
            // handle history
            Manager.logCreateUser(personalID);
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
                int statusOld = db.rs.getInt("status");
                
                // handle update
                sql = "update acc_user "
                        + "set status = " + status + " where userID =" + userID;
                db.stm.executeUpdate(sql);
                arr.add(userID);
                
                // handle history
                Manager.logUserStatusUpdate(userID, statusOld, status);
                Manager.logUpdateStatus(userID, statusOld, status);

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
                
                // handle history
                Manager.logUserPlaceUpdate(userID, oldPlaceID, placeID);
                Manager.logUpdatePlace(userID, oldPlaceID, placeID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addRelatedID(int userID, int relatedID) {
        try {
            // add relation
            DataQuery db = new DataQuery();
            String sql = "insert into relation (userID, relatedID) values (" + userID + ", " + relatedID + ")";
            db.stm.executeUpdate(sql);
            
            // handle history
            Manager.logAddRelation(userID, relatedID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*---Necessity Management---*/
    
    public static ArrayList<Integer> getNecessityIntList(String field) {
        ArrayList<Integer> ret = new ArrayList<>();

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt(field));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    
    public static ArrayList<String> getNecessityStringList(String field) {
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
    
    public static String getNecessityDetail(int necessityID, String field) {
        String ret = "";

        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity where necessityID = " + necessityID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getString(field);
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
    
    public static ArrayList<Integer> sortSearchedNecessity(String key, String citeria) {
        ArrayList<Integer> ret = new ArrayList<>();
        
        try {
            DataQuery db = new DataQuery();
            
            // select citeria
            if ("ID".equals(citeria)) {
                citeria = "necessityID";
            }
            else if ("Name".equals(citeria)) {
                citeria = "name";
            }
            else if ("Price".equals(citeria)) {
                citeria = "price";
            }
            
            String sql = "select * from necessity where name like '%" + key + "%'"
                    + "order by " + citeria + " ASC";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret.add(db.rs.getInt("necessityID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ret;
    }
    
    public static int existedNecessity(String name) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from necessity";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                if (db.rs.getString("name") == null ? name == null : db.rs.getString("name").equals(name)) {
                    return db.rs.getInt("necessityID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
    public static void createNecessity(String name, int price) {
        try {
            // create necessity
            DataQuery db = new DataQuery();
            String sql = "insert into necessity (name, price) values ('"
                    + name + "'," + price + ")";
            db.stm.executeUpdate(sql);
            
            // handle history
            Manager.logCreateNecessity(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateNecessity(int necessityID, String name, int price) {
        try {
            // update necessity
            DataQuery db = new DataQuery();
            String sql = "update necessity "
                    + "set name ='" + name + "', price =" + price + " where necessityID =" + necessityID;
            db.stm.executeUpdate(sql);
            
            // handle history
            Manager.logUpdateNecessity(necessityID, name, price);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteNecessity(int necessityID) {
        try {
            // delete history
            DataQuery db = new DataQuery();
            String sql = "delete from necessity where necessityID =" + necessityID;
            db.stm.executeUpdate(sql);
            
            // handle history
            Manager.logDeleteNecessity(necessityID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*---Statistics Management*/
    public static int countUserByStatus(int status) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select count(*) as total from acc_user where status =" + status;
            db.rs = db.stm.executeQuery(sql);
            
            if (db.rs.next()) {
                return db.rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public static int countNecessitySales(int necessityID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select sum(amount) as total from order_detail where necessityID =" + necessityID;
            db.rs = db.stm.executeQuery(sql);
            
            if (db.rs.next()) {
                return db.rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public static int sumDebtByPlace(int placeID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select sum(debt) as total from acc_user where placeID =" + placeID;
            db.rs = db.stm.executeQuery(sql);
            
            if (db.rs.next()) {
                return db.rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    /*---History Management*/
    public static void logUserStatusUpdate(int userID, int statusOld, int statusNew) {
        try {
            DataQuery db = new DataQuery();  
            
            String sql = "insert into history_status (userID, statusOld, statusNew, datetime) values ("
                    + userID + "," + statusOld + "," + statusNew + ",'" + new java.sql.Date(millis) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logUserPlaceUpdate(int userID, int placeOldID, int placeNewID) {
        try {
            DataQuery db = new DataQuery();
            
            String sql = "insert into history_place (userID, placeOldID, placeNewID, datetime) values ("
                    + userID + "," + placeOldID + "," + placeNewID + ",'" + new java.sql.Date(millis) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logCreateUser(String username) {
        try {
            DataQuery db = new DataQuery();
            String content = "Create User: " + username;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logUpdateStatus(int userID, int statusOld, int statusNew) {
        try {
            DataQuery db = new DataQuery();
            String content = "Update UserID = " + userID +  " status: F" + statusOld + " -> F" + statusNew;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logUpdatePlace(int userID, int placeOldID, int placeNewID) {
        try {
            DataQuery db = new DataQuery();
            String content = "Update UserID = " + userID +  " placeID: " + placeOldID + " -> " + placeNewID;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logAddRelation(int userID, int relatedID) {
        try {
            DataQuery db = new DataQuery();
            String content = "Update UserID = " + userID +  " new relatedID = " + relatedID;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logCreateNecessity(String name) {
        try {
            DataQuery db = new DataQuery();
            String content = "Create Necessity: " + name + "";
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logUpdateNecessity(int necessityID, String name, int price) {
        try {
            DataQuery db = new DataQuery();
            String content = "Update NecessityID = " + necessityID +  ": name = " + name + ", price = " + price;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void logDeleteNecessity(int necessityID) {
        try {
            DataQuery db = new DataQuery();
            String content = "Delete NecessityID = " + necessityID;
            
            String sql = "insert into history_manager (managerID, activity, datetime) values ("
                    + Manager.getID() + ",'" + content + "', '" + dtf.format(LocalDateTime.now()) + "')";
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
