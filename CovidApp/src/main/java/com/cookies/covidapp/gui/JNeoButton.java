package com.cookies.covidapp.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class JNeoButton extends JButton {

    boolean isTag, enabled;
    Color colBack;

    JNeoButton(String label, Color colBack, Color colFront, int radius, int insets, Font fnt, boolean isTag) {
        super(label);
        this.isTag = isTag;
        this.enabled = true;
        this.colBack = colBack;

        // color
        this.setForeground(colFront);
        this.setBackground(colBack);
        this.setOpaque(true);

        // font
        this.setFont(fnt);
        this.revalidate();

        // other configurations
        this.setFocusPainted(false); // focus border
        this.setBorder(new RoundedBorder(Global.btnRadius, insets)); // round border
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

        addAllActionListener();
    }

    void addAllActionListener() {
        if (isTag) addActionListener(e -> {
            enabled = !enabled;
            Color col = (enabled) ? colBack : colBack.darker().darker();
            setBackground(col);
            repaint();
        });
    }

    void setIcon(String iconName) {
        setIcon(new ImageIcon(Objects.requireNonNull(Global.getIcon(iconName))));
    }
}

class RoundedBorder implements Border {
    private final int radius;
    private final int insets;

    RoundedBorder(int radius, int insets) {
        this.radius = radius;
        this.insets = insets;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(insets, insets * 2, insets, insets * 2);
    }

    public boolean isBorderOpaque() {return true;}

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
