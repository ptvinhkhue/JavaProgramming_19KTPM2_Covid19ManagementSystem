/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp.server;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 *
 * @author ptvin
 */
public class Login {

    boolean ini;

    public Login() {
        ini = true; // use for testing
    }

    public static int handleUsername(String username) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_covid where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            if (db.rs.next()) {
                // check admin
                if ("admin".equals(db.rs.getString("username"))) {
                    return 2;
                }

                // check manager or user
                try {
                    // check user
                    int x = Integer.parseInt(db.rs.getString("username"));
                    return 0; //String is an Integer
                } catch (NumberFormatException e) {
                    // check manager
                    if (Manager.locked(db.rs.getString("username")) == false) 
                        return 1; //String is not an Integer
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean handlePassword(String username, String password) {
        try {
            DataQuery db = new DataQuery();
            String sql = "select password from acc_covid where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            db.rs.next();
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), db.rs.getString("password"));

            //Display values
            if (result.verified == true) {
                return true;
            } else {
                return false;
            }
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
