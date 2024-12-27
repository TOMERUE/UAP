package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class databarang {
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
        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Membuka form popup untuk menambah data baru
                JDialog dialog = new JDialog(frame, "Tambah Data", true);
                dialog.setSize(400, 300);
                dialog.setLayout(new GridLayout(6, 2, 10, 10));

                JTextField txtKode = new JTextField();
                JTextField txtNama = new JTextField();
                JComboBox<String> cmbKategori = new JComboBox<>(new String[]{"Pilih", "Barang Rumah Tangga", "Barang Elektronik","Pakaian dan Aksesoris", "Dokumen dan Arsip", "Barang Kesehatan dan Kecantikan","Peralatan Musik","Barang Konstruksi"});
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

                btnSimpan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String kode = txtKode.getText();
                        String nama = txtNama.getText();
                        String kategori = (String) cmbKategori.getSelectedItem();
                        String stok = txtStok.getText();
                        String satuan = txtSatuan.getText();

                        if (!kode.isEmpty() && !nama.isEmpty() && !kategori.equals("Pilih") && !stok.isEmpty() && !satuan.isEmpty()) {
                            tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, kode, nama, kategori, stok, satuan});
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                btnBatal.addActionListener(e1 -> dialog.dispose());

                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });

        // Event untuk tombol ubah
        btnUbah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Membuka form popup untuk mengubah data
                    JDialog dialog = new JDialog(frame, "Ubah Data", true);
                    dialog.setSize(400, 300);
                    dialog.setLayout(new GridLayout(6, 2, 10, 10));

                    JTextField txtKode = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
                    JTextField txtNama = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
                    JComboBox<String> cmbKategori = new JComboBox<>(new String[]{"Pilih", "Barang Rumah Tangga", "Barang Elektronik","Pakaian dan Aksesoris", "Dokumen dan Arsip", "Barang Kesehatan dan Kecantikan","Peralatan Musik","Barang Konstruksi"});
                    cmbKategori.setSelectedItem(tableModel.getValueAt(selectedRow, 3));
                    JTextField txtStok = new JTextField((String) tableModel.getValueAt(selectedRow, 4).toString());
                    JTextField txtSatuan = new JTextField((String) tableModel.getValueAt(selectedRow, 5));
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

                    btnSimpan.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String kode = txtKode.getText();
                            String nama = txtNama.getText();
                            String kategori = (String) cmbKategori.getSelectedItem();
                            String stok = txtStok.getText();
                            String satuan = txtSatuan.getText();

                            if (!kode.isEmpty() && !nama.isEmpty() && !kategori.equals("Pilih") && !stok.isEmpty() && !satuan.isEmpty()) {
                                tableModel.setValueAt(kode, selectedRow, 1);
                                tableModel.setValueAt(nama, selectedRow, 2);
                                tableModel.setValueAt(kategori, selectedRow, 3);
                                tableModel.setValueAt(stok, selectedRow, 4);
                                tableModel.setValueAt(satuan, selectedRow, 5);
                                dialog.dispose();
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });

                    btnBatal.addActionListener(e1 -> dialog.dispose());

                    dialog.setLocationRelativeTo(frame);
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "Pilih baris yang ingin diubah!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Event untuk tombol hapus
        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Pilih baris yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Event untuk tombol cetak laporan
        btnCetakLaporan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder laporan = new StringBuilder("Laporan Data Barang:\n\n");
                laporan.append(String.format("%-5s %-10s %-20s %-15s %-10s %-10s\n", "ID", "Kode", "Nama", "Kategori", "Stok", "Satuan"));
                laporan.append("==============================================================\n");
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    laporan.append(String.format("%-5s %-10s %-20s %-15s %-10s %-10s\n",
                            tableModel.getValueAt(i, 0),
                            tableModel.getValueAt(i, 1),
                            tableModel.getValueAt(i, 2),
                            tableModel.getValueAt(i, 3),
                            tableModel.getValueAt(i, 4),
                            tableModel.getValueAt(i, 5)));
                }

                JTextArea textArea = new JTextArea(laporan.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);

                JDialog dialog = new JDialog(frame, "Laporan", true);
                dialog.setSize(600, 400);
                dialog.add(scrollPane);
                dialog.setLocationRelativeTo(frame);
                dialog.setVisible(true);
            }
        });

        // Event untuk tombol kembali
        btnKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Menutup frame saat ini
                dashboard.main(null);// Membuka halaman dashboard
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}