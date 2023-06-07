/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Bill;
import model.Order;
import util.DatabaseUtil;

/**
 *
 * @author Lenovo
 */
public class OrderDao {

    public void addOrder(Order order) {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "insert into Orders values (?, ?, ?, ?)";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, order.getId());
            SimpleDateFormat simpDate = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = simpDate.format(order.getOrderDate());
            pst.setDate(2, java.sql.Date.valueOf(orderDate));
            pst.setString(3, order.getUsernameId());
            pst.setBoolean(4, order.isStatus());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
    }

    public void addBill(Bill bill) {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "insert into Bills values (?, ?, ?, ? ,?)";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, bill.getIdOrder());
            pst.setString(2, bill.getIdMoTo());
            pst.setString(3, bill.getTitle());
            pst.setLong(4, bill.getPrice());
            pst.setInt(5, bill.getQuantity());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
    }

    public List<Order> orderHistory(String username) {
        Connection connec = DatabaseUtil.getConnection();
        List<Order> list = new ArrayList<>();
        try {
            String sql = "select * from Orders where UsernameId = ? order by OrderDate desc";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int total = 0;
                List<Bill> listBill = showDetailOrder(rs.getString(1));
                for (Bill bill : listBill) {
                    total += bill.getPrice() * bill.getQuantity();
                }
                list.add(new Order(rs.getString("Id"), rs.getDate("OrderDate"), rs.getString("UsernameId"), total, rs.getBoolean("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return list;
    }

    public List<Order> searchOrder(String id) {
        Connection connec = DatabaseUtil.getConnection();
        List<Order> list = new ArrayList<>();
        try {
            String sql = "select * from Orders where Id like ? order by OrderDate desc";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, "%" + id + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int total = 0;
                List<Bill> listBill = showDetailOrder(rs.getString(1));
                for (Bill bill : listBill) {
                    total += bill.getPrice() * bill.getQuantity();
                }
                list.add(new Order(rs.getString("Id"), rs.getDate("OrderDate"), rs.getString("UsernameId"), total, rs.getBoolean("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return list;
    }

    public List<Bill> showDetailOrder(String id) {
        Connection connec = DatabaseUtil.getConnection();
        List<Bill> list = new ArrayList<>();
        try {
            String sql = "select * from Bills where IdOrder = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getString(2), rs.getString(3), rs.getString(4), rs.getLong(5), rs.getInt(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return list;
    }
}
