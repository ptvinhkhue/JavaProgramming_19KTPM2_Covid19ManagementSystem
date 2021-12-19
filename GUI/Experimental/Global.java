import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Global {

    // Path
    public static String pathFont;

    // Color
    public static Color colPrimary;

    // Font
    public static Font fntPrimary, fntButton;

    // Integers
    public static int btnRadius;

    Global() {
        init();
    }

    void init() {
        // Path
        pathFont = "\\resources\\font\\";

        // Color
        colPrimary = new Color(10, 147, 150);

        // Font
        try {
            fntPrimary = Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(getClass().getResource(pathFont + "Quicksand-Regular.ttf")).openStream());
            fntButton = Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(getClass().getResource(pathFont + "Quicksand-Medium.ttf")).openStream());
        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }

        // Font registry
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(fntPrimary);

        // Font derivation
        fntPrimary = fntPrimary.deriveFont(14f);
        fntButton = fntButton.deriveFont(18f);

        // Integers
        btnRadius = 10;

    }
}
