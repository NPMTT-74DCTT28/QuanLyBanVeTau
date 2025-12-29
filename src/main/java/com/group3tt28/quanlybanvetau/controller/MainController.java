package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.panel.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private final MainFrame mainFrame;

    public MainController(MainFrame frame) {
        this.mainFrame = frame;

        mainFrame.addNhanVienListener(new QLNhanVienListener(), new TKNhanVienListener());
        mainFrame.addTauListener(new QLTauListener(), new TKTauListener());
        mainFrame.addLoaiToaListener(new QLLoaiToaListener(), new TKLoaiToaListener());
        mainFrame.addToaTauListener(new QLToaTauListener(), new TKToaTauListener());
        mainFrame.addGheListener(new QLGheListener(), new TKGheListener());
        mainFrame.addGaTauListener(new QLGaTauListener(), new TKGaTauListener());
        mainFrame.addTuyenDuongListener(new QLTuyenDuongListener(), new TKTuyenDuongListener());
        mainFrame.addLichTrinhListener(new QLLichTrinhListener(), new TKLichTrinhListener());
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

    private class QLTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLTauPanel panel = new QLTauPanel();
            mainFrame.showPanel(panel);
            new QLTauController(panel);
        }
    }

    private class TKTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKTauPanel panel = new TKTauPanel();
            mainFrame.showPanel(panel);
            new TKTauController(panel);
        }
    }

    private class QLLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLLoaiToaPanel panel = new QLLoaiToaPanel();
            mainFrame.showPanel(panel);
            new QLLoaiToaController(panel);
        }
    }

    private class TKLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKLoaiToaPanel panel = new TKLoaiToaPanel();
            mainFrame.showPanel(panel);
            new TKLoaiToaController(panel);
        }
    }

    private class QLToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLToaTauPanel panel = new QLToaTauPanel();
            mainFrame.showPanel(panel);
            new QLToaTauController(panel);
        }
    }

    private class TKToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKToaTauPanel panel = new TKToaTauPanel();
            mainFrame.showPanel(panel);
            new TKToaTauController(panel);
        }
    }

    private class QLGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLGhePanel panel = new QLGhePanel();
            mainFrame.showPanel(panel);
            new QLGheController(panel);
        }
    }

    private class TKGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKGhePanel panel = new TKGhePanel();
            mainFrame.showPanel(panel);
            new TKGheController(panel);
        }
    }

    private class QLGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLGaTauPanel panel = new QLGaTauPanel();
            mainFrame.showPanel(panel);
            new QLGaTauController(panel);
        }
    }

    private class TKGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKGaTauPanel panel = new TKGaTauPanel();
            mainFrame.showPanel(panel);
            new TKGaTauController(panel);
        }
    }

    private class QLTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLTuyenDuongPanel panel = new QLTuyenDuongPanel();
            mainFrame.showPanel(panel);
            new QLTuyenDuongController(panel);
        }
    }

    private class TKTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKTuyenDuongPanel panel = new TKTuyenDuongPanel();
            mainFrame.showPanel(panel);
            new TKTuyenDuongController(panel);
        }
    }

    private class QLLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLLichTrinhPanel panel = new QLLichTrinhPanel();
            mainFrame.showPanel(panel);
            new QLLichTrinhController(panel);
        }
    }

    private class TKLichTrinhListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TKLichTrinhPanel panel = new TKLichTrinhPanel();
            mainFrame.showPanel(panel);
            new TKLichTrinhController(panel);
        }
    }

    private class QLKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
