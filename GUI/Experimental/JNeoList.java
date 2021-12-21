import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JNeoList extends Container {
    private ArrayList<JNeoListItem> item;
    SpringLayout laySpring;
    private final Container ctn_item;
    private Container ctn_btn;
    private JNeoButton btn_previous, btn_next, btn_add;
    private int curPage, totalPage;
    private JNeoLabel lb_page;

    JNeoList(String[] iconName, String[] name, String[] label) {
        super();
        laySpring = new SpringLayout();
        setLayout(laySpring);

        ctn_item = new Container();
        GridLayout layGrid = new GridLayout(Global.listHeight, 1);
        ctn_item.setLayout(layGrid); layGrid.setVgap(8);

        init(iconName, name, label);
        organize();
        addAllActionListener();
    }

    void init(String[] iconName, String[] name, String[] label) {
        curPage = 0;
        lb_page = new JNeoLabel("" + totalPage, Global.fntButton, Global.colSecond);

        // items
        item = new ArrayList<>();
        setNewList(iconName, name, label);


        // buttons
        btn_add = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_add.setIcon("add");
        btn_previous = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_previous.setIcon("arrow_left");
        btn_next = new JNeoButton("", Global.colPrimary, Color.WHITE, Global.btnRadius, 2, Global.fntButton, false);
        btn_next.setIcon("arrow_right");
        ctn_btn = new Container();
        ctn_btn.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 0));
        ctn_btn.add(btn_add); ctn_btn.add(btn_previous); ctn_btn.add(btn_next);

        // add
        add(ctn_item); add(ctn_btn); add(lb_page);
    }

    void organize() {
        // ctn_btn
        laySpring.putConstraint(SpringLayout.WEST, ctn_btn, 0,
                SpringLayout.WEST, this);
        laySpring.putConstraint(SpringLayout.EAST, ctn_btn, 0,
                SpringLayout.EAST, this);
        laySpring.putConstraint(SpringLayout.SOUTH, ctn_btn, 0,
                SpringLayout.SOUTH, this);

        // ctn_item
        laySpring.putConstraint(SpringLayout.WEST, ctn_item, 0,
                SpringLayout.WEST, this);
        laySpring.putConstraint(SpringLayout.EAST, ctn_item, 0,
                SpringLayout.EAST, this);
        laySpring.putConstraint(SpringLayout.NORTH, ctn_item, 0,
                SpringLayout.NORTH, this);
        laySpring.putConstraint(SpringLayout.SOUTH, ctn_item, -16,
                SpringLayout.NORTH, ctn_btn);

        // lb_page
        laySpring.putConstraint(SpringLayout.WEST, lb_page, 0,
                SpringLayout.WEST, ctn_btn);
        laySpring.putConstraint(SpringLayout.NORTH, lb_page, 0,
                SpringLayout.NORTH, ctn_btn);
    }

    void addAllActionListener() {
        btn_previous.addActionListener(e -> {
            curPage = (0 < curPage) ? curPage - 1 : totalPage - 1;
            showPage(curPage);
        });
        btn_next.addActionListener(e -> {
            curPage = (curPage < totalPage - 1) ? curPage + 1 : 0;
            showPage(curPage);
        });
    }

    void showPage(int page) {
        int size = Global.displaySize;
        if (page > item.size() / size) return;

        int pageSize = Math.min(item.size() - size * page, size);

        ctn_item.removeAll();
        for (int i = page * size; i < page * size + pageSize; i++)
            ctn_item.add(item.get(i));
        ctn_item.repaint();
        ctn_item.revalidate();
        lb_page.setText("Page " + (curPage + 1) + "/" + totalPage);
        lb_page.repaint();

    }

    void setNewList(String[] iconName, String[] name, String[] label) {
        item.clear();
        if (iconName.length != name.length || name.length != label.length)
            throw new java.lang.Error("[JNeoList] Unmatched length.");
        for (int k = 0; k < name.length; k++) {
            JNeoListItem i = new JNeoListItem(iconName[k], name[k], label[k]);
            item.add(i);
        }
        curPage = 0;
        totalPage = item.size() / Global.displaySize + 1;
        showPage(curPage);
    }

    JNeoButton getBtnAdd() { return btn_add; }

    ArrayList<JNeoListItem> getItemList() {
        return item;
    }
}

class JNeoListItem extends JPanel {

    private JLabel lb_img;
    private JNeoLabel lb_name;
    private JNeoLabel lb_sub;
    private final SpringLayout layout;

    // info button
    private JNeoButton btn_info;

    JNeoListItem(String iconName, String name, String label) {
        super();
        layout = new SpringLayout();
        setLayout(layout);

        this.setBackground(Global.colPrimaryMild);

        init(iconName, name, label);
        organize();
        addAll();
    }

    // name: patient's or necessity's name
    // label: patient's birth year or necessity's price
    void init(String iconName, String name, String sub) {
        // image
        lb_img = new JLabel("");
        try {
            BufferedImage icon = ImageIO.read(
                    Objects.requireNonNull(getClass().getResource(
                            Global.pathIcon + "ic_" + iconName + ".png")));
            lb_img.setIcon(new ImageIcon(icon));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // name & label
        lb_name = new JNeoLabel(name, Global.fntListName, Global.colDark);
        lb_sub = new JNeoLabel(sub, Global.fntListSub, Global.colSecond);

        // info button
        btn_info = new JNeoButton("", Global.colPrimary.darker(), getBackground(), 0, 8, Global.fntButton, false);
        btn_info.setIcon("info");

        // add all
    }

    void organize() {
        layout.putConstraint(SpringLayout.WEST, btn_info, -48,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.EAST, btn_info, 0,
                SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, btn_info, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, btn_info, 0,
                SpringLayout.SOUTH, this);

        layout.putConstraint(SpringLayout.WEST, lb_img, 12,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_img, 0,
                SpringLayout.VERTICAL_CENTER, this);

        layout.putConstraint(SpringLayout.WEST, lb_name, 12,
                SpringLayout.EAST, lb_img);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_name, 6,
                SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, lb_sub, 12,
                SpringLayout.EAST, lb_name);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, lb_sub, 0,
                SpringLayout.VERTICAL_CENTER, this);

    }


    void addAll() {
        add(lb_img); add(lb_name); add(lb_sub); add(btn_info);
    }

    JNeoButton getBtnInfo() { return btn_info; }

    JNeoLabel getLbName() {return lb_name;}
    JNeoLabel getLbSub() {return lb_sub;}
}
