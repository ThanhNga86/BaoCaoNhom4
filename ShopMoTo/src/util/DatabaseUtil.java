/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class DatabaseUtil {

    public static Connection getConnection() {
        Connection connec = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connec = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoTo", "sa", "mrngapro");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ket noi that bai !");
            e.printStackTrace();
        }
        return connec;
    }

    public static void closeConnection(Connection connec) {
        try {
            if (connec != null) {
                connec.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
