/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.util;

import com.group3tt28.quanlybanvetau.model.NhanVien;

/**
 *
 * @author qphwn
 */
public class SessionManager {
    
    private static NhanVien currentUser;
    
    public static void login(NhanVien nhanVien) {
        currentUser = nhanVien;
    }
    
    public static void logout() {
        currentUser = null;
    }

    public static NhanVien getCurrentUser() {
        return currentUser;
    }
    
    public static boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(currentUser.getVaiTro());
    }
}
