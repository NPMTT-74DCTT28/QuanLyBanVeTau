package com.group3tt28.quanlybanvetau.controller.nghiepvu;

import com.group3tt28.quanlybanvetau.dao.GaTauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.nghiepvu.TKTuyenDuongPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class TKTuyenDuongController {
    private final TKTuyenDuongPanel panel;
    private final TuyenDuongDAO dao;
    private final DefaultTableModel model;
    private final HashMap<Integer, String> map = new HashMap<>();

    public TKTuyenDuongController(TKTuyenDuongPanel panel) {
        this.panel = panel;
        this.dao = new TuyenDuongDAO();
        panel.TimKiemListener(new TimkiemListener());
        panel.ResetListener(new ResetListener());
        this.model = (DefaultTableModel) panel.getTable().getModel();
        loadGa();
        refreshTable();
    }
    private void loadGa() {
        GaTauDAO dao = new GaTauDAO();
        map.clear();
        for (GaTau gaTau: dao.getAll()){
            map.put(
                    gaTau.getId(),
                    gaTau.getMaGa()+" - "+gaTau.getTenGa()
            );
        }
    }
    private void refreshTable() {
        try {
            model.setRowCount(0);
            List<TuyenDuong> list = dao.getAll();

            for (TuyenDuong tuyenDuong : list) {
                String gaDi = map.getOrDefault(
                        tuyenDuong.getIdGaDi(),
                        String.valueOf(tuyenDuong.getIdGaDi())
                );
                String gaDen = map.getOrDefault(
                        tuyenDuong.getIdGaDen(),
                        String.valueOf(tuyenDuong.getIdGaDen())
                );
                model.addRow(new Object[]{
                        tuyenDuong.getMaTuyen(),
                        tuyenDuong.getTenTuyen(),
                        gaDi,
                        gaDen,
                        tuyenDuong.getKhoangCachKm(),
                        tuyenDuong.getGiaCoBan(),

                });
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }
    private class TimkiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.setRowCount(0);
                String timkiem = panel.getTimkiem();
                if (timkiem.isEmpty()) {
                    refreshTable();
                    return;
                }

                List<TuyenDuong> list = dao.timkiem(timkiem);

                for (TuyenDuong td : list) {
                    String gaDi = map.getOrDefault(
                            td.getIdGaDi(),
                            String.valueOf(td.getIdGaDi())
                    );
                    String gaDen = map.getOrDefault(
                            td.getIdGaDen(),
                            String.valueOf(td.getIdGaDen())
                    );

                    model.addRow(new Object[]{
                            td.getMaTuyen(),
                            td.getTenTuyen(),
                            gaDi,
                            gaDen,
                            td.getKhoangCachKm(),
                            td.getGiaCoBan()
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi tìm kiếm: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshTable();
        }
    }
}
