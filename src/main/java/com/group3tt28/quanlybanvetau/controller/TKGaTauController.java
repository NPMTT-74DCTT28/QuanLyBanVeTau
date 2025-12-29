package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.GaTauDAO;
import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.view.panel.TKGaTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKGaTauController {
    private final TKGaTauPanel panel;
    private final GaTauDAO dao;
    private final DefaultTableModel model;

    public TKGaTauController(TKGaTauPanel panel) {
        this.panel = panel;
        this.dao = new GaTauDAO();
        panel.TimKiemListener(new TimkiemListener());
        panel.ResetListener(new ResetListener());
        this.model = (DefaultTableModel) panel.getTable().getModel();
        refreshTable();
    }
    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<GaTau> List = dao.getAll();
            for (GaTau gaTau : List) {
                model.addRow(new Object[]{
                        gaTau.getMaGa(),
                        gaTau.getTenGa(),
                        gaTau.getDiaChi(),
                        gaTau.getThanhPho()
                });
            }
            model.fireTableDataChanged();
        }catch(Exception ex){
            ex.printStackTrace();
            panel.showError("Lỗi không xác định: "+ ex.getMessage());
        }
    }
    public class TimkiemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {

                model.setRowCount(0);
                String timKiem = panel.getTimkiem();
                List<GaTau> List = dao.timkiem(timKiem);

                if (timKiem.isEmpty()){
                    refreshTable();
                    return;
                }

                for (GaTau gaTau : List) {
                    model.addRow(new Object[]{
                       gaTau.getMaGa(),
                       gaTau.getTenGa(),
                       gaTau.getDiaChi(),
                       gaTau.getThanhPho()
                    });
                }
                model.fireTableDataChanged();
            }catch(Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: "+ ex.getMessage());
            }
        }
    }
    public class ResetListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }
}
