/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp;

import java.util.Scanner;

/**
 *
 * @author ptvin
 */
public class CovidApp {

    boolean ini = true; // use for testing
    String username;
    String password;

    public void login() {
        Scanner scn = new Scanner(System.in);

        if (ini == false) {
            username = "admin";
            password = scn.nextLine();

            Admin adm = new Admin(username, password);
            adm.signup();
        } else {
            username = scn.nextLine();
            password = scn.nextLine();

            try {
                DataQuery db = new DataQuery();
                String sql = "select password from acc_covid where username = '" + username + "'";
                db.rs = db.stm.executeQuery(sql);

                while (db.rs.next()) {
                    //Display values
                    if (db.rs.getString("password") == null ? password == null : db.rs.getString("password").equals(password)) System.out.println("Manager login");
                    else System.out.println("Manager not login");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Testing code 
            
            Manager mng = new Manager(username, password);
            mng.searchNecessityByName("Gạo");
            
        }
    }

    public static void main(String args[]) {
        CovidApp app = new CovidApp();
        app.login();
    }
}
