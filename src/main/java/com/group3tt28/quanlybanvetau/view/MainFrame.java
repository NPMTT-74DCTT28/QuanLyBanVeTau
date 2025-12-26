package com.group3tt28.quanlybanvetau.view;

import javax.swing.*;
import java.awt.*;

public final class MainFrame extends BaseFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() {
        super("Quản lý hệ thống bán vé tàu");
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    @Override
    protected void initComponents() {
        this.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        this.add(tabbedPane);
    }
}
