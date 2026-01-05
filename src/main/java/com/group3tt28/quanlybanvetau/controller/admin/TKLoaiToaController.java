package com.group3tt28.quanlybanvetau.controller.admin;

import com.group3tt28.quanlybanvetau.dao.LoaiToaDAO;
import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.view.admin.TKLoaiToaPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKLoaiToaController {

    private final TKLoaiToaPanel panel;
    private final LoaiToaDAO dao;
    private DefaultTableModel tableModel;

    public TKLoaiToaController(TKLoaiToaPanel panel) {
        this.panel = panel;
        this.dao = new LoaiToaDAO();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<LoaiToa> list = dao.getAll();
            for (LoaiToa loaiToa : list) {
                tableModel.addRow(new Object[]{
                        loaiToa.getId(),
                        loaiToa.getTenLoai(),
                        loaiToa.getHeSoGia()
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
                List<LoaiToa> list = dao.timKiemLoaiToa(tuKhoa);

                for (LoaiToa loaiToa : list) {
                    tableModel.addRow(new Object[]{
                            loaiToa.getId(),
                            loaiToa.getTenLoai(),
                            loaiToa.getHeSoGia()
                    });
                }

                tableModel.fireTableDataChanged();
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

    private class LamMoiListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }
}
