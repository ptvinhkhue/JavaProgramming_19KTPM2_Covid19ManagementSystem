package com.cookies.covidapp.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JNeoList extends Container {
    private ArrayList<JNeoListItem> item;
    SpringLayout laySpring;
    private final Container ctn_item;
    private Container ctn_btn;
    private JNeoButton btn_previous, btn_next, btn_add;
    private int curPage, totalPage;
    private JNeoLabel lb_page;

    JNeoList(ArrayList<String> iconName, ArrayList<String> name, ArrayList<String> label, ArrayList<String> label_full) {
        super();
        laySpring = new SpringLayout();
        setLayout(laySpring);

        ctn_item = new Container();
        GridLayout layGrid = new GridLayout(Global.listHeight, 1);
        ctn_item.setLayout(layGrid); layGrid.setVgap(8);

        init(iconName, name, label, label_full);
        organize();
        addAllActionListener();
    }

    void init(ArrayList<String> iconName, ArrayList<String> name, ArrayList<String> label, ArrayList<String> label_full) {
        curPage = 0;
        lb_page = new JNeoLabel("" + totalPage, Global.fntButton, Global.colSecond);

        // items
        item = new ArrayList<>();
        setNewList(iconName, name, label, label_full);


        // buttons
        btn_add = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_add.setIcon("add");
        btn_previous = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_previous.setIcon("arrow_left");
        btn_next = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_next.setIcon("arrow_right");
        ctn_btn = new Container();
        ctn_btn.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        ctn_btn.add(btn_add); ctn_btn.add(btn_previous); ctn_btn.add(btn_next);

        // add
        add(ctn_item); add(ctn_btn); add(lb_page);
    }

    void organize() {
        // ctn_btn
        laySpring.putConstraint(SpringLayout.WEST, ctn_btn, 0,
                SpringLayout.WEST, this);
        laySpring.putConstraint(SpringLayout.EAST, ctn_btn, 0,
                SpringLayout.EAST, this);
        laySpring.putConstraint(SpringLayout.SOUTH, ctn_btn, 0,
                SpringLayout.SOUTH, this);

        // ctn_item
        laySpring.putConstraint(SpringLayout.WEST, ctn_item, 0,
                SpringLayout.WEST, this);
        laySpring.putConstraint(SpringLayout.EAST, ctn_item, 0,
                SpringLayout.EAST, this);
        laySpring.putConstraint(SpringLayout.NORTH, ctn_item, 0,
                SpringLayout.NORTH, this);
        laySpring.putConstraint(SpringLayout.SOUTH, ctn_item, -16,
                SpringLayout.NORTH, ctn_btn);

        // lb_page
        laySpring.putConstraint(SpringLayout.WEST, lb_page, 0,
                SpringLayout.WEST, ctn_btn);
        laySpring.putConstraint(SpringLayout.NORTH, lb_page, 0,
                SpringLayout.NORTH, ctn_btn);
    }

    void addAllActionListener() {
        btn_previous.addActionListener(e -> {
            curPage = (0 < curPage) ? curPage - 1 : totalPage - 1;
            showPage(curPage);
        });
        btn_next.addActionListener(e -> {
            curPage = (curPage < totalPage - 1) ? curPage + 1 : 0;
            showPage(curPage);
        });
    }

    void showPage(int page) {
        int size = Global.displaySize;
        if (page > item.size() / size) return;

        int pageSize = Math.min(item.size() - size * page, size);

        ctn_item.removeAll();
        for (int i = page * size; i < page * size + pageSize; i++)
            ctn_item.add(item.get(i));
        ctn_item.repaint();
        ctn_item.revalidate();
        lb_page.setText("Page " + (curPage + 1) + "/" + totalPage);
        lb_page.repaint();

    }

    void setNewList(ArrayList<String> iconName, ArrayList<String> name, ArrayList<String> label, ArrayList<String> label_full) {
        item.clear();
        if (iconName.size() != name.size() || name.size() != label.size())
            throw new java.lang.Error("[JNeoList] Unmatched length.");
        for (int k = 0; k < name.size(); k++) {
            JNeoListItem i = new JNeoListItem(iconName.get(k), name.get(k), label.get(k), label_full.get(k));
            item.add(i);
        }
        curPage = 0;
        totalPage = item.size() / Global.displaySize + (item.size() % Global.displaySize != 0 ? 1 : 0);
        showPage(curPage);
    }

    void setBtnIcon(String iconName) {
        for (JNeoListItem i: item) i.setBtnIcon(iconName);
    }

    JNeoButton getBtnAdd() { return btn_add; }

    void removeBtnAdd() {
        ctn_btn.remove(btn_add);
        ctn_btn.repaint();
    }
    void removeAllBtnInfo() {
        for (JNeoListItem i : item) i.removeBtnInfo();
        repaint();
    }

    ArrayList<JNeoListItem> getItemList() {
        return item;
    }
}

class JNeoListItem extends JPanel {
    
    int ID;

    private JLabel lb_img;
    private JNeoLabel lb_name;
    private JNeoLabel lb_sub;
    private String lb_sub_full, lb_sub_full_2;
    private final SpringLayout layout;

    // info button
    private JNeoButton btn_info;

    JNeoListItem(String iconName, String name, String label, String sub_full) {
        super();
        layout = new SpringLayout();
        setLayout(layout);

        this.setBackground(Global.colPrimaryMild);

        init(iconName, name, label, sub_full);
        organize();
        addAll();
    }
    
    void assignID(int ID) {
        this.ID = ID;
    }

    // name: patient's or necessity's name
    // label: patient's birth year or necessity's price
    void init(String iconName, String name, String sub, String sub_full) {
        // image
        lb_img = new JLabel("");
        lb_img.setIcon(new ImageIcon(Objects.requireNonNull(Global.getIcon(iconName))));

        // name & label
        lb_name = new JNeoLabel(name, Global.fntListName, Global.colDark);
        lb_sub = new JNeoLabel(sub, Global.fntListSub, Global.colSecond);
        
        String[] str = sub_full.split("\n");
        lb_sub_full = str[0];
        if (str.length == 2) { 
            lb_sub_full_2 = str[1];
        }
        else lb_sub_full_2 = str[0];
        
        // info button
        btn_info = new JNeoButton("", Global.colPrimary.darker(), getBackground(), 0, 8, Global.fntButton, false);
        btn_info.setIcon("info");

        // add all
    }

    void organize() {
        layout.putConstraint(SpringLayout.WEST, btn_info, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.EAST, btn_info, 0,
                SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, btn_info, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_info, 0,
                SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.WEST, lb_img, 12,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_img, 0,
                SpringLayout.VERTICAL_CENTER, this);

        layout.putConstraint(SpringLayout.WEST, lb_name, 12,
                SpringLayout.EAST, lb_img);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_name, -6,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, lb_sub, 0,
                SpringLayout.WEST, lb_name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_sub, 12,
                SpringLayout.VERTICAL_CENTER, this);

    }

    void addAll() {
        add(lb_img); add(lb_name); add(lb_sub); add(btn_info);
    }

    void setBtnIcon(String iconName) {
        btn_info.setIcon(iconName);
    }

    JNeoButton getBtnInfo() { return btn_info; }
    JNeoLabel getLbName() {return lb_name;}
    JNeoLabel getLbSub() {return lb_sub;}
    String getLbSubFull() { return lb_sub_full; }
    String getLbSubFull2() { return lb_sub_full_2; }
    int getID() { return ID; } 

    void removeBtnInfo() {
        remove(btn_info);
        repaint();
    }

}
