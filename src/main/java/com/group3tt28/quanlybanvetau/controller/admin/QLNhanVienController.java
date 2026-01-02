package com.group3tt28.quanlybanvetau.controller.admin;

import com.group3tt28.quanlybanvetau.dao.NhanVienDAO;
import com.group3tt28.quanlybanvetau.model.NhanVien;
import com.group3tt28.quanlybanvetau.util.BamMatKhau;
import com.group3tt28.quanlybanvetau.util.DinhDang;
import com.group3tt28.quanlybanvetau.util.SessionManager;
import com.group3tt28.quanlybanvetau.view.admin.QLNhanVienPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class QLNhanVienController {

    private final QLNhanVienPanel panel;
    private final NhanVienDAO dao;
    private final DefaultTableModel tableModel;
    private NhanVien currentUser;
    private int selectedRow;

    public QLNhanVienController(QLNhanVienPanel panel) {
        this.dao = NhanVienDAO.getInstance();
        this.currentUser = SessionManager.getCurrentUser();

        this.panel = panel;
        panel.addThemNhanVienListener(new ThemNhanVienListener());
        panel.addSuaNhanVienListener(new SuaNhanVienListener());
        panel.addXoaNhanVienListener(new XoaNhanVienListener());
        panel.addResetFormListener(new ResetFormListener());
        panel.addRefreshListener(new RefreshListener());
        panel.addTableMouseClickListener(new TableMouseClickListener());

        if (this.panel.getTable() != null) {
            tableModel = (DefaultTableModel) this.panel.getTable().getModel();
        } else {
            tableModel = new DefaultTableModel();
        }

        refresh();
    }

    private void refresh() {
        try {
            List<NhanVien> list = dao.getAll();
            tableModel.setRowCount(0);

            for (NhanVien nhanVien : list) {
                tableModel.addRow(new Object[]{
                        nhanVien.getId(),
                        nhanVien.getMaNhanVien(),
                        nhanVien.getHoTen(),
                        DinhDang.formatNgayVN(nhanVien.getNgaySinh()),
                        nhanVien.getGioiTinh(),
                        nhanVien.getSdt(),
                        nhanVien.getEmail(),
                        nhanVien.getDiaChi(),
                        nhanVien.getVaiTro()
                });
            }

            tableModel.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            panel.showError("Có lỗi xảy ra khi tải dữ liệu nhân viên: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            panel.showError("Lỗi không xác định: " + e.getMessage());
        }
    }

    private class ThemNhanVienListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                NhanVien nhanVien = panel.getNhanVienFromForm();
                nhanVien.setId(0);

                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                if (dao.checkTrung(nhanVien.getMaNhanVien(), nhanVien.getSdt(), nhanVien.getId())) {
                    panel.showWarning("Mã nhân viên hoặc SĐT đã tồn tại.");
                    return;
                }

                String matKhauTho = nhanVien.getMatKhau();
                String matKhauBam = BamMatKhau.bamMatKhau(matKhauTho);
                if (matKhauBam == null) {
                    panel.showError("Lỗi bảo mật: Không thể mã hoá mật khẩu.");
                    return;
                }
                nhanVien.setMatKhau(matKhauBam);

                if (dao.insert(nhanVien)) {
                    panel.showMessage("Thêm nhân viên thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại! Vui lòng kiểm tra lại.");
                }
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062 || ex.getMessage().contains("Duplicate entry")) {
                    panel.showError("Dữ liệu đã bị thay đổi! Mã nhân viên hoặc số điện thoại đã tồn tại.");
                    refresh();
                } else {
                    panel.showError("Lỗi kết nối cơ sở dữ liệu (Mã lỗi: " + ex.getErrorCode() + ")");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Đã xảy ra lỗi: " + ex.getMessage());
            }
        }
    }

    private class SuaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn nhân viên cần sửa thông tin.");
                    return;
                }

                NhanVien nhanVien = panel.getNhanVienFromForm();
                nhanVien.setId(Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString()));

                if (nhanVien.getId() == currentUser.getId()) {
                    panel.showWarning("Bạn chỉ có thể cập nhật thông tin cá nhân bằng chức năng\ncập nhật thông tin trong menu tài khoản.");
                    return;
                }

                if (panel.thongBaoLoiDauVao() != null) {
                    panel.showWarning(panel.thongBaoLoiDauVao());
                    return;
                }

                if (dao.checkTrung(nhanVien.getMaNhanVien(), nhanVien.getSdt(), nhanVien.getId())) {
                    panel.showWarning("SĐT đã được sử dụng.");
                    return;
                }

                if (panel.showConfirm("Bạn muốn cập nhật thông tin nhân viên " + nhanVien.getMaNhanVien() + "?")) {
                    if (dao.update(nhanVien)) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại! Vui lòng kiểm tra lại!");
                    }
                }
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062 || ex.getMessage().contains("Duplicate entry")) {
                    panel.showError("Dữ liệu đã bị thay đổi! Mã nhân viên hoặc số điện thoại đã tồn tại.");
                    refresh();
                } else {
                    panel.showError("Lỗi cơ sở dữ liệu (Mã lỗi: " + ex.getErrorCode() + ")");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class XoaNhanVienListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (selectedRow == -1) {
                    panel.showWarning("Vui lòng chọn nhân viên cần xoá.");
                    return;
                }

                if (tableModel.getValueAt(selectedRow, 0).toString().trim().isEmpty()) {
                    panel.showWarning("Dữ liệu dòng chọn không hợp lệ.");
                    return;
                }

                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String maNhanVien = tableModel.getValueAt(selectedRow, 1).toString();

                if (id == currentUser.getId()) {
                    panel.showWarning("Bạn không thể xoá thông tin của chính mình!");
                    return;
                }

                if (panel.showConfirm("Bạn muốn xoá nhân viên " + maNhanVien + "?")) {
                    if (dao.delete(id)) {
                        panel.showMessage("Xoá thành công!");
                        refresh();
                    } else {
                        panel.showError("Xoá thất bại! Vui lòng kiểm tra lại.");
                    }
                }
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1451) {
                    panel.showError("Không thể xoá nhân viên này vì họ đã có lịch sử bán vé.");
                } else {
                    panel.showError("Lỗi cơ sở dữ liệu (Mã lỗi: " + ex.getErrorCode() + ")");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    private class ResetFormListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refresh();
        }
    }

    private class TableMouseClickListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();

            if (panel.getTable() != null) {
                selectedRow = panel.getTable().getSelectedRow();
            }

            if (selectedRow == -1) {
                return;
            }

            panel.setMaNhanVien(tableModel.getValueAt(selectedRow, 1).toString());
            panel.setHoTen(tableModel.getValueAt(selectedRow, 2).toString());

            Object ngaySinhObj = tableModel.getValueAt(selectedRow, 3);
            if (ngaySinhObj instanceof LocalDate) {
                panel.setNgaySinh((LocalDate) ngaySinhObj);
            } else {
                try {
                    panel.setNgaySinh(LocalDate.parse(ngaySinhObj.toString(), DinhDang.DATE_FORMATTER));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    panel.showError("Lỗi khi chuyển đổi ngày tháng: " + ex.getMessage());
                }
            }

            panel.setGioiTinh(tableModel.getValueAt(selectedRow, 4).toString());

            panel.setSdt(tableModel.getValueAt(selectedRow, 5).toString());

            Object emailObj = tableModel.getValueAt(selectedRow, 6);
            panel.setEmail(emailObj != null ? emailObj.toString() : "");

            Object diaChiObj = tableModel.getValueAt(selectedRow, 7);
            panel.setDiaChi(diaChiObj != null ? diaChiObj.toString() : "");

            panel.setVaiTro(tableModel.getValueAt(selectedRow, 8).toString());
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
