package com.cookies.covidapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PatientListItem extends JPanel {
    
    int userID;

    JLabel l_name;
    public JButton btn_info;
    public JButton btn_change;

    PatientListItem(int userID, String name, String year){
        super();
        
        this.userID = userID;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));

        l_name = new JLabel(name + " - " + year);
        l_name.setBorder(new EmptyBorder(0, 8, 0, 32));
        btn_info = new JButton("Info");
        btn_change = new JButton("Change");
        Container btn = new Container();
        btn.setLayout(new FlowLayout());
        btn.add(btn_info); btn.add(btn_change);

        this.add(l_name, BorderLayout.WEST);
        this.add(btn, BorderLayout.EAST);
    }

}
