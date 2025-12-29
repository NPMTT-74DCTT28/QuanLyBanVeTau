package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.frame.MainFrame;
import com.group3tt28.quanlybanvetau.view.panel.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {

    private final MainFrame mainFrame;
    private final NhanVien currentUser;
    private final boolean isAdmin;

    public MainController(MainFrame frame) {
        this.mainFrame = frame;
        this.currentUser = SessionManager.getCurrentUser();
        this.isAdmin = SessionManager.isAdmin();
        System.out.println("isAdmin: " + isAdmin);

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
        if (currentUser != null) {
            mainFrame.setXinChao(SessionManager.getCurrentUser().getHoTen());
        } else {
            mainFrame.setXinChao(null);
        }
        mainFrame.addThongTinCaNhanListener(new ThongTinCaNhanListener());
        mainFrame.addDoiMatKhauListener(new DoiMatKhauListener());
        mainFrame.addDangXuatListener(new DangXuatListener());
        mainFrame.addThoatListener(new ThoatListener());

        mainFrame.setVisible(true);
    }

    private class QLNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLNhanVienPanel qlNhanVienPanel = new QLNhanVienPanel();
                mainFrame.showPanel(qlNhanVienPanel);
                new QLNhanVienController(qlNhanVienPanel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKNhanVienPanel panel = new TKNhanVienPanel();
                mainFrame.showPanel(panel);
                new TKNhanVienController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLTauPanel panel = new QLTauPanel();
                mainFrame.showPanel(panel);
                new QLTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKTauPanel panel = new TKTauPanel();
                mainFrame.showPanel(panel);
                new TKTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLLoaiToaPanel panel = new QLLoaiToaPanel();
                mainFrame.showPanel(panel);
                new QLLoaiToaController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKLoaiToaPanel panel = new TKLoaiToaPanel();
                mainFrame.showPanel(panel);
                new TKLoaiToaController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLToaTauPanel panel = new QLToaTauPanel();
                mainFrame.showPanel(panel);
                new QLToaTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKToaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKToaTauPanel panel = new TKToaTauPanel();
                mainFrame.showPanel(panel);
                new TKToaTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLGhePanel panel = new QLGhePanel();
                mainFrame.showPanel(panel);
                new QLGheController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKGhePanel panel = new TKGhePanel();
                mainFrame.showPanel(panel);
                new TKGheController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLGaTauPanel panel = new QLGaTauPanel();
                mainFrame.showPanel(panel);
                new QLGaTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKGaTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKGaTauPanel panel = new TKGaTauPanel();
                mainFrame.showPanel(panel);
                new TKGaTauController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class QLTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                QLTuyenDuongPanel panel = new QLTuyenDuongPanel();
                mainFrame.showPanel(panel);
                new QLTuyenDuongController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
        }
    }

    private class TKTuyenDuongListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAdmin) {
                TKTuyenDuongPanel panel = new TKTuyenDuongPanel();
                mainFrame.showPanel(panel);
                new TKTuyenDuongController(panel);
            }
            mainFrame.showWarning("Bạn không có quyền thực hiện thao tác này!");
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

        }
    }

    private class ThoatListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
