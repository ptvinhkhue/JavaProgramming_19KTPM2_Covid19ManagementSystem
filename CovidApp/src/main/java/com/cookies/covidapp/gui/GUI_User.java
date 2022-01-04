package com.cookies.covidapp.gui;

import Necessity.Necessity;
import com.cookies.covidapp.server.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI_User {

    static User user;
    static ArrayList<Necessity> cart;
    static PanePasswordUser pPasswordUser;
    static PanePersonalInfo pPersonalInfo;
    static PaneBuyNecessity pBuyNecessity;
    static PaneNecessityInfo pNecessityInfo;
    static PanePayment pPayment;
    static PaneCart pCart;
    static PaneHistory pHistory;

    public static Socket socket = null;
    public static String ip = "";
    public static int port = 0;
    public static String name = "";
    public static BufferedReader br = null;
    public static PrintWriter pw = null;

    void initPane() {
        cart = new ArrayList<>();
        pPasswordUser = new PanePasswordUser();
        pPersonalInfo = new PanePersonalInfo();
        pBuyNecessity = new PaneBuyNecessity();
        pNecessityInfo = new PaneNecessityInfo();
        pPayment = new PanePayment();
        pCart = new PaneCart();
        pHistory = new PaneHistory();
    }

    void init() {
        initPane();
        initSocket();
    }

    void initSocket() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 1235);
            if (socket == null) {
                throw new Exception("Null Socket");
            }
        } catch (Exception e) {

        }
    }

    public static PanePasswordUser getPPasswordUser() {
        return pPasswordUser;
    }

    public static PanePersonalInfo getPPersonalInfo() {
        return pPersonalInfo;
    }

    public static PaneBuyNecessity getPBuyNecessity() {
        return pBuyNecessity;
    }

    public static PaneNecessityInfo getPNecessityInfo() {
        return pNecessityInfo;
    }

    public static PanePayment getPPayment() {
        return pPayment;
    }

    public static PaneCart getPCart() {
        return pCart;
    }

    public static PaneHistory getPHistory() {
        return pHistory;
    }

}

class PanePasswordUser extends JPanel {

    private JNeoButton btn_login, btn_return;
    private Container btnContainer;
    private JNeoTextField tf_password;
    private JLabel lb_credit;
    private static JLabel lb_subtitle;
    private LogoBig logo;
    static boolean firstTime = false;
    static String username = "username";

    PanePasswordUser() {
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

            if (!firstTime) {
                /*
                String[] iconName = {"male", "male", "female", "male", "female"};
                String[] name = {"Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E"};
                String[] label = {"2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                        "2003 | District 1"};

                String[][] related = new String[3][iconName.length];
                related[0] = iconName;
                related[1] = name;
                related[2] = label;
                 */
                if (Login.handlePassword(username, tf_password.getText())) {
                    GUI_User.user = new User(username);
                    //ArrayList name = new ArrayList<>(Arrays.asList("Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    //"Trinh Xuan F"));
                    //ArrayList label = new ArrayList<>(Arrays.asList("2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                    //"2003 | District 1", "1980 | District 3"));
                    //ArrayList iconName = new ArrayList<>(Arrays.asList("male", "male", "female", "male", "female", "male"));

                    ArrayList<Integer> relatedID = User.getUserRelation(GUI_User.user.getID());
                    ArrayList<String> name = new ArrayList<>();
                    ArrayList<String> yob = new ArrayList<>();
                    ArrayList<String> pID = new ArrayList<>();
                    ArrayList<String> address = new ArrayList<>();
                    ArrayList<String> status = new ArrayList<>();
                    ArrayList<String> place = new ArrayList<>();

                    for (int i = 0; i < relatedID.size(); i++) {
                        name.add(User.getUserDetail(relatedID.get(i), "fullname"));
                        yob.add(User.getUserDetail(relatedID.get(i), "yob"));
                        pID.add(User.getUserDetail(relatedID.get(i), "personalID"));
                        address.add(User.getUserDetail(relatedID.get(i), "addressID"));
                        status.add(User.getUserDetail(relatedID.get(i), "status"));
                        place.add(User.getUserDetail(relatedID.get(i), "placeID"));
                    }

                    ArrayList<String> label = new ArrayList<>();
                    for (int i = 0; i < relatedID.size(); i++) {
                        label.add(yob.get(i) + " | " + pID.get(i));
                    }

                    ArrayList<String> iconName = new ArrayList<>();
                    for (int k = 0; k < relatedID.size(); k++) {
                        if (k % 2 == 0) {
                            iconName.add("male");
                        } else {
                            iconName.add("female");
                        }
                    }
                    int uID = GUI_User.user.getID();
                    String subtitle = User.getUserDetail(uID, "yob") + " | " + User.getUserDetail(uID, "personalID")
                            + " | " + User.getUserAddress(uID);
                    String subtitle2 = "Status: F" + User.getUserDetail(uID, "status") + " | " + User.getUserPlace(uID);

                    ArrayList<ArrayList<String>> related = new ArrayList<>(3);
                    related.add(iconName);
                    related.add(name);
                    related.add(label);
                    
                    GUI_User.getPPayment().setInfo(Integer.toString(User.getDebt()), Integer.toString(User.getCurrentBalance()));
                    GUI_User.getPPersonalInfo().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), subtitle, subtitle2, related);
                    GUI_Master.changePanel(GUI_User.getPPersonalInfo());
                    
                } else {
                    tf_password.showHint(); // Show hint/error below if false
                }
            } else {
                User.setPassword(username, tf_password.getText());
                User.setLoggedIn(username);
                GUI_User.user = new User(username);
                ArrayList<Integer> relatedID = User.getUserRelation(GUI_User.user.getID());
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> yob = new ArrayList<>();
                ArrayList<String> pID = new ArrayList<>();
                ArrayList<String> address = new ArrayList<>();
                ArrayList<String> status = new ArrayList<>();
                ArrayList<String> place = new ArrayList<>();

                for (int i = 0; i < relatedID.size(); i++) {
                    name.add(User.getUserDetail(relatedID.get(i), "fullname"));
                    yob.add(User.getUserDetail(relatedID.get(i), "yob"));
                    pID.add(User.getUserDetail(relatedID.get(i), "personalID"));
                    address.add(User.getUserDetail(relatedID.get(i), "addressID"));
                    status.add(User.getUserDetail(relatedID.get(i), "status"));
                    place.add(User.getUserDetail(relatedID.get(i), "placeID"));
                }

                ArrayList<String> label = new ArrayList<>();
                for (int i = 0; i < relatedID.size(); i++) {
                    label.add(yob.get(i) + " | " + pID.get(i));
                }

                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < relatedID.size(); k++) {
                    if (k % 2 == 0) {
                        iconName.add("male");
                    } else {
                        iconName.add("female");
                    }
                }
                int uID = GUI_User.user.getID();
                String subtitle = User.getUserDetail(uID, "yob") + " | " + User.getUserDetail(uID, "personalID")
                        + " | " + User.getUserAddress(uID);
                String subtitle2 = "Status: F" + User.getUserDetail(uID, "status") + " | " + User.getUserPlace(uID);

                ArrayList<ArrayList<String>> related = new ArrayList<>(3);
                related.add(iconName);
                related.add(name);
                related.add(label);
                
                GUI_User.getPPayment().setInfo(Integer.toString(User.getDebt()), Integer.toString(User.getCurrentBalance()));
                GUI_User.getPPersonalInfo().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), subtitle, subtitle2, related);
                GUI_Master.changePanel(GUI_User.getPPersonalInfo());

                //resetSubtitle(username, false);
                //GUI_Master.changePanel(GUI_User.getPPasswordUser());
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

    public static void resetSubtitle(String newUsername, boolean firstTime) {
        PanePasswordUser.firstTime = firstTime;
        PanePasswordUser.username = newUsername;

        if (!firstTime) {
            lb_subtitle.setText("Welcome back, User " + newUsername + ". Please enter your password.");
        } else {
            lb_subtitle.setText("Welcome, User " + newUsername + ". Please set your new password.");
        }
    }
}

class PanePersonalInfo extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_fullname, lb_subtitle, lb_subtitle2;
    private JNeoList list;
    private JNeoButton btn_history;

    PanePersonalInfo() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, 0);

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
        btn_history = new JNeoButton("History", Global.colPrimary, Color.WHITE,
                Global.btnRadius, 8, Global.fntButton, false);

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

        // button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_history, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_history, -24,
                SpringLayout.SOUTH, this);
    }

    void addAllActionListener() {
        btn_history.addActionListener(e -> {

            ArrayList<ArrayList<String>> related = new ArrayList<>();
            ArrayList<String> iconName = new ArrayList<>();

            //ArrayList<String> name = User.displayNecessityList("name");
            ArrayList<String> title = User.displayStatusHistory(GUI_User.user.getID());
            ArrayList<String> subtitle = User.getTimeStatusHistory(GUI_User.user.getID());

            for (int k = 0; k < title.size(); k++) {
                iconName.add("sb_package");
            }
            related.add(iconName);
            related.add(title);
            related.add(subtitle);

            GUI_User.getPHistory().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), "Management History", related);
            GUI_User.getPHistory().addBtnChange();
            GUI_Master.changePanel(GUI_User.getPHistory());
        });
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(list);
        add(btn_history);
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

class PaneHistory extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_fullname, lb_subtitle;
    private JNeoList list;
    private JNeoButton btn_change;
    private SpringLayout layout;

    PaneHistory() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, -1);

        // labels
        lb_fullname = new JNeoLabel("", Global.fntHeader, Global.colDark);
        lb_subtitle = new JNeoLabel("Management History", Global.fntButton, Global.colSecond);

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(2, 1);
        grid.setVgap(-15);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_fullname);
        ctn_lb.add(lb_subtitle);

        // buttons
        btn_change = new JNeoButton("Change type", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

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

        // buttons
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_change, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_change, -24,
                SpringLayout.SOUTH, this);
    }

    void addAllActionListener() {
        btn_change.addActionListener(e -> {

            ArrayList<ArrayList<String>> related = new ArrayList<>();
            ArrayList<String> iconName = new ArrayList<>();

            //ArrayList<String> name = User.displayNecessityList("name");
            ArrayList<String> title = User.getNewPlaceList(GUI_User.user.getID());
            ArrayList<String> subtitle = User.getOldPlaceList(GUI_User.user.getID());
            /*
            ArrayList<String> subtitle = new ArrayList<>();
            for (int k = 0; k < title.size(); k++) {
                subtitle.add("27/12/2021");
            }
             */
            for (int k = 0; k < title.size(); k++) {
                iconName.add("sb_package");
            }
            related.add(iconName);
            related.add(title);
            related.add(subtitle);

            GUI_User.getPHistory().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), "Management History", related);
            GUI_Master.changePanel(GUI_User.getPHistory());
        });
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(list);
        add(btn_change);
    }

    void setInfo(String fullname, String subtitle, ArrayList<ArrayList<String>> related) {
        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();

        // change button
        removeBtnChange();

        // related
        list.setNewList(related.get(0), related.get(1), related.get(2), related.get(2));
        list.removeBtnAdd();
        list.removeAllBtnInfo();
    }

    void addAllBtnInfo() {
        addListActionListener();
        list.addAllBtnInfo();
    }

    void addBtnChange() {

        // buttons
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_change, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_change, -24,
                SpringLayout.SOUTH, this);
        add(btn_change);
        repaint();
    }

    void removeBtnChange() {
        remove(btn_change);
        repaint();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();
        ArrayList<Integer> id = GUI_User.user.getOrderIntList(GUI_User.user.getID());
        int count = 0;
        for (JNeoListItem i : item) {
            i.assignID(id.get(count));
            count++;
        }
        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {

                ArrayList<ArrayList<String>> related = new ArrayList<>();

                ArrayList<String> title = GUI_User.user.getOrderDetail(i.getID(), "necessityID");
                //for (int k = 0; k < 1; k++) {
                //title.add("Rice");
                //}
                ArrayList<String> subtitle = GUI_User.user.getOrderDetail(i.getID(), "amount");
                //for (int k = 0; k < 1; k++) {
                //subtitle.add("Amount: 1");
                //}
                ArrayList<String> iconName = new ArrayList<>();
                for (int k = 0; k < title.size(); k++) {
                    iconName.add("sb_package");
                }

                related.add(iconName);
                related.add(title);
                related.add(subtitle);

                GUI_User.getPHistory().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), "Order Detail", related);
                GUI_Master.changePanel(GUI_User.getPHistory());
            });
        }
    }

}

class PaneBuyNecessity extends JPanel {

    static ArrayList<Integer> id;
    JSideBar sideBar;
    JNeoList list;
    JNeoLabel title;
    JNeoSearchBar searchBar;
    JButton btn_cart;

    PaneBuyNecessity() {
        super();

        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, 1);

        // title
        title = new JNeoLabel("Necessity list", Global.fntHeader, Global.colDark);

        // search bar
        String[] filter_names = {"Name", "Limit", "Price"};
        searchBar = new JNeoSearchBar("Search...", 15, filter_names);

        // list
        ArrayList<String> name = User.displayNecessityList("name");
        ArrayList<String> label = User.displayNecessityList("price");
        ArrayList<String> label_full = User.displayNecessityList("price");

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            iconName.add("sb_package");
        }

        list = new JNeoList(iconName, name, label, label_full);
        list.setBtnIcon("cart");
        list.removeBtnAdd();

        // button cart
        btn_cart = new JNeoButton("Cart", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

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

        // button
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_cart, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_cart, -24,
                SpringLayout.SOUTH, this);

    }

    void addAllActionListener() {
        searchBar.getTf().addActionListener(e -> {
            id = User.searchNecessityByName(searchBar.getTf().getText());

            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> label = new ArrayList<>();
            ArrayList<String> label_full = new ArrayList<>();

            for (int i = 0; i < id.size(); i++) {
                name.add(User.getNecessityDetail(id.get(i), "name"));
                label.add(User.getNecessityDetail(id.get(i), "price"));
                label_full.add(User.getNecessityDetail(id.get(i), "price"));
            }

            ArrayList<String> iconName = new ArrayList<>();
            for (int i = 0; i < id.size(); i++) {
                iconName.add("sb_package");
            }

            list.setNewList(iconName, name, label, label_full);
        });
        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Manager.getPNecessityAdd());

        });
        btn_cart.addActionListener(e -> GUI_Master.changePanel(GUI_User.getPCart()));
        addListActionListener();
    }

    void addListActionListener() {
        ArrayList<JNeoListItem> item = list.getItemList();
        for (JNeoListItem i : item) {
            i.getBtnInfo().addActionListener(e -> {
                GUI_User.getPNecessityInfo().setInfo(i.getLbName().getText(), i.getLbSub().getText());
                GUI_Master.changePanel(GUI_User.getPNecessityInfo());
            });
        }
    }

    void addAll() {
        add(sideBar);
        add(title);
        add(list);
        add(searchBar);
        add(btn_cart);
    }

}

class PaneNecessityInfo extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_fullname, lb_subtitle;
    private JNeoTextField tf_count;
    private JNeoButton btn_add;

    PaneNecessityInfo() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, -1);

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

        // text field
        tf_count = new JNeoTextField("Necessity count", 16, false, "account", "Must be a positive number");

        // button
        btn_add = new JNeoButton("Add to cart", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
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

        // text field
        layout.putConstraint(SpringLayout.WEST, tf_count, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, tf_count, 16,
                SpringLayout.SOUTH, ctn_lb);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 16,
                SpringLayout.SOUTH, tf_count);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            boolean checkCart = true;
            for (int i = 0; i < GUI_User.cart.size(); i++) {
                if (lb_fullname.getText().equals(GUI_User.cart.get(i).name)) {
                    GUI_User.cart.get(i).amount = Integer.parseInt(tf_count.getText());
                    GUI_User.getPCart().setInfo(GUI_User.cart);
                    checkCart = false;
                    break;
                }
            }
            if (checkCart == true) {
                String[] price = lb_subtitle.getText().split("VNĐ");
                Necessity necessity = new Necessity(GUI_User.user.getNecessityID(lb_fullname.getText()),
                        lb_fullname.getText(), Integer.parseInt(tf_count.getText()), Integer.parseInt(price[0]));
                GUI_User.cart.add(necessity);
                GUI_User.getPCart().setInfo(GUI_User.cart);
            }
        });
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(tf_count);
        add(btn_add);
    }

    void setInfo(String fullname, String subtitle) {
        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();
    }

}

class PanePayment extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_title, lb_debt, lb_balance;
    private JNeoTextField tf_account, tf_amount;
    private JNeoButton btn_transfer;

    PanePayment() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, 2);

        // labels
        lb_title = new JNeoLabel("Payment", Global.fntHeader, Global.colDark);
        lb_balance = new JNeoLabel("Balance: 0", Global.fntButton, Global.colSecond);
        lb_debt = new JNeoLabel("Debt: 0", Global.fntButton, Global.colSecond);
        
        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(3, 1);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_title);
        ctn_lb.add(lb_balance);
        ctn_lb.add(lb_debt);

        // text fields & button
        tf_account = new JNeoTextField("Account to transfer", 16, false, "account", "Account does not exist");
        tf_amount = new JNeoTextField("Amount to transfer", 16, false, "account", "Must be a positive number");
        btn_transfer = new JNeoButton("Transfer", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
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

        // text fields & buttons
        layout.putConstraint(SpringLayout.WEST, tf_account, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, tf_account, 32,
                SpringLayout.SOUTH, ctn_lb);
        layout.putConstraint(SpringLayout.WEST, tf_amount, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, tf_amount, 16,
                SpringLayout.SOUTH, tf_account);
        layout.putConstraint(SpringLayout.WEST, btn_transfer, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, btn_transfer, 24,
                SpringLayout.SOUTH, tf_amount);
    }

    void addAllActionListener() {
        btn_transfer.addActionListener(e -> {
            try {
                if (Integer.parseInt(tf_amount.getText()) < 0 || Integer.parseInt(tf_amount.getText()) > User.getDebt())
                    tf_amount.showHint();
                else {
                    tf_amount.hideHint();
                    User.tranfer(Integer.parseInt(tf_amount.getText()));
                    GUI_User.pw = new PrintWriter(GUI_User.socket.getOutputStream(), true);
                    GUI_User.pw.println(tf_amount.getText());
                    GUI_User.pw.flush();
                    this.setInfo(Integer.toString(User.getDebt()), Integer.toString(User.getCurrentBalance()));
                }
            } catch (Exception ex) {

            }
        });
    }

    void addAll() {
        add(sideBar);
        add(ctn_lb);
        add(tf_account);
        add(tf_amount);
        add(btn_transfer);
    }

    void setInfo(String debt, String balance) {
        // info
        lb_debt.setText("Debt: " + debt);
        lb_debt.repaint();
        lb_balance.setText("Balance: " + balance);
        lb_balance.repaint();
    }

}

class PaneCart extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    JNeoList list;
    private JNeoLabel lb_title;
    private JNeoButton btn_buy, btn_history;
    private JNeoLabel lb_success;

    PaneCart() {
        super();

        init();
        addAllActionListener();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // sideBar
        sideBar = new JSideBar(Global.itemIcon_User, 0, -1);

        // labels
        lb_title = new JNeoLabel("Cart", Global.fntHeader, Global.colDark);
        lb_success = new JNeoLabel("Necessity bought!", Global.fntSecond, Color.WHITE);

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(2, 1);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_title);

        // list
        /*
        String[] iconName = new String[7];
        for (int i = 0; i < 7; i++) {
            iconName[i] = "sb_package";
        }
        String[] name = {"Rice", "Bleach", "Shampoo", "Noodle", "Perfume", "Drugs", "Panadol"};
        String[] label = {"$5", "$15", "$2", "$2", "$4", "$6", "$8",};
         */
        ArrayList name = new ArrayList<>(Arrays.asList("Rice", "Bleach", "Shampoo", "Noodle", "Perfume", "Drugs", "Panadol"));
        ArrayList label = new ArrayList<>(Arrays.asList("$5", "$15", "$2", "$2", "$4", "$6", "$8"));
        ArrayList label_full = new ArrayList<>(Arrays.asList("$5", "$15", "$2", "$2", "$4", "$6", "$8"));

        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            iconName.add("sb_package");
        }

        list = new JNeoList(iconName, name, label, label_full);
        list.removeAllBtnInfo();
        list.removeBtnAdd();

        // button
        btn_buy = new JNeoButton("Buy", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
        btn_history = new JNeoButton("History", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
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

        // text field
        layout.putConstraint(SpringLayout.WEST, list, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.EAST, list, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, list, -24,
                SpringLayout.SOUTH, ctn_lb);
        layout.putConstraint(SpringLayout.SOUTH, list, -48,
                SpringLayout.SOUTH, this);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_buy, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, btn_buy, 0,
                SpringLayout.SOUTH, list);
        layout.putConstraint(SpringLayout.EAST, btn_history, 0,
                SpringLayout.EAST, list);
        layout.putConstraint(SpringLayout.SOUTH, btn_history, 32,
                SpringLayout.SOUTH, lb_title);
        layout.putConstraint(SpringLayout.WEST, lb_success, 16,
                SpringLayout.EAST, btn_buy);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_success, 0,
                SpringLayout.VERTICAL_CENTER, btn_buy);

    }

    void addAllActionListener() {
        btn_buy.addActionListener(e -> {
            int sum = 0;
            for (int i = 0; i < GUI_User.cart.size(); i++) {
                sum += GUI_User.cart.get(i).amount * GUI_User.cart.get(i).price;
            }
            if (sum != 0) {
                GUI_User.user.createOrder(GUI_User.user.getID(), sum);
                GUI_User.user.buyNecessity(GUI_User.user.getID(), GUI_User.cart);
                GUI_User.user.setDebtOrder(sum);
                GUI_User.getPPayment().setInfo(Integer.toString(User.getDebt()), Integer.toString(User.getCurrentBalance()));
                lb_success.setSuccess();
            }
            GUI_User.cart.clear();
            GUI_User.getPCart().setInfo(GUI_User.cart);
        });

        btn_history.addActionListener(e -> {

            ArrayList<ArrayList<String>> related = new ArrayList<>();

            ArrayList<String> title = GUI_User.user.getOrderInfo(GUI_User.user.getID(), "orderID");
            //ArrayList<String> title = new ArrayList<>();
            //for (int k = 0; k < 1; k++) {
            //title.add("Trần Thanh Tùng");
            //}
            //ArrayList<String> subtitle = User.getSumOrder();
            ArrayList<String> subtitle = GUI_User.user.getOrderInfo(GUI_User.user.getID(), "sum");;
            //for (int k = 0; k < title.size(); k++) {
            //subtitle.add("001 | 27/12/2021");
            //}
            ArrayList<String> iconName = new ArrayList<>();
            for (int k = 0; k < title.size(); k++) {
                iconName.add("sb_package");
            }
            related.add(iconName);
            related.add(title);
            related.add(subtitle);

            GUI_User.getPHistory().setInfo(User.getUserDetail(GUI_User.user.getID(), "fullname"), "Buy History", related);
            GUI_User.getPHistory().addAllBtnInfo();
            GUI_Master.changePanel(GUI_User.getPHistory());
        });
    }

    void addAll() {
        add(sideBar);
        add(list);
        add(ctn_lb);
        add(btn_buy);
        add(btn_history);
        add(lb_success);
    }

    void setInfo(ArrayList<Necessity> cart) {
        ArrayList<String> name = new ArrayList<>();
        for (int i = 0; i < GUI_User.cart.size(); i++) {
            name.add(GUI_User.cart.get(i).name);
        }

        ArrayList<String> label = new ArrayList<>();
        for (int i = 0; i < GUI_User.cart.size(); i++) {
            label.add(GUI_User.cart.get(i).price + " | Amount: " + GUI_User.cart.get(i).amount);
        }
        ArrayList<String> label_full = new ArrayList<>();

        for (int i = 0; i < GUI_User.cart.size(); i++) {
            label_full.add(GUI_User.cart.get(i).price + " | Amount: " + GUI_User.cart.get(i).amount);
        }
        ArrayList<String> iconName = new ArrayList<>();
        for (int i = 0; i < name.size(); i++) {
            iconName.add("sb_package");
        }

        list.setNewList(iconName, name, label, label_full);
        list.removeBtnAdd();
        list.removeAllBtnInfo();
    }
}
