package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.KhachHangDAO;
import com.group3tt28.quanlybanvetau.model.KhachHang;
import com.group3tt28.quanlybanvetau.view.panel.QLKhachHangPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class QLKhachHangController {

    private final QLKhachHangPanel panel;
    private final KhachHangDAO dao;
    private final DefaultTableModel model;
    private int selectedRow;

    public QLKhachHangController(QLKhachHangPanel panel) {
        this.dao = new KhachHangDAO();

        this.panel = panel;
        panel.addThemKhachHangListener(new ThemKhachHangListener());
        panel.addSuaKhachHangListener(new SuaKhachHangListener());
        panel.addXoaKhachHangListener(new XoaKhachHangListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        panel.resetForm();

        List<KhachHang> list = dao.getAll();
        model.setRowCount(0);

        for (KhachHang khachHang : list) {
            model.addRow(new Object[]{
                    khachHang.getCccd(),
                    khachHang.getHoTen(),
                    khachHang.getNgaySinh(),
                    khachHang.getGioiTinh(),
                    khachHang.getSdt(),
                    khachHang.getDiaChi()
            });
        }

        model.fireTableDataChanged();
    }

    private String validateInput(KhachHang khachHang) {
        if (khachHang.getCccd().isEmpty()) {
            return "Vui lòng nhập CCCD!";
        }
        if (khachHang.getHoTen().isEmpty()) {
            return "Vui lòng nhập họ tên";
        }
        if (khachHang.getNgaySinh() == null) {
            return "Vui lòng chọn ngày sinh";
        }
        if (khachHang.getGioiTinh() == null || khachHang.getGioiTinh().isEmpty()) {
            return "Vui lòng chọn giới tính";
        }
        if (khachHang.getSdt().isEmpty()) {
            return "Vui lòng nhập số điện thoại";
        }
        if (khachHang.getDiaChi().isEmpty()) {
            return "Vui lòng nhập địa chỉ";
        }
        return null;
    }

    private class ThemKhachHangListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                KhachHang khachHang = panel.getKhachHangFromForm();

                if (validateInput(khachHang) != null) {
                    panel.showWarning(validateInput(khachHang));
                    return;
                }

                if (dao.checkTrung(khachHang.getCccd())) {
                    panel.showWarning("CCCD " + khachHang.getCccd() + " đã tồn tại!");
                    return;
                }

                if (dao.insert(khachHang)) {
                    panel.showMessage("Thêm khách hàng thành công");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại thông tin!");
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

    private class SuaKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                KhachHang khachHang = panel.getKhachHangFromForm();

                if (validateInput(khachHang) != null) {
                    panel.showWarning(validateInput(khachHang));
                    return;
                }

                if (panel.showConfirm("Bạn có muốn cập nhật thông tin khách hàng " + khachHang.getCccd() + "?")) {
                    if (dao.update(khachHang)) {
                        panel.showMessage("Cập nhật thành công");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại! Vui lòng kiểm tra lại thông tin");
                    }
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class XoaKhachHangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String cccd = model.getValueAt(selectedRow, 0).toString();

                if (cccd.isEmpty()) {
                    panel.showWarning("CCCD không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn chắc chắn muốn xóa khách hàng " + cccd + "?")) {
                    if (dao.delete(cccd)) {
                        panel.showMessage("Xóa thành công");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại! Vui lòng kiểm tra lại thông tin");
                    }
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

            panel.setCCCD(model.getValueAt(selectedRow, 0).toString());
            panel.setHoTen(model.getValueAt(selectedRow, 1).toString());

            Object ngaySinhObj = model.getValueAt(selectedRow, 2);
            if (ngaySinhObj instanceof LocalDate) {
                panel.setNgaySinh((LocalDate) ngaySinhObj);
            } else {
                try {
                    panel.setNgaySinh(LocalDate.parse(ngaySinhObj.toString()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    panel.showError("Lỗi chuyển đổi ngày tháng: " + ex.getMessage());
                }
            }

            panel.setGioiTinh(model.getValueAt(selectedRow, 3).toString());

            panel.setSdt(model.getValueAt(selectedRow, 4).toString());

            Object diaChiObj = model.getValueAt(selectedRow, 5);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }
}
