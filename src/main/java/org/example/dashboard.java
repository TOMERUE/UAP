package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboard {
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

        JButton btnBarangMasuk = new JButton("Barang Masuk");
        JButton btnBarangKeluar = new JButton("Barang Keluar");
        JButton btnDataBarang = new JButton("Data Barang");

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



        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(btnBarangMasuk, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(btnBarangKeluar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(btnDataBarang, gbc);

        // Menambahkan panel ke frame
        frame.add(logoPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Menampilkan frame
        frame.setVisible(true);
    }

}

