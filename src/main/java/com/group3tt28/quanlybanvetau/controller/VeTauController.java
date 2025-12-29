package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.VeTauDAO;
import com.group3tt28.quanlybanvetau.model.VeTau;
import com.group3tt28.quanlybanvetau.view.panel.VeTauPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VeTauController {
    private final VeTauPanel panel;
    private final VeTauDAO dao;
    private final DefaultTableModel model;
    private List<VeTau> listVeTau;
    private int selectedRow = -1;

    public VeTauController(VeTauPanel panel){
        this.dao = new VeTauDAO();
        this.panel = panel;
        panel.addThemVeTauListener(new ThemVeTauListener());
        panel.addSuaVeTauListener(new SuaVeTauListener());
        panel.addXoaVeTauListener(new XoaVeTauListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        model = (DefaultTableModel) panel.getTable().getModel();
        listVeTau = dao.getAll();

        refresh();
    }

    private void refresh(){
        panel.resetForm();
        selectedRow = -1;
        listVeTau = dao.getAll();
        model.setRowCount(0);

        for (VeTau veTau : listVeTau){
            model.addRow(new Object[]{
                    veTau.getMaVe(),
                    veTau.getIdKhachHang(),
                    veTau.getIdLichTrinh(),
                    veTau.getIdGhe(),
                    veTau.getIdNhanVien(),
                    veTau.getNgayDat() != null ? veTau.getNgayDat().toString() : "",
                    veTau.getGiaVe(),
                    veTau.getTrangThaiVe()
            });
        }
        model.fireTableDataChanged();
    }

    private String validateInput(VeTau veTau, boolean isEditMode) {
        if (veTau.getMaVe().isEmpty()){
            return "Vui lòng nhập mã vé!";
        }

        if (!isEditMode && dao.checkTrung(veTau.getMaVe())){
            return "Mã vé đã tồn tại!";
        }

        if (veTau.getIdKhachHang() < 1) {
            return "ID Khách hàng không hợp lệ!";
        }

        if (veTau.getIdLichTrinh() < 1) {
            return "ID Lịch trình không hợp lệ!";
        }

        if (veTau.getIdGhe() < 1) {
            return "ID Ghế không hợp lệ!";
        }

        if (veTau.getIdNhanVien() < 1) {
            return "ID Nhân viên không hợp lệ!";
        }

        if (veTau.getGiaVe() <= 0){
            return "Giá vé phải lớn hơn 0!";
        }

        if (veTau.getTrangThaiVe() == null || veTau.getTrangThaiVe().isEmpty()){
            return "Vui lòng nhập Trạng thái!";
        }

        return null;
    }

    private class ThemVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String maVe = panel.getMaVe();
                int idKhachHang = (panel.getSelectedKhachHangId());
                int idLichTrinh = (panel.getSelectedLichTrinhId());
                int idGhe = (panel.getSelectedGheId());
                int idNhanVien = (panel.getSelectedNhanVienId());
                ;
                LocalDateTime ngayDat = panel.getNgayDat() != null ?
                        panel.getNgayDat() : null;
                double giaVe = panel.getGiaVe();
                String trangThai = panel.getTrangThai();

                VeTau veTau = new VeTau(maVe, idKhachHang, idLichTrinh, idGhe,
                        idNhanVien, ngayDat, giaVe, trangThai);

                String error = validateInput(veTau, false);
                if (error != null) {
                    panel.showWarning(error);
                    return;
                }

                if(dao.insert(veTau)){
                    panel.showMessage("Thêm vé tàu thành công!");
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại");
                }
            } catch (NumberFormatException ex) {
                panel.showWarning("Các ID phải là số nguyên hợp lệ!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class SuaVeTauListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1 || selectedRow >= listVeTau.size()) {
                    panel.showWarning("Vui lòng chọn vé tàu để sửa!");
                    return;
                }

                VeTau veTauCu = listVeTau.get(selectedRow);

                String maVe = panel.getMaVe();
                int idKhachHang = (panel.getSelectedKhachHangId());
                int idLichTrinh = (panel.getSelectedLichTrinhId());
                int idGhe = (panel.getSelectedGheId());
                int idNhanVien = (panel.getSelectedNhanVienId());

                LocalDateTime ngayDat = panel.getNgayDat() != null ?
                        panel.getNgayDat() : null;
                double giaVe = panel.getGiaVe();
                String trangThai = panel.getTrangThai();

                VeTau veTauMoi = new VeTau(maVe, idKhachHang, idLichTrinh, idGhe,
                        idNhanVien, ngayDat, giaVe, trangThai);
                veTauMoi.setId(veTauCu.getId());

                String error = validateInput(veTauMoi, true);
                if (error != null) {
                    panel.showWarning(error);
                    return;
                }

                if(panel.showConfirm("Bạn có muốn cập nhật thông tin vé " + maVe + "?")){
                    if (dao.update(veTauMoi)){
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại! Vui lòng kiểm tra lại");
                    }
                }
            } catch (NumberFormatException ex) {
                panel.showWarning("Các ID phải là số nguyên hợp lệ!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class XoaVeTauListener implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e){
            try {
                if (selectedRow == -1 || selectedRow >= listVeTau.size()) {
                    panel.showWarning("Vui lòng chọn vé tàu để xóa!");
                    return;
                }

                VeTau veTau = listVeTau.get(selectedRow);

                if (panel.showConfirm("Bạn có chắc chắn muốn xóa vé " + veTau.getMaVe() + "?")){
                    if (dao.delete(veTau.getId())){
                        panel.showMessage("Xóa thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại! Vui lòng kiểm tra lại");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
            selectedRow = -1;
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1 || selectedRow >= listVeTau.size()) {
                return;
            }

            VeTau veTau = listVeTau.get(selectedRow);

            panel.setMaVe(veTau.getMaVe());
            panel.setSelectedKhachHangId((veTau.getIdKhachHang()));
            panel.setSelectedLichTrinhId((veTau.getIdLichTrinh()));
            panel.setSelectedGheId((veTau.getIdGhe()));
            panel.setSelectedNhanVienId((veTau.getIdNhanVien()));

            if (veTau.getNgayDat() != null) {
                LocalDateTime ngayDat = veTau.getNgayDat();
                panel.setNgayDat(ngayDat);
            } else {
                panel.setNgayDat(null);
            }

            panel.setGiaVe(String.valueOf(veTau.getGiaVe()));
            panel.setTrangThai(veTau.getTrangThaiVe());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
