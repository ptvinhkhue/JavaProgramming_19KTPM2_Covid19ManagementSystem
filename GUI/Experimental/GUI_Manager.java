import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static javax.swing.BoxLayout.Y_AXIS;

public class GUI_Manager {

    static PanePassword pPassword;
    static PanePatientList pPatientList;
    static PaneNecessityList pNecessityList;
    static PanePatientAdd pPatientAdd;
    static PaneNecessityAdd pNecessityAdd;

    void initPane() {
        pPassword = new PanePassword();
        pPatientList = new PanePatientList();
        pNecessityList = new PaneNecessityList();
        pPatientAdd = new PanePatientAdd();
        pNecessityAdd = new PaneNecessityAdd();
    }

    void init() {
        initPane();
    }

    public static PanePassword getPPassword() { return pPassword; }
    public static PanePatientList getPPatientList() { return pPatientList; }
    public static PaneNecessityList getPNecessityList() { return pNecessityList; }
    public static PanePatientAdd getPPatientAdd() { return pPatientAdd; }
    public static PaneNecessityAdd getPNecessityAdd() { return pNecessityAdd; }

}

class PanePassword extends JPanel {

    private JNeoButton btn_login, btn_return;
    private Container btnContainer;
    private JNeoTextField tf_password;
    private JLabel lb_credit;
    private static JLabel lb_subtitle;
    private LogoBig logo;

    PanePassword() {
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
        btnContainer.add(btn_login); btnContainer.add(btn_return);

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
            GUI_Master.changePanel(GUI_Manager.getPPatientList());
            //tf_password.showHint(); // Show hint/error below if false
        });
        btn_return.addActionListener(e -> GUI_Master.changePanel(GUI_Master.getPUsername()));
    }

    void addAll() {
        add(btnContainer);
        add(tf_password);
        add(lb_credit); add(lb_subtitle);
        add(logo);
    }

    public static void resetSubtitle(String newUsername) {
        lb_subtitle.setText("Welcome back, Manager " + newUsername + ". Please enter your password.");
    }

}

class PanePatientList extends JPanel {

    JSideBar sideBar;
    JNeoList list;
    JNeoLabel title;
    JNeoSearchBar searchBar;

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
        String[] filter_names = { "Name", "Birthyear", "Personal ID", "Address ID" };
        searchBar = new JNeoSearchBar("Search...", 15, filter_names);

        // list
        String[] iconName = { "male" , "male", "female", "male", "female", "male", "female", "female",
                "male" , "male", "female", "male", "female", "male", "female", "female"};
        String[] name = { "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F", "Le G", "Vo Lien H", "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                "Trinh Xuan F", "Le G", "Vo Lien H"};
        String[] label = { "1995", "1998", "1996", "2002", "1999", "2000", "2001", "1993",
                "1995", "1998", "1996", "2002", "1999", "2000", "2001", "1993"};
        list = new JNeoList(iconName, name, label);
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

            String[] iconName = { "male" , "male", "female", "male", "female", "male", "female", "female"};
            String[] name = { "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F", "Le G", "Vo Lien H"};
            String[] label = { "1995", "1998", "1996", "2002", "1999", "2000", "2001", "1993"};
            list.setNewList(iconName, name, label);

        });
        list.getBtnAdd().addActionListener(e -> {
            GUI_Master.changePanel(GUI_Manager.getPPatientAdd());
        });
    }

    void addAll() {
        add(sideBar); add(title); add(list); add(searchBar);
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
        String[] filter_names = { "Name", "Limit", "Price" };
        searchBar = new JNeoSearchBar("Search...", 15, filter_names);

        // list
        String[] iconName = new String[7];
        for (int i = 0; i < 7; i++) {
            iconName[i] = "sb_package";
        }
        String[] name = { "Rice", "Bleach", "Shampoo", "Noodle", "Perfume", "Drugs", "Panadol"};
        String[] label = { "$5", "$15", "$2", "$2", "$4", "$6", "$8",};
        list = new JNeoList(iconName, name, label);
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
    }

    void addAll() {
        add(sideBar); add(title); add(list); add(searchBar);
    }

}

class PanePatientAdd extends JPanel {

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_fullname, tf_birthyear, tf_personalID, tf_addressID;
    Container ctn_tf;
    JNeoButton btn_add;
    JLabel lb_error;

    PanePatientAdd() {
        super();

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
        title = new JNeoLabel("Add new patient", Global.fntHeader, Global.colDark);

        // text fields
        tf_fullname = new JNeoTextField("Full name", 20, false, "account", "!NULL");
        tf_birthyear = new JNeoTextField("Birth year", 20, false, "account", "Format must be 'yyyy'");
        tf_personalID = new JNeoTextField("Personal ID", 20, false, "account", "!NULL");
        tf_addressID = new JNeoTextField("Address ID", 20, false, "account", "!NULL");

        // button
        btn_add = new JNeoButton("Add",Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error = new JNeoLabel("Patient already exists.", Global.fntSecond, Color.WHITE);
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
        ctn_tf.add(tf_fullname);
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 12)));
        ctn_tf.add(tf_birthyear);
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 12)));
        ctn_tf.add(tf_personalID);
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 12)));
        ctn_tf.add(tf_addressID);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, -32,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 24,
                SpringLayout.SOUTH, ctn_tf);

        // label
        layout.putConstraint(SpringLayout.WEST, lb_error, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            String str; boolean valid = true; boolean exist = false;
            // check birthyear
            str = tf_birthyear.getText();
            if (str.matches("^[0-9]+$") && str.length() == 4)
                tf_birthyear.hideHint();
            else {
                tf_birthyear.showHint();
                return;
            }

            exist = (Objects.equals(tf_fullname.getText(), "Tran Thanh Tung"));

            if (exist) {
                lb_error.setForeground(Global.colError);
                valid = false;
            } else lb_error.setForeground(Color.WHITE);

            if (!valid) return;

            // reset all text fields
            GUI_Master.changePanel(GUI_Manager.getPNecessityList());
            lb_error.setForeground(Color.WHITE);
            tf_fullname.setText("Full name");
            tf_birthyear.setText("Birth year (yyyy)");
            tf_personalID.setText("Personal ID");
            tf_addressID.setText("Address ID");
        });
    }

    void addAll() {
        add(sideBar); add(title); add(ctn_tf); add(btn_add); add(lb_error);
    }

}

class PaneNecessityAdd extends JPanel {

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_name, tf_limit, tf_price;
    Container ctn_tf;
    JNeoButton btn_add;
    JNeoLabel lb_error;

    PaneNecessityAdd() {
        super();

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
        title = new JNeoLabel("Add new patient", Global.fntHeader, Global.colDark);

        // text fields
        tf_name = new JNeoTextField("Necessity name", 20, false, "account", "!NULL");
        tf_limit = new JNeoTextField("Limit", 20, false, "account", "Must be a positive number");
        tf_price = new JNeoTextField("Price", 20, false, "account", "Must be a positive number");

        // button
        btn_add = new JNeoButton("Add",Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);

        // label
        lb_error = new JNeoLabel("Necessity already exists.", Global.fntSecond, Color.WHITE);
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

        layout.putConstraint(SpringLayout.WEST, ctn_tf, -32,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 24,
                SpringLayout.SOUTH, ctn_tf);

        // label
        layout.putConstraint(SpringLayout.WEST, lb_error, 16,
                SpringLayout.EAST, btn_add);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_error, 0,
                SpringLayout.VERTICAL_CENTER, btn_add);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            String str; boolean valid = true;
            boolean exist = false;

            // check birthyear
            str = tf_limit.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) > 0) tf_limit.hideHint();
            else {
                tf_limit.showHint();
                valid = false;
            }

            str = tf_price.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) > 0) tf_price.hideHint();
            else {
                tf_price.showHint();
                valid = false;
            }

            exist = (Objects.equals(tf_name.getText(), "Rice"));

            if (exist) {
                lb_error.setForeground(Global.colError);
                valid = false;
            } else lb_error.setForeground(Color.WHITE);

            if (!valid) return;

            // reset all text fields
            GUI_Master.changePanel(GUI_Manager.getPNecessityList());
            lb_error.setForeground(Color.WHITE);
            tf_name.setText("Necessity name");
            tf_limit.setText("Limit");
            tf_price.setText("Price");
        });
    }

    void addAll() {
        add(sideBar); add(title); add(ctn_tf); add(btn_add); add(lb_error);
    }

}
