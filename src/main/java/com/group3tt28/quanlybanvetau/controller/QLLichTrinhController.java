package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LichTrinhDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.panel.QLLichTrinhPanel;

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

    // Danh sách gốc chứa toàn bộ dữ liệu từ DB
    private List<LichTrinh> listLichTrinh;
    private int selectedRow = -1;

    public QLLichTrinhController(QLLichTrinhPanel panel) {
        this.panel = panel;
        this.dao = new LichTrinhDAO();
        this.tauDAO = new TauDAO();
        this.tuyenDuongDAO = new TuyenDuongDAO();
        this.listLichTrinh = new ArrayList<>();

        // Đăng ký các sự kiện
        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
        // Sự kiện MỚI: Tìm kiếm
        panel.addTimKiemListener(new TimKiemListener());

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

    // Hàm refresh tải lại toàn bộ dữ liệu
    private void refresh() {
        panel.resetForm();
        selectedRow = -1;
        // Tải lại toàn bộ từ DB
        listLichTrinh = dao.getAll();
        // Hiển thị toàn bộ lên bảng
        updateTable(listLichTrinh);
    }

    // Hàm cập nhật bảng dựa trên 1 danh sách (dùng chung cho refresh và tìm kiếm)
    private void updateTable(List<LichTrinh> listHienThi) {
        model.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (LichTrinh lt : listHienThi) {
            String tenTau = getTenTauById(lt.getIdTau());
            String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong());

            model.addRow(new Object[]{
                    lt.getMaLichTrinh(),
                    tenTau,
                    tenTuyen,
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

    // --- CLASS XỬ LÝ SỰ KIỆN TÌM KIẾM ---
    private class TimKiemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tuKhoa = panel.getTuKhoaTimKiem().toLowerCase();

            // Nếu ô tìm kiếm trống, hiển thị lại toàn bộ
            if (tuKhoa.isEmpty()) {
                refresh();
                return;
            }

            List<LichTrinh> ketQua = new ArrayList<>();
            for (LichTrinh lt : listLichTrinh) {
                // Lấy các chuỗi hiển thị để so sánh
                String maLT = lt.getMaLichTrinh().toLowerCase();
                String tenTau = getTenTauById(lt.getIdTau()).toLowerCase();
                String tenTuyen = getTenTuyenById(lt.getIdTuyenDuong()).toLowerCase();
                String trangThai = lt.getTrangThai().toLowerCase();

                // Kiểm tra xem từ khóa có nằm trong bất kỳ trường nào không
                if (maLT.contains(tuKhoa) || tenTau.contains(tuKhoa) || tenTuyen.contains(tuKhoa) || trangThai.contains(tuKhoa)) {
                    ketQua.add(lt);
                }
            }

            // Chỉ cập nhật bảng với kết quả tìm thấy, KHÔNG tải lại DB
            updateTable(ketQua);

            if (ketQua.isEmpty()) {
                panel.showWarning("Không tìm thấy lịch trình nào khớp với từ khóa: " + tuKhoa);
            }
        }
    }

    // --- CÁC LISTENER CŨ ---

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

                // Lưu ý: listLichTrinh có thể đang là danh sách tìm kiếm hoặc danh sách gốc
                // Nên cần lấy ID từ table model để chính xác nhất nếu đang ở chế độ tìm kiếm
                // Tuy nhiên, logic đơn giản là lấy từ list gốc theo index table (nếu chưa sort)
                // Để an toàn, ở đây ta dùng selectedRow map vào list hiển thị.

                // Cách an toàn nhất: Lấy mã từ bảng dòng được chọn, rồi tìm trong list gốc
                String maLichTrinhTable = (String) panel.getTable().getValueAt(selectedRow, 0);
                LichTrinh ltCu = null;
                for (LichTrinh lt : listLichTrinh) {
                    if (lt.getMaLichTrinh().equals(maLichTrinhTable)) {
                        ltCu = lt;
                        break;
                    }
                }

                if (ltCu == null) return; // Không tìm thấy

                LichTrinh ltMoi = panel.getLichTrinhFromForm();
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

    private class XoaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn lịch trình để xoá!");
                    return;
                }

                // Logic lấy object tương tự phần Sửa để đảm bảo đúng ID khi đang tìm kiếm
                String maLichTrinhTable = (String) panel.getTable().getValueAt(selectedRow, 0);
                LichTrinh lt = null;
                for (LichTrinh item : listLichTrinh) {
                    if (item.getMaLichTrinh().equals(maLichTrinhTable)) {
                        lt = item;
                        break;
                    }
                }

                if (lt != null && panel.showConfirm("Xoá lịch trình " + lt.getMaLichTrinh() + "?")) {
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
            refresh(); // Reset form và tải lại bảng full
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) return;

            // Lấy dữ liệu từ bảng hiển thị (Visual)
            String maLT = (String) panel.getTable().getValueAt(selectedRow, 0);

            // Tìm đối tượng gốc trong listLichTrinh
            LichTrinh lt = null;
            for (LichTrinh item : listLichTrinh) {
                if (item.getMaLichTrinh().equals(maLT)) {
                    lt = item;
                    break;
                }
            }

            if (lt == null) return;

            panel.startEditMode();

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