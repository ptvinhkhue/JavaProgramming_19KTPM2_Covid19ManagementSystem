import javax.swing.*;
import java.awt.*;

public class GUI_Manager {

    int width = 640; int height = 480;
    int marginUnit = 4;

    private JFrame fMain;
    private JPanel pUsername, pPassword, pMain, pAdd, pChange, pChart, pInfo;
    private Font font;

    // info panel
    JLabel lv_fullname, lv_yob, lv_id, lv_address;
    JLabel lv_change;

    GUI_Manager() {
        initFont();
        initPanels();
        initFMain();

        fMain.setVisible(true);
    }

    // initiate components
    void initFMain() {
        fMain = new JFrame("Covid-19 Management - Manager");
        fMain.setSize(width, height);
        fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fMain.setLocationRelativeTo(null);
        fMain.setResizable(false);
    }
    void initFont() {
        font = new Font("San-Serif", Font.PLAIN, 16);
    }
    void initPUsername() {
        // initiate panel
        pUsername = new JPanel();
        SpringLayout layout = new SpringLayout();
        pUsername.setLayout(layout);

        // initiate components
        JLabel l_sub = new JLabel("Please enter your Username");
        JLabelTextField tf_username = new JLabelTextField("Username", 15);
        JButton btn_continue = new JButton("Continue");

        // components settings
        tf_username.setFont(font);

        // set layout
        layout.putConstraint(SpringLayout.SOUTH, l_sub, -marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, l_sub, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.NORTH, tf_username, marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_username, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);
        layout.putConstraint(SpringLayout.NORTH, btn_continue, marginUnit * 4,
                SpringLayout.SOUTH, tf_username);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_continue, 0,
                SpringLayout.HORIZONTAL_CENTER, pUsername);

        // action listeners
        btn_continue.addActionListener(e -> changePanel(pPassword));

        // add components
        pUsername.add(l_sub);
        pUsername.add(tf_username);
        pUsername.add(btn_continue);
    }
    void initPPassword() {
        // initiate panel
        pPassword = new JPanel();
        SpringLayout layout = new SpringLayout();
        pPassword.setLayout(layout);

        // initiate components
        JLabel l_sub = new JLabel("Welcome back! Please enter your password");
        JLabelPasswordField tf_password = new JLabelPasswordField("Username", 15);
        JButton btn_login = new JButton("Login");

        // components settings
        tf_password.setFont(font);

        // set layout
        layout.putConstraint(SpringLayout.SOUTH, l_sub, -marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, l_sub, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.NORTH, tf_password, marginUnit * 2,
                SpringLayout.VERTICAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_password, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);
        layout.putConstraint(SpringLayout.NORTH, btn_login, marginUnit * 4,
                SpringLayout.SOUTH, tf_password);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_login, 0,
                SpringLayout.HORIZONTAL_CENTER, pPassword);

        // action listeners
        btn_login.addActionListener(e -> changePanel(pMain));

        // add components
        pPassword.add(l_sub);
        pPassword.add(tf_password);
        pPassword.add(btn_login);
    }
    void initPMain() {

        // initiate panel
        pMain = new JPanel();
        SpringLayout layout = new SpringLayout();
        pMain.setLayout(layout);

        // initiate components
        JMenuBar mb = new JMenuBar();
        JMenu mMenu = new JMenu("Menu");
        JMenuItem miList = new JMenuItem("Patient List");
        JMenuItem miAdd = new JMenuItem("Add Patient");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miLogout = new JMenuItem("Logout");

        JLabel title = new JLabel("Patient List");
        title.setFont(new Font("SANS-SERIF", Font.BOLD, 24));

        String[] name = {"Jack" , "Paige Gordon Johnson"}; String[] year = {"1995", "2000"};
        PatientList pl = new PatientList(2, name, year);

        // set layout
        layout.putConstraint(SpringLayout.WEST, mb, marginUnit * 2,
                SpringLayout.WEST, pMain);
        layout.putConstraint(SpringLayout.NORTH, mb, marginUnit * 2,
                SpringLayout.NORTH, pMain);
        layout.putConstraint(SpringLayout.EAST, mb, marginUnit * 12,
                SpringLayout.WEST, pMain);
        layout.putConstraint(SpringLayout.SOUTH, mb, marginUnit * 8,
                SpringLayout.NORTH, pMain);

        layout.putConstraint(SpringLayout.WEST, title, marginUnit * 4,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.SOUTH, title, marginUnit * 4,
                SpringLayout.SOUTH, mb);

        layout.putConstraint(SpringLayout.WEST, pl, 0,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.NORTH, pl, marginUnit * 4,
                SpringLayout.SOUTH, title);

        // action listeners
        miLogout.addActionListener(e -> changePanel(pUsername));
        miAdd.addActionListener(e -> changePanel(pAdd));
        miStats.addActionListener(e -> changePanel(pChart));
        for (PatientListItem pli: pl.pli) {
            pli.btn_info.addActionListener(e -> {

                // set info here
                lv_fullname.setText("Nguyen Van A");
                lv_yob.setText("1990");
                lv_id.setText("079209090909");
                lv_address.setText("Tân Bình");

                changePanel(pInfo);
            });
            pli.btn_change.addActionListener(e -> {

                // set info here
                lv_change.setText("Nguyen Van A - 1990");

                changePanel(pChange);
            });
        }

        // add components
        mMenu.add(miList); mMenu.add(miAdd); mMenu.add(miStats);
        mMenu.addSeparator(); mMenu.add(miLogout);
        mb.add(mMenu);

        pMain.add(mb); pMain.add(pl); pMain.add(title);
    }
    void initPAdd() {

        // initiate panel
        pAdd = new JPanel();
        SpringLayout layout = new SpringLayout();
        pAdd.setLayout(layout);

        // initiate components
        JMenuBar mb = new JMenuBar();
        JMenu mMenu = new JMenu("Menu");
        JMenuItem miList = new JMenuItem("Patient List");
        JMenuItem miAdd = new JMenuItem("Add Patient");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miLogout = new JMenuItem("Logout");

        JLabel title = new JLabel("Add Patient");
        title.setFont(new Font("SANS-SERIF", Font.BOLD, 24));

        JTextField tf_fullname = new JTextField(25);
        JTextField tf_yob = new JTextField(25);
        JTextField tf_id = new JTextField(25);
        String[] address_id = { "001", "002" };
        JComboBox<String> cb_address = new JComboBox<>(address_id);

        JLabel l_fullname = new JLabel("Full name");
        JLabel l_yob = new JLabel("Birth Year");
        JLabel l_id = new JLabel("Personal ID");
        JLabel l_address = new JLabel("Address ID");

        JButton btn_add = new JButton("Add");

        // set layout
        layout.putConstraint(SpringLayout.WEST, mb, marginUnit * 2,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.NORTH, mb, marginUnit * 2,
                SpringLayout.NORTH, pAdd);
        layout.putConstraint(SpringLayout.EAST, mb, marginUnit * 12,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.SOUTH, mb, marginUnit * 8,
                SpringLayout.NORTH, pAdd);

        layout.putConstraint(SpringLayout.WEST, title, marginUnit * 4,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.SOUTH, title, marginUnit * 4,
                SpringLayout.SOUTH, mb);


        layout.putConstraint(SpringLayout.WEST, l_fullname, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_fullname, marginUnit * 8,
                SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.WEST, tf_fullname, marginUnit * 8,
                SpringLayout.EAST, l_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_fullname, 0,
                SpringLayout.VERTICAL_CENTER, l_fullname);

        layout.putConstraint(SpringLayout.WEST, l_yob, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_yob, marginUnit * 8,
                SpringLayout.SOUTH, l_fullname);
        layout.putConstraint(SpringLayout.WEST, tf_yob, 0,
                SpringLayout.WEST, tf_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_yob, 0,
                SpringLayout.VERTICAL_CENTER, l_yob);

        layout.putConstraint(SpringLayout.WEST, l_id, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_id, marginUnit * 8,
                SpringLayout.SOUTH, l_yob);
        layout.putConstraint(SpringLayout.WEST, tf_id, 0,
                SpringLayout.WEST, tf_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_id, 0,
                SpringLayout.VERTICAL_CENTER, l_id);

        layout.putConstraint(SpringLayout.WEST, l_address, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_address, marginUnit * 8,
                SpringLayout.SOUTH, l_id);
        layout.putConstraint(SpringLayout.WEST, cb_address, 0,
                SpringLayout.WEST, tf_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, cb_address, 0,
                SpringLayout.VERTICAL_CENTER, l_address);

        layout.putConstraint(SpringLayout.WEST, btn_add, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_add, marginUnit * 6,
                SpringLayout.SOUTH, cb_address);

        // action listeners
        miLogout.addActionListener(e -> changePanel(pUsername));
        miList.addActionListener(e -> changePanel(pMain));
        miStats.addActionListener(e -> changePanel(pChart));

        // add components
        mMenu.add(miList); mMenu.add(miAdd); mMenu.add(miStats);
        mMenu.addSeparator(); mMenu.add(miLogout);
        mb.add(mMenu);

        pAdd.add(mb); pAdd.add(title);
        pAdd.add(tf_fullname); pAdd.add(tf_yob); pAdd.add(tf_id); pAdd.add(cb_address);
        pAdd.add(l_fullname); pAdd.add(l_yob); pAdd.add(l_id); pAdd.add(l_address);
        pAdd.add(btn_add);
    }
    void initPChange() {

        // initiate panel
        pChange = new JPanel();
        SpringLayout layout = new SpringLayout();
        pChange.setLayout(layout);

        // initiate components
        JMenuBar mb = new JMenuBar();
        JMenu mMenu = new JMenu("Menu");
        JMenuItem miList = new JMenuItem("Patient List");
        JMenuItem miAdd = new JMenuItem("Add Patient");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miLogout = new JMenuItem("Logout");

        JLabel title = new JLabel("Change Patient Info");
        title.setFont(new Font("SANS-SERIF", Font.BOLD, 24));

        lv_change = new JLabel("");

        JTextField tf_status = new JTextField(25);
        JTextField tf_location = new JTextField(25);

        JLabel l_status = new JLabel("Status");
        JLabel l_location = new JLabel("Location");

        JButton btn_update = new JButton("Update");

        // set layout
        layout.putConstraint(SpringLayout.WEST, mb, marginUnit * 2,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.NORTH, mb, marginUnit * 2,
                SpringLayout.NORTH, pAdd);
        layout.putConstraint(SpringLayout.EAST, mb, marginUnit * 12,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.SOUTH, mb, marginUnit * 8,
                SpringLayout.NORTH, pAdd);

        layout.putConstraint(SpringLayout.WEST, title, marginUnit * 4,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.SOUTH, title, marginUnit * 4,
                SpringLayout.SOUTH, mb);

        layout.putConstraint(SpringLayout.WEST, lv_change, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, lv_change, marginUnit * 2,
                SpringLayout.SOUTH, title);

        layout.putConstraint(SpringLayout.WEST, l_status, 0,
                SpringLayout.WEST, lv_change);
        layout.putConstraint(SpringLayout.NORTH, l_status, marginUnit * 8,
                SpringLayout.SOUTH, lv_change);
        layout.putConstraint(SpringLayout.WEST, tf_status, marginUnit * 8,
                SpringLayout.EAST, l_status);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_status, 0,
                SpringLayout.VERTICAL_CENTER, l_status);

        layout.putConstraint(SpringLayout.WEST, l_location, 0,
                SpringLayout.WEST, lv_change);
        layout.putConstraint(SpringLayout.NORTH, l_location, marginUnit * 8,
                SpringLayout.SOUTH, l_status);
        layout.putConstraint(SpringLayout.WEST, tf_location, 0,
                SpringLayout.WEST, tf_status);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_location, 0,
                SpringLayout.VERTICAL_CENTER, l_location);

        layout.putConstraint(SpringLayout.WEST, btn_update, 0,
                SpringLayout.WEST, lv_change);
        layout.putConstraint(SpringLayout.NORTH, btn_update, marginUnit * 6,
                SpringLayout.SOUTH, tf_location);

        // action listeners
        miLogout.addActionListener(e -> changePanel(pUsername));
        miAdd.addActionListener(e -> changePanel(pAdd));
        miList.addActionListener(e -> changePanel(pMain));
        btn_update.addActionListener(e -> changePanel(pMain));
        miStats.addActionListener(e -> changePanel(pChart));

        // add components
        mMenu.add(miList); mMenu.add(miAdd); mMenu.add(miStats);
        mMenu.addSeparator(); mMenu.add(miLogout);
        mb.add(mMenu);

        pChange.add(mb); pChange.add(title); pChange.add(lv_change);
        pChange.add(tf_status); pChange.add(tf_location); pChange.add(l_status); pChange.add(l_location);
        pChange.add(btn_update);
    }
    void initPInfo() {

        // initiate panel
        pInfo = new JPanel();
        SpringLayout layout = new SpringLayout();
        pInfo.setLayout(layout);

        // initiate components
        JMenuBar mb = new JMenuBar();
        JMenu mMenu = new JMenu("Menu");
        JMenuItem miList = new JMenuItem("Patient List");
        JMenuItem miAdd = new JMenuItem("Add Patient");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miLogout = new JMenuItem("Logout");

        JLabel title = new JLabel("Patient Info");
        title.setFont(new Font("SANS-SERIF", Font.BOLD, 24));

        JLabel l_fullname = new JLabel("Full name:");
        JLabel l_yob = new JLabel("Birth Year:");
        JLabel l_id = new JLabel("Personal ID:");
        JLabel l_address = new JLabel("Address ID:");

        lv_fullname = new JLabel("Nguyễn Văn A");
        lv_yob = new JLabel("1990");
        lv_id = new JLabel("079209090909");
        lv_address = new JLabel("001");

        String[] name = {"Jack" , "Paige Gordon Johnson"}; String[] year = {"1995", "2000"};
        PatientList pl = new PatientList(2, name, year);

        // set layout
        layout.putConstraint(SpringLayout.WEST, mb, marginUnit * 2,
                SpringLayout.WEST, pInfo);
        layout.putConstraint(SpringLayout.NORTH, mb, marginUnit * 2,
                SpringLayout.NORTH, pInfo);
        layout.putConstraint(SpringLayout.EAST, mb, marginUnit * 12,
                SpringLayout.WEST, pInfo);
        layout.putConstraint(SpringLayout.SOUTH, mb, marginUnit * 8,
                SpringLayout.NORTH, pInfo);

        layout.putConstraint(SpringLayout.WEST, title, marginUnit * 4,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.SOUTH, title, marginUnit * 4,
                SpringLayout.SOUTH, mb);


        layout.putConstraint(SpringLayout.WEST, l_fullname, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_fullname, marginUnit * 8,
                SpringLayout.SOUTH, title);
        layout.putConstraint(SpringLayout.WEST, lv_fullname, marginUnit * 8,
                SpringLayout.EAST, l_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lv_fullname, 0,
                SpringLayout.VERTICAL_CENTER, l_fullname);

        layout.putConstraint(SpringLayout.WEST, l_yob, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_yob, marginUnit * 8,
                SpringLayout.SOUTH, l_fullname);
        layout.putConstraint(SpringLayout.WEST, lv_yob, 0,
                SpringLayout.WEST, lv_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lv_yob, 0,
                SpringLayout.VERTICAL_CENTER, l_yob);

        layout.putConstraint(SpringLayout.WEST, l_id, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_id, marginUnit * 8,
                SpringLayout.SOUTH, l_yob);
        layout.putConstraint(SpringLayout.WEST, lv_id, 0,
                SpringLayout.WEST, lv_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lv_id, 0,
                SpringLayout.VERTICAL_CENTER, l_id);

        layout.putConstraint(SpringLayout.WEST, l_address, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, l_address, marginUnit * 8,
                SpringLayout.SOUTH, l_id);
        layout.putConstraint(SpringLayout.WEST, lv_address, 0,
                SpringLayout.WEST, lv_fullname);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lv_address, 0,
                SpringLayout.VERTICAL_CENTER, l_address);

        layout.putConstraint(SpringLayout.WEST, pl, -marginUnit * 2,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, pl, -marginUnit * 40,
                SpringLayout.SOUTH, pInfo);
        layout.putConstraint(SpringLayout.SOUTH, pl, -marginUnit * 4,
                SpringLayout.SOUTH, pInfo);

        // action listeners
        miLogout.addActionListener(e -> changePanel(pUsername));
        miList.addActionListener(e -> changePanel(pMain));
        miStats.addActionListener(e -> changePanel(pChart));

        // add components
        mMenu.add(miList); mMenu.add(miAdd); mMenu.add(miStats);
        mMenu.addSeparator(); mMenu.add(miLogout);
        mb.add(mMenu);

        pInfo.add(mb); pInfo.add(title);
        pInfo.add(lv_fullname); pInfo.add(lv_yob); pInfo.add(lv_id); pInfo.add(lv_address);
        pInfo.add(l_fullname); pInfo.add(l_yob); pInfo.add(l_id); pInfo.add(l_address);
        pInfo.add(pl);
    }
    void initPChart() {

        // initiate panel
        pChart = new JPanel();
        SpringLayout layout = new SpringLayout();
        pChart.setLayout(layout);

        // initiate components
        JMenuBar mb = new JMenuBar();
        JMenu mMenu = new JMenu("Menu");
        JMenuItem miList = new JMenuItem("Patient List");
        JMenuItem miAdd = new JMenuItem("Add Patient");
        JMenuItem miStats = new JMenuItem("Statistics");
        JMenuItem miLogout = new JMenuItem("Logout");

        JLabel title = new JLabel("Statistics");
        title.setFont(new Font("SANS-SERIF", Font.BOLD, 24));

        JButton btn_people = new JButton("People");

        // set layout
        layout.putConstraint(SpringLayout.WEST, mb, marginUnit * 2,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.NORTH, mb, marginUnit * 2,
                SpringLayout.NORTH, pAdd);
        layout.putConstraint(SpringLayout.EAST, mb, marginUnit * 12,
                SpringLayout.WEST, pAdd);
        layout.putConstraint(SpringLayout.SOUTH, mb, marginUnit * 8,
                SpringLayout.NORTH, pAdd);

        layout.putConstraint(SpringLayout.WEST, title, marginUnit * 4,
                SpringLayout.EAST, mb);
        layout.putConstraint(SpringLayout.SOUTH, title, marginUnit * 4,
                SpringLayout.SOUTH, mb);

        layout.putConstraint(SpringLayout.WEST, btn_people, 0,
                SpringLayout.WEST, title);
        layout.putConstraint(SpringLayout.NORTH, btn_people, marginUnit * 4,
                SpringLayout.SOUTH, title);

        // action listeners
        miLogout.addActionListener(e -> changePanel(pUsername));
        miAdd.addActionListener(e -> changePanel(pAdd));
        miList.addActionListener(e -> changePanel(pMain));

        // add components
        mMenu.add(miList); mMenu.add(miAdd); mMenu.add(miStats);
        mMenu.addSeparator(); mMenu.add(miLogout);
        mb.add(mMenu);

        pChart.add(mb); pChart.add(title);
        pChart.add(btn_people);
    }
    void initPanels() {
        initPUsername();
        initPPassword();
        initPMain();
        initPAdd();
        initPChange();
        initPInfo();
    }

    // master panel
    void changePanel(JPanel p) {
        fMain.getContentPane().removeAll();
        fMain.getContentPane().add(p);
        fMain.getContentPane().repaint();
        fMain.revalidate();
    }

    public static void main(String[] args) {
        GUI_Manager gui = new GUI_Manager();
        gui.changePanel(gui.pUsername);
    }
}