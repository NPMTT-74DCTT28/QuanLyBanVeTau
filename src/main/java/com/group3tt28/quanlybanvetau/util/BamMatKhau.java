package com.group3tt28.quanlybanvetau.util;

import org.mindrot.jbcrypt.BCrypt;

public class BamMatKhau {

    public static String bamMatKhau(String matKhau) {
        return BCrypt.hashpw(matKhau, BCrypt.gensalt());
    }

    public static boolean checkMatKhau(String matKhau, String matKhauBam) {
        if (matKhauBam == null || !matKhauBam.startsWith("$2a$")) {
            throw new IllegalArgumentException("Ma bam duoc cung cap khong hop le de so sanh!");
        }
        return BCrypt.checkpw(matKhau, matKhauBam);
    }
}
