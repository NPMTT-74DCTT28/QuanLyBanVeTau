package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.panel.QLNhanVienPanel;
import com.group3tt28.quanlybanvetau.view.panel.TKNhanVienPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private final MainFrame mainFrame;

    public MainController(MainFrame frame) {
        this.mainFrame = frame;

        mainFrame.addNhanVienListener(new QLNhanVienListener(), new TKNhanVienListener());
//        mainFrame.addTauListener();
//        mainFrame.addLoaiToaListener();
//        mainFrame.addToaTauListener();
//        mainFrame.addGheListener();
//        mainFrame.addGaTauListener();
//        mainFrame.addTuyenDuongListener();
//        mainFrame.addLichTrinhListener(new LichTrinhListener());
//        mainFrame.addKhachHangListener();
//        mainFrame.addVeTauListener();

        mainFrame.setVisible(true);
    }

    private class QLNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLNhanVienPanel panel = new QLNhanVienPanel();
            mainFrame.showPanel(panel);
            new QLNhanVienController(panel);
        }
    }

    private class TKNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKNhanVienPanel panel = new TKNhanVienPanel();
            mainFrame.showPanel(panel);
            new TKNhanVienController(panel);
        }
    }
}
