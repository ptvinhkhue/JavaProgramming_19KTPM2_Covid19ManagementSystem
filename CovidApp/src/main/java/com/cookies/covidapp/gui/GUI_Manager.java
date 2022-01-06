package com.cookies.covidapp.gui;

import com.cookies.covidapp.server.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import static javax.swing.BoxLayout.Y_AXIS;

public class GUI_Manager {

    static Manager manager;

    static PanePasswordManager pPasswordManager;
    static PanePatientList pPatientList;
    static PaneNecessityList pNecessityList;
    static PanePatientForm pPatientAdd, pPatientEdit;
    static PaneNecessityForm pNecessityAdd, pNecessityEdit;
    static PanePatientInfo pPatientInfo;
    static PaneChart pChart;

    void initPane() {
        pPasswordManager = new PanePasswordManager();
        pPatientList = new PanePatientList();
        pNecessityList = new PaneNecessityList();
        pPatientAdd = new PanePatientForm(true);
        pPatientEdit = new PanePatientForm(false);
        pNecessityAdd = new PaneNecessityForm(true);
        pNecessityEdit = new PaneNecessityForm(false);
        pPatientInfo = new PanePatientInfo();
        pChart = new PaneChart();
    }

    void init() {
        initPane();
    }

    public static PanePasswordManager getPPasswordManager() {
        return pPasswordManager;
    }

    public static PanePatientList getPPatientList() {
        return pPatientList;
    }

    public static PaneNecessityList getPNecessityList() {
        return pNecessityList;
    }

    public static PanePatientForm getPPatientAdd() {
        return pPatientAdd;
    }

    public static PanePatientForm getPPatientEdit() {
        return pPatientEdit;
    }

    public static PaneNecessityForm getPNecessityAdd() {
        return pNecessityAdd;
    }

    public static PaneNecessityForm getPNecessityEdit() {
        return pNecessityEdit;
    }

    public static PanePatientInfo getPPatientInfo() {
        return pPatientInfo;
    }

    public static PanePatientList getUpdatedPPatientList() {
        return pPatientList = new PanePatientList();
    }

    public static PaneNecessityList getUpdatedPNecessityList() {
        return pNecessityList = new PaneNecessityList();
    }

    public static PanePatientInfo getUpdatedPPatientInfo() {
        pPatientList = new PanePatientList();
        return pPatientInfo = new PanePatientInfo();
    }

    public static PaneChart getPChart() {
        return pChart;
    }
}

class PanePasswordManager extends JPanel {

    static String username;

    private JNeoButton btn_login, btn_return;
    private Container btnContainer;
    private JNeoTextField tf_password;
    private JLabel lb_credit;
    private static JLabel lb_subtitle;
    private LogoBig logo;

    PanePasswordManager() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // buttons
        btn_login = new JNeoButton("Login", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_return = new JNeoButton("Return", Color.WHITE, Global.colSubtle, Global.btnRadius, 8, Global.fntButton, false);
        btnContainer = new Container();
        btnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnContainer.add(btn_login);
        btnContainer.add(btn_return);

        // text fields
        tf_password = new JNeoTextField("Password", 16, true, "lock", "Password does not match.");

        // labels
        lb_credit = new JNeoLabel("Developed by Cookies", Global.fntSecond, Global.colSecond);

        lb_subtitle = new JNeoLabel("Welcome back, " + "" + ". Please enter your password.", Global.fntButton, Global.colSecond);

        // logo
        logo = new LogoBig();
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // tf_password
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_password, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_password, 32,
                SpringLayout.VERTICAL_CENTER, this);

        // btn_login
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnContainer, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, btnContainer, 16,
                SpringLayout.SOUTH, tf_password);

        // lb_credit
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lb_credit, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, lb_credit, -8,
                SpringLayout.SOUTH, this);

        // lb_subtitle
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lb_subtitle, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, lb_subtitle, -12,
                SpringLayout.NORTH, tf_password);

        // lb_logo
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, logo, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, logo, -24,
                SpringLayout.NORTH, lb_subtitle);
    }

    void addAllActionListener() {
        btn_login.addActionListener(e -> {
            if (Login.handlePassword(username, tf_password.getText())) {
                GUI_Manager.manager = new Manager(username);
                //System.out.println(username + " " + Manager.getID());
                GUI_Master.changePanel(GUI_Manager.getPPatientList());
            } else {
                tf_password.showHint(); // Show hint/error below if false
            }
        });

        btn_return.addActionListener(e -> GUI_Master.changePanel(GUI_Master.getPUsername()));
    }

    void addAll() {
        add(btnContainer);
        add(tf_password);
        add(lb_credit);
        add(lb_subtitle);
        add(logo);
    }

    public static void resetSubtitle(String newUsername) {
        lb_subtitle.setText("Welcome back, Manager " + newUsername + ". Please enter your password.");
    }

    public static void assignUsername(String username) {
        PanePasswordManager.username = username;
    }

}

class PanePatientList extends JPanel {

    static ArrayList<Integer> id;
    String order = "ID";

    private JSideBar sideBar;
    private JNeoList list;
    private JNeoLabel title;
    private JNeoSearchBar searchBar;

    PanePatientList() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, 0);

        // title
        title = new JNeoLabel("Patient list", Global.fntHeader, Global.colDark);

        // search bar
        String[] filter_names = {"Name", "Age", "Status", "Place"};
        searchBar = new JNeoSearchBar("", 15, filter_names);

        // list
        /*
        String[] iconName = { "male" , "male", "female", "male", "female", "male", "female", "female",
                "male" , "male", "female", "female"};
        String[] name = { "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F", "Le G", "Vo Lien H", "Nguyen Van A", "Tran Thanh B", "Le G", "Vo Lien H"};
        String[] label = { "2001 | District 1", "1995 | District 7","1987 | District 4","1999 | District 6",
                "2003 | District 1","1980 | District 3","1970 | District 4", "2001 | District 1", "1995 | District 7","1987 | District 4","1999 | District 6",
                "2003 | District 1"};
         */
        id = Manager.getUserIntList("userID");

        ArrayList<String> name = Manager.getUserStringList("fullname");
        ArrayList<String> yob = Manager.getUserStringList("yob");
        ArrayList<String> pID = Manager.getUserStringList("personalID");
        ArrayList<String> addressID = Manager.getUserStringList("addressID");
        ArrayList<String> status = Manager.getUserStringList("status");
        ArrayList<String> placeID = Manager.getUserStringList("placeID");

        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> place = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            address.add(Manager.getFullAddress(Integer.parseInt(addressID.get(i))));
            place.add(Manager.getFullPlace(Integer.parseInt(placeID.get(i))));
        }

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label.add(yob.get(i) + " | " + pID.get(i));
        }

        ArrayList<String> label_full = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label_full.add(label.get(i) + " | " + address.get(i) + "\nF" + status.get(i) + " | " + place.get(i));
        }

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            if (i % 2 == 0) {
                iconName.add("male");
            } else {
                iconName.add("female");
            }
        }

        list = new JNeoList(iconName, name, label, label_full);
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // title
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);

        // search bar
        layout.putConstraint(SpringLayout.WEST, searchBar, 8,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.EAST, searchBar, -44,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, searchBar, -8,
                SpringLayout.NORTH, title);
        layout.putConstraint(SpringLayout.SOUTH, searchBar, 12,
                SpringLayout.SOUTH, title);

        // list
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, 12,
                SpringLayout.SOUTH, searchBar);
        layout.putConstraint(SpringLayout.SOUTH, list, -24,
                SpringLayout.SOUTH, this);

    }

    void addAllActionListener() {
        searchBar.getTf().addActionListener(e -> {
            id = Manager.sortSearchedUser(searchBar.getTf().getText(), order);

            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> yob = new ArrayList<>();
            ArrayList<String> pID = new ArrayList<>();
            ArrayList<String> address = new ArrayList<>();
            ArrayList<String> status = new ArrayList<>();
            ArrayList<String> place = new ArrayList<>();

            for (int k = 0; k < id.size(); k++) {
                name.add(Manager.getUserDetail(id.get(k), "fullname"));
                yob.add(Manager.getUserDetail(id.get(k), "yob"));
                pID.add(Manager.getUserDetail(id.get(k), "personalID"));
                address.add(Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(id.get(k), "addressID"))));
                status.add(Manager.getUserDetail(id.get(k), "status"));
                place.add(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(id.get(k), "placeID"))));
            }

            ArrayList<String> label = new ArrayList<>();
            for (int k = 0; k < id.size(); k++) {
                label.add(yob.get(k) + " | " + pID.get(k));
            }

            ArrayList<String> label_full = new ArrayList<>();
            for (int k = 0; k < id.size(); k++) {
                label_full.add(label.get(k) + " | " + address.get(k) + "\nF" + status.get(k) + " | " + place.get(k));
            }

            ArrayList<String> iconName = new ArrayList<>();
            for (int k = 0; k < id.size(); k++) {
                if (k % 2 == 0) {
                    iconName.add("male");
                } else {
                    iconName.add("female");
                }
            }

            list.setNewList(iconName, name, label, label_full);

            addListActionListener();
        });

        for (JNeoButton tag : searchBar.filter_tags) {
            tag.addActionListener(e -> {
                if (!tag.enabled) {
                    id = Manager.sortSearchedUser(searchBar.getTf().getText(), tag.getContent());
                    order = tag.getContent();
                } else {
                    id = Manager.sortSearchedUser(searchBar.getTf().getText(), "ID");
                    order = "ID";
                }

                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> yob = new ArrayList<>();
                ArrayList<String> pID = new ArrayList<>();
                ArrayList<String> address = new ArrayList<>();
                ArrayList<String> status = new ArrayList<>();
                ArrayList<String> place = new ArrayList<>();

                for (int k = 0; k < id.size(); k++) {
                    name.add(Manager.getUserDetail(id.get(k), "fullname"));
                    yob.add(Manager.getUserDetail(id.get(k), "yob"));
                    pID.add(Manager.getUserDetail(id.get(k), "personalID"));
                    address.add(Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(id.get(k), "addressID"))));
                    status.add(Manager.getUserDetail(id.get(k), "status"));
                    place.add(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(id.get(k), "placeID"))));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int k = 0; k < id.size(); k++) {
                    label.add(yob.get(k) + " | " + pID.get(k));
                }

                ArrayList<String> label_full = new ArrayList<>();
                for (int k = 0; k < id.size(); k++) {
                    label_full.add(label.get(k) + " | " + address.get(k) + "\nF" + status.get(k) + " | " + place.get(k));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < id.size(); k++) {
                    if (k % 2 == 0) {
                        iconName.add("male");
                    } else {
                        iconName.add("female");
                    }
                }

                list.setNewList(iconName, name, label, label_full);

                addListActionListener();
            });
        }

        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Manager.getPPatientAdd());
        });

        addListActionListener();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();

        int count = 0;
        for (JNeoListItem i : item) {
            i.assignID(id.get(count));
            count++;
        }

        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {
                ArrayList<Integer> relatedID = Manager.getUserRelation(i.getID());

                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> yob = new ArrayList<>();
                ArrayList<String> pID = new ArrayList<>();
                ArrayList<String> address = new ArrayList<>();
                ArrayList<String> status = new ArrayList<>();
                ArrayList<String> place = new ArrayList<>();

                for (int k = 0; k < relatedID.size(); k++) {
                    name.add(Manager.getUserDetail(relatedID.get(k), "fullname"));
                    yob.add(Manager.getUserDetail(relatedID.get(k), "yob"));
                    pID.add(Manager.getUserDetail(relatedID.get(k), "personalID"));
                    address.add(Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "addressID"))));
                    status.add(Manager.getUserDetail(relatedID.get(k), "status"));
                    place.add(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "placeID"))));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label.add(yob.get(k) + " | " + pID.get(k));
                }

                ArrayList<String> label_full = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label_full.add(label.get(k) + " | " + address.get(k) + "\nF" + status.get(k) + " | " + place.get(k));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    if (k % 2 == 0) {
                        iconName.add("male");
                    } else {
                        iconName.add("female");
                    }
                }

                ArrayList<ArrayList<String>> related = new ArrayList<>(4);
                related.add(iconName);
                related.add(name);
                related.add(label);
                related.add(label_full);

                GUI_Manager.getPPatientInfo().setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), i.getLbSubFull2(), relatedID, related);
                GUI_Master.changePanel(GUI_Manager.getPPatientInfo());
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(list);
        add(searchBar);
    }

}

class PaneNecessityList extends JPanel {

    static ArrayList<Integer> id;
    String order = "ID";

    JSideBar sideBar;
    JNeoList list;
    JNeoLabel title;
    JNeoSearchBar searchBar;

    PaneNecessityList() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, 1);

        // title
        title = new JNeoLabel("Necessity list", Global.fntHeader, Global.colDark);

        // search bar
        String[] filter_names = {"Name", "Price"};
        searchBar = new JNeoSearchBar("", 15, filter_names);

        // list
        id = Manager.getNecessityIntList("necessityID");

        ArrayList<String> name = Manager.getNecessityStringList("name");

        ArrayList<String> price = Manager.getNecessityStringList("price");
        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label.add("Price: " + price.get(i));
        }
        ArrayList<String> label_full = label;

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            iconName.add("sb_package");
        }

        list = new JNeoList(iconName, name, label, label_full);
        list.setBtnIcon("edit");
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // title
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);

        // search bar
        layout.putConstraint(SpringLayout.WEST, searchBar, 8,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.EAST, searchBar, -44,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, searchBar, -8,
                SpringLayout.NORTH, title);
        layout.putConstraint(SpringLayout.SOUTH, searchBar, 12,
                SpringLayout.SOUTH, title);

        // list
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, 12,
                SpringLayout.SOUTH, searchBar);
        layout.putConstraint(SpringLayout.SOUTH, list, -24,
                SpringLayout.SOUTH, this);

    }

    void addAllActionListener() {
        searchBar.getTf().addActionListener(e -> {
            //id = Manager.searchNecessityByName(searchBar.getTf().getText());
            id = Manager.sortSearchedNecessity(searchBar.getTf().getText(), order);

            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> label = new ArrayList<>();
            ArrayList<String> label_full = new ArrayList<>();

            for (int i = 0; i < id.size(); i++) {
                name.add(Manager.getNecessityDetail(id.get(i), "name"));
                label.add("Price: " + Manager.getNecessityDetail(id.get(i), "price"));
                label_full.add("Price: " + Manager.getNecessityDetail(id.get(i), "price"));
            }

            ArrayList<String> iconName = new ArrayList<>();
            for (int i = 0; i < id.size(); i++) {
                iconName.add("sb_package");
            }

            list.setNewList(iconName, name, label, label_full);

            addListActionListener();
        });

        for (JNeoButton tag : searchBar.filter_tags) {
            tag.addActionListener(e -> {
                if (!tag.enabled) {
                    id = Manager.sortSearchedNecessity(searchBar.getTf().getText(), tag.getContent());
                    order = tag.getContent();
                } else {
                    id = Manager.sortSearchedNecessity(searchBar.getTf().getText(), "ID");
                    order = "ID";
                }

                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> label = new ArrayList<>();
                ArrayList<String> label_full = new ArrayList<>();

                for (int i = 0; i < id.size(); i++) {
                    name.add(Manager.getNecessityDetail(id.get(i), "name"));
                    label.add("Price: " + Manager.getNecessityDetail(id.get(i), "price"));
                    label_full.add("Price: " + Manager.getNecessityDetail(id.get(i), "price"));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int i = 0; i < id.size(); i++) {
                    iconName.add("sb_package");
                }

                list.setNewList(iconName, name, label, label_full);

                addListActionListener();
            });
        }

        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Manager.getPNecessityAdd());
        });

        addListActionListener();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();

        int count = 0;
        for (JNeoListItem i : item) {
            i.assignID(id.get(count));
            count++;
        }

        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {
                GUI_Manager.getPNecessityEdit().assignID(i.getID());
                GUI_Master.changePanel(GUI_Manager.getPNecessityEdit());
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(list);
        add(searchBar);
    }

}

class PanePatientForm extends JPanel {

    int ID;

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_fullname, tf_birthyear, tf_personalID, tf_addressID;
    JNeoTextField tf_place, tf_status;
    Container ctn_tf, ctn_tf_2;
    JNeoButton btn_add, btn_updatePlace, btn_updateStatus;
    JLabel lb_error_add, lb_error_place, lb_error_status;
    JNeoComboBox cb_place;
    boolean isAdd;

    PanePatientForm(boolean isAdd) {
        super();
        this.isAdd = isAdd;

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, -1);

        // title
        String str_btn = isAdd ? "Add" : "Update";
        String str_title = isAdd ? " new " : " ";
        title = new JNeoLabel(str_btn + str_title + "patient", Global.fntHeader, Global.colDark);

        // text fields
        tf_fullname = new JNeoTextField("Full name", 14, false, "account", "!NULL");
        tf_birthyear = new JNeoTextField("Birth year", 14, false, "account", "Format must be 'yyyy'");
        tf_personalID = new JNeoTextField("Personal ID", 14, false, "account", "Personal ID is not valid");
        tf_addressID = new JNeoTextField("Address ID", 14, false, "account", "Address does not exist");

        tf_place = new JNeoTextField("Treatment location", 14, false, "account", "Location does not exist");
        tf_status = new JNeoTextField("Status", 14, false, "account", "Invalid status (0 -> 3)");

        // combo box
        cb_place = new JNeoComboBox("account");
        queryComboBox(cb_place);
        cb_place.setEditable(true);

        // button
        btn_add = new JNeoButton("Add", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_updatePlace = new JNeoButton("Update", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_updateStatus = new JNeoButton("Update", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error_add = new JNeoLabel("Patient already exists", Global.fntSecond, Color.WHITE);
        lb_error_place = new JNeoLabel("Location does not exist", Global.fntSecond, Color.WHITE);
        lb_error_status = new JNeoLabel("Must be a positive number", Global.fntSecond, Color.WHITE);
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // title
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);

        // text fields
        ctn_tf = new Container();
        ctn_tf_2 = new Container();
        ctn_tf.setLayout(new BoxLayout(ctn_tf, BoxLayout.Y_AXIS));
        ctn_tf_2.setLayout(new BoxLayout(ctn_tf_2, BoxLayout.Y_AXIS));
        if (isAdd) {
            ctn_tf.add(tf_fullname);
            ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
            ctn_tf.add(tf_birthyear);
            ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
            ctn_tf.add(tf_personalID);

            ctn_tf_2.add(tf_addressID);
            ctn_tf_2.add(Box.createRigidArea(new Dimension(0, 4)));
        } else {
            ctn_tf.add(tf_place);
        }
        ctn_tf_2.add(tf_status);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);
        if (isAdd) {
            // cb
            layout.putConstraint(SpringLayout.WEST, cb_place, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.NORTH, cb_place, 4,
                    SpringLayout.SOUTH, ctn_tf);
        }

        if (isAdd) {
            layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.NORTH, btn_add, 32,
                    SpringLayout.SOUTH, cb_place);
            layout.putConstraint(SpringLayout.WEST, ctn_tf_2, 8,
                    SpringLayout.EAST, ctn_tf);
            layout.putConstraint(SpringLayout.NORTH, ctn_tf_2, 0,
                    SpringLayout.NORTH, ctn_tf);
        } else {
            layout.putConstraint(SpringLayout.WEST, btn_updatePlace, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.WEST, btn_updateStatus, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.NORTH, btn_updatePlace, 8,
                    SpringLayout.SOUTH, ctn_tf);
            layout.putConstraint(SpringLayout.WEST, ctn_tf_2, 0,
                    SpringLayout.WEST, ctn_tf);
            layout.putConstraint(SpringLayout.NORTH, ctn_tf_2, 32,
                    SpringLayout.SOUTH, btn_updatePlace);
            layout.putConstraint(SpringLayout.NORTH, btn_updateStatus, 8,
                    SpringLayout.SOUTH, ctn_tf_2);
        }

        // label
        layout.putConstraint(SpringLayout.WEST, lb_error_add, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_add, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);
        layout.putConstraint(SpringLayout.WEST, lb_error_place, 16,
                SpringLayout.EAST, btn_updatePlace);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_place, 0,
                SpringLayout.VERTICAL_CENTER, btn_updatePlace);
        layout.putConstraint(SpringLayout.WEST, lb_error_status, 16,
                SpringLayout.EAST, btn_updateStatus);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_status, 0,
                SpringLayout.VERTICAL_CENTER, btn_updateStatus);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            int addressID, placeID;

            // check birthyear
            if (tf_birthyear.getText().matches("^[0-9]+$") && tf_birthyear.getText().length() == 4 && Integer.parseInt(tf_birthyear.getText()) < 2022) {
                tf_birthyear.hideHint();
            } else {
                tf_birthyear.showHint();
                return;
            }

            // check addressID
            if (Manager.existedAddress(tf_addressID.getText()) > 0) {
                addressID = Manager.existedAddress(tf_addressID.getText());
                tf_addressID.hideHint();
            } else {
                tf_addressID.showHint();
                return;
            }

            // check status
            if (!"".equals(tf_status.getText()) && Integer.parseInt(tf_status.getText()) > -1 && Integer.parseInt(tf_status.getText()) < 4) {
                tf_status.hideHint();
            } else {
                tf_status.showHint();
                return;
            }

            // check placeID
            if (Manager.existedPlace(tf_place.getText()) > 0) {
                placeID = Manager.existedPlace(tf_place.getText());
                tf_place.hideHint();
            } else {
                tf_place.showHint();
                return;
            }

            // check existed User
            if (Manager.existedUser(tf_personalID.getText()) == 0) {
                lb_error_add.setForeground(Color.WHITE);
            } else {
                tf_personalID.showHint();
                lb_error_add.setForeground(Global.colError);
                return;
            }

            // create a user in database
            Manager.createUser(tf_fullname.getText(), tf_personalID.getText(), Integer.parseInt(tf_birthyear.getText()), addressID, Integer.parseInt(tf_status.getText()), placeID);

            // reset all text fields
            PanePatientList.id = Manager.getUserIntList("userID");
            GUI_Master.changePanel(GUI_Manager.getUpdatedPPatientList());
            lb_error_add.setForeground(Color.WHITE);
            tf_fullname.setText("Full name");
            tf_birthyear.setText("Birth year (yyyy)");
            tf_personalID.setText("Personal ID");
            tf_addressID.setText("Address ID");
            tf_status.setText("Status");
            tf_place.setText("Treatment location");
        });

        btn_updateStatus.addActionListener(e -> {
            // check status
            if (!"".equals(tf_status.getText()) && Integer.parseInt(tf_status.getText()) > -1 && Integer.parseInt(tf_status.getText()) < 4
                    && Integer.parseInt(tf_status.getText()) < Integer.parseInt(Manager.getUserDetail(ID, "status"))) {
                // update DB
                ArrayList<Integer> arr = new ArrayList<Integer>();
                Manager.updateUserStatus(ID, Integer.parseInt(tf_status.getText()), arr);
                tf_status.hideHint();

                // reset panes
                PanePatientInfo.ID = ID;
                GUI_Master.changePanel(GUI_Manager.getUpdatedPPatientInfo());
                lb_error_add.setForeground(Color.WHITE);
                tf_status.setText("Status");
            } else {
                tf_status.showHint();
                return;
            }
        });

        btn_updatePlace.addActionListener(e -> {
            // check place
            if (Manager.existedPlace(tf_place.getText()) > 0) {
                // update DB
                Manager.updateUserPlace(ID, Manager.existedPlace(tf_place.getText()));
                tf_place.hideHint();

                // reset panes
                PanePatientInfo.ID = ID;
                GUI_Master.changePanel(GUI_Manager.getUpdatedPPatientInfo());
                lb_error_add.setForeground(Color.WHITE);
                tf_place.setText("Treatment location");
            } else {
                tf_place.showHint();
                return;
            }
        });
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(ctn_tf);
        add(ctn_tf_2);
        if (isAdd) {
            add(lb_error_add);
            add(btn_add);
            add(cb_place);
        } else {
            add(lb_error_place);
            add(lb_error_status);
            add(btn_updatePlace);
            add(btn_updateStatus);
        }
    }

    void assignID(int ID) {
        this.ID = ID;
        
        this.tf_status.setText(Manager.getUserDetail(ID, "status"));
        this.tf_place.setText(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(ID, "placeID"))));
    }

    // there's only 1 type so no need for 'int type'
    void queryComboBox(JNeoComboBox cb) {
        String[] sample = {"Bệnh viện đa khoa Bình Thuận", "Trường THPT chuyên Trần Hưng Đạo", "Kí túc xá ĐHQG TP. Hồ Chí Minh"}; // example
        ArrayList<String> item_list = new ArrayList<>();
        Collections.addAll(item_list, sample); // example
        cb.removeAllItems();
        cb.addItemList(item_list);
    }
}

class PaneNecessityForm extends JPanel {

    int ID = 0;

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_name, tf_price;
    Container ctn_tf;
    JNeoButton btn_add, btn_delete;
    JNeoLabel lb_error;
    boolean isAdd;

    PaneNecessityForm(boolean isAdd) {
        super();
        this.isAdd = isAdd;

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, -1);

        // title
        String str_btn = isAdd ? "Add" : "Update";
        String str_title = isAdd ? " new " : " ";
        title = new JNeoLabel(str_btn + str_title + "necessity", Global.fntHeader, Global.colDark);

        // text fields
        tf_name = new JNeoTextField("Necessity name", 20, false, "account", "!NULL");
        tf_price = new JNeoTextField("Price", 20, false, "account", "Must be a positive number");

        // button
        btn_add = new JNeoButton(str_btn, Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_delete = new JNeoButton("Delete", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        String str_error = isAdd ? "Necessity already exists" : "Information matches with another necessity's";
        lb_error = new JNeoLabel(str_error, Global.fntSecond, Color.WHITE);
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // title
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);

        // text fields
        ctn_tf = new Container();
        ctn_tf.setLayout(new BoxLayout(ctn_tf, BoxLayout.Y_AXIS));
        ctn_tf.add(tf_name);
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 12)));
        ctn_tf.add(tf_price);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 24,
                SpringLayout.SOUTH, ctn_tf);
        layout.putConstraint(SpringLayout.WEST, btn_delete, 144,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, btn_delete, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

        // label
        if (isAdd) {
            layout.putConstraint(SpringLayout.WEST, lb_error, 16,
                    SpringLayout.EAST, btn_add);
        } else {
            layout.putConstraint(SpringLayout.WEST, lb_error, 16,
                    SpringLayout.EAST, btn_delete);
        }
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            // add state
            if (isAdd == true) {
                boolean valid = true;

                // check valid price
                if (tf_price.getText().matches("^[0-9]+$") && Integer.parseInt(tf_price.getText()) > 0) {
                    tf_price.hideHint();
                } else {
                    valid = false;
                    tf_price.showHint();
                }

                // check existed necessity
                if (Manager.existedNecessity(tf_name.getText()) == 0) {
                    lb_error.setForeground(Color.WHITE);
                } else {
                    valid = false;
                    lb_error.setForeground(Global.colError);
                }

                if (valid) {
                    // create necessity
                    Manager.createNecessity(tf_name.getText(), Integer.parseInt(tf_price.getText()));

                    // reset all text fields
                    PaneNecessityList.id = Manager.getNecessityIntList("necessityID");
                    GUI_Master.changePanel(GUI_Manager.getUpdatedPNecessityList());
                    lb_error.setForeground(Color.WHITE);
                    tf_name.setText("Necessity name");
                    tf_price.setText("Price");
                }
            } // update state 
            else {
                boolean valid = true;

                // check valid price
                if (tf_price.getText().matches("^[0-9]+$") && Integer.parseInt(tf_price.getText()) > 0) {
                    tf_price.hideHint();
                } else {
                    valid = false;
                    tf_price.showHint();
                }

                // check existed necessity
                if (tf_name.getText().equals(Manager.getNecessityDetail(ID, "name")) || Manager.existedNecessity(tf_name.getText()) == 0) {
                    lb_error.setForeground(Color.WHITE);
                } else {
                    valid = false;
                    lb_error.setForeground(Global.colError);
                }

                if (valid) {
                    // update necessity
                    Manager.updateNecessity(ID, tf_name.getText(), Integer.parseInt(tf_price.getText()));

                    // reset all text fields
                    PaneNecessityList.id = Manager.getNecessityIntList("necessityID");
                    GUI_Master.changePanel(GUI_Manager.getUpdatedPNecessityList());
                    lb_error.setForeground(Color.WHITE);
                    tf_name.setText("Necessity name");
                    tf_price.setText("Price");
                }
            }
        });

        btn_delete.addActionListener(e -> {
            // delete necessity
            Manager.deleteNecessity(ID);

            // reset panel
            PaneNecessityList.id = Manager.getNecessityIntList("necessityID");
            GUI_Master.changePanel(GUI_Manager.getUpdatedPNecessityList());
        });
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(ctn_tf);
        add(btn_add);
        add(lb_error);
        if (!isAdd) {
            add(btn_delete);
        }
    }

    void assignID(int ID) {
        this.ID = ID;

        // set text
        tf_name.setText(Manager.getNecessityDetail(ID, "name"));
        tf_price.setText(Manager.getNecessityDetail(ID, "price"));
    }
}

class PanePatientInfo extends JPanel {

    static int ID = 1;
    static ArrayList<Integer> relatedID;

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoButton btn_edit;
    private JNeoLabel lb_fullname, lb_subtitle, lb_subtitle2;
    private JNeoList list;
    private JNeoTextField tf_add;

    PanePatientInfo() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() { // unvaluable list init because there're always setInfo() before 
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, -1);

        // labels
        lb_fullname = new JNeoLabel("", Global.fntHeader, Global.colDark);
        lb_subtitle = new JNeoLabel("", Global.fntButton, Global.colSecond);
        lb_subtitle2 = new JNeoLabel("", Global.fntButton, Global.colSecond);

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(3, 1);
        grid.setVgap(-16);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_fullname);
        ctn_lb.add(lb_subtitle);
        ctn_lb.add(lb_subtitle2);

        // list
        relatedID = Manager.getUserRelation(ID);

        lb_fullname.setText(Manager.getUserDetail(ID, "fullname"));
        lb_fullname.repaint();
        lb_subtitle.setText(Manager.getUserDetail(ID, "yob") + " | " + Manager.getUserDetail(ID, "personalID") + " | " + Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(ID, "addressID"))));
        lb_subtitle2.setText("F" + Manager.getUserDetail(ID, "status") + " | " + Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(ID, "placeID"))));
        lb_fullname.repaint();

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> yob = new ArrayList<>();
        ArrayList<String> pID = new ArrayList<>();
        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> status = new ArrayList<>();
        ArrayList<String> place = new ArrayList<>();

        for (int k = 0; k < relatedID.size(); k++) {
            name.add(Manager.getUserDetail(relatedID.get(k), "fullname"));
            yob.add(Manager.getUserDetail(relatedID.get(k), "yob"));
            pID.add(Manager.getUserDetail(relatedID.get(k), "personalID"));
            address.add(Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "addressID"))));
            status.add(Manager.getUserDetail(relatedID.get(k), "status"));
            place.add(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "placeID"))));
        }

        ArrayList<String> label = new ArrayList<>();
        for (int k = 0; k < relatedID.size(); k++) {
            label.add(yob.get(k) + " | " + pID.get(k));
        }

        ArrayList<String> label_full = new ArrayList<>();
        for (int k = 0; k < relatedID.size(); k++) {
            label_full.add(label.get(k) + " | " + address.get(k) + "\nF" + status.get(k) + " | " + place.get(k));
        }

        ArrayList<String> iconName = new ArrayList<>();
        for (int k = 0; k < relatedID.size(); k++) {
            if (k % 2 == 0) {
                iconName.add("male");
            } else {
                iconName.add("female");
            }
        }

        list = new JNeoList(iconName, name, label, label_full);

        // button
        btn_edit = new JNeoButton("Update", Global.colPrimary, Color.WHITE, Global.btnRadius,
                8, Global.fntButton, false);

        // text field
        tf_add = new JNeoTextField("Add related patient ID", 12, false, "account", "Patient does not exist");
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // labels container
        layout.putConstraint(SpringLayout.WEST, ctn_lb, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, ctn_lb, 32,
                SpringLayout.NORTH, this);

        // list
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, 12,
                SpringLayout.SOUTH, ctn_lb);
        layout.putConstraint(SpringLayout.SOUTH, list, -24,
                SpringLayout.SOUTH, this);

        // button
        layout.putConstraint(SpringLayout.EAST, btn_edit, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, btn_edit, 0,
                SpringLayout.NORTH, ctn_lb);

        // text field
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_add, -32,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, tf_add, -8,
                SpringLayout.SOUTH, this);
    }

    void addAllActionListener() {
        btn_edit.addActionListener(e -> {
            GUI_Manager.getPPatientEdit().assignID(ID);
            GUI_Master.changePanel(GUI_Manager.getPPatientEdit());
        });

        list.getBtnAdd().addActionListener(e -> {
            ArrayList<Integer> userID = Manager.getUserIntList("userID");
            int inputID = Manager.getUserID(tf_add.getText());

            if (inputID != 0 && !relatedID.contains(inputID)) {
                // add new relation
                Manager.addRelatedID(ID, inputID);
                Manager.addRelatedID(inputID, ID);

                // update status
                if (Integer.parseInt(Manager.getUserDetail(ID, "status")) < Integer.parseInt(Manager.getUserDetail(inputID, "status")) - 1) {
                    ArrayList<Integer> arr = new ArrayList<>();
                    Manager.updateUserStatus(inputID, Integer.parseInt(Manager.getUserDetail(inputID, "status")) - 1, arr);
                } else if (Integer.parseInt(Manager.getUserDetail(ID, "status")) - 1 > Integer.parseInt(Manager.getUserDetail(inputID, "status"))) {
                    ArrayList<Integer> arr = new ArrayList<>();
                    Manager.updateUserStatus(ID, Integer.parseInt(Manager.getUserDetail(ID, "status")) - 1, arr);
                }

                GUI_Master.changePanel(GUI_Manager.getUpdatedPPatientInfo());
                tf_add.setText("Add related patient ID");
            } else {
                tf_add.showHint();
                return;
            }
        });

        addListActionListener();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();

        int count = 0;
        for (JNeoListItem i : item) {
            i.assignID(relatedID.get(count));
            count++;
        }

        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {
                ArrayList<Integer> relatedID = Manager.getUserRelation(i.getID());

                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> yob = new ArrayList<>();
                ArrayList<String> pID = new ArrayList<>();
                ArrayList<String> address = new ArrayList<>();
                ArrayList<String> status = new ArrayList<>();
                ArrayList<String> place = new ArrayList<>();

                for (int k = 0; k < relatedID.size(); k++) {
                    name.add(Manager.getUserDetail(relatedID.get(k), "fullname"));
                    yob.add(Manager.getUserDetail(relatedID.get(k), "yob"));
                    pID.add(Manager.getUserDetail(relatedID.get(k), "personalID"));
                    address.add(Manager.getFullAddress(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "addressID"))));
                    status.add(Manager.getUserDetail(relatedID.get(k), "status"));
                    place.add(Manager.getFullPlace(Integer.parseInt(Manager.getUserDetail(relatedID.get(k), "placeID"))));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label.add(yob.get(k) + " | " + pID.get(k));
                }

                ArrayList<String> label_full = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label_full.add(label.get(k) + " | " + address.get(k) + "\nF" + status.get(k) + " | " + place.get(k));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    if (k % 2 == 0) {
                        iconName.add("male");
                    } else {
                        iconName.add("female");
                    }
                }

                ArrayList<ArrayList<String>> related = new ArrayList<>(4);
                related.add(iconName);
                related.add(name);
                related.add(label);
                related.add(label_full);

                setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), i.getLbSubFull2(), relatedID, related);
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(list);
        add(btn_edit);
        add(tf_add);
    }

    void setInfo(int ID, String fullname, String subtitle, String subtitle2, ArrayList<Integer> relatedID, ArrayList<ArrayList<String>> related) {
        PanePatientInfo.ID = ID;
        PanePatientInfo.relatedID = relatedID;

        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();
        lb_subtitle2.setText(subtitle2);
        lb_subtitle2.repaint();

        // related
        list.setNewList(related.get(0), related.get(1), related.get(2), related.get(3));
        addListActionListener();
    }
}

class PaneChart extends JPanel {

    JSideBar sideBar;
    JNeoLabel title;
    JNeoLabel lb_result, lb_type, lb_input;
    JNeoComboBox cb_type; // statistics type
    JNeoComboBox cb_input;

    SpringLayout layout;

    PaneChart() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Manager, 1, 2);

        // title
        title = new JNeoLabel("Statistics", Global.fntHeader, Global.colDark);

        // text field & label
        lb_type = new JNeoLabel("Type", Global.fntButton, Global.colDark);
        lb_input = new JNeoLabel("Status", Global.fntButton, Global.colDark);
        lb_result = new JNeoLabel("Result: 0", Global.fntButton, Global.colDark);

        // combo box
        cb_type = new JNeoComboBox("");
        String[] sample_type = {"Patient status", "Necessity", "Debt"};
        ArrayList<String> cb_type_list = new ArrayList<>();
        Collections.addAll(cb_type_list, sample_type);
        cb_type.addItemList(cb_type_list);
        cb_type.setEditable(true);

        cb_input = new JNeoComboBox("");
        String[] sample_input = {"F0", "F1", "F2"};
        ArrayList<String> cb_input_input = new ArrayList<>();
        Collections.addAll(cb_input_input, sample_input);
        cb_input.addItemList(cb_input_input);
        cb_input.setEditable(true);
    }

    void organize() {
        layout = new SpringLayout();
        this.setLayout(layout);

        // sideBar
        layout.putConstraint(SpringLayout.WEST, sideBar, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, sideBar, 48,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sideBar, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, sideBar, 0,
                SpringLayout.SOUTH, this);

        // title
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);

        // labels
        layout.putConstraint(SpringLayout.WEST, lb_type, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, lb_type, 48,
                SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.WEST, lb_input, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, lb_input, 48,
                SpringLayout.SOUTH, lb_type);
        layout.putConstraint(SpringLayout.WEST, lb_result, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, lb_result, 48,
                SpringLayout.SOUTH, lb_input);

        // combo boxes
        layout.putConstraint(SpringLayout.WEST, cb_type, 48,
                SpringLayout.EAST, lb_type);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, cb_type, 0,
                SpringLayout.VERTICAL_CENTER, lb_type);
        layout.putConstraint(SpringLayout.WEST, cb_input, 0,
                SpringLayout.WEST, cb_type);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, cb_input, 0,
                SpringLayout.VERTICAL_CENTER, lb_input);

    }

    void addAllActionListener() {
        cb_type.getSelector().addActionListener(e -> {
            ArrayList<String> cb_input_list = new ArrayList<>();
            switch (cb_type.getSelectedIndex()) {
                case 0:
                    String[] sample_list_0 = {"F0", "F1", "F2"}; // example
                    Collections.addAll(cb_input_list, sample_list_0);
                    cb_input.removeAllItems();
                    cb_input.addItemList(cb_input_list);
                    lb_result.setText("Number of F0 patients: 1000");
                    break;
                case 1:
                    String[] sample_list_1 = {"Gạo (1kg)", "Dầu ăn (1l)"}; // example
                    Collections.addAll(cb_input_list, sample_list_1);
                    cb_input.removeAllItems();
                    cb_input.addItemList(cb_input_list);
                    lb_result.setText("Total Rice (1kg) bought: 500");
                    break;
                case 2:
                    String[] sample_list_2 = {"Trần Thanh Tùng", "Phạm Trọng Vinh Khuê", "Trần Đại Hoàng Trung"}; // example
                    Collections.addAll(cb_input_list, sample_list_2);
                    cb_input.removeAllItems();
                    cb_input.addItemList(cb_input_list);
                    lb_result.setText("Trần Thanh Tùng's Debt: 200000");
                    break;
            }
            cb_input.repaintAll();
        });

        cb_input.getSelector().addActionListener(e -> {
            lb_result.setText("Query gì đó r đặt text mới ở đây!");
        });
    }

    void queryComboBox(JNeoComboBox cb) {
        String[] sample = {"Patient status", "Necessity", "Debt"};
        ArrayList<String> item_list = new ArrayList<>();
        Collections.addAll(item_list, sample);
        cb.removeAllItems();
        cb.addItemList(item_list);
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(cb_type); add(cb_input);
        add(lb_type); add(lb_input); add(lb_result);
    }
}
