import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JSideBar extends JPanel {

    ArrayList<JNeoButton> item;
    private int role; // 0 = User; 1 = Manager; 2 = Admin

    JSideBar(String[] itemIcon, int role) {
        super();
        this.role = role;

        setBackground(Global.colSecond);
        setLayout(new GridLayout(10, 1));

        // items
        System.out.println(itemIcon.length);
        item = new ArrayList<>();
        for (String icon : itemIcon) {
            JNeoButton i = new JNeoButton("", getBackground(), Color.WHITE, 0);
            i.setIcon(icon);
            item.add(i); add(i);
        }
    }
}