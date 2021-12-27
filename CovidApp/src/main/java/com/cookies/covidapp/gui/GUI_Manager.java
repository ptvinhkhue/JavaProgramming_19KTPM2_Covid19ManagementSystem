package com.cookies.covidapp.gui;

import com.cookies.covidapp.server.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static javax.swing.BoxLayout.Y_AXIS;

public class GUI_Manager {

    static Manager manager;

    static PanePasswordManager pPasswordManager;
    static PanePatientList pPatientList;
    static PaneNecessityList pNecessityList;
    static PanePatientForm pPatientAdd, pPatientEdit;
    static PaneNecessityForm pNecessityAdd, pNecessityEdit;
    static PanePatientInfo pPatientInfo;

    void initPane() {
        pPasswordManager = new PanePasswordManager();
        pPatientList = new PanePatientList();
        pNecessityList = new PaneNecessityList();
        pPatientAdd = new PanePatientForm(true);
        pPatientEdit = new PanePatientForm(false);
        pNecessityAdd = new PaneNecessityForm(true);
        pNecessityEdit = new PaneNecessityForm(false);
        pPatientInfo = new PanePatientInfo();
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

    public static PanePatientInfo getUpdatedPPatientInfo() {
        pPatientList = new PanePatientList();
        return pPatientInfo = new PanePatientInfo();
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
        String[] filter_names = {"Name", "Birthyear", "Personal ID", "Address ID"};
        searchBar = new JNeoSearchBar("Search...", 15, filter_names);

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
        ArrayList<String> address = Manager.getUserStringList("addressID");
        ArrayList<String> status = Manager.getUserStringList("status");
        ArrayList<String> place = Manager.getUserStringList("placeID");
        //ArrayList<String> debt = Manager.getUserStringList("debt");

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label.add(yob.get(i) + " | " + pID.get(i));
        }

        ArrayList<String> label_full = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label_full.add(label.get(i) + " | " + address.get(i) + " | F" + status.get(i) + " | " + place.get(i));
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
            /*
            String[] iconName = { "male" , "male", "female", "male", "female", "male", "female", "female"};
            String[] name = { "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F", "Le G", "Vo Lien H"};
            String[] label = { "1995", "1998", "1996", "2002", "1999", "2000", "2001", "1993"};
             */
            id = Manager.searchUserByName(searchBar.getTf().getText());

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
                address.add(Manager.getUserDetail(id.get(k), "addressID"));
                status.add(Manager.getUserDetail(id.get(k), "status"));
                place.add(Manager.getUserDetail(id.get(k), "placeID"));
            }

            ArrayList<String> label = new ArrayList<>();
            for (int k = 0; k < id.size(); k++) {
                label.add(yob.get(k) + " | " + pID.get(k));
            }

            ArrayList<String> label_full = new ArrayList<>();
            for (int k = 0; k < id.size(); k++) {
                label_full.add(label.get(k) + " | " + address.get(k) + " | F" + status.get(k) + " | " + place.get(k));
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
                /*
                String[] iconName = {"male", "male", "female", "male", "female", "male"};
                String[] name = {"Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F"};
                String[] label = {"2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                    "2003 | District 1", "1980 | District 3"};
                 
                String[][] related = new String[3][iconName.size()];
                related[0] = iconName.get(0);
                related[1] = name;
                related[2] = label;
                 */
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
                    address.add(Manager.getUserDetail(relatedID.get(k), "addressID"));
                    status.add(Manager.getUserDetail(relatedID.get(k), "status"));
                    place.add(Manager.getUserDetail(relatedID.get(k), "placeID"));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label.add(yob.get(k) + " | " + pID.get(k));
                }

                ArrayList<String> label_full = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label_full.add(label.get(k) + " | " + address.get(k) + " | F" + status.get(k) + " | " + place.get(k));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    if (k % 2 == 0) {
                        iconName.add("male");
                    } else {
                        iconName.add("female");
                    }
                }

                /*
                ArrayList name = new ArrayList<>(Arrays.asList("Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                        "Trinh Xuan F"));
                ArrayList label = new ArrayList<>(Arrays.asList("2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                        "2003 | District 1", "1980 | District 3"));
                ArrayList iconName = new ArrayList<>(Arrays.asList("male", "male", "female", "male", "female", "male"));
                 */
                ArrayList<ArrayList<String>> related = new ArrayList<>(4);
                related.add(iconName);
                related.add(name);
                related.add(label);
                related.add(label_full);

                GUI_Manager.getPPatientInfo().setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), relatedID, related);
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
        String[] filter_names = {"Name", "Limit", "Price"};
        searchBar = new JNeoSearchBar("Search...", 15, filter_names);

        // list
        /*
        String[] iconName = new String[7];
        for (int i = 0; i < 7; i++) {
            iconName[i] = "sb_package";
        }
        String[] name = {"Rice", "Bleach", "Shampoo", "Noodle", "Perfume", "Drugs", "Panadol"};
        String[] label = {"$5", "$15", "$2", "$2", "$4", "$6", "$8",};
         */
        ArrayList<String> name = Manager.displayNecessityList("name");
        ArrayList<String> label = Manager.displayNecessityList("price");
        ArrayList<String> label_full = Manager.displayNecessityList("price");

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
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

        });
        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Manager.getPNecessityAdd());

        });
        addListActionListener();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();
        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {
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
        tf_personalID = new JNeoTextField("Personal ID", 14, false, "account", "Personal ID already exists");
        tf_addressID = new JNeoTextField("Address ID", 14, false, "account", "Address does not exist");

        tf_place = new JNeoTextField("Treatment location", 14, false, "account", "Location does not exist");
        tf_status = new JNeoTextField("Status", 14, false, "account", "Invalid status (0 -> 3)");

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
            ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
            ctn_tf.add(tf_addressID);
            ctn_tf_2.add(tf_place);
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
            layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.NORTH, btn_add, 16,
                    SpringLayout.SOUTH, ctn_tf);
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
        } else {
            add(lb_error_place);
            add(lb_error_status);
            add(btn_updatePlace);
            add(btn_updateStatus);
        }
    }

    void assignID(int ID) {
        this.ID = ID;
    }
}

class PaneNecessityForm extends JPanel {

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_name, tf_limit, tf_price;
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
        tf_limit = new JNeoTextField("Limit", 20, false, "account", "Must be a positive number");
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
        ctn_tf.add(tf_limit);
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
        layout.putConstraint(SpringLayout.WEST, btn_delete, 8,
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
            String str;
            boolean valid = true;
            boolean exist = false;

            // check birthyear
            str = tf_limit.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) > 0) {
                tf_limit.hideHint();
            } else {
                tf_limit.showHint();
                valid = false;
            }

            str = tf_price.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) > 0) {
                tf_price.hideHint();
            } else {
                tf_price.showHint();
                valid = false;
            }

            exist = (Objects.equals(tf_name.getText(), "Rice"));

            if (exist) {
                lb_error.setForeground(Global.colError);
                valid = false;
            } else {
                lb_error.setForeground(Color.WHITE);
            }

            if (!valid) {
                return;
            }

            // reset all text fields
            GUI_Master.changePanel(GUI_Manager.getPNecessityList());
            lb_error.setForeground(Color.WHITE);
            tf_name.setText("Necessity name");
            tf_limit.setText("Limit");
            tf_price.setText("Price");
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
}

class PanePatientInfo extends JPanel {

    static int ID = 1;
    static ArrayList<Integer> relatedID;

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoButton btn_edit;
    private JNeoLabel lb_fullname, lb_subtitle;
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

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(2, 1);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_fullname);
        ctn_lb.add(lb_subtitle);

        // list
        relatedID = Manager.getUserRelation(ID);

        lb_fullname.setText(Manager.getUserDetail(ID, "fullname"));
        lb_fullname.repaint();
        lb_subtitle.setText(Manager.getUserDetail(ID, "yob") + " | " + Manager.getUserDetail(ID, "personalID") + " | " + Manager.getUserDetail(ID, "addressID") + " | F" + Manager.getUserDetail(ID, "status") + " | " + Manager.getUserDetail(ID, "placeID"));
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
            address.add(Manager.getUserDetail(relatedID.get(k), "addressID"));
            status.add(Manager.getUserDetail(relatedID.get(k), "status"));
            place.add(Manager.getUserDetail(relatedID.get(k), "placeID"));
        }

        ArrayList<String> label = new ArrayList<>();
        for (int k = 0; k < relatedID.size(); k++) {
            label.add(yob.get(k) + " | " + pID.get(k));
        }

        ArrayList<String> label_full = new ArrayList<>();
        for (int k = 0; k < relatedID.size(); k++) {
            label_full.add(label.get(k) + " | " + address.get(k) + " | F" + status.get(k) + " | " + place.get(k));
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
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, btn_edit, 0,
                SpringLayout.VERTICAL_CENTER, ctn_lb);

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
                /*
                ArrayList name = new ArrayList<>(Arrays.asList("Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                        "Trinh Xuan F"));
                ArrayList label = new ArrayList<>(Arrays.asList("2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                        "2003 | District 1", "1980 | District 3"));
                ArrayList label_full = new ArrayList<>(Arrays.asList("2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                        "2003 | District 1", "1980 | District 3"));
                ArrayList iconName = new ArrayList<>(Arrays.asList("male", "male", "female", "male", "female", "male"));
                 */

                ArrayList<Integer> relatedID = Manager.getUserRelation(i.getID());
                //System.out.println(i.getID());

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
                    address.add(Manager.getUserDetail(relatedID.get(k), "addressID"));
                    status.add(Manager.getUserDetail(relatedID.get(k), "status"));
                    place.add(Manager.getUserDetail(relatedID.get(k), "placeID"));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label.add(yob.get(k) + " | " + pID.get(k));
                }

                ArrayList<String> label_full = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    label_full.add(label.get(k) + " | " + address.get(k) + " | F" + status.get(k) + " | " + place.get(k));
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

                setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), relatedID, related);
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

    void setInfo(int ID, String fullname, String subtitle, ArrayList<Integer> relatedID, ArrayList<ArrayList<String>> related) {
        PanePatientInfo.ID = ID;
        PanePatientInfo.relatedID = relatedID;

        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();

        // related
        list.setNewList(related.get(0), related.get(1), related.get(2), related.get(3));
        addListActionListener();
    }
}
