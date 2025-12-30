package com.group3tt28.quanlybanvetau.view.frame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public final class MainFrame extends BaseFrame {

    private JMenu menuCaNhan;
    private JMenuItem thongTinCaNhan, doiMatKhau, dangXuat, thoat;
    private JMenu menuNhanVien;
    private JMenuItem qlNhanVien, tkNhanVien;
    private JMenu menuTau;
    private JMenuItem qlTau, tkTau;
    private JMenu menuLoaiToa;
    private JMenuItem qlLoaiToa, tkLoaiToa;
    private JMenu menuToaTau;
    private JMenuItem qlToaTau, tkToaTau;
    private JMenu menuGhe;
    private JMenuItem qlGhe, tkGhe;
    private JMenu menuGaTau;
    private JMenuItem qlGaTau, tkGaTau;
    private JMenu menuTuyenDuong;
    private JMenuItem qlTuyenDuong, tkTuyenDuong;
    private JMenu menuLichTrinh;
    private JMenuItem qlLichTrinh, tkLichTrinh;
    private JMenu menuKhachHang;
    private JMenuItem qlKhachHang, tkKhachHang;
    private JMenu menuVeTau;
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
        menuNhanVien = new JMenu("Nhân viên");
        menuTau = new JMenu("Tàu");
        menuLoaiToa = new JMenu("Loại toa");
        menuGhe = new JMenu("Ghế");
        menuToaTau = new JMenu("Toa tàu");
        menuGaTau = new JMenu("Ga tàu");
        menuTuyenDuong = new JMenu("Tuyến đường");
        menuLichTrinh = new JMenu("Lịch trình");
        menuKhachHang = new JMenu("Khách hàng");
        menuVeTau = new JMenu("Vé tàu");

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
        menuNhanVien.add(qlNhanVien);
        menuNhanVien.add(tkNhanVien);

        qlTau = new JMenuItem("Quản lý thông tin");
        tkTau = new JMenuItem("Tra cứu");
        menuTau.add(qlTau);
        menuTau.add(tkTau);

        qlLoaiToa = new JMenuItem("Quản lý thông tin");
        tkLoaiToa = new JMenuItem("Tra cứu");
        menuLoaiToa.add(qlLoaiToa);
        menuLoaiToa.add(tkLoaiToa);

        qlToaTau = new JMenuItem("Quản lý thông tin");
        tkToaTau = new JMenuItem("Tra cứu");
        menuToaTau.add(qlToaTau);
        menuToaTau.add(tkToaTau);

        qlGhe = new JMenuItem("Quản lý thông tin");
        tkGhe = new JMenuItem("Tra cứu");
        menuGhe.add(qlGhe);
        menuGhe.add(tkGhe);

        qlGaTau = new JMenuItem("Quản lý thông tin");
        tkGaTau = new JMenuItem("Tra cứu");
        menuGaTau.add(qlGaTau);
        menuGaTau.add(tkGaTau);

        qlTuyenDuong = new JMenuItem("Quản lý thông tin");
        tkTuyenDuong = new JMenuItem("Tra cứu");
        menuTuyenDuong.add(qlTuyenDuong);
        menuTuyenDuong.add(tkTuyenDuong);

        qlLichTrinh = new JMenuItem("Quản lý thông tin");
        tkLichTrinh = new JMenuItem("Tra cứu");
        menuLichTrinh.add(qlLichTrinh);
        menuLichTrinh.add(tkLichTrinh);

        qlKhachHang = new JMenuItem("Quản lý thông tin");
        tkKhachHang = new JMenuItem("Tra cứu");
        menuKhachHang.add(qlKhachHang);
        menuKhachHang.add(tkKhachHang);

        qlVeTau = new JMenuItem("Quản lý thông tin");
        tkVeTau = new JMenuItem("Tra cứu");
        menuVeTau.add(qlVeTau);
        menuVeTau.add(tkVeTau);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuNhanVien);
        menuBar.add(menuTau);
        menuBar.add(menuLoaiToa);
        menuBar.add(menuToaTau);
        menuBar.add(menuGhe);
        menuBar.add(menuGaTau);
        menuBar.add(menuTuyenDuong);
        menuBar.add(menuLichTrinh);
        menuBar.add(menuKhachHang);
        menuBar.add(menuVeTau);
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

    public void hienMenuTheoQuyen(boolean isAdmin, boolean isLoggedIn) {
        menuNhanVien.setVisible(isAdmin);

        menuTau.setVisible(isLoggedIn);
        qlTau.setVisible(isAdmin);
        tkTau.setVisible(isLoggedIn);

        menuLoaiToa.setVisible(isAdmin);

        menuToaTau.setVisible(isLoggedIn);
        qlToaTau.setVisible(isAdmin);
        tkToaTau.setVisible(isLoggedIn);

        menuGhe.setVisible(isAdmin);

        menuGaTau.setVisible(isLoggedIn);
        qlGaTau.setVisible(isAdmin);
        tkGaTau.setVisible(isLoggedIn);

        menuTuyenDuong.setVisible(isLoggedIn);
        qlTuyenDuong.setVisible(isAdmin);
        tkTuyenDuong.setVisible(isLoggedIn);

        menuLichTrinh.setVisible(isLoggedIn);

        menuKhachHang.setVisible(isLoggedIn);

        menuVeTau.setVisible(isLoggedIn);

        thongTinCaNhan.setVisible(isLoggedIn);
        doiMatKhau.setVisible(isLoggedIn);
        dangXuat.setVisible(isLoggedIn);
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
