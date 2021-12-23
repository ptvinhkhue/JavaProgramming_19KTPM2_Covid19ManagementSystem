/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp;

/**
 *
 * @author ptvin
 */
public class Login {

    boolean ini;

    public Login() {
        ini = true; // use for testing
    }

    public boolean handleUsername(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_covid where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            if (!db.rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean handlePassword(String username, String password) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select password from acc_covid where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                //Display values
                if (db.rs.getString("password") == null ? password == null : db.rs.getString("password").equals(password)) {
                    
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        /*
        // Testing code 
        Manager mng = new Manager(username, password);
        //GUI_Manager gui = new GUI_Manager(mng);
        mng.searchNecessityByName("Gạo");
        */
    }

    /*/
    public static void main(String args[]) {
        Login app = new Login();
        app.login();
    }
     */
}
