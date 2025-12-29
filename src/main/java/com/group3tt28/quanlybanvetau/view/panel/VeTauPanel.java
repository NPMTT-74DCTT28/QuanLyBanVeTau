package com.group3tt28.quanlybanvetau.view.panel;

import com.group3tt28.quanlybanvetau.model.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class VeTauPanel extends BasePanel{

    private JTextField fieldMaVe;
    private JComboBox<KhachHang> comboKhachHang;
    private JComboBox<LichTrinh> comboLichTrinh;
    private JComboBox<Ghe> comboGhe;
    private JComboBox<NhanVien> comboNhanVien;
    private JSpinner spinnerNgayDat;
    private JTextField fieldGiaVe;
    private JTextField fieldTrangThai;
    private JButton buttonThem, buttonSua, buttonXoa, buttonReset;
    private JTable table;


    public VeTauPanel() { initComponents();}
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        JPanel panelTitle = new JPanel();
        panelTitle.setBackground(new Color(0,255,255));
        panelTitle.setBorder(new EmptyBorder(5,5,5,5));
        JLabel labelTitle = new JLabel("Quản lý thông tin vé");
        labelTitle.setSize(200,80);
        labelTitle.setFont(new Font("Times New Roman", Font.BOLD,20));
        panelTitle.add(labelTitle);

        JPanel panelTop = new JPanel(new BorderLayout(5,5));

        JPanel panelForm = new JPanel(new GridLayout(2,4));

        fieldMaVe = new JTextField();
        panelForm.add(createInputField("Mã vé:", fieldMaVe, Color.WHITE));

        comboKhachHang = new JComboBox<>();
        panelForm.add(createInputField("Khách hàng:", comboKhachHang, Color.WHITE));

        comboLichTrinh = new JComboBox<>();
        panelForm.add(createInputField("Lịch trình:", comboLichTrinh, Color.WHITE));

        comboGhe = new JComboBox<>();
        panelForm.add(createInputField("Ghế:", comboGhe, Color.WHITE));

        comboNhanVien = new JComboBox<>();
        panelForm.add(createInputField("Nhân viên:", comboNhanVien, Color.WHITE));

        SpinnerDateModel modelDat = new SpinnerDateModel();
        spinnerNgayDat = new JSpinner(modelDat);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerNgayDat, "yyyy-MM-dd HH:mm:ss");
        spinnerNgayDat.setEditor(editor);
        panelForm.add(createInputField("Ngày đặt:", spinnerNgayDat, Color.WHITE));

        fieldGiaVe = new JTextField();
        panelForm.add(createInputField("Giá vé:", fieldGiaVe, Color.WHITE));

        fieldTrangThai = new JTextField();
        panelForm.add(createInputField("Trạng thái:", fieldTrangThai, Color.WHITE));

        buttonThem = createStyledButton("Thêm", new Dimension(80,40),PRIMARY_COLOR, Color.WHITE);
        buttonThem.setEnabled(true);
        buttonSua = createStyledButton("Sửa", new Dimension(80,40), new Color(82, 245, 218), Color.WHITE);
        buttonSua.setEnabled(false);
        buttonXoa = createStyledButton("Xóa", new Dimension(80,40), Color.blue, Color.WHITE);
        buttonXoa.setEnabled(false);
        buttonReset = createStyledButton("Reset", new Dimension(80,40), PRIMARY_COLOR, Color.WHITE);
        buttonReset.setEnabled(true);

        JButton[] buttons = {buttonThem, buttonSua, buttonXoa, buttonReset};

        panelTop.add(panelTitle, BorderLayout.NORTH);
        panelTop.add(panelForm);
        panelTop.add(createButtonField(buttons, Color.WHITE), BorderLayout.SOUTH);

        Object[] columns = new Object[] {"ID", "Mã vé", "Id Khách hàng", "Id Lịch trình", "Id Ghế", "Id Nhân viên", "Ngày đặt", "Giá vé", "Trạng thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table=new JTable(tableModel);
        TableColumnModel clm = table.getColumnModel();
        TableColumn columnid = clm.getColumn(0);
        table.removeColumn(columnid);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setFont(new Font("Times New Roman", Font.BOLD, 13));
        scrollPane.setBorder(new EmptyBorder(10,10,10,10));

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public String getMaVe() {
        return fieldMaVe.getText().trim();
    }

    public void setMaVe(String maVe) {
        fieldMaVe.setText(maVe);
    }

    public int getSelectedKhachHangId() {
        if (comboKhachHang.getItemCount()>0 && comboKhachHang.getSelectedItem() != null){
            return Integer.parseInt(comboKhachHang.getSelectedItem().toString());
        }
        return 0;
    }

    public void setSelectedKhachHangId(int id) {
        for (int i = 0; i < comboKhachHang.getItemCount(); i++) {
            if (comboKhachHang.getItemAt(i).getId() == id) {
                comboKhachHang.setSelectedIndex(i);
                return;
            }
        }
    }

    public int getSelectedLichTrinhId() {
        if (comboLichTrinh.getItemCount()>0 && comboLichTrinh.getSelectedItem() != null){
            return Integer.parseInt(comboLichTrinh.getSelectedItem().toString());
        }
        return 0;
    }

    public void setSelectedLichTrinhId(int id) {
        for (int i = 0; i < comboLichTrinh.getItemCount(); i++) {
            if (comboLichTrinh.getItemAt(i).getId() == id) {
                comboLichTrinh.setSelectedIndex(i);
                return;
            }
        }
    }

    public int getSelectedGheId() {
        if (comboGhe.getItemCount()>0 && comboGhe.getSelectedItem() != null){
            return Integer.parseInt(comboGhe.getSelectedItem().toString());
        }
        return 0;
    }

    public void setSelectedGheId(int id) {
        for (int i = 0; i < comboGhe.getItemCount(); i++) {
            if (comboGhe.getItemAt(i).getId() == id) {
                comboGhe.setSelectedIndex(i);
                return;
            }
        }
    }

    public int getSelectedNhanVienId() {
        if (comboNhanVien.getItemCount()>0 && comboNhanVien.getSelectedItem() != null){
            return Integer.parseInt(comboNhanVien.getSelectedItem().toString());
        }
        return 0;
    }

    public void setSelectedNhanVienId(int id) {
        for (int i = 0; i < comboNhanVien.getItemCount(); i++) {
            if (comboNhanVien.getItemAt(i).getId() == id) {
                comboNhanVien.setSelectedIndex(i);
                return;
            }
        }
    }

    public LocalDateTime getNgayDat() {
        Object value = spinnerNgayDat.getValue();
        if (value instanceof Date) {
            Date date = (Date) value;
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }

    // Phương thức chuyển đổi LocalDateTime -> JSpinner
    public void setNgayDat(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            spinnerNgayDat.setValue(new Date()); // Nếu null thì đặt ngày hiện tại
        } else {
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            spinnerNgayDat.setValue(date);
        }
    }

    public double getGiaVe() {
        if (fieldGiaVe.getText().trim().isEmpty()){
            return 0;
        }else
            return Double.parseDouble(fieldGiaVe.getText().trim());
    }

    public void setGiaVe(String giaVe) {fieldGiaVe.setText(giaVe);}

    public String getTrangThai() {return fieldTrangThai.getText().trim();}

    public void setTrangThai(String trangThai) {fieldTrangThai.setText(trangThai);}

    public JTable getTable() {return table;}

    public VeTau getVeTauFromForm() {
        String maVe = getMaVe();
        int idKhachHang =  getSelectedKhachHangId();
        int idLichTrinh = getSelectedLichTrinhId();
        int idGhe = getSelectedGheId();
        int idNhanVien = getSelectedNhanVienId();
        LocalDateTime ngayDat = getNgayDat();
        Double giaVe = getGiaVe();
        String trangThai = getTrangThai();

        return new VeTau(maVe, idKhachHang, idLichTrinh, idGhe, idNhanVien, ngayDat, giaVe, trangThai );
    }

    public void startEditMode() {
        fieldMaVe.setEnabled(false);

        buttonThem.setEnabled(false);
        buttonSua.setEnabled(true);
        buttonXoa.setEnabled(true);
        buttonReset.setEnabled(true);
    }

    public void resetForm(){
        fieldMaVe.setEnabled(true);
        fieldMaVe.setText("");

        if (comboKhachHang.getItemCount()>0){
            comboKhachHang.setSelectedIndex(0);
        }

        if (comboLichTrinh.getItemCount()>0){
            comboLichTrinh.setSelectedIndex(0);
        }

        if (comboGhe.getItemCount()>0){
            comboGhe.setSelectedIndex(0);
        }

        if (comboNhanVien.getItemCount()>0){
            comboNhanVien.setSelectedIndex(0);
        }

        spinnerNgayDat.setValue(new Date());

        fieldGiaVe.setText("");

        fieldTrangThai.setText("");

        buttonThem.setEnabled(true);
        buttonSua.setEnabled(false);
        buttonXoa.setEnabled(false);
        buttonReset.setEnabled(true);

        if (table != null){
            table.clearSelection();
        }
    }

    public void addThemVeTauListener(ActionListener l) {buttonThem.addActionListener(l);}

    public void addSuaVeTauListener(ActionListener l) {buttonSua.addActionListener(l);}

    public void addXoaVeTauListener(ActionListener l) {buttonXoa.addActionListener(l);}

    public void addResetFormListener(ActionListener l) {buttonReset.addActionListener(l);}

    public void addTableMouseClickListener(MouseListener l) {table.addMouseListener(l);}


}
