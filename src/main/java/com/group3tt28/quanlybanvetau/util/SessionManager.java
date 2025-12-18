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

    public static void setCurrentUser(NhanVien nhanVien) {
        currentUser = nhanVien;
    }

    public static NhanVien getCurrentUser() {
        return currentUser;
    }

    public static boolean hasAnyRole(String... roles) {
        if (currentUser == null) {
            return false;
        }

        for (String role : roles) {
            if (role.equalsIgnoreCase(currentUser.getVaiTro())) {
                return true;
            }
        }
        return false;
    }

    public static void clearCurrentUser() {
        currentUser = null;
    }
}
