/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.view;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

/**
 *
 * @author qphwn
 */
public final class MainView extends BaseView {

    private JTabbedPane tabbedPane;

    public MainView() {
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
    }
}
