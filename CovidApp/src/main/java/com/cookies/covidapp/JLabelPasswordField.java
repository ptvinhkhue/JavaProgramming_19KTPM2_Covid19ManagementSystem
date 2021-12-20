package com.cookies.covidapp;

import javax.swing.*;
import java.awt.*;

public class JLabelPasswordField extends JComponent {
    
    JLabel l;
    JPasswordField tf;
    
    JLabelPasswordField(String l_str, int col) {
        super();

        this.setLayout(new FlowLayout());
        l = new JLabel(l_str);
        tf = new JPasswordField(col);

        this.add(l);
        this.add(tf);
    }
    
    public String getText() {
        return tf.getText();
    }
}
