import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GUI_Login {

    public static JFrame fMain;
    PaneUsername pUsername;

    GUI_Login() {
        initPane();
        initFrame();
        addAll();

        fMain.setVisible(true);
    }

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

    public static void main(String[] args) {
        (new Global()).init();

        SwingUtilities.invokeLater(() -> {
            GUI_Login gui = new GUI_Login();
        });
    }
}

class PaneUsername extends JPanel {

    private JNeoButton btn_continue, btn_exit;
    private Container btnContainer;
    private JNeoTextField tf_username;
    private JLabel lb_credit, lb_subtitle;
    private Logo logo;

    PaneUsername() {
        super();
        init();
        organize();
        addActionListener();
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
        logo = new Logo();
    }

    void addActionListener() {
        btn_continue.addActionListener(e -> {
            tf_username.showHint(); // Show hint/error below if false
        });
        btn_exit.addActionListener(e -> {
            System.exit(0);
        });
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

class Logo extends Container {
    Logo() {
        super();
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // logo
        JLabel lb_logo = new JLabel("");
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(Objects.requireNonNull(getClass().getResource(
                            Global.pathImage + "img_logo.png")));
            lb_logo.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // title
        JLabel lb_title = new JNeoLabel("Shelter", Global.fntTitle, Global.colPrimaryMild);
        JLabel lb_sub = new JNeoLabel("Covid Management", Global.fntSub, Global.colSecond);

        // layout
        layout.putConstraint(SpringLayout.WEST, lb_logo, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, this, 0,
                SpringLayout.NORTH, lb_logo);
        layout.putConstraint(SpringLayout.SOUTH, this, 7,
                SpringLayout.SOUTH, lb_logo);

        layout.putConstraint(SpringLayout.NORTH, lb_title, 0,
                SpringLayout.NORTH, lb_logo);
        layout.putConstraint(SpringLayout.WEST, lb_title, 16,
                SpringLayout.EAST, lb_logo);
        layout.putConstraint(SpringLayout.SOUTH, lb_sub, 7,
                SpringLayout.SOUTH, lb_logo);
        layout.putConstraint(SpringLayout.WEST, lb_sub, 16,
                SpringLayout.EAST, lb_logo);
        layout.putConstraint(SpringLayout.EAST, this, 0,
                SpringLayout.EAST, lb_sub);

        // add
        add(lb_logo); add(lb_title); add(lb_sub);
    }
}