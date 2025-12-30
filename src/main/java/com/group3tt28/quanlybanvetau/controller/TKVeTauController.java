package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.KhachHangDAO;
import com.group3tt28.quanlybanvetau.dao.VeTauDAO;
import com.group3tt28.quanlybanvetau.model.KhachHang;
import com.group3tt28.quanlybanvetau.model.VeTau;
import com.group3tt28.quanlybanvetau.view.panel.TKKhachHangPanel;
import com.group3tt28.quanlybanvetau.view.panel.TKVeTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKVeTauController {
    private final TKVeTauPanel panel;
    private final VeTauDAO dao;
    private final DefaultTableModel tableModel;

    public TKVeTauController(TKVeTauPanel panel){
        this.panel=panel;
        this.dao=new VeTauDAO();

        panel.TimKiemListener(new TKVeTauController.TimKiemListener());
        panel.ResetFormListener(new TKVeTauController.ResetListener());
        panel.LamMoiListener(new TKVeTauController.LamMoiListener());

        this.tableModel=(DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh(){
        try {
            tableModel.setRowCount(0);
            List<VeTau> list = dao.getAll();
            for (VeTau veTau : list){
                tableModel.addRow(new Object[]{
                        veTau.getId(),
                        veTau.getMaVe(),
                        veTau.getIdKhachHang(),
                        veTau.getIdLichTrinh(),
                        veTau.getIdGhe(),
                        veTau.getIdNhanVien(),
                        veTau.getNgayDat(),
                        veTau.getGiaVe(),
                        veTau.getTrangThaiVe()

                });
            }

            tableModel.fireTableDataChanged();
        }catch (RuntimeException e){
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            try {
                tableModel.setRowCount(0);
                String tuKhoa = panel.getTuKhoa();
                List<VeTau> list = dao.timkiem(tuKhoa);

                if (tuKhoa.isEmpty()){
                    refresh();
                    return;
                }

                for (VeTau veTau : list){
                    tableModel.addRow(new Object[]{
                            veTau.getId(),
                            veTau.getMaVe(),
                            veTau.getIdKhachHang(),
                            veTau.getIdLichTrinh(),
                            veTau.getIdGhe(),
                            veTau.getIdNhanVien(),
                            veTau.getNgayDat(),
                            veTau.getGiaVe(),
                            veTau.getTrangThaiVe()
                    });
                }
                tableModel.fireTableDataChanged();
            }catch (Exception ex){
                ex.printStackTrace();
                panel.showError("Lỗi không xác đinh: " + ex.getMessage());
            }
        }
    }
    private class ResetListener implements ActionListener{
        public void actionPerformed(ActionEvent e){ panel.resetForm();}
    }

    private class LamMoiListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {refresh();}
    }
}
