import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Global {

    // Path
    public static String pathFont, pathIcon;

    // Color
    public static Color colPrimary, colSecond;

    // Font
    public static Font fntPrimary, fntButton;

    // Integer - Button
    public static int btnRadius;

    // Integer - Text field
    public static int tfThickness, tfRadius;

    Global() {
        init();
    }

    void init() {
        // Path
        pathFont = "\\resources\\font\\";
        pathIcon = "\\resources\\image\\icon\\";

        // Color
        colPrimary = new Color(99, 214, 179);
        colSecond = new Color(200, 200, 200);

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

        // Integers - Button
        btnRadius = 8;

        // Integers - Text field
        tfThickness = 2;
        tfRadius = 8;
    }
}
