package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.enums.GioiTinh;
import com.group3tt28.quanlybanvetau.util.DinhDang;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ThongTinCaNhanPanel extends BasePanel {

    private JTextField fieldHoTen;
    private JDateChooser chooserNgaySinh;
    private JComboBox<Object> boxGioiTinh;
    private JTextField fieldSdt;
    private JTextField fieldEmail;
    private JTextField fieldDiaChi;
    private JButton buttonXacNhan, buttonQuayLai;

    public ThongTinCaNhanPanel() {
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.WHITE);

        JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTitle.setBackground(PRIMARY_COLOR);
        JLabel labelTitle = new JLabel("THÔNG TIN CÁ NHÂN");
        labelTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTitle.setForeground(Color.WHITE);
        panelTitle.add(labelTitle);

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 0, 0));
        panelForm.setBorder(new EmptyBorder(20, 5, 5, 5));
        panelForm.setBackground(Color.WHITE);

        fieldHoTen = new JTextField();
        panelForm.add(createInputField("Họ tên*", fieldHoTen, Color.WHITE));

        chooserNgaySinh = new JDateChooser();
        panelForm.add(createInputField("Ngày sinh*", chooserNgaySinh, Color.WHITE));

        boxGioiTinh = new JComboBox<>(GioiTinh.values());
        panelForm.add(createInputField("Giới tính*", boxGioiTinh, Color.WHITE));

        fieldSdt = new JTextField();
        panelForm.add(createInputField("SĐT*", fieldSdt, Color.WHITE));

        fieldEmail = new JTextField();
        panelForm.add(createInputField("Email", fieldEmail, Color.WHITE));

        fieldDiaChi = new JTextField();
        panelForm.add(createInputField("Địa chỉ*", fieldDiaChi, Color.WHITE));

        buttonXacNhan = createStyledButton("Lưu thông tin", new Dimension(150, 40), new Color(191, 214, 65), Color.BLACK);
        buttonQuayLai = createStyledButton("Về trang chủ", new Dimension(150, 40), SECONDARY_COLOR, Color.BLACK);
        JButton[] buttons = new JButton[]{buttonXacNhan, buttonQuayLai};

        JPanel container = new JPanel(new BorderLayout(0, 0));
        container.add(panelTitle, BorderLayout.NORTH);
        container.add(panelForm, BorderLayout.CENTER);
        container.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(container);
        add(scrollPane);
    }

    public String getHoTen() {
        return fieldHoTen.getText().trim();
    }

    public void setHoTen(String hoTen) {
        fieldHoTen.setText(hoTen != null ? hoTen : "");
    }

    public LocalDate getNgaySinh() {
        if (chooserNgaySinh.getDate() != null) {
            return chooserNgaySinh.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        if (ngaySinh != null) {
            chooserNgaySinh.setDate(Date.from(ngaySinh.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        } else {
            chooserNgaySinh.setDate(null);
        }
    }

    public String getGioiTinh() {
        Object selectedItem = boxGioiTinh.getSelectedItem();
        if (selectedItem != null && !selectedItem.toString().equalsIgnoreCase("Tất cả")) {
            return selectedItem.toString();
        }
        return null;
    }

    public void setGioiTinh(String label) {
        if (label != null) {
            for (int i = 0; i < boxGioiTinh.getItemCount(); i++) {
                if (label.equalsIgnoreCase(boxGioiTinh.getItemAt(i).toString())) {
                    boxGioiTinh.setSelectedIndex(i);
                }
            }
        }
    }

    public String getSdt() {
        return fieldSdt.getText().trim();
    }

    public void setSdt(String sdt) {
        fieldSdt.setText(sdt != null ? sdt : "");
    }

    public String getEmail() {
        return !fieldEmail.getText().trim().isEmpty() ? fieldEmail.getText().trim() : null;
    }

    public void setEmail(String email) {
        fieldEmail.setText(email != null ? email : "");
    }

    public String getDiaChi() {
        return fieldDiaChi.getText().trim();
    }

    public void setDiaChi(String diaChi) {
        fieldDiaChi.setText(diaChi != null ? diaChi : "");
    }

    public String thongBaoLoiDauVao() {
        if (getHoTen().isEmpty()) {
            fieldHoTen.requestFocus();
            return "Họ tên không được để trống!";
        }
        if (getHoTen().length() > 50) {
            fieldHoTen.requestFocus();
            return "Họ tên quá dài (tối đa 50 ký tự)!";
        }
        if (getNgaySinh() == null) {
            chooserNgaySinh.requestFocus();
            return "Vui lòng chọn ngày sinh!";
        }
        int tuoi = Period.between(getNgaySinh(), LocalDate.now()).getYears();
        if (tuoi < 18) {
            chooserNgaySinh.requestFocus();
            return "Tuổi phải từ 18 trở lên!";
        }
        if (tuoi > 65) {
            chooserNgaySinh.requestFocus();
            return "Tuổi vượt quá tuổi lao động!";
        }
        if (getGioiTinh() == null) {
            boxGioiTinh.requestFocus();
            return "Vui lòng chọn giới tính!";
        }
        if (getSdt().isEmpty()) {
            fieldSdt.requestFocus();
            return "Số điện thoại không được để trống!";
        }
        if (!getSdt().matches(DinhDang.DINH_DANG_SDT)) {
            fieldSdt.requestFocus();
            return "Số điện thoại phải bắt đầu bằng số 0 và có 10 chữ số!";
        }
        if (getEmail() != null) {
            if (getEmail().length() > 100) {
                fieldEmail.requestFocus();
                return "Email quá dài (tối đa 100 ký tự)!";
            }
            if (!getEmail().matches(DinhDang.DINH_DANG_EMAIL)) {
                fieldEmail.requestFocus();
                return "Định dạng email không hợp lệ!";
            }
        }
        if (getDiaChi().isEmpty()) {
            fieldDiaChi.requestFocus();
            return "Địa chỉ không được để trống!";
        }
        return null;
    }

    public void addXacNhanListener(ActionListener l) {
        buttonXacNhan.addActionListener(l);
    }

    public void addQuayLaiListener(ActionListener l) {
        buttonQuayLai.addActionListener(l);
    }
}
