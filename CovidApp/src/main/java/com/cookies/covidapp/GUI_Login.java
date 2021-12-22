package com.cookies.covidapp;

import javax.swing.*;
import java.awt.*;

public class GUI_Login {
    
    Login login = new Login();
    String username;

    int width = 640; int height = 480;
    int marginUnit = 4;

    private JFrame fMain;
    private final GUI_Manager gui_manager;
    private JPanel pUsername, pPassword;
    private Font font;

    GUI_Login() {

        initFont();
        initPanels();
        initFMain();

        gui_manager = new GUI_Manager(this);
        fMain.setVisible(true);
    }

    // initiate components
    void initFMain() {
        fMain = new JFrame("Covid-19 Management - Manager");
        fMain.setSize(width, height);
        fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fMain.setLocationRelativeTo(null);
        fMain.setResizable(false);
    }
    void initFont() {
        font = new Font("San-Serif", Font.PLAIN, 16);
    }
    void initPUsername() {
        // initiate panel
        pUsername = new JPanel();
        SpringLayout layout = new SpringLayout();
        pUsername.setLayout(layout);

        // initiate components
        JLabel l_sub = new JLabel("Please enter your Username");
        JLabelTextField tf_username = new JLabelTextField("Username", 15);
        JButton btn_continue = new JButton("Continue");

        // components settings
        tf_username.setFont(font);

        // set layout
        layout.putConstraint(SpringLayout.SOUTH, l_sub, -marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, l_sub, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.NORTH, tf_username, marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_username, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.NORTH, btn_continue, marginUnit * 4,
                SpringLayout.SOUTH, tf_username);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_continue, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);

        // action listeners
        btn_continue.addActionListener(e -> {
            if (login.handleUsername(tf_username.getText())) {
                //System.out.println(tf_username.getText());
                username = tf_username.getText();
                gui_manager.assignManager(username);
                changePanel(pPassword);
            }
        });

        // add components
        pUsername.add(l_sub);
        pUsername.add(tf_username);
        pUsername.add(btn_continue);
    }
    void initPPassword() {
        // initiate panel
        pPassword = new JPanel();
        SpringLayout layout = new SpringLayout();
        pPassword.setLayout(layout);

        // initiate components
        JLabel l_sub = new JLabel("Welcome back! Please enter your password");
        JLabelPasswordField tf_password = new JLabelPasswordField("Password", 15);
        JButton btn_login = new JButton("Login");

        // components settings
        tf_password.setFont(font);

        // set layout
        layout.putConstraint(SpringLayout.SOUTH, l_sub, -marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, l_sub, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.NORTH, tf_password, marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_password, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.NORTH, btn_login, marginUnit * 4,
                SpringLayout.SOUTH, tf_password);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_login, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);

        // action listeners
        btn_login.addActionListener(e -> {
            if (login.handlePassword(username, tf_password.getText())) {
                changePanel(gui_manager.getpMain());
            }
        });

        // add components
        pPassword.add(l_sub);
        pPassword.add(tf_password);
        pPassword.add(btn_login);
    }
    void initPanels() {
        initPUsername();
        initPPassword();
    }

    // get panel


    public JPanel getpUsername() {
        return pUsername;
    }

    public JFrame getfMain() {
        return fMain;
    }

    // master panel
    void changePanel(JPanel p) {
        fMain.getContentPane().removeAll();
        fMain.getContentPane().add(p);
        fMain.getContentPane().repaint();
        fMain.revalidate();
    }

    public static void main(String[] args) {
        GUI_Login gui = new GUI_Login();
        gui.changePanel(gui.pUsername);
    }
}