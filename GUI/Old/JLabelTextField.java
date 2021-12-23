package com.cookies.covidapp;

import javax.swing.*;
import java.awt.*;

public class JLabelTextField extends JComponent {
    
    JLabel l;
    JTextField tf;
    
    JLabelTextField(String l_str, int col) {
        super();

        this.setLayout(new FlowLayout());
        l = new JLabel(l_str);
        tf = new JTextField(col);

        this.add(l);
        this.add(tf);
    }
    
    public String getText() {
        return tf.getText();
    }
}