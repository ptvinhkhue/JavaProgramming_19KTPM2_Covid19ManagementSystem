import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class JNeoButton extends JButton {

    JNeoButton(String label) {
        super(label);
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

                    g.setColor(fill);
                    g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), Global.btnRadius, Global.btnRadius);
                }
                paint(g, c);
            }
        });
    }
}
