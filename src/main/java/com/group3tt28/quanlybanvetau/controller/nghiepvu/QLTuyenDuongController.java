package com.group3tt28.quanlybanvetau.controller.nghiepvu;

import com.group3tt28.quanlybanvetau.dao.GaTauDAO;
import com.group3tt28.quanlybanvetau.dao.TuyenDuongDAO;
import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.model.TuyenDuong;
import com.group3tt28.quanlybanvetau.view.nghiepvu.QLTuyenDuongPanel;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

public class QLTuyenDuongController {
    private final QLTuyenDuongPanel panel;
    private final TuyenDuongDAO dao;
    private final DefaultTableModel model;
    private HashMap<Integer, String> mapGaTau = new HashMap<>();
    private int selectedRow;

    public QLTuyenDuongController(QLTuyenDuongPanel panel) {
        this.dao = new TuyenDuongDAO();
        this.panel = panel;
        panel.AddTuyen(new AddTuyen());
        panel.EditTuyen(new EditTuyen());
        panel.RemoveTuyen(new RemoveTuyen());
        panel.ResetTuyen(new ResetTuyen());
        panel.TableMouseClickListener(new TableMouseClickListener());
        model = (DefaultTableModel) panel.getTable().getModel();
        refresh();
    }

    public void refresh() {
        panel.resetForm();
        try {
            GaTauDAO gaDAO = new GaTauDAO();
            List<GaTau> listgatau = gaDAO.getAll();

            mapGaTau.clear();
            for (GaTau ga : listgatau) {
                mapGaTau.put(ga.getId(), ga.getMaGa() + " - " + ga.getTenGa());
            }

            panel.setGatau(listgatau);

            List<TuyenDuong> list = dao.getAll();
            model.setRowCount(0);
            for (TuyenDuong d : list) {
                String tenGaDi = mapGaTau.getOrDefault(d.getIdGaDi(), String.valueOf(d.getIdGaDi()));
                String tenGaDen = mapGaTau.getOrDefault(d.getIdGaDen(), String.valueOf(d.getIdGaDen()));

                model.addRow(new Object[]{
                        d.getMaTuyen(),
                        d.getTenTuyen(),
                        tenGaDi,
                        tenGaDen,
                        d.getKhoangCachKm(),
                        d.getGiaCoBan()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.fireTableDataChanged();
    }

    public class AddTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TuyenDuong td = panel.getTuyenDuong();
                if (td.getMaTuyen().isEmpty() || td.getTenTuyen().isEmpty()) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }

                if (td.getKhoangCachKm() <= 0 || td.getGiaCoBan() <= 0){
                    panel.showError("Khoảng cách / giá cơ bản phải lớn hon 0!");
                    return;
                }
                if (dao.checkTrung(td.getMaTuyen(), td.getIdGaDi(), td.getIdGaDen())) {
                    panel.showWarning("Tuyến đường đã tồn tại!");
                    return;
                }
                if (td.getIdGaDi() == td.getIdGaDen()) {
                    panel.showError("Ga đi và Ga đến không được trùng nhau!");
                    return;
                }
                if (dao.insert(td)) {
                    panel.showMessage("Thêm tuyến đường thành công!");
                    panel.resetForm();
                    refresh();
                } else {
                    panel.showError("Thêm thất bại!");
                }
            } catch (NumberFormatException ex) {
                panel.showError("Khoảng cách / Giá cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi không xác định: " + ex.getMessage());
            }
        }
    }

    public class EditTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TuyenDuong td = panel.getTuyenDuong();
                if (td.getMaTuyen().isEmpty() || td.getTenTuyen().isEmpty() ) {
                    panel.showError("Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                if (td.getKhoangCachKm() <= 0 || td.getGiaCoBan() <= 0){
                    panel.showError("Khoảng cách / giá cơ bản phải lớn hon 0!");
                    return;
                }
                if (panel.showConfirm("Bạn có muốn cập nhật thông tin của" + td.getMaTuyen() + " không ?")) {
                    if (dao.update(td)) {
                        panel.showMessage("Cập nhật thành công!");
                        refresh();
                    } else {
                        panel.showError("Cập nhật thất bại!");
                    }
                }
            } catch (NumberFormatException ex) {
                panel.showError("Khoảng cách / Giá cơ bản phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class RemoveTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String maTuyen = model.getValueAt(selectedRow, 0).toString();
                if (maTuyen.isEmpty()) {
                    return;
                }

                if (panel.showConfirm("Bạn có muốn xóa" + maTuyen + " không ?")) {
                    if (dao.delete(maTuyen)) {
                        panel.showMessage("Xóa thành công!");
                        refresh();
                    } else {
                        panel.showError("Xóa thất bại!");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                panel.showError("Lỗi hệ thống: " + ex.getMessage());
            }
        }
    }

    public class ResetTuyen implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.resetForm();
        }
    }

    private class TableMouseClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            panel.startEditMode();
            selectedRow = panel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                return;
            }
            String maTuyen = model.getValueAt(selectedRow, 0).toString();
            List<TuyenDuong> list = dao.getAll();
            TuyenDuong selectedTD = null;
            for (TuyenDuong td : list) {
                if (td.getMaTuyen().equals(maTuyen)) {
                    selectedTD = td;
                    break;
                }
            }
            panel.setMaTuyen(model.getValueAt(selectedRow, 0).toString());
            panel.setTenTuyen(model.getValueAt(selectedRow, 1).toString());
            panel.setGadi(selectedTD.getIdGaDi());
            panel.setGaden(selectedTD.getIdGaDen());
            panel.setKhoangcach(model.getValueAt(selectedRow, 4).toString());
            panel.setGiaCB(model.getValueAt(selectedRow, 5).toString());
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