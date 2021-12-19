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

    private JNeoButton btn_continue;
    private JNeoTextField tf_username;

    PaneUsername() {
        super();
        init();
        organize();
        addAll();
    }

    void init() {
        this.setBackground(Color.WHITE);
        btn_continue = new JNeoButton("Continue");
        btn_continue.addActionListener(e -> System.out.println("Continue pressed!"));
        tf_username = new JNeoTextField(25);
    }

    void organize() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        // btn_continue
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btn_continue, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, btn_continue, 32,
                SpringLayout.VERTICAL_CENTER, this);

        // tf_username
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, tf_username, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, tf_username, -32,
                SpringLayout.VERTICAL_CENTER, this);
    }

    void addAll() {
        this.add(btn_continue);
        this.add(tf_username);
    }

    JNeoButton getBtnContinue() { return btn_continue;}
}