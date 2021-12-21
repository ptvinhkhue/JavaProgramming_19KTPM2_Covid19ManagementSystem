import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class GUI_Manager {

    static PanePassword pPassword;
    static PaneList pList;
    static PaneAdd pAdd;
    static PaneNecessity pNecessity;

    void initPane() {
        pPassword = new PanePassword();
        pList = new PaneList();
        pAdd = new PaneAdd();
        pNecessity = new PaneNecessity();
    }

    void init() {
        initPane();
    }

    public static PanePassword getPPassword() { return pPassword; }
    public static PaneList getPList() { return pList; }
    public static PaneAdd getPAdd() { return pAdd; }
    public static PaneNecessity getPNecessity() { return pNecessity; }

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
        btn_login = new JNeoButton("Login", Global.colPrimary, Color.WHITE, Global.btnRadius);
        btn_return = new JNeoButton("Return", Color.WHITE, Global.colSubtle, Global.btnRadius);
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
            GUI_Master.changePanel(GUI_Manager.getPList());
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

class PaneList extends JPanel {

    JSideBar sideBar;
    JNeoList list;
    JNeoLabel title;
    JNeoSearchBar searchBar;

    PaneList() {
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
        searchBar = new JNeoSearchBar("Search...", 16);

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
        layout.putConstraint(SpringLayout.EAST, searchBar, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, searchBar, -6,
                SpringLayout.VERTICAL_CENTER, title);

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
        searchBar.getTf().addActionListener(e -> {

            String[] iconName = { "male" , "male", "female", "male", "female", "male", "female", "female"};
            String[] name = { "Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E",
                    "Trinh Xuan F", "Le G", "Vo Lien H"};
            String[] label = { "1995", "1998", "1996", "2002", "1999", "2000", "2001", "1993"};
            list.setNewList(iconName, name, label);

        });
    }

    void addAll() {
        add(sideBar); add(title); add(list); add(searchBar);
    }

}

class PaneAdd extends JPanel {

    JSideBar sideBar;
    JNeoLabel title;
    JNeoTextField tf_fullname, tf_birthyear, tf_personalID, tf_addressID;
    Container ctn_tf;
    JNeoButton btn_add;

    PaneAdd() {
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
        title = new JNeoLabel("Add new patient", Global.fntHeader, Global.colDark);

        // text fields
        tf_fullname = new JNeoTextField("Full name", 20, false, "account", "!NULL");
        tf_birthyear = new JNeoTextField("Birth year", 20, false, "account", "Format must be 'yyyy'");
        tf_personalID = new JNeoTextField("Personal ID", 20, false, "account", "!NULL");
        tf_addressID = new JNeoTextField("Address ID", 20, false, "account", "!NULL");

        // button
        btn_add = new JNeoButton("Add",Global.colPrimary, Color.WHITE, Global.btnRadius);
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

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, ctn_tf);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 24,
                SpringLayout.SOUTH, ctn_tf);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            String str;
            // check birthyear
            str = tf_birthyear.getText();
            if (str.matches("^[0-9]+$") && str.length() == 4) tf_birthyear.hideHint();
            else tf_birthyear.showHint();
        });
    }

    void addAll() {
        add(sideBar); add(title); add(ctn_tf); add(btn_add);
    }

}

class PaneNecessity extends JPanel {

    JSideBar sideBar;
    JNeoLabel title, item;
    JNeoTextField tf_limit, tf_price;
    Container ctn_tf;
    JNeoButton btn_add;

    PaneNecessity() {
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
        title = new JNeoLabel("Edit necessity", Global.fntHeader, Global.colDark);
        item = new JNeoLabel("Rice - 5000đ - Limit: 15", Global.fntButton, Global.colSecond);

        // text fields
        tf_limit = new JNeoTextField("Limit", 20, false, "account", "Input must be a positive number");
        tf_price = new JNeoTextField("Price", 20, false, "account", "Input must be a positive number");

        // button
        btn_add = new JNeoButton("Change",Global.colPrimary, Color.WHITE, Global.btnRadius);
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

        // title & item label
        layout.putConstraint(SpringLayout.WEST, title, 48,
                SpringLayout.EAST, sideBar);
        layout.putConstraint(SpringLayout.NORTH, title, 32,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, item, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, item, 12,
                SpringLayout.NORTH, title);

        // text fields
        ctn_tf = new Container();
        ctn_tf.setLayout(new BoxLayout(ctn_tf, BoxLayout.Y_AXIS));
        ctn_tf.add(tf_limit);
        ctn_tf.add(Box.createRigidArea(new Dimension(0, 12)));
        ctn_tf.add(tf_price);

        layout.putConstraint(SpringLayout.WEST, ctn_tf, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, ctn_tf, 24,
                SpringLayout.SOUTH, title);

        // button
        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, ctn_tf);
        layout.putConstraint(SpringLayout.NORTH, btn_add, 24,
                SpringLayout.SOUTH, ctn_tf);

    }

    void addAllActionListener() {
        btn_add.addActionListener(e -> {
            String str;
            // check limit
            str = tf_limit.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) >= 0) tf_limit.hideHint();
            else tf_limit.showHint();
            // check price
            str = tf_price.getText();
            if (str.matches("^[0-9]+$") && Integer.parseInt(str) >= 0) tf_price.hideHint();
            else tf_price.showHint();
        });
    }

    void addAll() {
        add(sideBar); add(title); add(ctn_tf); add(btn_add);
    }

}