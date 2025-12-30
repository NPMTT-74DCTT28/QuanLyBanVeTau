package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.panel.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainController {

    private final MainFrame mainFrame;
    private final boolean isAdmin;
    private final boolean isLoggedIn;

    public MainController(MainFrame frame) {
        this.mainFrame = frame;
        this.isLoggedIn = SessionManager.getCurrentUser() != null;
        this.isAdmin = SessionManager.isAdmin();

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
        if (isLoggedIn) {
            mainFrame.setXinChao(SessionManager.getCurrentUser().getHoTen());
        } else {
            mainFrame.setXinChao(null);
        }
        mainFrame.addThongTinCaNhanListener(new ThongTinCaNhanListener());
        mainFrame.addDoiMatKhauListener(new DoiMatKhauListener());
        mainFrame.addDangXuatListener(new DangXuatListener());
        mainFrame.addThoatListener(new ThoatListener());
        mainFrame.addWindowCloseListener(new WindowCloseListener());

        quanLyMenu();

        mainFrame.setVisible(true);
    }

    private void quanLyMenu() {
        mainFrame.hienMenuTheoQuyen(isAdmin, isLoggedIn);
    }

    private class QLNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            QLNhanVienPanel qlNhanVienPanel = new QLNhanVienPanel();
            mainFrame.showPanel(qlNhanVienPanel);
            new QLNhanVienController(qlNhanVienPanel);
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

    private class QLVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class TKVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ThongTinCaNhanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DoiMatKhauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DangXuatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainFrame.showConfirm("Bạn chắc chắn muốn đăng xuất?")) {
                mainFrame.dispose();
                SessionManager.clearSession();
                new DangNhapController();
            }
        }
    }

    private class ThoatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mainFrame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
                mainFrame.dispose();
                System.exit(0);
            }
        }
    }

    private class WindowCloseListener extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            if (mainFrame.showConfirm("Bạn chắc chắn muốn thoát ứng dụng?")) {
                mainFrame.dispose();
                System.exit(0);
            }
        }
    }
}
