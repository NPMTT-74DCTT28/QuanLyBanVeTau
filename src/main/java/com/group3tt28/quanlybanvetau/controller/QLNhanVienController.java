package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.view.panel.QLNhanVienPanel;

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
    private final DefaultTableModel tableModel;
    private int selectedRow;

    public QLNhanVienController(QLNhanVienPanel panel) {
        this.dao = NhanVienDAO.getInstance();

        this.panel = panel;
        panel.addThemNhanVienListener(new ThemNhanVienListener());
        panel.addSuaNhanVienListener(new SuaNhanVienListener());
        panel.addXoaNhanVienListener(new XoaNhanVienListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addRefreshListener(new RefreshListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        if (this.panel.getTable() != null) {
            tableModel = (DefaultTableModel) this.panel.getTable().getModel();
        } else {
            tableModel = new DefaultTableModel();
        }

        refresh();
    }

    private void refresh() {
        try {
            List<NhanVien> list = dao.getAll();
            tableModel.setRowCount(0);

            for (NhanVien nhanVien : list) {
                tableModel.addRow(new Object[]{
                        nhanVien.getId(),
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

            tableModel.fireTableDataChanged();
        } catch (RuntimeException e) {
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class ThemNhanVienListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                NhanVien nhanVien = panel.getNhanVienFromForm();
                nhanVien.setId(0);

                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                if (dao.checkTrung(nhanVien.getMaNhanVien(), nhanVien.getSdt(), nhanVien.getId())) {
                    panel.showWarning("Mã nhân viên hoặc SĐT đã tồn tại!");
                    return;
                }

                String matKhauTho = nhanVien.getMatKhau();
                String matKhauBam = BamMatKhau.bamMatKhau(matKhauTho);
                if (matKhauBam != null) {
                    nhanVien.setMatKhau(matKhauBam);
                }

                if (dao.insert(nhanVien)) {
                    panel.showMessage("Thêm nhân viên thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi khi check mật khẩu: " + ex.getMessage());
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
                if (selectedRow == -1) {
                    panel.showWarning("Bạn chưa chọn nhân viên nào để sửa thông tin!");
                    return;
                }

                NhanVien nhanVien = panel.getNhanVienFromForm();
                nhanVien.setId(Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));

                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                if (dao.checkTrung(nhanVien.getMaNhanVien(), nhanVien.getSdt(), nhanVien.getId())) {
                    panel.showWarning("SĐT đã được sử dụng!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn cập nhật thông tin nhân viên " + nhanVien.getMaNhanVien() + "?")) {
                    if (dao.update(nhanVien)) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại! Vui lòng kiểm tra lại!");
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

    private class XoaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showError("Bạn chưa chọn nhân viên nào để xoá!");
                }

                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String maNhanVien = tableModel.getValueAt(selectedRow, 1).toString();

                if (tableModel.getValueAt(selectedRow, 0).toString().trim().isEmpty()) {
                    panel.showWarning("Mã nhân viên không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá nhân viên " + maNhanVien + "?")) {
                    if (dao.delete(id)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xoá thất bại! Vui lòng kiểm tra lại!");
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

    private class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            if (panel.getTable() != null) {
                selectedRow = panel.getTable().getSelectedRow();
            }

            if (selectedRow == -1) {
                return;
            }

            panel.setMaNhanVien(tableModel.getValueAt(selectedRow, 1).toString());
            panel.setHoTen(tableModel.getValueAt(selectedRow, 2).toString());

            Object ngaySinhObj = tableModel.getValueAt(selectedRow, 3);
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

            panel.setGioiTinh(tableModel.getValueAt(selectedRow, 4).toString());

            panel.setSdt(tableModel.getValueAt(selectedRow, 5).toString());

            Object emailObj = tableModel.getValueAt(selectedRow, 6);
            panel.setEmail(emailObj != null ? emailObj.toString() : "");

            Object diaChiObj = tableModel.getValueAt(selectedRow, 7);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");

            panel.setVaiTro(tableModel.getValueAt(selectedRow, 8).toString());
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
