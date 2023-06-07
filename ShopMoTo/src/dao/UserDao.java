/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.User;
import util.DatabaseUtil;

/**
 *
 * @author Lenovo
 */
public class UserDao {

    public User selectById(String id) {
        User user = null;
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "select * from Users where username = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, id.trim());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Fullname"),
                        rs.getString("Email"), rs.getString("Address"), rs.getString("Phone"), rs.getBoolean("Role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return user;
    }

    public User loginUser(String username, String password) {
        User user = null;
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "select * from Users where username = ? and password = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, username.trim());
            pst.setString(2, password.trim());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Fullname"),
                        rs.getString("Email"), rs.getString("Address"), rs.getString("Phone"), rs.getBoolean("Role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return user;
    }

    public User updateUser(User user) {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "update Users set Password = ?, Fullname = ?, Email = ?, Address = ?, Phone = ? "
                    + "where Username = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, user.getPassword().trim());
            pst.setString(2, user.getFullname().trim());
            pst.setString(3, user.getEmail().trim());
            pst.setString(4, user.getAddress().trim());
            pst.setString(5, user.getPhone().trim());
            pst.setString(6, user.getUsername().trim());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return user;
    }

    public void registerUser(User user) {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "insert into Users values(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, user.getUsername().trim());
            pst.setString(2, user.getPassword().trim());
            pst.setString(3, user.getFullname().trim());
            pst.setString(4, user.getEmail().trim());
            pst.setString(5, user.getAddress().trim());
            pst.setString(6, user.getPhone().trim());
            pst.setBoolean(7, user.isRole());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
    }

    public String revoeryByEmail(String fullname, String email) {
        String maXN = "";
        final String tk = "testtest86n@gmail.com";
        final String mk = "mvqxbguzhczgntoj";
        // Tạo đối tượng Properties và chỉ định thông tin host, port
        try {
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");

            // Tạo đối tượng Session session (phiên làm việc)
            Session session = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // User va password nguoi gui
                    return new PasswordAuthentication(tk, mk);
                }
            });

            // Tạo đối tượng messeage
            MimeMessage msg = new MimeMessage(session);
            // Nguoi gui email
            msg.setFrom(new InternetAddress(tk));
            // Gui den email nao [To (Chi 1 nguoi), CC (Nhieu nhung thay email nguoi khac).
            // BCC (Nhieu nhung khong thay)
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email + ""));
            // Tieu de muon gui
            msg.setSubject("Thông tin mã xác thực đặt lại mật khẩu", "UTF-8");
            // Noi dung
            Random rd = new Random();
            for (int i = 0; i < 6; i++) {
                maXN += String.valueOf(rd.nextInt(9) + 0);
            }
            String noidung = "Chào " + fullname + ".<br>"
                    + "Chúng tôi nhận được yêu cầu lấy lại mật khẩu của bạn qua email. Để hoàn tất quá trình lấy lại mật khẩu, vui lòng sử dụng mã xác nhận sau đây để đặt lại mật khẩu của bạn:<br>"
                    + "Mã xác nhận: <span style=\"color: skyblue; font-size: 16px; font-weight: bold;\">" + maXN
                    + "</span><br>"
                    + "Vui lòng nhập mã xác nhận này vào trang đặt lại mật khẩu của chúng tôi để tiếp tục quá trình đặt lại mật khẩu của bạn.<br>"
                    + "Đây là email gửi tự động, vui lòng không phản hồi.<br>"
                    + "Nếu bạn không gửi yêu cầu này, vui lòng bỏ qua email này.<br>" + "Xin chân thành cảm ơn !";
            msg.setContent(noidung, "text/html; charset=utf-8");
            // => Gui di
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return maXN;
    }

    public boolean changePasswordUser(User user, String password) {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "update Users set Password = ? where Username = ?";
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, password.trim());
            pst.setString(2, user.getUsername());
            int check = pst.executeUpdate();
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
        return false;
    }
}
