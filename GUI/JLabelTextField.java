import javax.swing.*;
import java.awt.*;

public class JLabelTextField extends JComponent {
    JLabelTextField(String l_str, int col) {
        super();

        this.setLayout(new FlowLayout());
        JLabel l = new JLabel(l_str);
        JTextField tf = new JTextField(col);

        this.add(l);
        this.add(tf);
    }
}