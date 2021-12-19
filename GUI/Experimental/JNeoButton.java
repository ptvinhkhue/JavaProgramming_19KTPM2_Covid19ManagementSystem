import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class JNeoButton extends JButton {

    JNeoButton(String label, Color colBack, Color colFront) {
        super(label);
        int radius = Global.btnRadius;

        // color
        this.setForeground(colFront);
        this.setBackground(colBack);
        this.setOpaque(true);

        // font
        this.setFont(Global.fntButton);
        this.revalidate();

        // other configurations
        this.setFocusPainted(false); // focus border
        this.setBorder(new RoundedBorder(Global.btnRadius)); // round border
        this.setBorderPainted(false);

        // Color handling
        this.setUI(new BasicButtonUI() {
            @Override
            public void update(Graphics g, JComponent c) {
                if (c.isOpaque()) {
                    Color fill = c.getBackground();

                    AbstractButton button = (AbstractButton) c;
                    ButtonModel model = button.getModel();

                    if (model.isPressed()) {
                        fill = fill.darker();
                    } else if (model.isRollover()) {
                        fill = fill.brighter();
                    }

                    g.setColor(Color.WHITE);
                    g.fillRect(0, 0, c.getWidth(), c.getHeight());
                    g.setColor(fill);
                    g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);
                }
                paint(g, c);
            }
        });
    }
}

class RoundedBorder implements Border {
    private final int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(8, 16, 8, 16);
    }

    public boolean isBorderOpaque() {return true;}

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
