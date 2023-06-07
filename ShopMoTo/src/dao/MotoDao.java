/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import model.Moto;
import java.sql.*;
import java.util.ArrayList;
import util.DatabaseUtil;

/**
 *
 * @author Lenovo
 */
public class MotoDao {

    public List<Moto> getAllMoTO() {
        List<Moto> list = new ArrayList<>();
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "select * from Motos";
            PreparedStatement pst = connec.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Moto(rs.getString("Id"), rs.getString("Title"), rs.getInt("Price"),
                        rs.getString("Describe"), rs.getString("Poster")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return list;
    }

    public Moto selectById(String id) {
        Connection connec = DatabaseUtil.getConnection();
        Moto moto = null;
        try {
            String sql = "select * from Motos where Id = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            moto = new Moto(rs.getString("Id"), rs.getString("Title"), rs.getInt("Price"),
                    rs.getString("Describe"), rs.getString("Poster"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return moto;
    }
}
