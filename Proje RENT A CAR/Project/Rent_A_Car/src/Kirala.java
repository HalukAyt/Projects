import com.aribilgi.java.Database.DbAccess;
import com.aribilgi.java.Database.DbCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Kirala extends javax.swing.JFrame {
    private javax.swing.JButton jButton_geridon;
    private DbCrud dbCrud;
    private javax.swing.JButton jButton_Kirala;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel2_tarihOrnek1;
    private javax.swing.JLabel jLabel_Tutar;
    private javax.swing.JLabel jLabel_baslik;
    private javax.swing.JLabel jLabel_musteriAdi;
    private javax.swing.JLabel jLabel_musteriDogumtarihi;
    private javax.swing.JLabel jLabel_musteriMail;
    private javax.swing.JLabel jLabel_musteriSoyad;
    private javax.swing.JLabel jLabel_tarihbaslangic;
    private javax.swing.JLabel jLabel_tarihbitis;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner_ID;
    private javax.swing.JTable jTable_Araclar;
    private javax.swing.JTextField jTextField_musteriAdi;
    private javax.swing.JTextField jTextField_musteriDogumTarihi;
    private javax.swing.JTextField jTextField_musteriMail;
    private javax.swing.JTextField jTextField_musteriSoyad;
    private javax.swing.JTextField jTextField_tarihbaslangic;
    private javax.swing.JTextField jTextField_tarihbitis;
    private DbAccess dbAccess;

    private ResultSet resultSet;
    private Statement statement;
   private Connection connection;






    public Kirala() {
        Arayuz();
        DbAccess dbAccess = new DbAccess("root", "", "rentacar", 3306);
        connection = dbAccess.getConnection();
        araclarTablo();

        jButton_geridon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                AnaMenu anaMenu = new AnaMenu();
                anaMenu.setVisible(true);
                dispose();
            }
        });
        jButton_Kirala.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int id =(int) jSpinner_ID.getValue();
                String MusteriAdi = jTextField_musteriAdi.getText();
                String MusteriSoyadi = jTextField_musteriSoyad.getText();
                String MusteriMail = jTextField_musteriMail.getText();
                String DogumTarihi = jTextField_musteriDogumTarihi.getText();
                String BaslangicTarih = jTextField_tarihbaslangic.getText();
                String SonGunTarih = jTextField_tarihbitis.getText();


                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date startDate = format.parse(BaslangicTarih);
                    Date endDate = format.parse(SonGunTarih);
                    Date dogumtarihi = format.parse(DogumTarihi);
                    if (isVehicleBooked(id, startDate, endDate)) {
                        JOptionPane.showMessageDialog(Kirala.this, "Araç belirtilen tarihler arasında önceden kiralanmış", "Kiralama Hatası", JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                    long duration = endDate.getTime() - startDate.getTime();
                    long days = duration / (24 * 60 * 60 * 1000);


                    int dailyRate = (int) jTable_Araclar.getValueAt((int)jSpinner_ID.getValue()-1, jTable_Araclar.getColumn("G.Tutar").getModelIndex());
                    double totalPrice = dailyRate * days;

                    // Display the total price
                    jLabel_Tutar.setText(String.valueOf(totalPrice));

                    String sql = "INSERT INTO musteri (id, MusteriAdi, MusteriSoyadi, MusteriMail, DogumTarihi, KiralanacakTarih, TeslimTarihi, Tutar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, id);
                    statement.setString(2, MusteriAdi);
                    statement.setString(3, MusteriSoyadi);
                    statement.setString(4, MusteriMail);
                    statement.setDate(5, new java.sql.Date(dogumtarihi.getTime()));
                    statement.setDate(6,new java.sql.Date(startDate.getTime()));
                    statement.setDate(7,new java.sql.Date(endDate.getTime()));
                    statement.setDouble(8, totalPrice);

                    statement.executeUpdate();



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        }
    private boolean isVehicleBooked(int vehicleId, Date startDate, Date endDate) {
        try {
            String sql = "SELECT * FROM musteri WHERE id = ? AND ((KiralanacakTarih <= ? AND TeslimTarihi >= ?) OR (KiralanacakTarih <= ? AND TeslimTarihi >= ?))";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, vehicleId);
            statement.setDate(2, new java.sql.Date(startDate.getTime()));
            statement.setDate(3, new java.sql.Date(startDate.getTime()));
            statement.setDate(4, new java.sql.Date(endDate.getTime()));
            statement.setDate(5, new java.sql.Date(endDate.getTime()));

            ResultSet result = statement.executeQuery();
            return result.next(); // If there is a result, the vehicle is already booked

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void araclarTablo() {
        DefaultTableModel tableModel = (DefaultTableModel) jTable_Araclar.getModel();
        tableModel.setRowCount(0);

        try {
            String query = "SELECT * FROM araclar";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String marka = resultSet.getString("Marka");
                String model = resultSet.getString("Model");
                int tutar = resultSet.getInt("Tutar");

                // Add row to the table model
                tableModel.addRow(new Object[]{id, marka, model, tutar});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void Arayuz() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_baslik = new javax.swing.JLabel();
        jButton_geridon = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Araclar = new javax.swing.JTable();
        jSpinner_ID = new javax.swing.JSpinner();
        jLabel_musteriAdi = new javax.swing.JLabel();
        jTextField_musteriAdi = new javax.swing.JTextField();
        jLabel_musteriSoyad = new javax.swing.JLabel();
        jTextField_musteriSoyad = new javax.swing.JTextField();
        jLabel_musteriMail = new javax.swing.JLabel();
        jTextField_musteriMail = new javax.swing.JTextField();
        jLabel_musteriDogumtarihi = new javax.swing.JLabel();
        jLabel_tarihbaslangic = new javax.swing.JLabel();
        jTextField_musteriDogumTarihi = new javax.swing.JTextField();
        jTextField_tarihbaslangic = new javax.swing.JTextField();
        jLabel_tarihbitis = new javax.swing.JLabel();
        jTextField_tarihbitis = new javax.swing.JTextField();
        jLabel2_tarihOrnek1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_Tutar = new javax.swing.JLabel();
        jButton_Kirala = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));

        jLabel_baslik.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_baslik.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_baslik.setText("                                             Araç Kirala");

        jButton_geridon.setText("<-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton_geridon, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_baslik, javax.swing.GroupLayout.DEFAULT_SIZE, 867, Short.MAX_VALUE)
                                .addGap(22, 22, 22))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(36, 36, 36)
                                                .addComponent(jLabel_baslik))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jButton_geridon, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(48, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Araba ID:");

        jTable_Araclar.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String [] {
                        "ID", "Marka", "Model", "G.Tutar"
                }
        ));
        jScrollPane1.setViewportView(jTable_Araclar);

        jSpinner_ID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel_musteriAdi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_musteriAdi.setText("Müşteri Adı:");

        jLabel_musteriSoyad.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_musteriSoyad.setText("Müşteri Soyad:");

        jLabel_musteriMail.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_musteriMail.setText("Mail:");

        jLabel_musteriDogumtarihi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_musteriDogumtarihi.setText("Doğum Tarihi:");

        jLabel_tarihbaslangic.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_tarihbaslangic.setText("Kiralanacak tarih");

        jLabel2_tarihOrnek1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2_tarihOrnek1.setText("Ornek: gg-aa-yyyy");

        jLabel_tarihbitis.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_tarihbitis.setText("Teslim tarihi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Tutar:");

        jLabel_Tutar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jButton_Kirala.setText("Kirala");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_musteriAdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_musteriSoyad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_musteriMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_musteriDogumtarihi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_tarihbaslangic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel_tarihbitis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jSpinner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField_musteriSoyad)
                                                .addComponent(jTextField_musteriAdi)
                                                .addComponent(jTextField_musteriMail, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                .addComponent(jTextField_musteriDogumTarihi, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                .addComponent(jTextField_tarihbaslangic, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                                .addComponent(jTextField_tarihbitis, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                                        .addComponent(jLabel_Tutar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2_tarihOrnek1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(44, 44, 44))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2_tarihOrnek1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton_Kirala, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 20, Short.MAX_VALUE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSpinner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_musteriAdi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_musteriAdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_musteriSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_musteriSoyad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_musteriMail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_musteriMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_musteriDogumtarihi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_musteriDogumTarihi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_tarihbaslangic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_tarihbaslangic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2_tarihOrnek1))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_tarihbitis, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_tarihbitis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel2_tarihOrnek1))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(jLabel_Tutar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addGap(33, 33, 33))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jButton_Kirala, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18))))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kirala().setVisible(true);
            }
        });
    }


}