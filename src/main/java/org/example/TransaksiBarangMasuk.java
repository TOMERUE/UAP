package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransaksiBarangMasuk extends JFrame {
    private JTextField inputKodeBarang, inputJumlah;
    private JComboBox<String> comboSupplier;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel labelTanggalValue;
    private JFrame frame;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/penyimpananbarang";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public TransaksiBarangMasuk() {
        setTitle("Inventori Barang :: Transaksi Barang Masuk");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        loadSuppliers();
    }

    private void initUI() {
        setLayout(null);

        // Tanggal otomatis dari perangkat
        JFrame frame = new JFrame("Barang Masuk");
        JLabel labelTanggal = new JLabel("Tanggal:");
        labelTanggal.setBounds(20, 20, 100, 25);
        add(labelTanggal);

        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        labelTanggalValue = new JLabel(currentDate);
        labelTanggalValue.setBounds(100, 20, 200, 25);
        add(labelTanggalValue);

        // Nama Supplier
        JLabel labelSupplier = new JLabel("Nama Supplier:");
        labelSupplier.setBounds(20, 60, 100, 25);
        add(labelSupplier);

        comboSupplier = new JComboBox<>(new String[]{"Pilih"});
        comboSupplier.setBounds(120, 60, 200, 25);
        add(comboSupplier);

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

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(550, 320, 100, 25);
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

        JButton btnKembali = new JButton("Kembali");

        btnKembali.setBounds(550, 20, 100, 25);
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                dashboard.main(null); // Kembali ke halaman login
            }
        });
        frame.setVisible(true);

        add(btnKembali);
    }

    private void loadSuppliers() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nama_supplier FROM supplier")) {

            while (rs.next()) {
                comboSupplier.addItem(rs.getString("nama_supplier"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading suppliers: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tambahBarang() {
        String kodeBarang = inputKodeBarang.getText();
        String jumlah = inputJumlah.getText();

        if (!kodeBarang.isEmpty() && !jumlah.isEmpty()) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 PreparedStatement ps = conn.prepareStatement("SELECT nama_barang FROM barang WHERE kode_barang = ?")) {

                ps.setString(1, kodeBarang);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String namaBarang = rs.getString("nama_barang");
                    int rowCount = tableModel.getRowCount();
                    tableModel.addRow(new Object[]{rowCount + 1, kodeBarang, namaBarang, jumlah});

                    // Clear input fields
                    inputKodeBarang.setText("");
                    inputJumlah.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Kode Barang tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding item: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String tanggal = labelTanggalValue.getText();

            conn.setAutoCommit(false);

            String insertTransaksiSQL = "INSERT INTO transaksi_masuk (tanggal) VALUES (?)";
            try (PreparedStatement psTransaksi = conn.prepareStatement(insertTransaksiSQL, Statement.RETURN_GENERATED_KEYS)) {
                psTransaksi.setString(1, tanggal);
                psTransaksi.executeUpdate();

                ResultSet generatedKeys = psTransaksi.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int transaksiId = generatedKeys.getInt(1);

                    String insertDetailSQL = "INSERT INTO transaksi_masuk (id_transaksi, kode_barang, jumlah) VALUES (?, ?, ?)";
                    try (PreparedStatement psDetail = conn.prepareStatement(insertDetailSQL)) {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            psDetail.setInt(1, transaksiId);
                            psDetail.setString(2, (String) tableModel.getValueAt(i, 1));
                            psDetail.setInt(3, Integer.parseInt((String) tableModel.getValueAt(i, 3)));
                            psDetail.addBatch();
                        }
                        psDetail.executeBatch();
                    }

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    hapusSemua();
                } else {
                    throw new SQLException("Failed to retrieve transaction ID.");
                }
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving transaction: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
