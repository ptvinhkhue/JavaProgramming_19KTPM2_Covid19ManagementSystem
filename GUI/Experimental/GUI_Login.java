import javax.swing.*;
import java.awt.*;

public class GUI_Login {

    JFrame fMain;
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
        btn_continue = new JNeoButton("Continue");
        btn_exit = new JNeoButton("Exit");
        btnContainer = new Container();
        btnContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnContainer.add(btn_continue); btnContainer.add(btn_exit);

        tf_username = new JNeoTextField("Username", 16, false, "account");
    }

    void addActionListener() {
        btn_continue.addActionListener(e -> System.out.println("Continue pressed!"));
        btn_exit.addActionListener(e -> System.exit(0));
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // btn_continue
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnContainer, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, btnContainer, 32,
                SpringLayout.VERTICAL_CENTER, this);

        // tf_username
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_username, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_username, -32,
                SpringLayout.VERTICAL_CENTER, this);
    }

    void addAll() {
        this.add(btnContainer);
        this.add(tf_username);
    }

    JNeoButton getBtnContinue() { return btn_continue;}

    JNeoButton getBtnExit() { return btn_exit;}
}