package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LoaiToaDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.ToaTauDAO;
import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.ToaTau;
import com.group3tt28.quanlybanvetau.view.QLToaTauPanel;

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
        // Đã bỏ addLuuListener
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
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
        model.setRowCount(0);

        for (ToaTau tt : listToaTau) {
            String tenTau = getTenTauById(tt.getIdTau());
            String tenLoai = getTenLoaiToaById(tt.getIdLoaiToa());
            model.addRow(new Object[]{ tt.getId(), tt.getMaToa(), tenTau, tenLoai });
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

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ToaTau tt = panel.getToaTauFromForm();

                if (dao.checkTrung(tt.getMaToa(), tt.getIdTau())) {
                    panel.showWarning("Mã toa '" + tt.getMaToa() + "' đã tồn tại trên tàu này!");
                    return;
                }
                // ---------------------------------

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
                // Hiển thị thông báo lỗi thân thiện hơn thay vì crash
                panel.showError("Lỗi hệ thống (có thể do trùng dữ liệu): " + ex.getMessage());
            }
        }
    }

    private class SuaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1 || selectedRow >= listToaTau.size()) {
                    panel.showWarning("Vui lòng chọn toa tàu để sửa!");
                    return;
                }
                ToaTau ttMoi = panel.getToaTauFromForm();
                ToaTau ttCu = listToaTau.get(selectedRow);
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

    // Đã xóa class LuuListener

    private class XoaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1 || selectedRow >= listToaTau.size()) {
                    panel.showWarning("Vui lòng chọn toa tàu để xoá!");
                    return;
                }
                ToaTau tt = listToaTau.get(selectedRow);
                if (panel.showConfirm("Xoá toa " + tt.getMaToa() + "?")) {
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
            panel.resetForm();
            selectedRow = -1;
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1 || selectedRow >= listToaTau.size()) return;

            panel.startEditMode();
            ToaTau tt = listToaTau.get(selectedRow);
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
        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}