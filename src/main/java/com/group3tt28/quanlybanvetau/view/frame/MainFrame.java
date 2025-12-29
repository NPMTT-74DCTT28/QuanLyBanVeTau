package com.group3tt28.quanlybanvetau.view.frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public final class MainFrame extends BaseFrame {

    private JMenu menuCaNhan;
    private JMenuItem thongTinCaNhan, doiMatKhau, dangXuat, thoat;
    private JMenuItem qlNhanVien, tkNhanVien;
    private JMenuItem qlTau, tkTau;
    private JMenuItem qlLoaiToa, tkLoaiToa;
    private JMenuItem qlToaTau, tkToaTau;
    private JMenuItem qlGhe, tkGhe;
    private JMenuItem qlGaTau, tkGaTau;
    private JMenuItem qlTuyenDuong, tkTuyenDuong;
    private JMenuItem qlLichTrinh, tkLichTrinh;
    private JMenuItem qlKhachHang, tkKhachHang;
    private JMenuItem qlVeTau, tkVeTau;
    private JPanel container;

    public MainFrame() {
        super("Quản lý hệ thống bán vé tàu");
        this.setLocationRelativeTo(null);
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        container = new JPanel();
        container.setBorder(new EmptyBorder(10, 5, 10, 5));

        JScrollPane scrollPane = new JScrollPane(container);
        add(scrollPane, BorderLayout.CENTER);

        menuCaNhan = new JMenu("Xin chào");
        JMenu nhanVien = new JMenu("Nhân viên");
        JMenu tau = new JMenu("Tàu");
        JMenu loaiToa = new JMenu("Loại toa");
        JMenu ghe = new JMenu("Ghế");
        JMenu toaTau = new JMenu("Toa tàu");
        JMenu gaTau = new JMenu("Ga tàu");
        JMenu tuyenDuong = new JMenu("Tuyến đường");
        JMenu lichTrinh = new JMenu("Lịch trình");
        JMenu khachHang = new JMenu("Khách hàng");
        JMenu veTau = new JMenu("Vé tàu");

        thongTinCaNhan = new JMenuItem("Thông tin cá nhân");
        doiMatKhau = new JMenuItem("Đổi mật khẩu");
        dangXuat = new JMenuItem("Đăng xuất");
        thoat = new JMenuItem("Thoát ứng dụng");
        menuCaNhan.add(thongTinCaNhan);
        menuCaNhan.add(doiMatKhau);
        menuCaNhan.add(dangXuat);
        menuCaNhan.add(thoat);

        qlNhanVien = new JMenuItem("Quản lý thông tin");
        tkNhanVien = new JMenuItem("Tra cứu");
        nhanVien.add(qlNhanVien);
        nhanVien.add(tkNhanVien);

        qlTau = new JMenuItem("Quản lý thông tin");
        tkTau = new JMenuItem("Tra cứu");
        tau.add(qlTau);
        tau.add(tkTau);

        qlLoaiToa = new JMenuItem("Quản lý thông tin");
        tkLoaiToa = new JMenuItem("Tra cứu");
        loaiToa.add(qlLoaiToa);
        loaiToa.add(tkLoaiToa);

        qlToaTau = new JMenuItem("Quản lý thông tin");
        tkToaTau = new JMenuItem("Tra cứu");
        toaTau.add(qlToaTau);
        toaTau.add(tkToaTau);

        qlGhe = new JMenuItem("Quản lý thông tin");
        tkGhe = new JMenuItem("Tra cứu");
        ghe.add(qlGhe);
        ghe.add(tkGhe);

        qlGaTau = new JMenuItem("Quản lý thông tin");
        tkGaTau = new JMenuItem("Tra cứu");
        gaTau.add(qlGaTau);
        gaTau.add(tkGaTau);

        qlTuyenDuong = new JMenuItem("Quản lý thông tin");
        tkTuyenDuong = new JMenuItem("Tra cứu");
        tuyenDuong.add(qlTuyenDuong);
        tuyenDuong.add(tkTuyenDuong);

        qlLichTrinh = new JMenuItem("Quản lý thông tin");
        tkLichTrinh = new JMenuItem("Tra cứu");
        lichTrinh.add(qlLichTrinh);
        lichTrinh.add(tkLichTrinh);

        qlKhachHang = new JMenuItem("Quản lý thông tin");
        tkKhachHang = new JMenuItem("Tra cứu");
        khachHang.add(qlKhachHang);
        khachHang.add(tkKhachHang);

        qlVeTau = new JMenuItem("Quản lý thông tin");
        tkVeTau = new JMenuItem("Tra cứu");
        veTau.add(qlVeTau);
        veTau.add(tkVeTau);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(nhanVien);
        menuBar.add(tau);
        menuBar.add(loaiToa);
        menuBar.add(toaTau);
        menuBar.add(ghe);
        menuBar.add(gaTau);
        menuBar.add(tuyenDuong);
        menuBar.add(lichTrinh);
        menuBar.add(khachHang);
        menuBar.add(veTau);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuCaNhan);

        setJMenuBar(menuBar);
    }

    public void setXinChao(String tenNhanVien) {
        menuCaNhan.setText("Xin chào, " + tenNhanVien);
    }

    public void showPanel(JPanel panel) {
        container.removeAll();
        container.add(panel);
        container.revalidate();
        container.repaint();
    }

    public void addNhanVienListener(ActionListener qlListener, ActionListener tkListener) {
        qlNhanVien.addActionListener(qlListener);
        tkNhanVien.addActionListener(tkListener);
    }

    public void addTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlTau.addActionListener(qlListener);
        tkTau.addActionListener(tkListener);
    }

    public void addLoaiToaListener(ActionListener qlListener, ActionListener tkListener) {
        qlLoaiToa.addActionListener(qlListener);
        tkLoaiToa.addActionListener(tkListener);
    }

    public void addToaTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlToaTau.addActionListener(qlListener);
        tkToaTau.addActionListener(tkListener);
    }

    public void addGheListener(ActionListener qlListener, ActionListener tkListener) {
        qlGhe.addActionListener(qlListener);
        tkGhe.addActionListener(tkListener);
    }

    public void addGaTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlGaTau.addActionListener(qlListener);
        tkGaTau.addActionListener(tkListener);
    }

    public void addTuyenDuongListener(ActionListener qlListener, ActionListener tkListener) {
        qlTuyenDuong.addActionListener(qlListener);
        tkTuyenDuong.addActionListener(tkListener);
    }

    public void addLichTrinhListener(ActionListener qlListener, ActionListener tkListener) {
        qlLichTrinh.addActionListener(qlListener);
        tkLichTrinh.addActionListener(tkListener);
    }

    public void addKhachHangListener(ActionListener qlListener, ActionListener tkListener) {
        qlKhachHang.addActionListener(qlListener);
        tkKhachHang.addActionListener(tkListener);
    }

    public void addVeTauListener(ActionListener qlListener, ActionListener tkListener) {
        qlVeTau.addActionListener(qlListener);
        tkVeTau.addActionListener(tkListener);
    }

    public void addThongTinCaNhanListener(ActionListener l) {
        thongTinCaNhan.addActionListener(l);
    }

    public void addDoiMatKhauListener(ActionListener l) {
        doiMatKhau.addActionListener(l);
    }

    public void addDangXuatListener(ActionListener l) {
        dangXuat.addActionListener(l);
    }

    public void addThoatListener(ActionListener l) {
        thoat.addActionListener(l);
    }
}
