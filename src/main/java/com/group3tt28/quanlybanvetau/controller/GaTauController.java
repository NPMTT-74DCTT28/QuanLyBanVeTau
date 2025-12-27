package com.group3tt28.quanlybanvetau.controller;

import com.group3tt28.quanlybanvetau.dao.GaTauDAO;
import com.group3tt28.quanlybanvetau.model.GaTau;
import com.group3tt28.quanlybanvetau.view.GaTauPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaTauController {
    private static GaTauPanel view;

    public GaTauController() {
        GaTauDAO dao = new GaTauDAO();

        view = new GaTauPanel();
        view.loadGaTau(dao.getAll());
        view.addGaTau(new add_gatau());
        view.editGaTau(new edit_gatau());
        view.removeGaTau(new remove_gatau());
        view.setVisible(true);

    }
    public static class add_gatau implements ActionListener {
        GaTauDAO dao = new GaTauDAO();
        @Override
        public void actionPerformed(ActionEvent e) {

            String maGa = view.getMaGa();
            String tenGa = view.getTenga();
            String dc = view.getDiachi();
            String tp = view.getThanhpho();

            if(maGa.isEmpty()||tenGa.isEmpty()||dc.isEmpty()||tp.isEmpty()){
                view.showError("Cần điền đầy đủ thông tin!");
                return;
            }

            if (dao.checkTrung(maGa)) {
                view.showError("Mã ga đã tồn tại!");
                return;
            }

            GaTau gaTau = new GaTau(maGa, tenGa, dc, tp);

            boolean success = dao.insert(gaTau);
            if (!success) {
                view.showError("Thêm ga tàu thất bại!");
                return;
            }

            view.addRow(new Object[]{
                    maGa, tenGa, dc, tp
            });

            view.showMessage("Thêm ga tàu thành công");
        }
    }

    public static class edit_gatau implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public static class remove_gatau implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    static void main() {
        new GaTauController();
    }
}
