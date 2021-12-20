package com.cookies.covidapp;

/**
 *
 * @author ptvin
 */
public class Manager extends CovidAccount {
    
    /*---Constructor---*/

    public Manager(String username, String password) {
        super(username, password);
    
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
    
    public void displayUserList() {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.print("ID: " + db.rs.getInt("userID"));
                System.out.print(",Username: " + db.rs.getString("username"));
                System.out.print(", Fullname: " + db.rs.getString("fullname") + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    
   public void displayUserDetail(int userID) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where userID =" + userID;
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                System.out.println("ID: " + db.rs.getInt("userID"));
                System.out.println("Username: " + db.rs.getString("username"));
                System.out.println("Fullname: " + db.rs.getString("fullname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
   
   public void updateUser(int userID, String username, String fullname, String personalID, int YoB, int addressID, int status, int placeID) {
       try {
            DataQuery db = new DataQuery();
            String sql = "update acc_user"
                    + "set username ='" + username + "', fullname ='" + fullname + "', personalID ='" + personalID
                    + "', yob =" + YoB + ", addressID =" + addressID + ", status =" + status + ", placeID =" + placeID 
                    + "where userID =" + userID;
            db.stm.executeUpdate(sql);
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
    
   /*
    public static void main(String args[]) {
        
        Manager m = new Manager("abc", "123");
    }
    /*
}
