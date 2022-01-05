package com.cookies.covidapp.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.*;
import java.util.ArrayList;

public class JNeoComboBox extends JComboBox {
    private DefaultComboBoxModel model;

    public JNeoComboBox() {
        model = new DefaultComboBoxModel();
        setModel(model);
        setRenderer(new JNeoComboBoxRender());
        setEditor(new JNeoComboBoxEditor());
    }

    public void addItemList(ArrayList<String> item) {
        for (String i: item) {
            model.addElement(i);
        }
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
}
