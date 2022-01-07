package com.cookies.covidapp.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class JNeoComboBox extends Container {

    private JNeoComboBoxSelector selector;

    JNeoComboBox(String iconName) {
        super();
        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        // icon
        JLabel lb_icon = new JLabel("");
        if (!iconName.equals(""))
            lb_icon.setIcon(new ImageIcon(Objects.requireNonNull(Global.getIcon(iconName))));

        // selector
        selector = new JNeoComboBoxSelector();

        // layout
        layout.putConstraint(SpringLayout.WEST, lb_icon, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, lb_icon, 0,
                SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, selector, (Objects.equals(iconName, "")) ? 0 : 4,
                SpringLayout.EAST, lb_icon);
        if (!iconName.equals(""))
            layout.putConstraint(SpringLayout.VERTICAL_CENTER, selector, 0,
                SpringLayout.VERTICAL_CENTER, lb_icon);
        else
            layout.putConstraint(SpringLayout.NORTH, selector, 0,
                    SpringLayout.NORTH, lb_icon);

        layout.putConstraint(SpringLayout.EAST, this, 0,
                SpringLayout.EAST, selector);
        layout.putConstraint(SpringLayout.SOUTH, this, 0,
                SpringLayout.SOUTH, selector);

        add(lb_icon); add(selector);
    }

    JNeoComboBoxSelector getSelector() {
        return selector;
    }

    void removeAllItems() {
        selector.removeAllItems();
    }

    void addItemList(ArrayList<String> item_list) {
        selector.addItemList(item_list);
    }

    void setEditable(boolean editable) {
        selector.setEditable(editable);
    }

    int getSelectedIndex() {
        return selector.getSelectedIndex();
    }
    
    String getSelectedItem() {
        return (String) selector.getSelectedItem();
    }
    
    void setSelectedItem(String selected) {
        selector.setSelectedItem(selected);
    }

    public void repaintAll() {
        repaint();
        this.getSelector().repaint();
        this.getSelector().getRender().repaint();
        this.getSelector().getEdit().getPanel().repaint();
    }
}

class JNeoComboBoxSelector extends JComboBox {
    private final DefaultComboBoxModel model;
    private final JNeoComboBoxRender render = new JNeoComboBoxRender();
    private final JNeoComboBoxEditor editor = new JNeoComboBoxEditor();

    public JNeoComboBoxSelector() {
        model = new DefaultComboBoxModel();
        setModel(model);
        setRenderer(render);
        setEditor(editor);
    }

    public void addItemList(ArrayList<String> item) {
        for (String i: item) {
            model.addElement(i);
        }
    }

    JNeoComboBoxRender getRender() {
        return render;
    }

    JNeoComboBoxEditor getEdit() {
        return editor;
    }
}

class JNeoComboBoxRender extends JPanel implements ListCellRenderer {
    private JNeoLabel lb = new JNeoLabel("", Global.fntPrimary, Global.colDark);

    JNeoComboBoxRender() {
        setLayout(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 1.0;
        cs.insets = new Insets(4, 4, 4, 4);

        lb.setOpaque(true);
        lb.setHorizontalAlignment(JLabel.LEFT);

        add(lb, cs);
        setBackground(Color.WHITE);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String item = (String) value;

        // set name
        lb.setText(item);

        if (isSelected) {
            lb.setBackground(Global.colPrimary);
            lb.setForeground(Color.WHITE);
        } else {
            lb.setBackground(Color.WHITE);
            lb.setForeground(Global.colDark);
        }

        return this;
    }
}

class JNeoComboBoxEditor extends BasicComboBoxEditor {
    private JPanel panel = new JPanel();
    private JNeoLabel lb = new JNeoLabel("", Global.fntListSub, Global.colDark);
    private String selectedValue;

    public JNeoComboBoxEditor() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.weightx = 1.0;
        cs.insets = new Insets(4, 4, 4, 4);

        lb.setOpaque(false);
        lb.setHorizontalAlignment(JLabel.LEFT);
        lb.setForeground(Color.WHITE);

        panel.add(lb, cs);
        panel.setBackground(Global.colPrimary);
    }

    public Component getEditorComponent() {
        return this.panel;
    }

    public Object getItem() {
        return this.selectedValue;
    }

    public void setItem(Object item) {
        if (item == null) {
            return;
        }
        String item_list = (String) item;
        selectedValue = item_list;
        lb.setText(selectedValue);
    }

    JPanel getPanel() { return panel; }
}
