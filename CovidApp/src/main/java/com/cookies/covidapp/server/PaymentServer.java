/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cookies.covidapp.server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class PaymentServer {

    public static void receive(int money) {
        try {
            DataQuery db = new DataQuery();

            int newBalance = money + getCurrentBalance();
            // update current
            String sql = "update acc_bank "
                    + "set balance = " + newBalance + " where personalID =  0";
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCurrentBalance() {
        int ret = 0;
        try {
            DataQuery db = new DataQuery();
            // update current
            String sql = "select * from acc_bank where personalID = 0";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getInt("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static void setDebt(String username, int money) {
        try {
            DataQuery db = new DataQuery();
            int newDebt = getUserDebt(username) - money;
            String sql = "update acc_user "
                    + "set debt = " + newDebt + " where username = "  + username;
            db.stm.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void tranfer(String username, int money) {
        try {
            DataQuery db = new DataQuery();
            int newBalance = getUserBalance(username) - money;
            String sql = "update acc_bank "
                    + "set balance = " + newBalance + " where personalID = "  + username;
            db.stm.executeUpdate(sql);
            setDebt(username, money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static int getUserDebt(String username) {
        int ret = 0;
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_user where username = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);
            while (db.rs.next()) {
                ret = db.rs.getInt("debt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static int getUserBalance(String username) {
        int ret = 0;
        try {
            DataQuery db = new DataQuery();
            String sql = "select * from acc_bank where personalID = '" + username + "'";
            db.rs = db.stm.executeQuery(sql);

            while (db.rs.next()) {
                ret = db.rs.getInt("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {

        ServerSocket server = null;
        ArrayList<Socket> clientList = new ArrayList<Socket>();

        int portnumber = 1235;

        try {
            server = new ServerSocket(portnumber);
        } catch (IOException ie) {
            System.out.println("Cannot open socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is created " + server);
        while (true) {
            try {
                System.out.println("Waiting for connect request...");
                Socket client = server.accept();
                clientList.add(client);
                System.out.println("Connect request is accepted...");

                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client host = " + clientHost + " Client port = " + clientPort);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream clientIn = null;
                        try {
                            Scanner in = new Scanner(client.getInputStream());
                            while (in.hasNextLine()) {

                                String msgFromClient = in.nextLine();
                                for (Socket s : clientList) {
                                    if (s == null) {
                                        clientList.remove(s);
                                    } else if (s.equals(client)) {
                                        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                                        if (!msgFromClient.isEmpty()) {
                                            String info[] = msgFromClient.split("&");
                                            receive(Integer.parseInt(info[1]));
                                            tranfer(info[0] ,Integer.parseInt(info[1]));
                                            out.println("Tranfer successfully");
                                            System.out.println(msgFromClient);
                                        }
                                        else out.println("Tranfer error");
                                    }
                                }
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(PaymentServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }).start();
            } catch (IOException ie) {
            }
        }
    }
}
