import javax.swing.*;
import java.awt.*;

public class JLabelPasswordField extends JComponent {
    JLabelPasswordField(String l_str, int col) {
        super();

        this.setLayout(new FlowLayout());
        JLabel l = new JLabel(l_str);
        JPasswordField tf = new JPasswordField(col);

        this.add(l);
        this.add(tf);
    }
}
