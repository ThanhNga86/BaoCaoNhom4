/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package controller;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import model.User;
import model.Moto;

import java.util.ArrayList;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class admin extends javax.swing.JFrame {

    // Tham so ket noi
    String databaseName = "QLMoto";
    String userSQL = "sa";
    String pwSQL = "mrngapro";
    String x = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    //Bien toan cuc
    DefaultTableModel modelUser, modelMoto;
    ArrayList<User> arrUser = new ArrayList<>();
    ArrayList<Moto> arrMoto = new ArrayList<>();
    int index;
    String pathfile;
    String pathsource = "C:\\Users\\Lenovo\\Documents\\NetBeansProjects\\ShopMoTo\\src\\image";
    String filename;
    String pathPoster;
    String[] headerUser = {"Fullname", "User", "Password", "Email", "Adress", "PhoneNumber", "Role"};
    String[] headerMoto = {"ID", "Title", "Price", "Describe", "Poster"};

    public admin() {
        initComponents();
        setTitle("Shop Moto");
        setLocationRelativeTo(null);
        setResizable(false);
        filldataUser();
        fillcontrolUser(0);
        filldataMoto();
        fillcontrolMoto(0);
    }

    public void filldataUser() {
        modelUser = new DefaultTableModel(null, headerUser);
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Users");
            arrUser.clear();
            while (rs.next()) {
                String Username = rs.getString(1);
                String pw = rs.getString(2);
                String Fullname = rs.getString(3);
                String Email = rs.getString(4);
                String Address = rs.getString(5);
                String Phone = rs.getString(6);
                boolean Role = rs.getBoolean(7);

                Vector v = new Vector();
                v.add(Fullname);
                v.add(Username);
                v.add(pw);
                v.add(Email);
                v.add(Phone);
                v.add(Address);
                v.add(Role ? "NhanVien" : "KhachHang");
                modelUser.addRow(v);

                User user = new User(Username, pw, Fullname, Email, Address, Phone, Role);
                arrUser.add(user);

            }
            tableUser.setModel(modelUser);
            con.close();
        } catch (Exception e) {
            System.out.println("Connection is failed !");
            e.printStackTrace();
        }
    }

    public void fillcontrolUser(int index) {
        textFullname.setEditable(false);
        textUser.setEditable(false);
        textAdress.setEditable(false);
        textPassword.setEditable(false);
        textPhonenumber.setEditable(false);
        textEmail.setEditable(false);
        textRole.setEditable(false);

        textFullname.setText(arrUser.get(index).getFullname());
        textUser.setText(arrUser.get(index).getUsername());
        textAdress.setText(arrUser.get(index).getAddress());
        textPassword.setText(arrUser.get(index).getPassword());
        textPhonenumber.setText(arrUser.get(index).getPhone());
        textEmail.setText(arrUser.get(index).getEmail());
        textRole.setText(arrUser.get(index).isRole() ? "NhanVien" : "KhachHang");
    }

    public ImageIcon ResizeImage(String path) {
//        "/ShopMoTo/image/1."
        ImageIcon MyImage = new javax.swing.ImageIcon(path);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(ImageMoto.getWidth(), ImageMoto.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    public void filldataMoto() {
        modelMoto = new DefaultTableModel(null, headerMoto);
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Motos");
            arrMoto.clear();
            while (rs.next()) {
                String Id = rs.getString(1);
                String Title = rs.getString(2);
                int Price = rs.getInt(3);
                String Describe = rs.getString(4);
                String Poster = rs.getString(5);

                Vector v = new Vector();
                v.add(Id);
                v.add(Title);
                v.add(Price);
                v.add(Describe);
                v.add(Poster);
                modelMoto.addRow(v);

                Moto moto = new Moto(Id, Title, Price, Describe, Poster);
                arrMoto.add(moto);

            }
            tableMoto.setModel(modelMoto);
            con.close();
        } catch (Exception e) {
            System.out.println("Connection is failed !");
            e.printStackTrace();
        }
    }

    public void fillcontrolMoto(int i) {
        textMotoId.setEditable(false);
        textMotoName.setEditable(false);
        textMotoPrice.setEditable(false);
        textDescribe.setEditable(false);

        textMotoId.setText(arrMoto.get(i).getId());
        textMotoName.setText(arrMoto.get(i).getTitle());
        textMotoPrice.setText(String.valueOf(arrMoto.get(i).getPrice()));
        textDescribe.setText(arrMoto.get(i).getDescribe());
        pathPoster = arrMoto.get(i).getPoster();
        ImageMoto.setIcon(ResizeImage(pathsource + "\\" + pathPoster));
    }

    public void addImageMoto() throws IOException {
        FileInputStream in = new FileInputStream(pathfile);
        FileOutputStream ou = new FileOutputStream(pathsource + "\\" + filename);
        BufferedInputStream bin = new BufferedInputStream(in);
        BufferedOutputStream bou = new BufferedOutputStream(ou);
        int b = 0;
        while (b != -1) {
            b = bin.read();
            bou.write(b);
        }
        bin.close();
        bou.close();
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JTabbedPane();
        menuUsers = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFullname = new javax.swing.JTextField();
        textUser = new javax.swing.JTextField();
        textPassword = new javax.swing.JTextField();
        textEmail = new javax.swing.JTextField();
        textAdress = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        textSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        textPhonenumber = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        textRole = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        menuMotos = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        textMotoName = new javax.swing.JTextField();
        textMotoPrice = new javax.swing.JTextField();
        ImageMoto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textDescribe = new javax.swing.JTextArea();
        btnMotoUpdate = new javax.swing.JButton();
        btnMotoDelete = new javax.swing.JButton();
        btnMotoReset = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMoto = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        textMotoId = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnMotoReset1 = new javax.swing.JButton();
        btnMotoAdd = new javax.swing.JButton();
        textMotoSearch = new javax.swing.JTextField();
        btnMotoSearch = new javax.swing.JButton();
        menuReports = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableOrders = new javax.swing.JTable();
        btnOrdersUpdate = new javax.swing.JButton();
        btnOrdersDelete = new javax.swing.JButton();
        btnOrdersReset = new javax.swing.JButton();
        textOrdersSearch = new javax.swing.JTextField();
        btnOrdersSearch = new javax.swing.JButton();
        menuOrders = new javax.swing.JPanel();

        menu.setBackground(new java.awt.Color(153, 204, 255));
        menu.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        menu.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Full name:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("User name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Password:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Email:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Phone number:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Adress:");
        jLabel6.setToolTipText("");

        textFullname.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEmailActionPerformed(evt);
            }
        });

        textAdress.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textAdress.setToolTipText("");

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableUser);

        textSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textSearchActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        textPhonenumber.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textPhonenumber.setToolTipText("");

        btnUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText(" Role :");
        jLabel11.setToolTipText("");

        textRole.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textRole.setToolTipText("");

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuUsersLayout = new javax.swing.GroupLayout(menuUsers);
        menuUsers.setLayout(menuUsersLayout);
        menuUsersLayout.setHorizontalGroup(
            menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuUsersLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 691, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
            .addGroup(menuUsersLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(menuUsersLayout.createSequentialGroup()
                        .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuUsersLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34)
                        .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFullname, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textUser, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPhonenumber, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(menuUsersLayout.createSequentialGroup()
                        .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(menuUsersLayout.createSequentialGroup()
                                .addComponent(btnUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, menuUsersLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(textRole, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuUsersLayout.setVerticalGroup(
            menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuUsersLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(menuUsersLayout.createSequentialGroup()
                        .addComponent(textFullname)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textUser))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPhonenumber))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textAdress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textRole))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuUsersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        menu.addTab("Users", menuUsers);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Name :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Price :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Describe :");

        textMotoName.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        textMotoPrice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        ImageMoto.setBackground(new java.awt.Color(255, 255, 255));
        ImageMoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ImageMoto.setText("Imgae");

        textDescribe.setColumns(20);
        textDescribe.setRows(5);
        jScrollPane2.setViewportView(textDescribe);

        btnMotoUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMotoUpdate.setText("Update");
        btnMotoUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoUpdateActionPerformed(evt);
            }
        });

        btnMotoDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMotoDelete.setText("Delete");
        btnMotoDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoDeleteActionPerformed(evt);
            }
        });

        btnMotoReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMotoReset.setText("Reset");
        btnMotoReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoResetActionPerformed(evt);
            }
        });

        tableMoto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tableMoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMotoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableMoto);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("ID :");

        textMotoId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Select Poster");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnMotoReset1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMotoReset1.setText("Edit");
        btnMotoReset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoReset1ActionPerformed(evt);
            }
        });

        btnMotoAdd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMotoAdd.setText("Add");
        btnMotoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoAddActionPerformed(evt);
            }
        });

        textMotoSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnMotoSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnMotoSearch.setText("Search");
        btnMotoSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMotoSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuMotosLayout = new javax.swing.GroupLayout(menuMotos);
        menuMotos.setLayout(menuMotosLayout);
        menuMotosLayout.setHorizontalGroup(
            menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuMotosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(menuMotosLayout.createSequentialGroup()
                        .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(menuMotosLayout.createSequentialGroup()
                                .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(menuMotosLayout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(textMotoPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(menuMotosLayout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(textMotoName, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(menuMotosLayout.createSequentialGroup()
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jScrollPane2)))
                                    .addGroup(menuMotosLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(textMotoId, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(13, 13, 13)
                                .addComponent(ImageMoto, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(menuMotosLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(btnMotoAdd)
                                .addGap(18, 18, 18)
                                .addComponent(btnMotoUpdate)
                                .addGap(18, 18, 18)
                                .addComponent(btnMotoDelete)
                                .addGap(18, 18, 18)
                                .addComponent(btnMotoReset)
                                .addGap(18, 18, 18)
                                .addComponent(btnMotoReset1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)))
                        .addGap(0, 25, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(menuMotosLayout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(textMotoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnMotoSearch)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuMotosLayout.setVerticalGroup(
            menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuMotosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(menuMotosLayout.createSequentialGroup()
                        .addGap(0, 50, Short.MAX_VALUE)
                        .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(textMotoId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(textMotoName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(textMotoPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(ImageMoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnMotoUpdate)
                    .addComponent(btnMotoDelete)
                    .addComponent(btnMotoReset)
                    .addComponent(btnMotoReset1)
                    .addComponent(btnMotoAdd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuMotosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textMotoSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMotoSearch))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        menu.addTab("Motos", menuMotos);

        tableOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tableOrders);

        btnOrdersUpdate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOrdersUpdate.setText("Update");

        btnOrdersDelete.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOrdersDelete.setText("Delete");

        btnOrdersReset.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOrdersReset.setText("Reset");

        textOrdersSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnOrdersSearch.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnOrdersSearch.setText("Search");

        javax.swing.GroupLayout menuReportsLayout = new javax.swing.GroupLayout(menuReports);
        menuReports.setLayout(menuReportsLayout);
        menuReportsLayout.setHorizontalGroup(
            menuReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(menuReportsLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addComponent(btnOrdersUpdate)
                .addGap(18, 18, 18)
                .addComponent(btnOrdersDelete)
                .addGap(18, 18, 18)
                .addComponent(btnOrdersReset)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(menuReportsLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(textOrdersSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnOrdersSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuReportsLayout.setVerticalGroup(
            menuReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuReportsLayout.createSequentialGroup()
                .addContainerGap(214, Short.MAX_VALUE)
                .addGroup(menuReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOrdersUpdate)
                    .addComponent(btnOrdersDelete)
                    .addComponent(btnOrdersReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(menuReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textOrdersSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOrdersSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menu.addTab("Orders", menuReports);

        javax.swing.GroupLayout menuOrdersLayout = new javax.swing.GroupLayout(menuOrders);
        menuOrders.setLayout(menuOrdersLayout);
        menuOrdersLayout.setHorizontalGroup(
            menuOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 699, Short.MAX_VALUE)
        );
        menuOrdersLayout.setVerticalGroup(
            menuOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 526, Short.MAX_VALUE)
        );

        menu.addTab("Report", menuOrders);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textSearchActionPerformed

    private void textEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textEmailActionPerformed

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked

    }//GEN-LAST:event_menuMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            String sql = "update  Users set "
                    + "Password=?,Fullname=?, Email=?, Address=?, Phone=?, Role=? where Username=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, textPassword.getText());
            st.setString(2, textFullname.getText());
            st.setString(3, textEmail.getText());
            st.setString(4, textAdress.getText());
            st.setString(5, textPhonenumber.getText());
            st.setBoolean(6, (textRole.getText().equalsIgnoreCase("Khachhang") ? false : true));
            st.setString(7, textUser.getText());
            st.execute();
            JOptionPane.showMessageDialog(this, "Update is compelete !");
            con.close();
            filldataUser();
            fillcontrolUser(index);
            tableUser.setRowSelectionInterval(index, index);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection is fail !");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        textFullname.setEditable(true);
        textAdress.setEditable(true);
        textPassword.setEditable(true);
        textPhonenumber.setEditable(true);
        textEmail.setEditable(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked
        // TODO add your handling code here:
        index = tableUser.getSelectedRow();
        fillcontrolUser(index);
    }//GEN-LAST:event_tableUserMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        fillcontrolUser(0);
        filldataUser();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            String sql = "delete from Users where Username=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, textUser.getText());
            st.execute();
            JOptionPane.showMessageDialog(this, "Delete is compelete !");
            con.close();
            filldataUser();
            fillcontrolUser(index - 1);
            tableUser.setRowSelectionInterval(index - 1, index - 1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection is fail !");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        for (int i = 0; i < arrUser.size(); i++) {
            if (textSearch.getText().equalsIgnoreCase(arrUser.get(i).getUsername())) {
                fillcontrolUser(i);
                tableUser.setRowSelectionInterval(i, i);
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnMotoUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoUpdateActionPerformed
        // TODO add your handling code here:
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            String sql = "update  Motos set "
                    + "Title=?,Price=?,Describe=?,Poster=? where Id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, textMotoName.getText());
            st.setInt(2, Integer.parseInt(textMotoPrice.getText()));
            st.setString(3, textDescribe.getText());
            st.setString(4, pathPoster);
            st.setString(5, textMotoId.getText());
            st.execute();
            if (pathfile != null) {
                try {
                    addImageMoto();
                } catch (IOException ex) {
                    Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            JOptionPane.showMessageDialog(this, "Update is compelete !");
            con.close();
            filldataMoto();
            fillcontrolMoto(index);
            tableMoto.setRowSelectionInterval(index, index);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection is fail !");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnMotoUpdateActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here
        JFileChooser filechooser = new JFileChooser();
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnvalue = filechooser.showOpenDialog(this);
        if (returnvalue == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();
            filename = file.getName();
            pathfile = file.getPath();
            pathPoster = filename;
            ImageMoto.setIcon(ResizeImage(pathfile));
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tableMotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMotoMouseClicked
        // TODO add your handling code here:
        index = tableMoto.getSelectedRow();
        fillcontrolMoto(index);
    }//GEN-LAST:event_tableMotoMouseClicked

    private void btnMotoReset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoReset1ActionPerformed
        // TODO add your handling code here:
        textMotoId.setEditable(true);
        textMotoName.setEditable(true);
        textMotoPrice.setEditable(true);
        textDescribe.setEditable(true);
    }//GEN-LAST:event_btnMotoReset1ActionPerformed

    private void btnMotoResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoResetActionPerformed
        // TODO add your handling code here:
        filldataMoto();
        fillcontrolMoto(0);
    }//GEN-LAST:event_btnMotoResetActionPerformed

    private void btnMotoDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoDeleteActionPerformed
        // TODO add your handling code here:
        try {
            Class.forName(x);
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
            String sql = "delete from Motos where Id=?";
            PreparedStatement st1 = con.prepareStatement("delete from Bills where IdMoTo=?");
            st1.setString(1, textMotoId.getText());
            st1.execute();
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, textMotoId.getText());
            st.execute();
            JOptionPane.showMessageDialog(this, "Delete is compelete !");
            con.close();
            filldataMoto();
            if (index != 0) {
                index--;
            }
            fillcontrolMoto(index);
            tableMoto.setRowSelectionInterval(index, index);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Connection is fail !");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnMotoDeleteActionPerformed

    private void btnMotoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoAddActionPerformed
        // TODO add your handling code here:
        boolean flag = true;
        //Check co trung poster trung idMoto trong database
        for (int i = 0; i < arrMoto.size(); i++) {
            if (arrMoto.get(i).getPoster().equalsIgnoreCase(pathPoster)) {
                JOptionPane.showMessageDialog(this, "Poster da ton tai !\nHay chon lai Poster khac !");
                flag = false;
            }
            if (arrMoto.get(i).getId().equalsIgnoreCase(textMotoId.getText())) {
                JOptionPane.showMessageDialog(this, "Id is duplicate\nPlease input Id again!");
                flag = false;
            }
        }
        if (flag) {
            try {
                Class.forName(x);
                Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=QLMoto", userSQL, pwSQL);
                String sql = "insert into Motos values(?,?,?,?,?)";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, textMotoId.getText());
                st.setString(2, textMotoName.getText());
                st.setString(3, textMotoPrice.getText());
                st.setString(4, textDescribe.getText());
                st.setString(5, pathPoster);
                st.execute();
                JOptionPane.showMessageDialog(this, "Insert is compelete !");
                con.close();
                filldataMoto();
                // Tim vi tri vua duoc them vao trong data
                for (int i = 0; i < arrMoto.size(); i++) {
                    if (arrMoto.get(i).getId().equalsIgnoreCase(textMotoId.getText())) {
                        index = i;
                    }
                }
                fillcontrolMoto(index);
                tableMoto.setRowSelectionInterval(index, index);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Connection is fail !");
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnMotoAddActionPerformed

    private void btnMotoSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMotoSearchActionPerformed
        // TODO add your handling code here:
        for (int i = 0; i < arrMoto.size(); i++) {
            if (textMotoSearch.getText().equalsIgnoreCase(arrMoto.get(i).getId())
                    || textMotoSearch.getText().equalsIgnoreCase(arrMoto.get(i).getTitle())) {
                fillcontrolMoto(i);
                tableMoto.setRowSelectionInterval(i, i);
            }
        }
    }//GEN-LAST:event_btnMotoSearchActionPerformed

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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImageMoto;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnMotoAdd;
    private javax.swing.JButton btnMotoDelete;
    private javax.swing.JButton btnMotoReset;
    private javax.swing.JButton btnMotoReset1;
    private javax.swing.JButton btnMotoSearch;
    private javax.swing.JButton btnMotoUpdate;
    private javax.swing.JButton btnOrdersDelete;
    private javax.swing.JButton btnOrdersReset;
    private javax.swing.JButton btnOrdersSearch;
    private javax.swing.JButton btnOrdersUpdate;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane menu;
    private javax.swing.JPanel menuMotos;
    private javax.swing.JPanel menuOrders;
    private javax.swing.JPanel menuReports;
    private javax.swing.JPanel menuUsers;
    private javax.swing.JTable tableMoto;
    private javax.swing.JTable tableOrders;
    private javax.swing.JTable tableUser;
    private javax.swing.JTextField textAdress;
    private javax.swing.JTextArea textDescribe;
    private javax.swing.JTextField textEmail;
    private javax.swing.JTextField textFullname;
    private javax.swing.JTextField textMotoId;
    private javax.swing.JTextField textMotoName;
    private javax.swing.JTextField textMotoPrice;
    private javax.swing.JTextField textMotoSearch;
    private javax.swing.JTextField textOrdersSearch;
    private javax.swing.JTextField textPassword;
    private javax.swing.JTextField textPhonenumber;
    private javax.swing.JTextField textRole;
    private javax.swing.JTextField textSearch;
    private javax.swing.JTextField textUser;
    // End of variables declaration//GEN-END:variables
}
