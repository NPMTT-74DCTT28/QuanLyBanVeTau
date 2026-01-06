package com.group3tt28.quanlybanvetau.controller.nghiepvu;

import com.group3tt28.quanlybanvetau.dao.KhachHangDAO;
import com.group3tt28.quanlybanvetau.model.KhachHang;
import com.group3tt28.quanlybanvetau.view.nghiepvu.TKKhachHangPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKKhachHangController {
    private final TKKhachHangPanel panel;
    private final KhachHangDAO dao;
    private final DefaultTableModel tableModel;

    public TKKhachHangController(TKKhachHangPanel panel) {
        this.panel = panel;
        this.dao = new KhachHangDAO();

        panel.TimKiemListener(new TimKiemListener());
        panel.ResetFormListener(new ResetListener());
        panel.LamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<KhachHang> list = dao.getAll();
            for (KhachHang khachHang : list) {
                tableModel.addRow(new Object[]{
                        khachHang.getId(),
                        khachHang.getCccd(),
                        khachHang.getHoTen(),
                        khachHang.getNgaySinh(),
                        khachHang.getGioiTinh(),
                        khachHang.getSdt(),
                        khachHang.getDiaChi()
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

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                tableModel.setRowCount(0);
                String tuKhoa = panel.getTuKhoa();
                List<KhachHang> list = dao.timKiemKH(tuKhoa);

                if (tuKhoa.isEmpty()) {
                    refresh();
                    return;
                }

                for (KhachHang khachHang : list) {
                    tableModel.addRow(new Object[]{
                            khachHang.getId(),
                            khachHang.getCccd(),
                            khachHang.getHoTen(),
                            khachHang.getNgaySinh(),
                            khachHang.getGioiTinh(),
                            khachHang.getSdt(),
                            khachHang.getDiaChi()
                    });
                }
                tableModel.fireTableDataChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác đinh: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class LamMoiListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }
}
