import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {
    private final int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(12, 16, 12, 16);
    }

    public boolean isBorderOpaque() {return true;}

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Global.colPrimary);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
