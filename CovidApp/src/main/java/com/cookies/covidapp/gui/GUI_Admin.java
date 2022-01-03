package com.cookies.covidapp.gui;

import com.cookies.covidapp.server.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI_Admin {
    
    static Admin admin;

    static PanePasswordAdmin pPasswordAdmin;
    static PaneManagerList pManagerList;
    static PaneManagerForm pManagerForm;
    static PaneManagerInfo pManagerInfo;
    static PanePlaceList pPlaceList;
    static PanePlaceForm pPlaceAdd;
    static PanePlaceForm pPlaceEdit;

    void initPane() {
        pPasswordAdmin = new PanePasswordAdmin();
        pManagerList = new PaneManagerList();
        pManagerForm = new PaneManagerForm(true);
        pManagerInfo = new PaneManagerInfo();
        pPlaceList = new PanePlaceList();
        pPlaceAdd = new PanePlaceForm(true);
        pPlaceEdit = new PanePlaceForm(false);
    }

    void init() {
        initPane();
    }

    public static PanePasswordAdmin getPPasswordAdmin() {
        return pPasswordAdmin;
    }
    public static PaneManagerList getPManagerList() { return pManagerList; }
    public static PaneManagerForm getPManagerForm() { return pManagerForm; }
    public static PaneManagerInfo getPManagerInfo() { return pManagerInfo; }
    public static PanePlaceList getPPlaceList() { return pPlaceList; }
    public static PanePlaceForm getPPlaceAdd() { return pPlaceAdd; }
    public static PanePlaceForm getPPlaceEdit() { return pPlaceEdit; }
}

class PanePasswordAdmin extends JPanel {

    static String username;

    private JNeoButton btn_login, btn_return;
    private Container btnContainer;
    private JNeoTextField tf_password;
    private JLabel lb_credit;
    private static JLabel lb_subtitle;
    private LogoBig logo;

    PanePasswordAdmin() {
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
        tf_password = new JNeoTextField("Password", 16, true, "lock_hl", "Password does not match.");

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
                GUI_Admin.admin = new Admin();
                GUI_Master.changePanel(GUI_Admin.getPManagerList());
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
        lb_subtitle.setText("Welcome back, Admin " + newUsername + ". Please enter your password.");
    }

    public static void assignUsername(String username) {
        PanePasswordAdmin.username = username;
    }

}

class PaneManagerList extends JPanel {

    static ArrayList<Integer> id;

    private JSideBar sideBar;
    private JNeoList list;
    private JNeoLabel title;

    PaneManagerList() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Admin, 2, 0);

        // title
        title = new JNeoLabel("Manager list", Global.fntHeader, Global.colDark);

        // search bar
        String[] filter_names = {"Name", "Birthyear", "Personal ID", "Address ID"};

        // list
        id = Admin.getManagerIDList();
        ArrayList<String> name = Admin.getManagerNameList();

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label.add("Manager ID: " + id.get(i).toString());
        }

        ArrayList<String> label_full = label;

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

        // list
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, 12,
                SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.SOUTH, list, -24,
                SpringLayout.SOUTH, this);

    }

    void addAllActionListener() {
        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Admin.getPManagerForm());
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
                ArrayList<Integer> historyID = Admin.getManagerHistoryID(i.getID());
                ArrayList<String> name = Admin.getManagerHistoryInfo(i.getID(), "activity");
                ArrayList<String> label = Admin.getManagerHistoryInfo(i.getID(), "datetime");
                ArrayList<String> label_full = label;

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < historyID.size(); k++) {
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

                GUI_Admin.getPManagerInfo().setInfo(i.getLbName().getText(), i.getLbSubFull(), "", related);
                GUI_Master.changePanel(GUI_Admin.getPManagerInfo());
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(list);
    }

}

class PaneManagerForm extends JPanel {

    int ID;

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_username, tf_password;
    Container ctn_tf;
    JNeoButton btn_add;
    JLabel lb_error_add;
    boolean isAdd;

    PaneManagerForm(boolean isAdd) {
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
        sideBar = new JSideBar(Global.itemIcon_Admin, 2, -1);

        // title
        String str_btn = isAdd ? "Add" : "Update";
        String str_title = isAdd ? " new " : " ";
        title = new JNeoLabel(str_btn + str_title + "manager", Global.fntHeader, Global.colDark);

        // text fields
        tf_username = new JNeoTextField("Username", 14, false, "account", "!NULL");
        tf_password = new JNeoTextField("Password", 14, false, "lock_hl", "!NULL");
        // button
        btn_add = new JNeoButton("Add", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error_add = new JNeoLabel("Manager already exists", Global.fntSecond, Color.WHITE);
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
        if (isAdd) {
            ctn_tf.add(tf_username);
            ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
            ctn_tf.add(tf_password);
        }

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        if (isAdd) {
            layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                    SpringLayout.WEST, title);
            layout.putConstraint(SpringLayout.NORTH, btn_add, 16,
                    SpringLayout.SOUTH, ctn_tf);
        }


        // label
        layout.putConstraint(SpringLayout.WEST, lb_error_add, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_add, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {

        });
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(ctn_tf);
        if (isAdd) {
            add(lb_error_add);
            add(btn_add);
        }
    }

    void assignID(int ID) {
        this.ID = ID;
    }
}

class PaneManagerInfo extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_fullname, lb_subtitle, lb_subtitle2;
    private JNeoList list;
    private JNeoButton btn_lock;
    private JNeoLabel lb_lock;

    PaneManagerInfo() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Admin, 2, -1);

        // labels
        lb_fullname = new JNeoLabel("", Global.fntHeader, Global.colDark);
        lb_subtitle = new JNeoLabel("", Global.fntButton, Global.colSecond);
        lb_subtitle2 = new JNeoLabel("", Global.fntButton, Global.colSecond);

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(3, 1);
        grid.setVgap(-15);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_fullname);
        ctn_lb.add(lb_subtitle);
        ctn_lb.add(lb_subtitle2);

        // button
        btn_lock = new JNeoButton("Lock", Global.colPrimary, Color.WHITE,
                Global.btnRadius, 8, Global.fntButton, false);
        lb_lock = new JNeoLabel("Status: Unlocked", Global.fntButton, Global.colDark);

        // list
        /*
        String[] iconName = { "male"};
        String[] name = { "Nguyen Van Lee"};
        String[] label = { "2001 | District 1"};
         */
        ArrayList<String> name = new ArrayList<>(Arrays.asList("Nguyen Van Lee"));
        ArrayList<String> label = new ArrayList<>(Arrays.asList("2001 | District 1"));
        ArrayList<String> label_full = new ArrayList<>(Arrays.asList("2001"));

        ArrayList<String> iconName = new ArrayList<>(Arrays.asList("male"));

        list = new JNeoList(iconName, name, label, label_full);
        list.removeBtnAdd();
        list.removeAllBtnInfo();

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

        // button - lock
        layout.putConstraint(SpringLayout.EAST, btn_lock, -32,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_lock, -24,
                SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, lb_lock, -16,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_lock, 0,
                SpringLayout.VERTICAL_CENTER, btn_lock);
    }

    void addAllActionListener() {
        btn_lock.addActionListener(e -> {
            if (btn_lock.getText().equals("Lock")) {
                btn_lock.setText("Unlock");
                lb_lock.setText("Status: Locked");
            } else {
                btn_lock.setText("Lock");
                lb_lock.setText("Status: Unlocked");
            }
        });
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(list);
        add(btn_lock);
        add(lb_lock);
    }

    void setInfo(String fullname, String subtitle, String subtitle2, ArrayList<ArrayList<String>> related) {
        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();
        lb_subtitle2.setText(subtitle2);
        lb_subtitle2.repaint();

        // related
        list.setNewList(related.get(0), related.get(1), related.get(2), related.get(2));
        list.removeBtnAdd();
        list.removeAllBtnInfo();
    }

}

class PanePlaceList extends JPanel {

    static ArrayList<Integer> id;

    private JSideBar sideBar;
    private JNeoList list;
    private JNeoLabel title;

    PanePlaceList() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_Admin, 2, 1);

        // title
        title = new JNeoLabel("Treatment location list", Global.fntHeader, Global.colDark);

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

        // list
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, 12,
                SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.SOUTH, list, -24,
                SpringLayout.SOUTH, this);

    }

    void addAllActionListener() {

        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Admin.getPPlaceAdd());
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

                GUI_Manager.getPPatientInfo().setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), i.getLbSubFull(), relatedID, related);
                GUI_Master.changePanel(GUI_Admin.getPPlaceEdit());
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(list);
    }

}

class PanePlaceForm extends JPanel {

    int ID;

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_name, tf_capacity;
    Container ctn_tf;
    JNeoButton btn_add;
    JLabel lb_error_add;
    boolean isAdd;

    PanePlaceForm(boolean isAdd) {
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
        sideBar = new JSideBar(Global.itemIcon_Admin, 2, -1);

        // title
        String str_btn = isAdd ? "Add" : "Update";
        String str_title = isAdd ? " new " : " ";
        title = new JNeoLabel(str_btn + str_title + "treatment location", Global.fntHeader, Global.colDark);

        // text fields
        tf_name = new JNeoTextField("Location name", 14, false, "account", "!NULL");
        tf_capacity = new JNeoTextField("Capacity", 14, false, "lock_hl", "Must be a positive number.");
        // button
        btn_add = new JNeoButton(str_btn, Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error_add = new JNeoLabel("Location already exists", Global.fntSecond, Color.WHITE);
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
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
        ctn_tf.add(tf_capacity);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 16,
                SpringLayout.SOUTH, ctn_tf);


        // label
        layout.putConstraint(SpringLayout.WEST, lb_error_add, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_add, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {

            // capacity must be number
            if (tf_capacity.getText().matches("^[0-9]+$") && Integer.parseInt(tf_capacity.getText()) < 0) {
                tf_capacity.hideHint();
            } else {
                tf_capacity.showHint();
                GUI_Master.changePanel(GUI_Admin.getPPlaceList());
            }

        });
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(ctn_tf);
        add(lb_error_add);
        add(btn_add);
    }

    void assignID(int ID) {
        this.ID = ID;
    }
}
