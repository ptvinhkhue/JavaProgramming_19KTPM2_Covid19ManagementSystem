package com.cookies.covidapp.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LogoBig extends Container {

    LogoBig() {
        super();
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // logo
        JLabel lb_logo = new JLabel("");
        try {
            BufferedImage icon = ImageIO.read(
                    new File(System.getProperty("user.dir") +
                            Global.pathImage + "img_logo.png"));
            lb_logo.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // title
        JLabel lb_title = new JNeoLabel("Shelter", Global.fntTitle, Global.colPrimaryMild);
        JLabel lb_sub = new JNeoLabel("Covid Management", Global.fntSub, Global.colSecond);

        // layout
        layout.putConstraint(SpringLayout.WEST, lb_logo, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, this, 0,
                SpringLayout.NORTH, lb_logo);
        layout.putConstraint(SpringLayout.SOUTH, this, 7,
                SpringLayout.SOUTH, lb_logo);

        layout.putConstraint(SpringLayout.NORTH, lb_title, 0,
                SpringLayout.NORTH, lb_logo);
        layout.putConstraint(SpringLayout.WEST, lb_title, 16,
                SpringLayout.EAST, lb_logo);
        layout.putConstraint(SpringLayout.SOUTH, lb_sub, 7,
                SpringLayout.SOUTH, lb_logo);
        layout.putConstraint(SpringLayout.WEST, lb_sub, 16,
                SpringLayout.EAST, lb_logo);
        layout.putConstraint(SpringLayout.EAST, this, 0,
                SpringLayout.EAST, lb_sub);

        // add
        add(lb_logo); add(lb_title); add(lb_sub);
    }
}
