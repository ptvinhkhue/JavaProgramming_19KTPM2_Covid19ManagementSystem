import javax.swing.*;
import java.awt.*;

public class JNeoLabel extends JLabel {
    JNeoLabel(String str, Font font, Color col) {
        super(str);
        setFont(font);
        setForeground(col);
    }
}
