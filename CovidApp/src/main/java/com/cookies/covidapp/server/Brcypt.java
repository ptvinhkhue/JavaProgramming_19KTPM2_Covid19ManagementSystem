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
public class Brcypt {

    public static void main(String args[]) {
        /*
        String password = "1234";
        String bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        System.out.println(bcryptHashString);
        // $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6
        // $2a$12$5uhQjXg3VWu.Wc3J8fLIeO0q22ebdLoLCiPSmgFLJ2hkyf1Qbrv02

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        System.out.println(result.verified);
        // result.verified == true
        */
        
        
        try {
            
            String username = "admin";
            String pass = "admin";
            String passHash = BCrypt.withDefaults().hashToString(12, pass.toCharArray());
            System.out.println(passHash);
            
            DataQuery db = new DataQuery();
            String sql = "update acc_covid "
                        + "set password = '" + passHash + "' where username = '" + username + "'";
            db.stm.executeUpdate(sql);
            
            /*
            DataQuery db = new DataQuery();
            String sql = "select * from acc_covid where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            db.rs.next();
            String passData = db.rs.getString("password");
            System.out.println(passData);
            
            BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), passData);
            System.out.println(result.verified);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
