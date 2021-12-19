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
        fMain.setSize(800, 600);
        fMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fMain.setLocationRelativeTo(null);
        fMain.setResizable(false);
    }

    void addAll() {
        fMain.add(pUsername);
    }

    public static void main(String[] args) {
        (new Global()).init();

        GUI_Login gui = new GUI_Login();
    }
}

class PaneUsername extends JPanel {

    private JNeoButton btn_continue;

    PaneUsername() {
        super();
        this.init();
        this.addAll();
    }

    void init() {
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);
        btn_continue = new JNeoButton("Continue");
        btn_continue.addActionListener(e -> System.out.println("Continue pressed!"));
    }

    void addAll() {
        this.add(btn_continue);
    }

    JNeoButton getBtnContinue() { return btn_continue;}
}