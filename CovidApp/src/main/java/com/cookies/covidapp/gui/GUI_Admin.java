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

    public static PaneManagerList getPManagerList() {
        return pManagerList;
    }

    public static PaneManagerForm getPManagerForm() {
        return pManagerForm;
    }

    public static PaneManagerInfo getPManagerInfo() {
        return pManagerInfo;
    }

    public static PanePlaceList getPPlaceList() {
        return pPlaceList;
    }

    public static PanePlaceForm getPPlaceAdd() {
        return pPlaceAdd;
    }

    public static PanePlaceForm getPPlaceEdit() {
        return pPlaceEdit;
    }

    public static PaneManagerList getUpdatedPManagerList() {
        return pManagerList = new PaneManagerList();
    }

    public static PanePlaceList getUpdatedPPlaceList() {
        return pPlaceList = new PanePlaceList();
    }
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
            iconName.add("account");
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
                    iconName.add("account");
                }

                ArrayList<ArrayList<String>> related = new ArrayList<>(4);
                related.add(iconName);
                related.add(name);
                related.add(label);
                related.add(label_full);

                GUI_Admin.getPManagerInfo().setInfo(i.getID(), i.getLbName().getText(), i.getLbSubFull(), "", related);
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
        tf_username = new JNeoTextField("Username", 14, false, "account_hl", "!NULL");
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
            boolean valid = true;

            // check username
            if (!"".equals(tf_username.getText())) {
                tf_username.hideHint();
            } else {
                valid = false;
                tf_username.showHint();
            }

            // check password
            if (!"".equals(tf_password.getText())) {
                tf_password.hideHint();
            } else {
                valid = false;
                tf_password.showHint();
            }

            // check existed manager
            if (Admin.existedManager(tf_username.getText()) == 0) {
                lb_error_add.setForeground(Color.WHITE);
            } else {
                valid = false;
                lb_error_add.setForeground(Global.colError);
            }

            if (valid) {
                // create manager
                Admin.createManager(tf_username.getText(), tf_password.getText());

                // reset panel
                PaneManagerList.id = Admin.getManagerIDList();
                GUI_Master.changePanel(GUI_Admin.getUpdatedPManagerList());
                lb_error_add.setForeground(Color.WHITE);
                tf_username.setText("Username");
                tf_password.setText("Password");
            }
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

    static int ID = 1;

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

        if (Integer.parseInt(Admin.getManagerDetail(ID, "locked")) == 0) {
            btn_lock.setText("Lock");
            lb_lock.setText("Status: Unlocked");
        } else {
            btn_lock.setText("Unlock");
            lb_lock.setText("Status: Locked");
        }

        // list
        ArrayList<Integer> historyID = Admin.getManagerHistoryID(ID);
        ArrayList<String> name = Admin.getManagerHistoryInfo(ID, "activity");
        ArrayList<String> label = Admin.getManagerHistoryInfo(ID, "datetime");
        ArrayList<String> label_full = label;

        ArrayList<String> iconName = new ArrayList<>();
        for (int k = 0; k < historyID.size(); k++) {
            iconName.add("account");
        }

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
                // lock manager
                Admin.lockManager(ID);
                btn_lock.setText("Unlock");
                lb_lock.setText("Status: Locked");
            } else {
                // unlock manager
                Admin.unlockManager(ID);
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

    void setInfo(int ID, String fullname, String subtitle, String subtitle2, ArrayList<ArrayList<String>> related) {
        PaneManagerInfo.ID = ID;

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
        id = Admin.getPlaceIntList("placeID");

        ArrayList<String> name = Admin.getPlaceStringList("name");

        ArrayList<String> capacity = Admin.getPlaceStringList("capacity");
        ArrayList<String> current = Admin.getPlaceStringList("current");
        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            label.add("Capacity: " + current.get(i) + "/" + capacity.get(i));
        }

        ArrayList<String> label_full = label;

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            iconName.add("location");
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
                GUI_Admin.getPPlaceEdit().assignID(i.getID());
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
    JNeoTextField tf_name, tf_capacity, tf_current;
    Container ctn_tf;
    JNeoButton btn_add, btn_delete;
    JLabel lb_error_add, lb_error_delete;
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
        tf_name = new JNeoTextField("Location name", 20, false, "location_hl", "!NULL");
        tf_capacity = new JNeoTextField("Maximum capacity", 20, false, "capacity_max_hl", "Must be a positive number.");
        tf_current = new JNeoTextField("Current capacity", 20, false, "capacity_hl", "Must be lower than Maximum capacity.");

        // button
        btn_add = new JNeoButton(str_btn, Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_delete = new JNeoButton("Delete", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error_add = new JNeoLabel("Location already exists", Global.fntSecond, Color.WHITE);
        lb_error_delete = new JNeoLabel("This place is still occupied bi active patient", Global.fntSecond, Color.WHITE);
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
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 4)));
        ctn_tf.add(tf_current);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 16,
                SpringLayout.SOUTH, ctn_tf);
        layout.putConstraint(SpringLayout.WEST, btn_delete, 0,
                SpringLayout.WEST, btn_add);
        layout.putConstraint(SpringLayout.NORTH, btn_delete, 32,
                SpringLayout.SOUTH, btn_add);

        // label
        layout.putConstraint(SpringLayout.WEST, lb_error_add, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_add, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);
        layout.putConstraint(SpringLayout.WEST, lb_error_delete, 16,
                SpringLayout.EAST, btn_delete);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error_delete, 0,
                SpringLayout.VERTICAL_CENTER, btn_delete);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            if (isAdd) {
                boolean valid = true;

                // capacity must be number
                if (tf_capacity.getText().matches("^[0-9]+$") && Integer.parseInt(tf_capacity.getText()) > 0) {
                    tf_capacity.hideHint();
                } else {
                    valid = false;
                    tf_capacity.showHint();
                    GUI_Master.changePanel(GUI_Admin.getPPlaceList());
                }

                // current validation
                if (tf_current.getText().matches("^[0-9]+$") && Integer.parseInt(tf_current.getText()) >= 0 && tf_capacity.getText().matches("^[0-9]+$")
                        && Integer.parseInt(tf_current.getText()) <= Integer.parseInt(tf_capacity.getText())) {
                    tf_current.hideHint();
                } else {
                    valid = false;
                    tf_current.showHint();
                }

                // existed place
                if (Admin.existedPlace(tf_name.getText()) == 0) {
                    lb_error_add.setForeground(Color.WHITE);
                } else {
                    valid = false;
                    lb_error_add.setForeground(Global.colError);
                }

                if (valid) {
                    // create a place in DB
                    Admin.createPlace(tf_name.getText(), Integer.parseInt(tf_capacity.getText()), Integer.parseInt(tf_current.getText()));

                    // reset panel
                    PanePlaceList.id = Admin.getPlaceIntList("placeID");
                    GUI_Master.changePanel(GUI_Admin.getUpdatedPPlaceList());
                    lb_error_add.setForeground(Color.WHITE);
                    tf_name.setText("Location name");
                    tf_capacity.setText("Maximum capacity");
                    tf_current.setText("Current capacity");
                }
            } else {
                boolean valid = true;

                // capacity must be number
                if (tf_capacity.getText().matches("^[0-9]+$") && Integer.parseInt(tf_capacity.getText()) > 0) {
                    tf_capacity.hideHint();
                } else {
                    valid = false;
                    tf_capacity.showHint();
                }

                // current validation
                if (tf_current.getText().matches("^[0-9]+$") && Integer.parseInt(tf_current.getText()) > 0 && tf_capacity.getText().matches("^[0-9]+$")
                        && Integer.parseInt(tf_current.getText()) <= Integer.parseInt(tf_capacity.getText())) {
                    tf_current.hideHint();
                } else {
                    valid = false;
                    tf_current.showHint();
                }

                // existed place
                if (tf_name.getText().equals(Admin.getPlaceDetail(ID, "name")) || Admin.existedPlace(tf_name.getText()) == 0) {
                    lb_error_add.setForeground(Color.WHITE);
                } else {
                    valid = false;
                    lb_error_add.setForeground(Global.colError);
                }

                if (valid) {
                    // create a place in DB
                    Admin.updatePlace(ID, tf_name.getText(), Integer.parseInt(tf_capacity.getText()), Integer.parseInt(tf_current.getText()));

                    // reset panel
                    PanePlaceList.id = Admin.getPlaceIntList("placeID");
                    GUI_Master.changePanel(GUI_Admin.getUpdatedPPlaceList());
                    lb_error_add.setForeground(Color.WHITE);
                    tf_name.setText("Location name");
                    tf_capacity.setText("Maximum capacity");
                    tf_current.setText("Current capacity");
                }
            }
        });

        btn_delete.addActionListener(e -> {
            // delete necessity
            if (Admin.occupiedPlace(ID)) {
                lb_error_delete.setForeground(Global.colError);
                return;
            } else {
                Admin.deletePlace(ID);

                // reset panel
                PanePlaceList.id = Admin.getIntField("place", "placeID");
                GUI_Master.changePanel(GUI_Admin.getUpdatedPPlaceList());
                lb_error_delete.setForeground(Color.WHITE);
            }
        });
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(ctn_tf);
        add(lb_error_add);
        add(btn_add);
        if (!isAdd) {
            add(btn_delete);
            add(lb_error_delete);
        }
    }

    void assignID(int ID) {
        this.ID = ID;

        // set text
        tf_name.setText(Admin.getPlaceDetail(ID, "name"));
        tf_capacity.setText(Admin.getPlaceDetail(ID, "capacity"));
        tf_current.setText(Admin.getPlaceDetail(ID, "current"));
    }
}
