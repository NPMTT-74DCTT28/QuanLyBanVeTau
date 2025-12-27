package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LoaiToaDAO;
import com.group3tt28.quanlybanvetau.model.LoaiToa;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.view.LoaiToaPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.util.List;

public class LoaiToaController {

    private final LoaiToaPanel panel;
    private final LoaiToaDAO dao;
    private final DefaultTableModel model;
    private int selectedRow;

    public LoaiToaController(LoaiToaPanel panel) {
        this.dao = new LoaiToaDAO();

        this.panel = panel;
        panel.setButtonThemActionListener(new ThemLoaiToaListener());
        panel.setButtonSuaActionListener(new SuaLoaiToaListener());
        panel.setButtonXoaActionListener(new XoaLoaiToaListener());
        panel.setButtonResetActionListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();

        refresh();
    }

    private void refresh() {
        panel.resetForm();

        List<LoaiToa> list = dao.getAll();
        model.setRowCount(0);

        for (LoaiToa loaiToa : list) {
            model.addRow(new Object[]{
                    loaiToa.getId(),
                    loaiToa.getTenLoai(),
                    loaiToa.getHeSoGia()
            });
        }
        model.fireTableDataChanged();
    }

    private String validateInput(LoaiToa loaiToa) {
        if (loaiToa.getTenLoai().isEmpty()) {
            return "Tên loại toa không được để trống!";
        }
        if (loaiToa.getHeSoGia() <= 0 || loaiToa.getHeSoGia() >= 2) {
            return "Hệ số giá phải lớn hơn 0 và bé hơn 2!";
        }
        return null;
    }

    private class ThemLoaiToaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LoaiToa loaiToa = panel.getLoaiToaFromForm();

                if (validateInput(loaiToa) != null) {
                    panel.showWarning(validateInput(loaiToa));
                    return;
                }

                if (dao.checkTrung(loaiToa.getTenLoai(), loaiToa.getId())) {
                    panel.showWarning("Tên loại toa " + loaiToa.getTenLoai() + " đã tồn tại!");
                    return;
                }
                if (dao.insert(loaiToa)) {
                    panel.showMessage("Thêm loại toa thành công!");
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

    private class SuaLoaiToaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                LoaiToa loaiToa = panel.getLoaiToaFromForm();
                loaiToa.setId(Integer.parseInt(model.getValueAt(selectedRow, 0 ).toString()));

                if (validateInput(loaiToa) != null) {
                    panel.showWarning(validateInput(loaiToa));
                    return;
                }

                if(dao.checkTrung(loaiToa.getTenLoai(), loaiToa.getId())){
                    panel.showWarning("Tên loại toa " + loaiToa.getTenLoai()+  " đã tồn tại");
                    return;
                }

                if (panel.showConfirm("Bạn có chắc muốn sửa loại toa: " + loaiToa.getTenLoai() + "?")) {
                    if (dao.update(loaiToa)) {
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

    private class XoaLoaiToaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String Tenloai = model.getValueAt(selectedRow, 1).toString();

                if (Tenloai.isEmpty()) {
                    panel.showWarning("Tên loại toa không hợp lệ!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá loại toa " + Tenloai + "?")) {
                    if (dao.delete(Tenloai)) {
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

            panel.setTenLoai(model.getValueAt(selectedRow, 1).toString());
            panel.setHeSoGia(model.getValueAt(selectedRow, 2).toString());

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
