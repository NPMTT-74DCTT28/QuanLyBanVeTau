package com.group3tt28.quanlybanvetau.util;

import com.group3tt28.quanlybanvetau.model.NhanVien;

public class SessionManager {

    private static NhanVien currentUser;

    public static NhanVien getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(NhanVien nhanVien) {
        currentUser = nhanVien;
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
