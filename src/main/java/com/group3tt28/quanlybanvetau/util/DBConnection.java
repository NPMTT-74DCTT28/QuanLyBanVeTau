package com.group3tt28.quanlybanvetau.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties p = new Properties();
            InputStream is = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            p.load(is);

            String url = p.getProperty("db.url");
            String user = p.getProperty("db.user");
            String password = p.getProperty("db.password");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy driver MySQL! Kiểm tra file pom.xml.", e);
        } catch (IOException e) {
            throw new RuntimeException("Không tìm thấy file db.properties.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Sai thông tin kết nối CSDL! Kiểm tra lại file db.properties.", e);
        }
        return conn;
    }
}
