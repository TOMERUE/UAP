package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    public static void main(String[] args) {
        // Membuat frame untuk login
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.setLocationRelativeTo(null); // Center the frame

        // Label dan field untuk username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 50, 100, 25);
        loginFrame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 180, 25);
        loginFrame.add(usernameField);

        // Label dan field untuk password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 90, 100, 25);
        loginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 90, 180, 25);
        loginFrame.add(passwordField);

        // Tombol login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 140, 100, 30);
        loginFrame.add(loginButton);

        // Action listener untuk tombol login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Validasi username dan password
                if ("admin".equals(username) && "admin123".equals(password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Login berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.dispose(); // Menutup frame login

                    // Membuka halaman dashboard
                    dashboard.main(null);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Username atau Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Menampilkan frame login
        loginFrame.setVisible(true);
    }
}

class dashboard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gudang");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel untuk logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.BLUE); // Background biru
        JLabel logoLabel = new JLabel("Gudang");
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Font besar dan tebal
        logoLabel.setForeground(Color.WHITE); // Warna teks putih
        logoPanel.add(logoLabel);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margin antar tombol

        ImageIcon iconBarangMasuk = new ImageIcon("D:\\Coding\\UAP_proglan\\src\\img\\enter.png");

        JButton btnBarangMasuk = new JButton("Barang Masuk");
        JButton btnBarangKeluar = new JButton("Barang Keluar");
        JButton btnDataBarang = new JButton("Data Barang");
        JButton btnKembali = new JButton("Kembali");

        btnDataBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Menutup frame dashboard
                databarang dataBarangPage = new databarang(); // Membuka halaman Data Barang
            }
        });

        btnBarangKeluar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Menutup frame dashboard
                TransaksiBarangKeluar barangKeluarPage = new TransaksiBarangKeluar(); // Membuka halaman Barang Keluar
                barangKeluarPage.setVisible(true); // Menampilkan frame Barang Keluar
            }
        });

        btnBarangMasuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Menutup frame dashboard
                TransaksiBarangMasuk barangMasukPage = new TransaksiBarangMasuk(); // Membuka halaman Barang Masuk
                barangMasukPage.setVisible(true); // Menampilkan frame Barang Masuk
            }
        });

        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                login.main(null); // Kembali ke halaman login
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(btnBarangMasuk, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(btnBarangKeluar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(btnDataBarang, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(btnKembali, gbc);

        // Menambahkan panel ke frame
        frame.add(logoPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Menampilkan frame
        frame.setVisible(true);
    }
}
