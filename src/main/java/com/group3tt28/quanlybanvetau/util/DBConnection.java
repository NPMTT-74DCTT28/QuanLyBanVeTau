/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.group3tt28.quanlybanvetau.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author qphwn
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/quan_ly_ban_ve_tau";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Loi: Khong tim thay Driver MySQL! Kiem tra file pom.xml.");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Loi: Khong the ket noi den CSDL!");
            throw new RuntimeException(e);
        }
        return conn;
    }
}
