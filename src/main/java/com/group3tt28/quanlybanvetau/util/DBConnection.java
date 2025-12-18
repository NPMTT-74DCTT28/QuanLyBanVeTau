/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author qphwn
 */
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
            System.err.println("Loi: Khong tim thay Driver MySQL! Kiem tra file pom.xml.");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Loi: Khong tim thay file db.properties!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Loi: Sai thong tin ket noi CSDL! Kiem tra lai file db.properties");
            throw new RuntimeException(e);
        }
        return conn;
    }
}
