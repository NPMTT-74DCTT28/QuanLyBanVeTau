package com.group3tt28.quanlybanvetau.controller.admin;

import com.group3tt28.quanlybanvetau.dao.GheDAO;
import com.group3tt28.quanlybanvetau.model.Ghe;
import com.group3tt28.quanlybanvetau.view.admin.TKGhePanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKGheController {

    private final TKGhePanel panel;
    private final GheDAO dao;
    private DefaultTableModel tableModel;

    public TKGheController(TKGhePanel panel) {
        this.panel = panel;
        this.dao = new GheDAO();

        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addLamMoiListener(new LamMoiListener());

        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<Ghe> list = dao.getAll();
            for (Ghe ghe : list) {
                tableModel.addRow(new Object[]{
                        ghe.getId(),
                        ghe.getSoGhe(),
                        ghe.getIdToaTau()
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
                List<Ghe> list = dao.timKiemGhe(tuKhoa);

                for (Ghe ghe : list) {
                    tableModel.addRow(new Object[]{
                            ghe.getId(),
                            ghe.getSoGhe(),
                            ghe.getIdToaTau()
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
