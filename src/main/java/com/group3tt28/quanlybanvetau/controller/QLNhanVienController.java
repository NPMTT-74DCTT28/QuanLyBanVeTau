package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.view.QLNhanVienPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class QLNhanVienController {

    private final QLNhanVienPanel panel;
    private final NhanVienDAO dao;
    private final DefaultTableModel model;
    private int selectedRow;

    public QLNhanVienController(QLNhanVienPanel panel) {
        this.dao = new NhanVienDAO();

        this.panel = panel;
        panel.addThemNhanVienListener(new ThemNhanVienListener());
        panel.addSuaNhanVienListener(new SuaNhanVienListener());
        panel.addXoaNhanVienListener(new XoaNhanVienListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        loadTableData();
    }

    private void loadTableData() {
        List<NhanVien> list = dao.getAll();
        model.setRowCount(0);

        for (NhanVien nhanVien : list) {
            model.addRow(new Object[]{
                    nhanVien.getMaNhanVien(),
                    nhanVien.getHoTen(),
                    nhanVien.getNgaySinh(),
                    nhanVien.getGioiTinh(),
                    nhanVien.getSdt(),
                    nhanVien.getEmail(),
                    nhanVien.getDiaChi(),
                    nhanVien.getVaiTro()
            });
        }

        model.fireTableDataChanged();
    }

    private class ThemNhanVienListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                NhanVien nhanVien = panel.getNhanVienFromForm();

                if (validateInput(nhanVien) != null) {
                    panel.showWarning(validateInput(nhanVien));
                    return;
                }

                if (nhanVien.getMatKhau() == null || nhanVien.getMatKhau().isEmpty()) {
                    panel.showWarning("Vui lòng nhập mật khẩu cho nhân viên mới!");
                    return;
                }

                if (dao.checkTrung(nhanVien.getMaNhanVien())) {
                    panel.showWarning("Mã nhân viên " + nhanVien.getMaNhanVien() + " đã tồn tại!");
                    return;
                }

                String matKhauTho = nhanVien.getMatKhau();
                String matKhauBam = BamMatKhau.bamMatKhau(matKhauTho);
                nhanVien.setMatKhau(matKhauBam);

                if (nhanVien.getEmail().isEmpty()) {
                    nhanVien.setEmail(null);
                }

                if (dao.insert(nhanVien)) {
                    panel.showMessage("Thêm nhân viên thành công!");
                    panel.resetForm();
                    loadTableData();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class SuaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                NhanVien nhanVien = panel.getNhanVienFromForm();
            } catch (RuntimeException ex) {

            } catch (Exception ex) {

            }
        }
    }

    private class XoaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            panel.setMaNhanVien(model.getValueAt(selectedRow, 0).toString());
            panel.setHoTen(model.getValueAt(selectedRow, 1).toString());

            Object ngaySinhObj = model.getValueAt(selectedRow, 2);
            if (ngaySinhObj instanceof LocalDate) {
                panel.setNgaySinh((LocalDate) ngaySinhObj);
            } else {
                try {
                    panel.setNgaySinh(LocalDate.parse(ngaySinhObj.toString()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    panel.showError("Lỗi khi chuyển đổi ngày tháng: " + ex.getMessage());
                }
            }

            panel.setGioiTinh(model.getValueAt(selectedRow, 3).toString());

            panel.setSdt(model.getValueAt(selectedRow, 4).toString());

            Object emailObj = model.getValueAt(selectedRow, 5);
            panel.setEmail(emailObj != null ? emailObj.toString() : "");

            Object diaChiObj = model.getValueAt(selectedRow, 6);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");

            panel.setVaiTro(model.getValueAt(selectedRow, 7).toString());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private String validateInput(NhanVien nhanVien) {
        if (nhanVien.getMaNhanVien().isEmpty()) {
            return "Vui lòng nhập mã nhân viên!";
        }
        if (nhanVien.getHoTen().isEmpty()) {
            return "Vui lòng nhập họ tên";
        }
        if (nhanVien.getNgaySinh() == null) {
            return "Vui lòng chọn ngày sinh";
        }
        if (nhanVien.getGioiTinh() == null || nhanVien.getGioiTinh().isEmpty()) {
            return "Vui lòng chọn giới tính!";
        }
        if (nhanVien.getSdt().isEmpty()) {
            return "Vui lòng nhập số điện thoại";
        }
        if (nhanVien.getDiaChi().isEmpty()) {
            return "Vui lòng nhập địa chỉ";
        }
        if (nhanVien.getVaiTro() == null || nhanVien.getVaiTro().isEmpty()) {
            return "Vui lòng chọn vai trò";
        }
        return null;
    }
}
