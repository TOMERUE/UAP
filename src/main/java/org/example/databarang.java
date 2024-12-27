package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.sql.SQLException;

public class databarang {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/penyimpananbarang";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    public databarang() {
        JFrame frame = new JFrame("Data Barang");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Tabel untuk menampilkan data barang
        String[] columnNames = {"ID", "Kode", "Nama", "Kategori", "Stok", "Satuan"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        loadData(tableModel);

        // Panel tombol aksi
        JPanel actionPanel = new JPanel();
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnCetakLaporan = new JButton("Cetak Laporan");
        JButton btnKembali = new JButton("Kembali"); // Tombol Kembali

        actionPanel.add(btnTambah);
        actionPanel.add(btnUbah);
        actionPanel.add(btnHapus);
        actionPanel.add(btnCetakLaporan);
        actionPanel.add(btnKembali);

        // Event untuk tombol tambah
        btnTambah.addActionListener(e -> {
            JDialog dialog = new JDialog(frame, "Tambah Data", true);
            dialog.setSize(400, 300);
            dialog.setLayout(new GridLayout(6, 2, 10, 10));

            JTextField txtKode = new JTextField();
            JTextField txtNama = new JTextField();
            JComboBox<String> cmbKategori = new JComboBox<>(new String[]{"Pilih", "Barang Rumah Tangga", "Barang Elektronik", "Pakaian dan Aksesoris", "Dokumen dan Arsip", "Barang Kesehatan dan Kecantikan", "Peralatan Musik", "Barang Konstruksi"});
            JTextField txtStok = new JTextField();
            JTextField txtSatuan = new JTextField();
            JButton btnSimpan = new JButton("Simpan");
            JButton btnBatal = new JButton("Batal");

            dialog.add(new JLabel("Kode:"));
            dialog.add(txtKode);
            dialog.add(new JLabel("Nama:"));
            dialog.add(txtNama);
            dialog.add(new JLabel("Kategori:"));
            dialog.add(cmbKategori);
            dialog.add(new JLabel("Stok:"));
            dialog.add(txtStok);
            dialog.add(new JLabel("Satuan:"));
            dialog.add(txtSatuan);
            dialog.add(btnBatal);
            dialog.add(btnSimpan);

            btnSimpan.addActionListener(event -> {
                String kode_barang = txtKode.getText();
                String nama_barang = txtNama.getText();
                String kategori = (String) cmbKategori.getSelectedItem();
                String stok = txtStok.getText();
                String satuan = txtSatuan.getText();

                if (!kode_barang.isEmpty() && !nama_barang.isEmpty() && !kategori.equals("Pilih") && !stok.isEmpty() && !satuan.isEmpty()) {
                    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         PreparedStatement statement = connection.prepareStatement("INSERT INTO barang (kode_barang, nama_barang, kategori, stok, satuan) VALUES (?, ?, ?, ?, ?)");) {
                        statement.setString(1, kode_barang);
                        statement.setString(2, nama_barang);
                        statement.setString(3, kategori);
                        statement.setInt(4, Integer.parseInt(stok));
                        statement.setString(5, satuan);
                        statement.executeUpdate();

                        tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, kode_barang, nama_barang, kategori, stok, satuan});
                        dialog.dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(dialog, "Gagal menyimpan data!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            btnBatal.addActionListener(event -> dialog.dispose());

            dialog.setLocationRelativeTo(frame);
            dialog.setVisible(true);
        });

        // Event untuk tombol hapus
        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     PreparedStatement statement = connection.prepareStatement("DELETE FROM barang WHERE id_barang = ?");) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                    tableModel.removeRow(selectedRow);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Gagal menghapus data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih baris yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Event untuk tombol kembali
        btnKembali.addActionListener(e -> {
            frame.dispose();
            dashboard.main(null);
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private ArrayList<Barang> getAllBarang() {
        ArrayList<Barang> barangList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_barang, kode_barang, nama_barang,kategori,stok,satuan FROM barang");) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_barang");
                String kode = resultSet.getString("kode_barang");
                String nama = resultSet.getString("nama_barang");
                String kategori = resultSet.getString("kategori");
                int stok = resultSet.getInt("stok");
                String satuan = resultSet.getString("satuan");

                Barang barang = new Barang(id, kode, nama, kategori, stok, satuan);
                barangList.add(barang);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data barang ke ArrayList!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return barangList;
    }


    private void loadData(DefaultTableModel tableModel) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_barang, kode_barang, nama_barang,kategori,stok,satuan  FROM barang");) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id_barang");
                String kode_barang = resultSet.getString("kode_barang");
                String nama_barang = resultSet.getString("nama_barang");
                String kategori = resultSet.getString("kategori");
                int stok = resultSet.getInt("stok");
                String satuan = resultSet.getString("satuan");
                tableModel.addRow(new Object[]{id, kode_barang, nama_barang, kategori, stok, satuan});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal memuat data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


}
