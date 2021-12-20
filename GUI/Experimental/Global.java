import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Global {

    // Path
    public static String pathFont, pathIcon, pathImage;

    // Color
    public static Color colPrimary, colPrimaryMild, colSecond, colSubtle, colError, colDark;

    // Font
    public static Font fntPrimary, fntSecond, fntButton, fntTitle, fntSub, fntHeader, fntHint;

    // Integer - Button
    public static int btnRadius;

    // Integer - Text field
    public static int tfThickness, tfRadius;

    // Integer - List
    public static int listHeight, displaySize;
    public static Font fntListName, fntListSub;

    // Integer - Screen resolution
    public static int width, height;

    // Integer - Side bar
    public static int sb_height;

    // String - Side bar items
    public static String[] itemIcon_Manager = {"list", "add", "package", "logout"};

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
        colDark = colPrimary.darker().darker();
        colSubtle = new Color(200, 200, 200);
        colError = new Color(240, 60, 36);

        // Font
        fntPrimary = fntHint = createFont("Quicksand-Regular.ttf");
        fntSecond = fntButton = fntSub = createFont("Quicksand-Medium.ttf");
        fntTitle = fntListName = fntHeader = createFont("iCielPanton-Black.otf");
        fntListSub = createFont("Quicksand-Bold.ttf");

        // Font registry
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        genv.registerFont(fntPrimary);

        // Font derivation
        fntPrimary = fntPrimary.deriveFont(14f);
        fntSecond = fntSecond.deriveFont(12f);
        fntButton = fntButton.deriveFont(16f); // Also, for: Subtitle
        fntTitle = fntTitle.deriveFont(72f); // Logo title
        fntHeader = fntHeader.deriveFont(32f); // Logo title
        fntSub = fntSub.deriveFont(26f); // Logo subtitle
        fntHint = fntHint.deriveFont(12f);
        fntListName = fntListName.deriveFont(20f);
        fntListSub = fntListSub.deriveFont(16f);

        // Integers - Button
        btnRadius = 8;

        // Integers - Text field
        tfThickness = 1;
        tfRadius = 8;

        // Integers - List
        listHeight = 5;
        displaySize = 5;

        // Integers - Screen resolution
        width = 640; height = 480;

        // Integer - Side bar
        sb_height = 10;
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
