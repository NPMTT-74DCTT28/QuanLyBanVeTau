package com.group3tt28.quanlybanvetau.controller.admin;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.view.admin.TKNhanVienPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKNhanVienController {

    private final TKNhanVienPanel panel;
    private final NhanVienDAO dao;
    private final DefaultTableModel tableModel;

    public TKNhanVienController(TKNhanVienPanel panel) {
        this.panel = panel;
        this.dao = NhanVienDAO.getInstance();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<NhanVien> list = dao.getAll();
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
            panel.showError("Có lỗi xảy ra khi tải dữ liệu nhân viên: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tableModel.setRowCount(0);
                String tuKhoa = panel.getTuKhoa();
                String gioiTinh = panel.getGioiTinh();
                String vaiTro = panel.getVaiTro();
                List<NhanVien> list = dao.timKiemNhanVien(tuKhoa, gioiTinh, vaiTro);

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
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Có lỗi xảy ra khi tải dữ liệu nhân viên: " + ex.getMessage());
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

    private class LamMoiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }
}
