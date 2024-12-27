package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransaksiBarangKeluar extends JFrame {
    private JTextField inputKodeBarang, inputJumlah;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel labelTanggalValue;
    private JFrame frame;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/penyimpananbarang";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public TransaksiBarangKeluar() {
        setTitle("Inventori Barang :: Transaksi Barang Keluar");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        setLayout(null);

        // Nama Petugas
        JFrame frame = new JFrame("Barang Keluar");
        JLabel labelPetugas = new JLabel("Nama Petugas:");
        labelPetugas.setBounds(20, 20, 100, 25);
        add(labelPetugas);

        JTextField inputPetugas = new JTextField();
        inputPetugas.setBounds(120, 20, 200, 25);
        add(inputPetugas);

        // Tanggal
        JLabel labelTanggal = new JLabel("Tanggal:");
        labelTanggal.setBounds(350, 20, 100, 25);
        add(labelTanggal);

        // Tanggal otomatis dari perangkat
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        labelTanggalValue = new JLabel(currentDate);
        labelTanggalValue.setBounds(420, 20, 200, 25);
        add(labelTanggalValue);

        // Tabel Barang
        tableModel = new DefaultTableModel(new Object[]{"ID", "Kode", "Nama Barang", "Jumlah"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 100, 650, 200);
        add(scrollPane);

        // Input Barang
        JLabel labelKodeBarang = new JLabel("Kode Barang:");
        labelKodeBarang.setBounds(20, 320, 100, 25);
        add(labelKodeBarang);

        inputKodeBarang = new JTextField();
        inputKodeBarang.setBounds(120, 320, 200, 25);
        add(inputKodeBarang);

        JLabel labelJumlah = new JLabel("Jumlah:");
        labelJumlah.setBounds(350, 320, 100, 25);
        add(labelJumlah);

        inputJumlah = new JTextField();
        inputJumlah.setBounds(420, 320, 100, 25);
        add(inputJumlah);

        JButton btnTambah = new JButton("OK");
        btnTambah.setBounds(550, 320, 80, 25);
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahBarang();
            }
        });
        add(btnTambah);

        // Tombol Aksi
        JButton btnHapusSemua = new JButton("Hapus Semua");
        btnHapusSemua.setBounds(20, 360, 150, 25);
        btnHapusSemua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusSemua();
            }
        });
        add(btnHapusSemua);

        JButton btnHapusDipilih = new JButton("Hapus Yang Terpilih");
        btnHapusDipilih.setBounds(200, 360, 180, 25);
        btnHapusDipilih.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusDipilih();
            }
        });
        add(btnHapusDipilih);

        JButton btnSimpan = new JButton("Simpan Transaksi");
        btnSimpan.setBounds(400, 360, 150, 25);
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanTransaksi();
            }
        });
        add(btnSimpan);

        JButton btnLoadBarang = new JButton("Load Data Barang");
        btnLoadBarang.setBounds(20, 60, 150, 25);
        btnLoadBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBarangFromDatabase();
            }
        });
        add(btnLoadBarang);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.setBounds(550, 20, 100, 25);
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                dashboard.main(null); // Kembali ke halaman login
            }
        });
        add(btnKembali);
    }

    private void tambahBarang() {
        String kodeBarang = inputKodeBarang.getText();
        String jumlah = inputJumlah.getText();

        if (!kodeBarang.isEmpty() && !jumlah.isEmpty()) {
            int rowCount = tableModel.getRowCount();
            tableModel.addRow(new Object[]{rowCount + 1, kodeBarang, "Nama Barang", jumlah});

            // Clear input fields
            inputKodeBarang.setText("");
            inputJumlah.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Kode Barang dan Jumlah harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusSemua() {
        tableModel.setRowCount(0);
    }

    private void hapusDipilih() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanTransaksi() {
        JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadBarangFromDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM barang")) {

            tableModel.setRowCount(0); // Clear existing data

            while (resultSet.next()) {
                int id = resultSet.getInt("id_barang");
                String kode = resultSet.getString("kode_barang");
                String nama = resultSet.getString("nama_barang");
                int jumlah = resultSet.getInt("stok");
                tableModel.addRow(new Object[]{id, kode, nama, jumlah});
            }

            JOptionPane.showMessageDialog(this, "Data barang berhasil dimuat!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data barang: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
