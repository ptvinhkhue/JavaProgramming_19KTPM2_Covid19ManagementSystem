import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Global {

    // Path
    public static String pathFont, pathIcon, pathImage;

    // Color
    public static Color colPrimary, colPrimaryMild, colSecond, colSubtle;

    // Font
    public static Font fntPrimary, fntSecond, fntButton, fntTitle, fntSub;

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
        pathImage = "\\resources\\image\\main\\";

        // Color
        colPrimary = new Color(99, 214, 179);
        colPrimaryMild = new Color(130, 220, 178);
        colSecond = new Color(78, 96, 88);
        colSubtle = new Color(200, 200, 200);

        // Font
        fntPrimary = createFont("Quicksand-Regular.ttf");
        fntSecond = fntButton = fntSub = createFont("Quicksand-Medium.ttf");
        fntTitle = createFont("iCielPanton-Black.otf");

        // Font registry
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(fntPrimary);

        // Font derivation
        fntPrimary = fntPrimary.deriveFont(14f);
        fntSecond = fntSecond.deriveFont(12f);
        fntButton = fntButton.deriveFont(16f);
        fntTitle = fntTitle.deriveFont(72f);
        fntSub = fntSub.deriveFont(26f);

        // Integers - Button
        btnRadius = 8;

        // Integers - Text field
        tfThickness = 1;
        tfRadius = 8;
    }

    Font createFont(String fontName) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT,
                    Objects.requireNonNull(getClass().getResource(pathFont + fontName)).openStream());
        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
