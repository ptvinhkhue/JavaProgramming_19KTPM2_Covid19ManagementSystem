/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp.server;

/**
 *
 * @author ptvin
 */
public class CovidAccount {

    String username = "";
    String password = "";
    
    public CovidAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public CovidAccount() {
    }
    
    public CovidAccount(String username) {
        this.username = username;
    }
}
