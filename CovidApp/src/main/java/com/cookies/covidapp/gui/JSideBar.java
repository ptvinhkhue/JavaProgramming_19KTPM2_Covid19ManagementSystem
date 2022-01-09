package com.cookies.covidapp.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class JSideBar extends JPanel {

    ArrayList<JNeoButton> item;
    private int role; // 0 = User; 1 = Manager; 2 = Admin

    JSideBar(String[] itemIcon, int role, int highlight) {
        super();
        this.role = role;

        setBackground(Global.colSecond);
        setLayout(new GridLayout(Global.sb_height, 1));

        // items
        init(itemIcon, highlight);

        // action listener
        addAllActionListener(role);
    }

    // initialize items
    void init(String[] itemIcon, int highlight) {
        item = new ArrayList<>();
        for (int k = 0; k < itemIcon.length; k++) {
            if (Objects.equals(itemIcon[k], "logout"))
                for (int i = 0; i < Global.sb_height - itemIcon.length; i++)
                    add(new JLabel(""));
            JNeoButton i = new JNeoButton("", getBackground(), Color.WHITE, 0, 8, Global.fntButton, false);

            String iconPath = itemIcon[k];
            if (k == highlight) {
                iconPath += "_hl";
                i.setBackground(i.getBackground().darker());
            }
            i.setIcon(iconPath);
            item.add(i); add(i);
        }
    }

    void addAllActionListener(int role) {
        switch (role) {
            case 0: // user
                item.get(0).addActionListener(e -> GUI_Master.changePanel(GUI_User.getPPersonalInfo()));
                item.get(1).addActionListener(e -> GUI_Master.changePanel(GUI_User.getPBuyNecessity()));
                item.get(2).addActionListener(e -> GUI_Master.changePanel(GUI_User.getUpdatedPPayment()));
                item.get(3).addActionListener(e -> GUI_Master.changePanel(GUI_Master.getPUsername()));
                break;
            case 1: // manager
                item.get(0).addActionListener(e -> GUI_Master.changePanel(GUI_Manager.getUpdatedPPatientList()));
                item.get(1).addActionListener(e -> GUI_Master.changePanel(GUI_Manager.getUpdatedPNecessityList()));
                item.get(2).addActionListener(e -> GUI_Master.changePanel(GUI_Manager.getUpdatedPChart()));
                item.get(3).addActionListener(e -> GUI_Master.changePanel(GUI_Master.getPUsername()));
                break;
            case 2: // manager
                item.get(0).addActionListener(e -> GUI_Master.changePanel(GUI_Admin.getPManagerList()));
                item.get(1).addActionListener(e -> GUI_Master.changePanel(GUI_Admin.getUpdatedPPlaceList()));
                item.get(2).addActionListener(e -> GUI_Master.changePanel(GUI_Master.getPUsername()));
                break;
        }
    }

}