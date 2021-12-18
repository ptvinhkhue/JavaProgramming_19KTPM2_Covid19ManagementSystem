import javax.swing.*;
import java.awt.*;

public class GUI_User {

    int width = 640; int height = 480;
    int marginUnit = 4;

    private final JFrame fMain;
    private JPanel pMain, pAdd, pChange, pChart, pInfo;
    private Font font;
    private final GUI_Login gui_login;

    // info panel
    JLabel lv_fullname, lv_yob, lv_id, lv_address;
    JLabel lv_change;

    GUI_User(GUI_Login gui_login) {
        initFont();
        initPanels();

        this.gui_login = gui_login;
        this.fMain = gui_login.getfMain();
    }

    void initFont() {
        font = new Font("San-Serif", Font.PLAIN, 16);
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
        miLogout.addActionListener(e -> changePanel(gui_login.getpUsername()));
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
    void initPanels() {
        initPInfo();
    }

    // get panels

    public JPanel getpInfo() {
        return pInfo;
    }

    // master panel
    void changePanel(JPanel p) {
        fMain.getContentPane().removeAll();
        fMain.getContentPane().add(p);
        fMain.getContentPane().repaint();
        fMain.revalidate();
    }
}