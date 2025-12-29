package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LoaiToaDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.ToaTauDAO;
import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.ToaTau;
import com.group3tt28.quanlybanvetau.view.panel.QLToaTauPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class QLToaTauController {

    private final QLToaTauPanel panel;
    private final ToaTauDAO dao;
    private final TauDAO tauDAO;
    private final LoaiToaDAO loaiToaDAO;
    private final DefaultTableModel model;

    private List<ToaTau> listToaTau;
    private int selectedRow = -1;

    public QLToaTauController(QLToaTauPanel panel) {
        this.panel = panel;
        this.dao = new ToaTauDAO();
        this.tauDAO = new TauDAO();
        this.loaiToaDAO = new LoaiToaDAO();
        this.listToaTau = new ArrayList<>();

        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());

        // Listener tìm kiếm
        panel.addTimKiemListener(new TimKiemListener());

        panel.getTable().addMouseListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        loadComboBoxData();
        refresh();
    }

    private void loadComboBoxData() {
        try {
            List<Tau> listTau = tauDAO.getAll();
            panel.getBoxTau().removeAllItems();
            for (Tau t : listTau) panel.getBoxTau().addItem(t);

            List<LoaiToa> listLoai = loaiToaDAO.getAll();
            panel.getBoxLoaiToa().removeAllItems();
            for (LoaiToa lt : listLoai) panel.getBoxLoaiToa().addItem(lt);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu ComboBox: " + e.getMessage());
        }
    }

    private void refresh() {
        panel.resetForm();
        selectedRow = -1;
        listToaTau = dao.getAll();
        updateTable(listToaTau); // Dùng hàm dùng chung
    }

    // Hàm cập nhật bảng chung cho refresh và tìm kiếm
    private void updateTable(List<ToaTau> listHienThi) {
        model.setRowCount(0);
        for (ToaTau tt : listHienThi) {
            String tenTau = getTenTauById(tt.getIdTau());
            String tenLoai = getTenLoaiToaById(tt.getIdLoaiToa());
            model.addRow(new Object[]{tt.getId(), tt.getMaToa(), tenTau, tenLoai});
        }
        model.fireTableDataChanged();
    }

    private String getTenTauById(int id) {
        JComboBox<Tau> box = panel.getBoxTau();
        for (int i = 0; i < box.getItemCount(); i++) {
            Tau t = box.getItemAt(i);
            if (t.getId() == id) return t.toString();
        }
        return "ID: " + id;
    }

    private String getTenLoaiToaById(int id) {
        JComboBox<LoaiToa> box = panel.getBoxLoaiToa();
        for (int i = 0; i < box.getItemCount(); i++) {
            LoaiToa lt = box.getItemAt(i);
            if (lt.getId() == id) return lt.toString();
        }
        return "ID: " + id;
    }

    // --- LISTENER TÌM KIẾM ---
    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tuKhoa = panel.getTuKhoaTimKiem().toLowerCase();

            if (tuKhoa.isEmpty()) {
                refresh();
                return;
            }

            List<ToaTau> ketQua = new ArrayList<>();
            for (ToaTau tt : listToaTau) {
                // Lấy thông tin hiển thị để tìm kiếm (Mã toa, Tên tàu, Tên loại toa)
                String maToa = tt.getMaToa().toLowerCase();
                String tenTau = getTenTauById(tt.getIdTau()).toLowerCase();
                String tenLoai = getTenLoaiToaById(tt.getIdLoaiToa()).toLowerCase();

                if (maToa.contains(tuKhoa) || tenTau.contains(tuKhoa) || tenLoai.contains(tuKhoa)) {
                    ketQua.add(tt);
                }
            }
            updateTable(ketQua);

            if (ketQua.isEmpty()) {
                panel.showWarning("Không tìm thấy toa tàu nào khớp với từ khóa: " + tuKhoa);
            }
        }
    }

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ToaTau tt = panel.getToaTauFromForm();

                if (dao.checkTrung(tt.getMaToa(), tt.getIdTau())) {
                    panel.showWarning("Mã toa '" + tt.getMaToa() + "' đã tồn tại trên tàu này!");
                    return;
                }

                if (dao.insert(tt)) {
                    panel.showMessage("Thêm toa tàu thành công!");
                    refresh();
                } else {
                    panel.showError("Thêm thất bại!");
                }
            } catch (IllegalArgumentException ex) {
                panel.showWarning(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class SuaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn toa tàu để sửa!");
                    return;
                }

                // Lấy ID từ bảng (cột 0) để tìm đúng đối tượng gốc
                int idToa = (int) panel.getTable().getValueAt(selectedRow, 0);

                ToaTau ttCu = null;
                for (ToaTau item : listToaTau) {
                    if (item.getId() == idToa) {
                        ttCu = item;
                        break;
                    }
                }

                if (ttCu == null) return;

                ToaTau ttMoi = panel.getToaTauFromForm();
                ttMoi.setId(ttCu.getId());

                if (panel.showConfirm("Cập nhật toa " + ttMoi.getMaToa() + "?")) {
                    if (dao.update(ttMoi)) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!");
                    }
                }
            } catch (IllegalArgumentException ex) {
                panel.showWarning(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class XoaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn toa tàu để xoá!");
                    return;
                }

                // Lấy ID từ bảng (cột 0)
                int idToa = (int) panel.getTable().getValueAt(selectedRow, 0);

                // Tìm object trong list gốc để lấy thông tin hiển thị (VD: Mã toa)
                ToaTau tt = null;
                for (ToaTau item : listToaTau) {
                    if (item.getId() == idToa) {
                        tt = item;
                        break;
                    }
                }

                if (tt != null && panel.showConfirm("Xoá toa " + tt.getMaToa() + "?")) {
                    if (dao.delete(tt.getId())) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xoá thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            panel.startEditMode();

            // Lấy ID từ cột 0 của bảng
            int idToa = (int) panel.getTable().getValueAt(selectedRow, 0);

            // Tìm đối tượng trong list gốc
            ToaTau tt = null;
            for (ToaTau item : listToaTau) {
                if (item.getId() == idToa) {
                    tt = item;
                    break;
                }
            }

            if (tt == null) return;

            panel.setMaToa(tt.getMaToa());

            JComboBox<Tau> boxTau = panel.getBoxTau();
            for (int i = 0; i < boxTau.getItemCount(); i++) {
                if (boxTau.getItemAt(i).getId() == tt.getIdTau()) {
                    boxTau.setSelectedIndex(i);
                    break;
                }
            }

            JComboBox<LoaiToa> boxLoai = panel.getBoxLoaiToa();
            for (int i = 0; i < boxLoai.getItemCount(); i++) {
                if (boxLoai.getItemAt(i).getId() == tt.getIdLoaiToa()) {
                    boxLoai.setSelectedIndex(i);
                    break;
                }
            }
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