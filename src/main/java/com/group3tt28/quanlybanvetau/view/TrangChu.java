package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import java.awt.*;

public class TrangChu extends BasePanel {

    public TrangChu() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel title = new JLabel("XIN CHÀO");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        panelTitle.add(title);

        add(panelTitle, BorderLayout.NORTH);
    }
}
