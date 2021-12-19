import javax.swing.*;
import java.awt.*;

public class JNeoButton extends JButton {

    JNeoButton(String str) {
        super(str);
        // color
        this.setForeground(Color.WHITE);
        this.setBackground(Global.colPrimary);
        this.setOpaque(true);
        // font
        this.setFont(Global.fntButton);
        this.revalidate();
        // other configurations
        this.setFocusPainted(false); // focus border
        this.setBorder(new RoundedBorder(Global.btnRadius)); // round border
    }
}
