import javax.swing.*;
import java.awt.*;

public class GUI_Master {

    public static JFrame fMain;
    static PaneUsername pUsername;

    void initPane() {
        pUsername = new PaneUsername();
    }

    void initFrame() {
        fMain = new JFrame("Covid-19 Management - Login");
        fMain.setSize(640, 480);
        fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fMain.setLocationRelativeTo(null);
        fMain.setResizable(false);
    }

    void addAll() {
        fMain.add(pUsername);
    }

    void init() {
        initPane();
        initFrame();
        addAll();

        fMain.setVisible(true);
    }

    public static void changePanel(JPanel p) {
        fMain.getContentPane().removeAll();
        fMain.getContentPane().add(p);
        fMain.getContentPane().repaint();
        fMain.revalidate();
    }

    public static PaneUsername getPUsername() { return pUsername; }

    public static void main(String[] args) {
        (new Global()).init();

        SwingUtilities.invokeLater(() -> {
            GUI_Master gui = new GUI_Master();
            GUI_Manager gui_manager = new GUI_Manager();
            gui.init();
        });
    }
}

class PaneUsername extends JPanel {

    private JNeoButton btn_continue, btn_exit;
    private Container btnContainer;
    private JNeoTextField tf_username;
    private JLabel lb_credit, lb_subtitle;
    private LogoBig logo;

    PaneUsername() {
        super();
        init();
        organize();
        addAllActionListener();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);

        // buttons
        btn_continue = new JNeoButton("Continue", Global.colPrimary, Color.WHITE);
        btn_exit = new JNeoButton("Exit", Color.WHITE, Global.colSubtle);
        btnContainer = new Container();
        btnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnContainer.add(btn_continue); btnContainer.add(btn_exit);

        // text fields
        tf_username = new JNeoTextField("Username", 16, false, "account", "Account does not exist.");

        // labels
        lb_credit = new JNeoLabel("Developed by Cookies", Global.fntSecond, Global.colSecond);

        lb_subtitle = new JNeoLabel("Please enter your username", Global.fntButton, Global.colSecond);

        // logo
        logo = new LogoBig();
    }

    void addAllActionListener() {
        btn_continue.addActionListener(e -> {
            PanePassword.resetSubtitle(tf_username.getText());
            GUI_Master.changePanel(GUI_Manager.getPPassword()); // Show hint/error below if false
        });
        btn_exit.addActionListener(e -> System.exit(0));
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // tf_username
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_username, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_username, 32,
                SpringLayout.VERTICAL_CENTER, this);

        // btn_continue
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnContainer, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, btnContainer, 16,
                SpringLayout.SOUTH, tf_username);

        // lb_credit
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lb_credit, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, lb_credit, -8,
                SpringLayout.SOUTH, this);

        // lb_subtitle
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, lb_subtitle, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, lb_subtitle, -12,
                SpringLayout.NORTH, tf_username);

        // lb_logo
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, logo, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, logo, -24,
                SpringLayout.NORTH, lb_subtitle);
    }

    void addAll() {
        add(btnContainer);
        add(tf_username);
        add(lb_credit); add(lb_subtitle);
        add(logo);
    }
}