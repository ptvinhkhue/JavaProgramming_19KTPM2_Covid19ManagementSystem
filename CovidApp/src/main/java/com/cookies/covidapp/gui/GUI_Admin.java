package com.cookies.covidapp.gui;

import com.cookies.covidapp.server.Login;
import com.cookies.covidapp.server.Manager;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUI_Admin {

    static PanePasswordAdmin pPasswordAdmin;
    static PaneManagerList pManagerList;
    static PaneManagerForm pManagerForm;

    void initPane() {
        pPasswordAdmin = new PanePasswordAdmin();
        pManagerList = new PaneManagerList();
        pManagerForm = new PaneManagerForm(true);
    }

    void init() {
        initPane();
    }

    public static PanePasswordAdmin getPPasswordAdmin() {
        return pPasswordAdmin;
    }
    public static PaneManagerList getPManagerList() { return pManagerList; }
    public static PaneManagerForm getPManagerForm() { return pManagerForm; }
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
//            if (Login.handlePassword(username, tf_password.getText())) {
//                GUI_Manager.manager = new Manager(username);
//                //System.out.println(username + " " + Manager.getID());
//                GUI_Master.changePanel(GUI_Manager.getPPatientList());
//            } else {
//                tf_password.showHint(); // Show hint/error below if false
//            }
            GUI_Master.changePanel(GUI_Admin.getPManagerList());
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
        PanePasswordManager.username = username;
    }

}

class PaneManagerList extends JPanel {

    static ArrayList<Integer> id;

    private JSideBar sideBar;
    private JNeoList list;
    private JNeoLabel title;
    private JNeoSearchBar searchBar;

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
        list.setBtnIcon("lock");

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
            int addressID, placeID;

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
