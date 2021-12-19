import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Global {

    // Path
    public static String pathFont, pathIcon, pathImage;

    // Color
    public static Color colPrimary, colPrimaryMild, colSecond, colSubtle, colError;

    // Font
    public static Font fntPrimary, fntSecond, fntButton, fntTitle, fntSub, fntSubtitle, fntHint;

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
        colError = new Color(240, 60, 36);

        // Font
        fntPrimary = fntHint = createFont("Quicksand-Regular.ttf");
        fntSecond = fntButton = fntSub = createFont("Quicksand-Medium.ttf");
        fntTitle = createFont("iCielPanton-Black.otf");

        // Font registry
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(fntPrimary);

        // Font derivation
        fntPrimary = fntPrimary.deriveFont(14f);
        fntSecond = fntSecond.deriveFont(12f);
        fntButton = fntButton.deriveFont(16f); // Also, for: Subtitle
        fntTitle = fntTitle.deriveFont(72f); // Logo title
        fntSub = fntSub.deriveFont(26f); // Logo subtitle
        fntHint = fntHint.deriveFont(12f);

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
