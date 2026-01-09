package com.group3tt28.quanlybanvetau.util;

import com.group3tt28.quanlybanvetau.enums.VaiTro;
import com.group3tt28.quanlybanvetau.model.NhanVien;

public class SessionManager {

    private static NhanVien currentUser;

    public static NhanVien getCurrentUser() {
        return currentUser;
    }

    public static void startSession(NhanVien nhanVien) {
        currentUser = nhanVien;
    }

    public static void clearSession() {
        currentUser = null;
    }

    public static boolean isAdmin() {
        if (currentUser != null && currentUser.getVaiTro() != null) {
            return currentUser.getVaiTro().equalsIgnoreCase(VaiTro.ADMIN.toString());
        }
        return false;
    }

    public static boolean hasAnyRole(String... roles) {
        if (currentUser == null || currentUser.getVaiTro() == null) {
            return false;
        }

        for (String role : roles) {
            if (role.equalsIgnoreCase(currentUser.getVaiTro())) {
                return true;
            }
        }
        return false;
    }
}
