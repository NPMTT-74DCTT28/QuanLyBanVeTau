package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LoaiToaDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.ToaTauDAO;
import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.ToaTau;
import com.group3tt28.quanlybanvetau.view.panel.TKToaTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TKToaTauController {

    private final TKToaTauPanel panel;
    private final ToaTauDAO dao;
    private final TauDAO tauDAO;
    private final LoaiToaDAO loaiToaDAO;
    private final DefaultTableModel tableModel;

    // Cache list để hiển thị tên
    private List<Tau> listTauCache;
    private List<LoaiToa> listLoaiToaCache;

    public TKToaTauController(TKToaTauPanel panel) {
        this.panel = panel;
        this.dao = new ToaTauDAO();
        this.tauDAO = new TauDAO();
        this.loaiToaDAO = new LoaiToaDAO();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        // 1. Load dữ liệu ComboBox
        loadComboBoxData();

        // 2. Load toàn bộ dữ liệu lên bảng ban đầu
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

            // Load Loại Toa
            listLoaiToaCache = loaiToaDAO.getAll();
            panel.getBoxLoaiToa().removeAllItems();
            panel.getBoxLoaiToa().addItem("Tất cả");
            for (LoaiToa lt : listLoaiToaCache) {
                panel.getBoxLoaiToa().addItem(lt);
            }

        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu danh mục: " + e.getMessage());
        }
    }

    private void refresh() {
        try {
            tableModel.setRowCount(0);
            List<ToaTau> list = dao.getAll();
            updateTable(list);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private void updateTable(List<ToaTau> list) {
        tableModel.setRowCount(0);
        for (ToaTau tt : list) {
            String tenTau = getTenTauById(tt.getIdTau());
            String tenLoai = getTenLoaiToaById(tt.getIdLoaiToa());

            tableModel.addRow(new Object[]{
                    tt.getId(),
                    tt.getMaToa(),
                    tenTau,
                    tenLoai
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

    private String getTenLoaiToaById(int id) {
        if (listLoaiToaCache == null) return String.valueOf(id);
        for (LoaiToa lt : listLoaiToaCache) {
            if (lt.getId() == id) return lt.getTenLoai();
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

                // Lấy ID Loại Toa
                int idLoaiToa = 0;
                Object selectedLoai = panel.getBoxLoaiToa().getSelectedItem();
                if (selectedLoai instanceof LoaiToa) {
                    idLoaiToa = ((LoaiToa) selectedLoai).getId();
                }

                List<ToaTau> ketQua = dao.timKiemToaTau(tuKhoa, idTau, idLoaiToa);
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