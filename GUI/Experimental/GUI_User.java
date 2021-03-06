import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class GUI_User {

    static PanePasswordUser pPasswordUser;
    static PanePersonalInfo pPersonalInfo;
    static PaneBuyNecessity pBuyNecessity;
    static PaneNecessityInfo pNecessityInfo;
    static PanePayment pPayment;
    static PaneCart pCart;

    void initPane() {
        pPasswordUser = new PanePasswordUser();
        pPersonalInfo = new PanePersonalInfo();
        pBuyNecessity = new PaneBuyNecessity();
        pNecessityInfo = new PaneNecessityInfo();
        pPayment = new PanePayment();
        pCart = new PaneCart();
    }

    void init() {
        initPane();
    }

    public static PanePasswordUser getPPasswordUser() { return pPasswordUser; }
    public static PanePersonalInfo getPPersonalInfo() { return pPersonalInfo; }
    public static PaneBuyNecessity getPBuyNecessity() { return pBuyNecessity; }
    public static PaneNecessityInfo getPNecessityInfo() { return pNecessityInfo; }
    public static PanePayment getPPayment() { return pPayment; }
    public static PaneCart getPCart() { return pCart; }

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
            if (!firstTime) {
                String[] iconName = {"male", "male", "female", "male", "female"};
                String[] name = {"Nguyen Van A", "Tran Thanh B", "Phan Thi C", "Dinh Ba D", "Bui Kim E"};
                String[] label = {"2001 | District 1", "1995 | District 7", "1987 | District 4", "1999 | District 6",
                        "2003 | District 1"};

                String[][] related = new String[3][iconName.length];
                related[0] = iconName;
                related[1] = name;
                related[2] = label;

                GUI_User.getPPersonalInfo().setInfo("Nguyen Van A", "2001 | District 1", related);
                GUI_Master.changePanel(GUI_User.getPPersonalInfo());
            }
            else {
                resetSubtitle(username, false);
                GUI_Master.changePanel(GUI_User.getPPasswordUser());
            }
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

    public static void resetSubtitle(String newUsername, boolean firstTime) {
        PanePasswordUser.firstTime = firstTime;
        PanePasswordUser.username = newUsername;

        if (!firstTime)
            lb_subtitle.setText("Welcome back, User " + newUsername + ". Please enter your password.");
        else lb_subtitle.setText("Welcome, User " + newUsername + ". Please set your new password.");
    }
}

class PanePersonalInfo extends JPanel {

    private JSideBar sideBar;
    private JPanel ctn_lb;
    private JNeoLabel lb_fullname, lb_subtitle;
    private JNeoList list;

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

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(2, 1);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_fullname);
        ctn_lb.add(lb_subtitle);

        // list
        String[] iconName = { "male"};
        String[] name = { "Nguyen Van Lee"};
        String[] label = { "2001 | District 1"
        };
        list = new JNeoList(iconName, name, label);
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
    }

    void addAllActionListener() {

    }

    void addAll() {
        add(sideBar); add(ctn_lb); add(list);
    }

    void setInfo(String fullname, String subtitle, String[][] related) {
        // info
        lb_fullname.setText(fullname);
        lb_fullname.repaint();
        lb_subtitle.setText(subtitle);
        lb_subtitle.repaint();

        // related
        list.setNewList(related[0], related[1], related[2]);
        list.removeBtnAdd();
        list.removeAllBtnInfo();
    }

}

class PaneBuyNecessity extends JPanel {

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
        add(sideBar); add(title); add(list); add(searchBar); add(btn_cart);
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

    }

    void addAll() {
        add(sideBar); add(ctn_lb); add(tf_count); add(btn_add);
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

    }

    void addAll() {
        add(sideBar); add(ctn_lb); add(tf_account); add(tf_amount); add(btn_transfer);
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
    private JNeoButton btn_buy;

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

        // labels container
        ctn_lb = new JPanel();
        ctn_lb.setBackground(Color.WHITE);
        GridLayout grid = new GridLayout(2, 1);
        ctn_lb.setLayout(grid);
        ctn_lb.setAlignmentX(Component.LEFT_ALIGNMENT);
        ctn_lb.add(lb_title);

        // list
        String[] iconName = new String[7];
        for (int i = 0; i < 7; i++) {
            iconName[i] = "sb_package";
        }
        String[] name = { "Rice", "Bleach", "Shampoo", "Noodle", "Perfume", "Drugs", "Panadol"};
        String[] label = { "$5", "$15", "$2", "$2", "$4", "$6", "$8",};
        list = new JNeoList(iconName, name, label);
        list.removeAllBtnInfo();
        list.removeBtnAdd();

        // button
        btn_buy = new JNeoButton("Buy", Global.colPrimary, Color.WHITE, Global.btnRadius, 8, Global.fntButton, false);
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

    }

    void addAllActionListener() {

    }

    void addAll() {
        add(sideBar); add(ctn_lb); add(list); add(btn_buy);
    }
}
