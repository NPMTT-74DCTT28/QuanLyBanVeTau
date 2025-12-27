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
import java.util.List;

public class QLNhanVienController {

    private final QLNhanVienPanel panel;
    private final NhanVienDAO dao;

    public QLNhanVienController(QLNhanVienPanel panel) {
        this.dao = new NhanVienDAO();

        this.panel = panel;
        panel.addThemNhanVienListener(new ThemNhanVienListener());
        panel.addSuaNhanVienListener(new SuaNhanVienListener());
        panel.addXoaNhanVienListener(new XoaNhanVienListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        loadTableData();
    }

    private void loadTableData() {
        List<NhanVien> list = dao.getAll();
        DefaultTableModel model = (DefaultTableModel) panel.getTable().getModel();
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

                if (dao.insert(nhanVien)) {
                    panel.showMessage("Thêm nhân viên thành công!");
                    panel.resetForm();
                    loadTableData();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi nhập liệu: " + ex.getMessage());
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

        }
    }

    private class TableMouseClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

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
}
