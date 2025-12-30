package com.group3tt28.quanlybanvetau.util;

import org.mindrot.jbcrypt.BCrypt;

public class BamMatKhau {

    public static String bamMatKhau(String matKhau) {
        if (matKhau != null) {
            return BCrypt.hashpw(matKhau, BCrypt.gensalt());
        }
        return null;
    }

    public static boolean checkMatKhau(String matKhau, String matKhauBam) {
        if (matKhauBam != null && matKhauBam.startsWith("$2a$")) {
            return BCrypt.checkpw(matKhau, matKhauBam);
        } else {
            throw new IllegalArgumentException("Mã băm được cung cấp không hợp lệ để so sánh!");
        }
    }
}
