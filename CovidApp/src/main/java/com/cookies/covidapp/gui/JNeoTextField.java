package com.cookies.covidapp.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JNeoTextField extends Container {

    private final JNeoLabel hint;
    private final JTextField tf;

    JNeoTextField(String str, int col, boolean isPassword, String iconName, String hintStr) {
        super();
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // icon
        JLabel lb_icon = new JLabel("");
        lb_icon.setIcon(new ImageIcon(Objects.requireNonNull(Global.getIcon(iconName))));

        tf = isPassword ? new JPasswordField(str, col) : new JTextField(str, col);
        tf.setFont(Global.fntPrimary);
        tf.setBorder(new BubbleBorder());

        hint = new JNeoLabel(hintStr, Global.fntHint, Global.colError);
        hint.setForeground(Color.WHITE);

        layout.putConstraint(SpringLayout.WEST, lb_icon, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lb_icon, 0,
                SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, tf, 4,
                SpringLayout.EAST, lb_icon);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf, 0,
                SpringLayout.VERTICAL_CENTER, lb_icon);

        layout.putConstraint(SpringLayout.WEST, hint, 0,
                SpringLayout.WEST, tf);
        layout.putConstraint(SpringLayout.NORTH, hint, 4,
                SpringLayout.SOUTH, tf);

        layout.putConstraint(SpringLayout.EAST, this, 0,
                SpringLayout.EAST, tf);
        layout.putConstraint(SpringLayout.SOUTH, this, 0,
                SpringLayout.SOUTH, hint);


        add(lb_icon); add(tf);
        add(hint);
        hideHint();
    }

    void showHint() { hint.setForeground(Global.colError); }

    void hideHint() { hint.setForeground(Color.WHITE); }

    String getText() {
        return tf.getText();
    }

    void setText(String str) { tf.setText(str); }

    void setHint(String str) { hint.setText(str); }
}

class JNeoSearchBar extends Container {

    private final JTextField tf;
    int curState;
    ArrayList<JNeoButton> filter_tags;
    Container ctn_search, ctn_tags;
    SpringLayout layout;
    int filter = 0;

    JNeoSearchBar(String str, int col, String[] filter_names) {
        super();
        layout = new SpringLayout();
        setLayout(layout);

        // icon & search bar
        JLabel lb_icon = new JLabel("");
        lb_icon.setIcon(new ImageIcon(Objects.requireNonNull(Global.getIcon("search_st"))));

        tf = new JTextField(str, col);
        tf.setFont(Global.fntPrimary);
        tf.setBorder(new BubbleBorder());

        ctn_search = new Container();
        ctn_search.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));
        ctn_search.add(lb_icon);
        ctn_search.add(tf);

        // tags
        ctn_tags = new Container();
        ctn_tags.setLayout(new FlowLayout(FlowLayout.RIGHT, 4, 0));

        curState = 0;
        filter_tags = new ArrayList<>();

        for (String filter_name : filter_names) {
            JNeoButton tag = new JNeoButton(filter_name, Global.colSecond, Color.WHITE, 4, 2, Global.fntHint, true);
            tag.setMaster(this);
            filter_tags.add(tag);
            ctn_tags.add(tag);
        }

        addAllActionListener();
        organize();

        add(ctn_search);
        add(ctn_tags);
    }

    void organize() {
        layout.putConstraint(SpringLayout.EAST, ctn_search, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.EAST, ctn_tags, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, ctn_tags, 4,
                SpringLayout.SOUTH, ctn_search);
    }

    void addAllActionListener() {

    }

    JTextField getTf() { return tf; }

    void resetTags() {
        for (JNeoButton tag: filter_tags)
            tag.turnOff();
    }
}

class BubbleBorder extends AbstractBorder {

    private final Color color;
    private final int thickness;
    private final int radius;
    private final Insets insets;
    private final BasicStroke stroke;
    private final int strokePad;
    private final int pointerPad;
    RenderingHints hints;

    BubbleBorder() {
        color = Global.colSubtle;
        thickness = Global.tfThickness;
        radius = Global.tfRadius;
        pointerPad = 4;

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = radius + strokePad;
        int bottomPad = pad + strokePad;
        insets = new Insets(pad, pad, bottomPad, pad);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;

        int bottomLineY = height - thickness;

        RoundRectangle2D.Double bubble = new RoundRectangle2D.Double(strokePad, strokePad,
                width - thickness, bottomLineY, radius, radius);

        Polygon pointer = new Polygon();

        pointer.addPoint(strokePad + radius + pointerPad, bottomLineY);
        pointer.addPoint(strokePad + radius, bottomLineY);
        pointer.addPoint(strokePad + radius + pointerPad, height - strokePad);

        Area area = new Area(bubble);
        area.add(new Area(pointer));

        g2d.setRenderingHints(hints);

        Area space = new Area(new Rectangle(0, 0, width, height));
        space.subtract(area);
        g2d.setClip(space);
        g2d.clearRect(0, 0, width, height);
        g2d.setClip(null);

        g2d.setColor(color);
        g2d.setStroke(stroke);
        g2d.draw(area);
    }
}