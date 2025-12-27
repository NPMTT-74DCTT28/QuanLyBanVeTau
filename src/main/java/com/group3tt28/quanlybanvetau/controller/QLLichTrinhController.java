package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LichTrinhDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.QLLichTrinhPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QLLichTrinhController {

    private final QLLichTrinhPanel panel;
    private final LichTrinhDAO dao;
    private final TauDAO tauDAO;
    private final TuyenDuongDAO tuyenDuongDAO;
    private final DefaultTableModel model;

    private List<LichTrinh> listLichTrinh;
    private int selectedRow = -1;

    public QLLichTrinhController(QLLichTrinhPanel panel) {
        this.panel = panel;
        this.dao = new LichTrinhDAO();
        this.tauDAO = new TauDAO();
        this.tuyenDuongDAO = new TuyenDuongDAO();
        this.listLichTrinh = new ArrayList<>();

        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        // Đã bỏ addLuuListener
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
        panel.getTable().addMouseListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        loadDataToComboBoxes();
        refresh();
    }

    private void loadDataToComboBoxes() {
        try {
            List<Tau> listTau = tauDAO.getAll();
            panel.getBoxTau().removeAllItems();
            for (Tau t : listTau) panel.getBoxTau().addItem(t);

            List<TuyenDuong> listTuyen = tuyenDuongDAO.getAll();
            panel.getBoxTuyenDuong().removeAllItems();
            for (TuyenDuong td : listTuyen) panel.getBoxTuyenDuong().addItem(td);
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu Tàu/Tuyến đường: " + e.getMessage());
        }
    }

    private void refresh() {
        panel.resetForm();
        selectedRow = -1;
        listLichTrinh = dao.getAll();
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (LichTrinh lt : listLichTrinh) {
            String tenTau = getTenTauById(lt.getIdTau());
            String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong());

            model.addRow(new Object[]{
                    lt.getMaLichTrinh(), tenTau, tenTuyen,
                    lt.getNgayDi().format(formatter),
                    lt.getNgayDen().format(formatter),
                    lt.getTrangThai()
            });
        }
        model.fireTableDataChanged();
    }

    private String getTenTauById(int id) {
        for (int i = 0; i < panel.getBoxTau().getItemCount(); i++) {
            Tau t = panel.getBoxTau().getItemAt(i);
            if (t.getId() == id) return t.toString();
        }
        return String.valueOf(id);
    }

    private String getTenTuyenById(int id) {
        for (int i = 0; i < panel.getBoxTuyenDuong().getItemCount(); i++) {
            TuyenDuong td = panel.getBoxTuyenDuong().getItemAt(i);
            if (td.getId() == id) return td.toString();
        }
        return String.valueOf(id);
    }

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LichTrinh lt = panel.getLichTrinhFromForm();
                if (dao.checktrung(lt.getMaLichTrinh())) {
                    panel.showWarning("Mã lịch trình đã tồn tại!");
                    return;
                }
                if (dao.insert(lt)) {
                    panel.showMessage("Thêm lịch trình thành công!");
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
                if (selectedRow == -1 || selectedRow >= listLichTrinh.size()) {
                    panel.showWarning("Vui lòng chọn lịch trình để sửa!");
                    return;
                }

                LichTrinh ltMoi = panel.getLichTrinhFromForm();
                LichTrinh ltCu = listLichTrinh.get(selectedRow);
                ltMoi.setId(ltCu.getId());

                if (panel.showConfirm("Cập nhật lịch trình " + ltMoi.getMaLichTrinh() + "?")) {
                    if (dao.update(ltMoi)) {
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
                if (selectedRow == -1 || selectedRow >= listLichTrinh.size()) {
                    panel.showWarning("Vui lòng chọn lịch trình để xoá!");
                    return;
                }
                LichTrinh lt = listLichTrinh.get(selectedRow);
                if (panel.showConfirm("Xoá lịch trình " + lt.getMaLichTrinh() + "?")) {
                    if (dao.delete(lt.getId())) {
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
            if (selectedRow == -1 || selectedRow >= listLichTrinh.size()) return;

            panel.startEditMode();
            LichTrinh lt = listLichTrinh.get(selectedRow);

            panel.setMaLichTrinh(lt.getMaLichTrinh());
            panel.setNgayDi(lt.getNgayDi());
            panel.setNgayDen(lt.getNgayDen());
            panel.setTrangThai(lt.getTrangThai());

            JComboBox<Tau> boxTau = panel.getBoxTau();
            for (int i = 0; i < boxTau.getItemCount(); i++) {
                if (boxTau.getItemAt(i).getId() == lt.getIdTau()) {
                    boxTau.setSelectedIndex(i);
                    break;
                }
            }

            JComboBox<TuyenDuong> boxTuyen = panel.getBoxTuyenDuong();
            for (int i = 0; i < boxTuyen.getItemCount(); i++) {
                if (boxTuyen.getItemAt(i).getId() == lt.getIdTuyenDuong()) {
                    boxTuyen.setSelectedIndex(i);
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