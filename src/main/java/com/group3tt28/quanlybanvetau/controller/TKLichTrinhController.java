package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LichTrinhDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.panel.TKLichTrinhPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TKLichTrinhController {

    private final TKLichTrinhPanel panel;
    private final LichTrinhDAO lichTrinhDAO;
    private final TauDAO tauDAO;
    private final TuyenDuongDAO tuyenDuongDAO;
    private final DefaultTableModel tableModel;

    // Cache list
    private List<Tau> listTauCache;
    private List<TuyenDuong> listTuyenCache;

    public TKLichTrinhController(TKLichTrinhPanel panel) {
        this.panel = panel;
        this.lichTrinhDAO = new LichTrinhDAO();
        this.tauDAO = new TauDAO();
        this.tuyenDuongDAO = new TuyenDuongDAO();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        // 1. Load dữ liệu ComboBox TRƯỚC
        loadComboBoxData();

        // 2. Load toàn bộ lịch trình lên bảng
        refresh();

        // 3. Đăng ký sự kiện
        panel.addTimKiemListener(new TimKiemListener());
        panel.addResetFormListener(e -> panel.resetForm());
        panel.addLamMoiListener(e -> refresh());
    }

    private void loadComboBoxData() {
        try {
            // Load Tàu
            listTauCache = tauDAO.getAll();
            panel.getBoxTau().removeAllItems();
            panel.getBoxTau().addItem("Tất cả");
            for (Tau t : listTauCache) {
                panel.getBoxTau().addItem(t);
            }

            // Load Tuyến
            listTuyenCache = tuyenDuongDAO.getAll();
            panel.getBoxTuyenDuong().removeAllItems();
            panel.getBoxTuyenDuong().addItem("Tất cả");
            for (TuyenDuong td : listTuyenCache) {
                panel.getBoxTuyenDuong().addItem(td);
            }
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu Tàu/Tuyến: " + e.getMessage());
        }
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<LichTrinh> list = lichTrinhDAO.getAll();
            updateTable(list);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void updateTable(List<LichTrinh> list) {
        tableModel.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (LichTrinh lt : list) {
            String tenTau = getTenTauById(lt.getIdTau());
            String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong());

            tableModel.addRow(new Object[]{
                    lt.getId(),
                    lt.getMaLichTrinh(),
                    tenTau,
                    tenTuyen,
                    (lt.getNgayDi() != null) ? lt.getNgayDi().format(formatter) : "",
                    (lt.getNgayDen() != null) ? lt.getNgayDen().format(formatter) : "",
                    lt.getTrangThai()
            });
        }
        tableModel.fireTableDataChanged();
    }

    private String getTenTauById(int id) {
        if (listTauCache == null) return String.valueOf(id);
        for (Tau t : listTauCache) {
            if (t.getId() == id) return t.getTenTau();
        }
        return String.valueOf(id);
    }

    private String getTenTuyenById(int id) {
        if (listTuyenCache == null) return String.valueOf(id);
        for (TuyenDuong td : listTuyenCache) {
            if (td.getId() == id) return td.getTenTuyen();
        }
        return String.valueOf(id);
    }

    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String tuKhoa = panel.getTuKhoa();

                // Lấy ID Tàu
                int idTau = 0;
                Object selectedTau = panel.getBoxTau().getSelectedItem();
                if (selectedTau instanceof Tau) {
                    idTau = ((Tau) selectedTau).getId();
                }

                // Lấy ID Tuyến
                int idTuyen = 0;
                Object selectedTuyen = panel.getBoxTuyenDuong().getSelectedItem();
                if (selectedTuyen instanceof TuyenDuong) {
                    idTuyen = ((TuyenDuong) selectedTuyen).getId();
                }

                String trangThai = panel.getTrangThai();

                List<LichTrinh> ketQua = lichTrinhDAO.timKiemLichTrinh(tuKhoa, idTau, idTuyen, trangThai);
                updateTable(ketQua);

                if (ketQua.isEmpty()) {
                    panel.showMessage("Không tìm thấy kết quả phù hợp!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi tìm kiếm: " + ex.getMessage());
            }
        }
    }
}