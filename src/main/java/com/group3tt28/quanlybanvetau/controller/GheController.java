package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.GheDAO;
import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.dao.ToaTauDAO;
import com.group3tt28.quanlybanvetau.model.Ghe;
import com.group3tt28.quanlybanvetau.model.ToaTau;
import com.group3tt28.quanlybanvetau.view.panel.GhePanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GheController {
    private final GhePanel panel;
    private final GheDAO dao;
    private final DefaultTableModel model;
    private int selectedRow;


    public GheController(GhePanel panel) {
        this.panel = panel;
        this.dao = new GheDAO();
        ToaTauDAO toaTauDAO = new ToaTauDAO();
        NhanVienDAO nvDAO = new NhanVienDAO();

        this.panel.addButtonThemActionListener(new GheController.ThemGheListener());
        this.panel.addButtonSuaActionListener(new GheController.SuaGheListener());
        this.panel.addButtonXoaActionListener(new GheController.XoaGheListener());
        this.panel.addButtonResetActionListener(new GheController.ResetFormListener());
        this.panel.addTableMouseClickListener(new GheController.TableMouseClickListener());

        List<ToaTau> dsToa = toaTauDAO.getAll();
        this.panel.setComboBoxToaTauData(dsToa);


        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        panel.resetForm();
        List<Ghe> list = dao.getAll();
        model.setRowCount(0);

        for (Ghe ghe : list) {
            model.addRow(new Object[]{
                    ghe.getId(),
                    ghe.getSoGhe(),
                    ghe.getIdToaTau()
            });
        }
        model.fireTableDataChanged();
    }

    private String validateInput(Ghe ghe) {
        if (ghe.getSoGhe().isEmpty()) {
            return "Số ghế không được để trống!";
        }
        if (ghe.getIdToaTau() == 0) {
            return "ID toa tàu không được để trống!";
        }
        return null;
    }

    private class ThemGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Ghe ghe = panel.getGheFromForm();
                ghe.setId(0);

                if (validateInput(ghe) != null) {
                    panel.showWarning(validateInput(ghe));
                    return;
                }

                if (dao.checkTrung(ghe.getSoGhe(), ghe.getIdToaTau(), ghe.getId())) {
                    panel.showWarning("Số ghế " + ghe.getSoGhe() + " đã tồn tại trong toa này!");
                    return;
                }
                if (dao.insert(ghe)) {
                    panel.showMessage("Thêm ghế thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại!");
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class SuaGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Ghe ghe = panel.getGheFromForm();
                if (model.getValueAt(selectedRow, 0).toString().isEmpty()) {
                    panel.showWarning("ID ghế không hợp lệ");
                    return;
                }
                ghe.setId(Integer.parseInt(model.getValueAt(selectedRow, 0).toString()));

                if (validateInput(ghe) != null) {
                    panel.showWarning(validateInput(ghe));
                    return;
                }

                if (dao.checkTrung(ghe.getSoGhe(), ghe.getIdToaTau(), ghe.getId())) {
                    panel.showWarning("Số ghế " + ghe.getSoGhe() + " đã tồn tại trong toa!");
                    return;
                }

                if (panel.showConfirm("Bạn có chắc muốn sửa ghế: " + ghe.getSoGhe() + "?")) {
                    if (dao.update(ghe)) {
                        panel.showMessage("Sửa thành công!");
                        refresh();
                    } else {
                        panel.showError("Sửa thất bại!");
                    }
                }
            } catch (RuntimeException ex) {
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class XoaGheListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String SoGhe = model.getValueAt(selectedRow, 1).toString();

                if (SoGhe.isEmpty()) {
                    panel.showWarning("Số ghế không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá tàu " + SoGhe + "?")) {
                    if (dao.delete(SoGhe)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại! Vui lòng kiểm tra lại!");
                    }
                }
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
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            panel.setSoGhe(model.getValueAt(selectedRow, 1).toString());
            panel.setIDToaTau(Integer.parseInt(model.getValueAt(selectedRow, 2).toString()));

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
