/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.admin;
import controller.cart;
import controller.change_password;
import controller.forgot_password;
import controller.login;
import controller.order_history;
import controller.create_account;
import controller.order_detail;
import controller.product_detail;
import controller.update_informaction;
import dao.MotoDao;
import dao.OrderDao;
import dao.UserDao;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Bill;
import model.Moto;
import model.Order;
import model.User;
import util.DatabaseUtil;

/**
 *
 * @author Lenovo
 */
public class index extends javax.swing.JFrame {

    private List<Moto> list;
    private Order order;
    private final List<Bill> listBill = new ArrayList<>();
    private User user;
    private final CardLayout cardlayout;
    private login login;
    int columnSP = 3;
    int countOrder = 0;
    JButton datMua;
    JButton xemChiTiet;
    Font font = new Font("Arial", Font.PLAIN, 16);

    public index() {
        initComponents();
        setTitle("Shop Moto");
        setLocationRelativeTo(null);
        setResizable(false);
        cardlayout = (CardLayout) panelLogin.getLayout();
        MotoDao dao = new MotoDao();
        list = dao.getAllMoTO();
        listMoto();
    }

    private void listMoto() {
        int rows = list.size() / columnSP;
        if (list.size() % columnSP != 0) {
            rows++;
        }
        if (list.size() <= 3) {
            panelMoTo.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        } else {
            panelMoTo.setLayout(new GridLayout(rows, columnSP, 10, 10));
        }

        scrollPaneMoTo.getVerticalScrollBar().setUnitIncrement(18); // toc do cuon chuot
        NumberFormat numb = NumberFormat.getInstance(new Locale("Vi", "VN"));
        for (int i = 0; i < list.size(); i++) {
            JPanel panel = new JPanel();
            JLabel lblImage = new JLabel();
            // Poster
            Image image = new ImageIcon(getClass().getResource("/image/" + list.get(i).getPoster())).getImage();
            lblImage.setIcon(new ImageIcon(image.getScaledInstance(260, 160, Image.SCALE_SMOOTH)));
            JPanel panelImage = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelImage.add(lblImage);

            // Title
            JLabel lblTitle = new JLabel(list.get(i).getTitle());
            lblTitle.setFont(font);
            JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelTitle.add(lblTitle);

            // Price
            JLabel lblPrice = new JLabel("Price: " + numb.format(list.get(i).getPrice()) + " VNĐ");
            lblPrice.setFont(font);
            JPanel panelPrice = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelPrice.add(lblPrice);

            // btn dat mua va xem chi tiet
            datMua = new JButton("Order");
            datMua.setFont(font);
            datMua.setCursor(new Cursor(Cursor.HAND_CURSOR));
            datMua.setName(i + "");
            datMua.addActionListener((ActionEvent e) -> {
                if (user != null) {
                    String index = ((JButton) e.getSource()).getName();
                    countOrder++; // dem so lan dat hang
                    orderMoTo(list.get(Integer.parseInt(index)).getId(), list.get(Integer.parseInt(index)).getTitle(),
                            list.get(Integer.parseInt(index)).getPrice());
                } else {
                    loginUser();
                }
            });

            xemChiTiet = new JButton("See details");
            xemChiTiet.setFont(font);
            xemChiTiet.setCursor(new Cursor(Cursor.HAND_CURSOR));
            xemChiTiet.setName(i + "");
            xemChiTiet.addActionListener((ActionEvent e) -> {
                String index = ((JButton) e.getSource()).getName();
                showDetailMoTo(list.get(Integer.parseInt(index)).getId(), list.get(Integer.parseInt(index)).getTitle(),
                        list.get(Integer.parseInt(index)).getPrice(), list.get(Integer.parseInt(index)).getPoster(),
                        list.get(Integer.parseInt(index)).getDescribe());
            });

            JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBtn.add(datMua);
            panelBtn.add(xemChiTiet);

            panel.add(panelImage);
            panel.add(panelTitle);
            panel.add(panelPrice);
            panel.add(panelBtn);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panelMoTo.add(panel);
        }
    }

    private void checkLogin() {
        UserDao dao = new UserDao();
        // truyen du lieu vao user
        user = dao.loginUser(login.textUserName.getText(), login.textPassword.getText());
        if (user != null) {
            login.dispose(); // tat form login
            comboUser.removeAllItems();
            comboUser.addItem("Hello: " + user.getUsername());
            // Neu role la admin thi them item Admin
            if (user.isRole() == true) {
                comboUser.addItem("Admin");
            }
            comboUser.addItem("Profile");
            comboUser.addItem("Order History");
            comboUser.addItem("Logout");
            comboUser.setSelectedItem(user.getUsername());
            cardlayout.next(panelLogin); // Chuyen den comboUse
        } else {
            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu không chính xác");
        }
    }

    private void loginUser() {
        login = new login();
        login.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // chi tat form login

        // Login
        login.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin();
            }
        });

        // Evt khi nhap xong bam enter se thuc hien login
        login.textUserName.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) { // nut Enter
                    checkLogin();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        login.textPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) { // nut Enter
                    checkLogin();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Cancel
        login.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login.dispose();
            }
        });

        // Forgot password
        login.btnForgotPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forgotPassword();
            }
        });

        // create account
        login.btnCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        login.setVisible(true); // mo form user
    }

    private void orderMoTo(String idMoTo, String title, long price) {
        // Order
        if (countOrder == 1) {
            String id = "";
            String code = "qwertyuiopasdfghjklzxcvbnmQWERTYUIPOASDFGHJKLZXCVBNM";
            for (int i = 0; i < 9; i++) {
                Random rd = new Random();
                id += code.charAt(rd.nextInt(code.length()) + 0);
            }
            order = new Order();
            order.setId(id);
            order.setOrderDate(new Date());
            order.setUsernameId(user.getUsername());
        }

        // Bill
        int flag = 0;
        for (int i = 0; i < listBill.size(); i++) {
            if (idMoTo.equals(listBill.get(i).getIdMoTo())) {
                int quantity = listBill.get(i).getQuantity();
                quantity += 1;
                listBill.set(i, new Bill(order.getId(), idMoTo, title, price, quantity));
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            listBill.add(new Bill(order.getId(), idMoTo, title, price, 1));
        }

        // total
        long total = 0;
        for (Bill bill : listBill) {
            total += bill.getPrice() * bill.getQuantity();
        }
        order.setTotal(total);
    }

    private void carts() {
        NumberFormat numb = NumberFormat.getInstance(new Locale("Vi", "VN"));
        cart cart = new cart();
        cart.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        for (int i = 0; i < listBill.size(); i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
            panel.setSize(cart.panelCart.getWidth(), 30);

            JLabel motoId = new JLabel("ID: " + listBill.get(i).getIdMoTo());
            motoId.setFont(font);

            JLabel title = new JLabel("Name: " + listBill.get(i).getTitle());
            title.setFont(font);

            JLabel price = new JLabel("Price: " + numb.format(listBill.get(i).getPrice()) + " VNÐ");
            price.setFont(font);

            JPanel panelQ = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JLabel labelQtity = new JLabel("Quantity:");
            labelQtity.setFont(font);

            JTextField quantity = new JTextField(4);
            quantity.setText(listBill.get(i).getQuantity() + "");
            quantity.setHorizontalAlignment((int) CENTER_ALIGNMENT);
            quantity.setFont(new Font("Arial", Font.PLAIN, 14));
            quantity.setName(i + "");
            quantity.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (quantity.getText().equals("") || quantity.getText().equals("-")) {

                    } else if (Integer.parseInt(quantity.getText()) <= 0 && e.getKeyCode() != 10) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số lượng lớn hơn 0 !");
                    } else {
                        String index = ((JTextField) e.getSource()).getName();
                        listBill.set(Integer.parseInt(index), new Bill(listBill.get(Integer.parseInt(index)).getIdOrder(), listBill.get(Integer.parseInt(index)).getIdMoTo(),
                                list.get(Integer.parseInt(index)).getTitle(), listBill.get(Integer.parseInt(index)).getPrice(), Integer.parseInt(quantity.getText())));
                        long total = 0;
                        for (Bill bill : listBill) {
                            total += bill.getPrice() * bill.getQuantity();
                        }
                        order.setTotal(total);
                        cart.labelToTal.setText("Total: " + numb.format(order.getTotal()) + " VNÐ");
                    }
                }
            });
            panelQ.add(labelQtity);
            panelQ.add(quantity);

            panel.add(motoId);
            panel.add(title);
            panel.add(price);
            panel.add(panelQ);
            cart.panelCart.add(panel);
        }

        cart.labelAddress.setText("Adress: " + user.getAddress());
        cart.labelPhone.setText("Phone: " + user.getPhone());
        if (order != null) {
            cart.labelToTal.setText("Total: " + numb.format(order.getTotal()) + " VNÐ");
        }

        cart.btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (order != null) {
                    OrderDao dao = new OrderDao();
                    dao.addOrder(order);
                    for (Bill bill : listBill) {
                        dao.addBill(new Bill(bill.getIdOrder(), bill.getIdMoTo(), bill.getTitle(), bill.getPrice(), bill.getQuantity()));
                    }

                    JOptionPane.showMessageDialog(null, "Đặt hàng thành công !");
                    order = null;
                    listBill.clear();
                    cart.labelToTal.setText("");
                    countOrder = 0;
                    cart.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa có đơn hàng nào được đặt !");
                }
            }
        });

        cart.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cf = JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn hủy đặt hàng ?");
                if (cf == JOptionPane.YES_OPTION) {
                    order = null;
                    listBill.clear();
                    cart.labelToTal.setText("");
                    countOrder = 0;
                    cart.dispose();
                }
            }
        });
        cart.setVisible(true);
    }

    private void showDetailMoTo(String idMoTo, String title, long price, String poster, String describe) {
        product_detail detail = new product_detail();
        detail.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        NumberFormat numb = NumberFormat.getInstance(new Locale("Vi", "VN"));
        Image image = new ImageIcon(getClass().getResource("/image/" + poster)).getImage();
        detail.labelPoster.setIcon(new ImageIcon(image.getScaledInstance(detail.labelPoster.getWidth(), detail.labelPoster.getHeight(), Image.SCALE_SMOOTH)));
        detail.labelDetails.setText("<html>" + "<Strong style=\"font-size: 20px;\">" + title + "</Strong> <br>"
                + "<div style=\"font-size: 16px;\">Giá bán: " + numb.format(price) + " VNÐ</div>"
                + "THÔNG SỐ KỸ THUẬT:"
                + describe.replaceAll("-", "<br>- ") + "</html>");

        detail.btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (user != null) {
                    countOrder++;
                    orderMoTo(idMoTo, title, price);
                } else {
                    loginUser();
                }
            }
        });

        detail.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detail.dispose();
            }
        });
        detail.setVisible(true);
    }

    private void forgotPassword() {
        forgot_password forgot = new forgot_password();
        forgot.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        UserDao dao = new UserDao();
        forgot.btnResetPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User username = dao.selectById(forgot.textUsername.getText());
                if (username != null) {
                    changePassword(forgot, username);
                    JOptionPane.showMessageDialog(null, "Chúng tôi đã gửi mã xác nhận qua email " + username.getEmail() + ". Vui lòng kiểm tra hộp thư !");
                } else {
                    JOptionPane.showMessageDialog(null, "Username không tồn tại trong hệ thống !");
                }
            }
        });

        forgot.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
                forgot.dispose();
            }
        });

        forgot.btnCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
                forgot.dispose();
            }
        });
        login.setVisible(false);
        forgot.setVisible(true);
    }

    private void changePassword(forgot_password forgot, User username) {
        change_password change = new change_password();
        change.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        UserDao dao = new UserDao();
        String codeCF = dao.revoeryByEmail(username.getFullname(), username.getEmail());
        change.btnChangePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (codeCF.equals(change.textVCCode.getText().trim())) {
                    if (change.textNewPassword.getText().isEmpty() || change.textConfirmPW.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng không để trống mật khẩu !");
                    } else {
                        if (change.textNewPassword.getText().trim().equals(change.textConfirmPW.getText().trim())) {
                            boolean check = dao.changePasswordUser(username, change.textNewPassword.getText());
                            if (check == true) {
                                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công !");
                                change.dispose();
                                login.setVisible(true);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Mật khẩu chưa khớp !");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mã xác nhận chưa đúng !");
                }
            }
        });

        change.btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                change.dispose();
                forgot.setVisible(true);
            }
        });
        forgot.setVisible(false);
        change.setVisible(true);
    }

    private void createAccount() {
        create_account createAcc = new create_account();
        UserDao dao = new UserDao();
        createAcc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        createAcc.btnCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createAcc.textUsername.getText().isEmpty()
                        || createAcc.textPassword.getText().isEmpty()
                        || createAcc.textFullname.getText().isEmpty()
                        || createAcc.textEmail.getText().isEmpty()
                        || createAcc.textAddress.getText().isEmpty()
                        || createAcc.textPhone.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng không để trống thông tin đăng ký !");
                } else {
                    User userId = dao.selectById(createAcc.textUsername.getText());
                    if (userId == null) {
                        dao.registerUser(new User(createAcc.textUsername.getText(), createAcc.textPassword.getText(),
                                createAcc.textFullname.getText(), createAcc.textEmail.getText(),
                                createAcc.textAddress.getText(), createAcc.textPhone.getText(), false));
                        JOptionPane.showMessageDialog(null, "Đăng ký thành công !");
                    } else {
                        JOptionPane.showMessageDialog(null, "Username đã tồn tại !");
                    }
                }
            }
        });

        createAcc.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAcc.textUsername.setText("");
                createAcc.textPassword.setText("");
                createAcc.textFullname.setText("");
                createAcc.textEmail.setText("");
                createAcc.textAddress.setText("");
                createAcc.textPhone.setText("");
            }
        });

        createAcc.btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
                createAcc.dispose();
            }
        });

        login.setVisible(false);
        createAcc.setVisible(true);
    }

    private void profileUser() {
        update_informaction update = new update_informaction();
        UserDao dao = new UserDao();
        update.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        update.textUsername.setEnabled(false);
        update.textUsername.setText(user.getUsername());
        update.textPassword.setText(user.getPassword());
        update.textFullname.setText(user.getFullname());
        update.textEmail.setText(user.getEmail());
        update.textAddress.setText(user.getAddress());
        update.textPhone.setText(user.getPhone());

        update.btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = dao.updateUser(new User(user.getUsername(), update.textPassword.getText(), update.textFullname.getText(),
                        update.textEmail.getText(), update.textAddress.getText(), update.textPhone.getText(), user.isRole()));
                JOptionPane.showMessageDialog(null, "Cập nhật thành công !");
            }
        });

        update.btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update.textUsername.setText(user.getUsername());
                update.textPassword.setText(user.getPassword());
                update.textFullname.setText(user.getFullname());
                update.textEmail.setText(user.getEmail());
                update.textAddress.setText(user.getAddress());
                update.textPhone.setText(user.getPhone());
            }
        });

        update.setVisible(true);
    }

    private void orderHistory() {
        order_history orderHT = new order_history();
        orderHT.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        OrderDao dao = new OrderDao();
        NumberFormat numb = NumberFormat.getInstance(new Locale("Vi", "VN"));
        List<Order> listOrder = dao.orderHistory(user.getUsername());
        DefaultTableModel modelOrder = new DefaultTableModel();
        modelOrder.setColumnIdentifiers(new Object[]{"ID", "Order Date", "Total", "Status"});
        for (Order o : listOrder) {
            modelOrder.addRow(new Object[]{o.getId(), o.getOrderDate(), numb.format(o.getTotal()), o.isStatus() == true ? "approved" : "not approved"});
        }
        orderHT.tableHistory.setModel(modelOrder);

        orderHT.tableHistory.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                order_detail orderDT = new order_detail();
                orderDT.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                int row = orderHT.tableHistory.getSelectedRow();
                List<Bill> listBill = dao.showDetailOrder(orderHT.tableHistory.getValueAt(row, 0) + "");
                DefaultTableModel modelBill = new DefaultTableModel();
                modelBill.setColumnIdentifiers(new Object[]{"Moto", "Name", "Price", "Quantity"});
                for (Bill b : listBill) {
                    modelBill.addRow(new Object[]{b.getIdMoTo(), b.getTitle(), numb.format(b.getPrice()), b.getQuantity()});
                }
                orderDT.tableOrderDetail.setModel(modelBill);
                orderDT.setVisible(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        orderHT.textSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                listOrder.clear();
                modelOrder.setRowCount(0);
                List<Order> listSearch = dao.searchOrder(orderHT.textSearch.getText());
                listOrder.addAll(listSearch);
                for (Order o : listOrder) {
                    modelOrder.addRow(new Object[]{o.getId(), o.getOrderDate(), numb.format(o.getTotal()), o.isStatus() == true ? "approved" : "not approved"});
                }
                orderHT.tableHistory.setModel(modelOrder);
            }
        });
        orderHT.setVisible(true);
    }

    private void admin() {
        admin admin = new admin();
        admin.main(null);
    }

    private void searchMoTo() {
        Connection connec = DatabaseUtil.getConnection();
        try {
            String sql = "select * from Motos where Title like ?";
            list.clear();
            panelMoTo.removeAll();;
            PreparedStatement pst = connec.prepareStatement(sql);
            pst.setString(1, "%" + textSearch.getText() + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(new Moto(rs.getString("ID"), rs.getString("Title"), rs.getLong("Price"), rs.getString("Describe"), rs.getString("Poster")));
            }
            listMoto();
            // cap nhat lai giao dien
            panelMoTo.revalidate();
            panelMoTo.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(connec);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        panelMenu = new javax.swing.JPanel();
        textSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        labelLogo = new javax.swing.JLabel();
        btnCart = new javax.swing.JButton();
        panelLogin = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        comboUser = new javax.swing.JComboBox<>();
        scrollPaneMoTo = new javax.swing.JScrollPane();
        panelMoTo = new javax.swing.JPanel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(153, 204, 255));

        textSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        textSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textSearchKeyReleased(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        labelLogo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logoShop.png"))); // NOI18N

        btnCart.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnCart.setText("Cart");
        btnCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartActionPerformed(evt);
            }
        });

        panelLogin.setBackground(new java.awt.Color(153, 204, 255));
        panelLogin.setLayout(new java.awt.CardLayout());

        btnLogin.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        panelLogin.add(btnLogin, "card2");

        comboUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        comboUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboUser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboUserItemStateChanged(evt);
            }
        });
        panelLogin.add(comboUser, "card3");

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(labelLogo)
                .addGap(28, 28, 28)
                .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addGap(26, 26, 26)
                .addComponent(btnCart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelLogo)
            .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(panelMenuLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelMenuLayout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch)
                        .addComponent(btnCart))))
        );

        scrollPaneMoTo.setBorder(null);

        javax.swing.GroupLayout panelMoToLayout = new javax.swing.GroupLayout(panelMoTo);
        panelMoTo.setLayout(panelMoToLayout);
        panelMoToLayout.setHorizontalGroup(
            panelMoToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 873, Short.MAX_VALUE)
        );
        panelMoToLayout.setVerticalGroup(
            panelMoToLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 611, Short.MAX_VALUE)
        );

        scrollPaneMoTo.setViewportView(panelMoTo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrollPaneMoTo)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneMoTo, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        loginUser();
    }//GEN-LAST:event_btnLoginActionPerformed

    private void comboUserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboUserItemStateChanged
        if (evt.getStateChange() == 1) {
            String select = (String) evt.getItem();
            if (select.equals("Profile")) {
                profileUser();
            } else if (select.equals("Admin")) {
                admin();
            } else if (select.equals("Order History")) {
                orderHistory();
            } else if (select.equals("Logout")) {
                user = null;
                cardlayout.next(panelLogin);
            }
            comboUser.setSelectedIndex(0);
        }
    }//GEN-LAST:event_comboUserItemStateChanged

    private void btnCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartActionPerformed
        if (user != null) {
            carts();
        } else {
            loginUser();
        }
    }//GEN-LAST:event_btnCartActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchMoTo();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void textSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textSearchKeyReleased
        searchMoTo();
    }//GEN-LAST:event_textSearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new index().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCart;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> comboUser;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelMoTo;
    private javax.swing.JScrollPane scrollPaneMoTo;
    private javax.swing.JTextField textSearch;
    // End of variables declaration//GEN-END:variables
}
