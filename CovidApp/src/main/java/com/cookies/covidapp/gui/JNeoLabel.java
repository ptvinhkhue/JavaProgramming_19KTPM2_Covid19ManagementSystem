package com.cookies.covidapp.gui;

import javax.swing.*;
import java.awt.*;

public class JNeoLabel extends JLabel {
    JNeoLabel(String str, Font font, Color col) {
        super(str);
        setFont(font);
        setForeground(col);
    }

    void setHide() {
        setForeground(Color.WHITE);
    }

    void setSuccess() {
        setForeground(Global.colPrimary);
    }

    void setError() {
        setForeground(Global.colError);
    }
}
