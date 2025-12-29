package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.LichTrinhDAO;
import com.group3tt28.quanlybanvetau.dao.TauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.LichTrinh;
import com.group3tt28.quanlybanvetau.model.Tau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.panel.QLLichTrinhPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QLLichTrinhController {

    private final QLLichTrinhPanel panel;
    private final LichTrinhDAO lichTrinhDAO;
    private final TauDAO tauDAO;
    private final TuyenDuongDAO tuyenDuongDAO;
    private final DefaultTableModel tableModel;
    private int selectedId = -1; // Lưu ID của bản ghi đang chọn

    // Cache để hiển thị tên trong bảng
    private List<Tau> listTau;
    private List<TuyenDuong> listTuyen;

    public QLLichTrinhController(QLLichTrinhPanel panel) {
        this.panel = panel;
        this.lichTrinhDAO = new LichTrinhDAO();
        this.tauDAO = new TauDAO();
        this.tuyenDuongDAO = new TuyenDuongDAO();
        this.tableModel = (DefaultTableModel) panel.getTable().getModel();

        // Đăng ký sự kiện
        panel.addThemListener(new ThemListener());
        panel.addSuaListener(new SuaListener());
        panel.addXoaListener(new XoaListener());
        panel.addResetListener(new ResetListener());
        panel.addTableMouseListener(new TableMouseListener());

        // Load dữ liệu
        loadComboBoxData();
        refresh();
    }

    private void loadComboBoxData() {
        try {
            listTau = tauDAO.getAll();
            panel.getBoxTau().removeAllItems();
            for (Tau t : listTau) panel.getBoxTau().addItem(t);

            listTuyen = tuyenDuongDAO.getAll();
            panel.getBoxTuyenDuong().removeAllItems();
            for (TuyenDuong td : listTuyen) panel.getBoxTuyenDuong().addItem(td);

        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải dữ liệu Tàu/Tuyến: " + e.getMessage());
        }
    }

    private void refresh() {
        panel.resetForm();
        selectedId = -1;

        try {
            List<LichTrinh> list = lichTrinhDAO.getAll();
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
                        lt.getNgayDi().format(formatter),
                        lt.getNgayDen().format(formatter),
                        lt.getTrangThai()
                });
            }
            tableModel.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi tải danh sách lịch trình: " + e.getMessage());
        }
    }

    // Helper: Validate
    private String validate(LichTrinh lt) {
        if (lt.getMaLichTrinh().isEmpty()) return "Vui lòng nhập mã lịch trình!";
        if (lt.getNgayDi().isAfter(lt.getNgayDen())) return "Ngày đi phải trước ngày đến!";
        if (lt.getNgayDi().isEqual(lt.getNgayDen())) return "Ngày đi và đến không được trùng nhau!";
        return null;
    }

    // Helper: Get Name by ID
    private String getTenTauById(int id) {
        if (listTau == null) return String.valueOf(id);
        return listTau.stream().filter(t -> t.getId() == id).findFirst().map(Tau::getTenTau).orElse(String.valueOf(id));
    }

    private String getTenTuyenById(int id) {
        if (listTuyen == null) return String.valueOf(id);
        return listTuyen.stream().filter(t -> t.getId() == id).findFirst().map(TuyenDuong::getTenTuyen).orElse(String.valueOf(id));
    }

    // --- INNER CLASSES LISTENER ---

    private class ThemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                LichTrinh lt = panel.getLichTrinhFromForm();
                if (lt == null) return;

                String err = validate(lt);
                if (err != null) {
                    panel.showWarning(err);
                    return;
                }

                if (lichTrinhDAO.checktrung(lt.getMaLichTrinh())) {
                    panel.showWarning("Mã lịch trình đã tồn tại!");
                    return;
                }

                if (lichTrinhDAO.insert(lt)) {
                    panel.showMessage("Thêm thành công!");
                    refresh();
                } else {
                    panel.showError("Thêm thất bại!");
                }
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
                if (selectedId == -1) {
                    panel.showWarning("Chưa chọn dòng để sửa!");
                    return;
                }

                LichTrinh lt = panel.getLichTrinhFromForm();
                if (lt == null) return;
                lt.setId(selectedId); // Gán ID để update đúng dòng

                String err = validate(lt);
                if (err != null) {
                    panel.showWarning(err);
                    return;
                }

                if (panel.showConfirm("Bạn muốn cập nhật lịch trình này?")) {
                    if (lichTrinhDAO.update(lt)) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!");
                    }
                }
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
                if (selectedId == -1) {
                    panel.showWarning("Chưa chọn dòng để xoá!");
                    return;
                }

                String ma = panel.getMaLichTrinh();
                if (panel.showConfirm("Bạn chắc chắn muốn xoá lịch trình " + ma + "?")) {
                    if (lichTrinhDAO.delete(selectedId)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xoá thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi xoá dữ liệu: " + ex.getMessage());
            }
        }
    }

    private class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = panel.getTable().getSelectedRow();
            if (row == -1) return;

            // Lấy ID từ cột ẩn (cột 0 trong model, nhưng đã bị remove khỏi view)
            // Cần lấy từ Model
            selectedId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String maLT = tableModel.getValueAt(row, 1).toString();
            // Các cột khác chỉ là text hiển thị, cần lấy từ DB hoặc List gốc để chính xác nhất
            // Tuy nhiên, ta có thể lấy ID Tàu/Tuyến bằng cách map ngược (hoặc query lại getByID)
            // Ở đây để đơn giản và chính xác, ta query lại object từ DB hoặc tìm trong list

            // Cách nhanh: Lấy object đầy đủ từ DB dựa trên ID
            // Nhưng để tối ưu, ta map dữ liệu từ bảng + list cache

            // Tìm trong list hiện tại (đã load ở refresh) - nhưng list local ở refresh scope
            // Nên gọi DAO getById hoặc filter
            // Để đơn giản code này, ta dùng thông tin trên bảng và cache

            panel.startEditMode();
            panel.setMaLichTrinh(maLT);

            // Set Tau
            String tenTau = tableModel.getValueAt(row, 2).toString();
            for(Tau t : listTau) {
                if(t.getTenTau().equals(tenTau)) {
                    panel.setTau(t.getId());
                    break;
                }
            }

            // Set Tuyen
            String tenTuyen = tableModel.getValueAt(row, 3).toString();
            for(TuyenDuong td : listTuyen) {
                if(td.getTenTuyen().equals(tenTuyen)) {
                    panel.setTuyenDuong(td.getId());
                    break;
                }
            }

            // Set Ngay (Parse từ String trên bảng)
            try {
                String strDi = tableModel.getValueAt(row, 4).toString();
                String strDen = tableModel.getValueAt(row, 5).toString();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                panel.setNgayDi(java.time.LocalDateTime.parse(strDi, fmt));
                panel.setNgayDen(java.time.LocalDateTime.parse(strDen, fmt));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Set Trang thai
            String tt = tableModel.getValueAt(row, 6).toString();
            panel.setTrangThai(tt);
        }

        @Override public void mousePressed(MouseEvent e) {}
        @Override public void mouseReleased(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    }
}