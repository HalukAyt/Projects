import com.aribilgi.java.Database.DbAccess;
import com.aribilgi.java.Database.DbCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Araclar extends javax.swing.JFrame {

    private javax.swing.JButton jButtonSil;
    private javax.swing.JTextField jTextField_Tutar;
    private javax.swing.JTextField jTextField_Model;
    private javax.swing.JLabel jLabel_Tutar;
    private javax.swing.JButton jButton_Ekle;
    private javax.swing.JButton jButton_GeriDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_ID;
    private javax.swing.JLabel jLabel_Marka;
    private javax.swing.JLabel jLabel_Model;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner_ID;
    private javax.swing.JTable jTable_Araclar;
    private javax.swing.JTextField jTextField_Marka;
    private int currentID = 1;
    private DbCrud dbCrud;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;





    public void insertCar(int id, String marka, String model, int tutar) {
        try {
            String query = "INSERT INTO araclar (ID, Marka, Model, Tutar) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, marka);
            statement.setString(3, model);
            statement.setInt(4, tutar);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(int id) {
        try {
            String query = "DELETE FROM araclar WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hafiza() {

        try {
            String query = "SELECT * FROM araclar";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            DefaultTableModel tModel = (DefaultTableModel) jTable_Araclar.getModel();
            tModel.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String marka = resultSet.getString("Marka");
                String model = resultSet.getString("Model");
                int tutar = resultSet.getInt("Tutar");
                tModel.addRow(new Object[]{id, marka, model, tutar});
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Araclar() {
        Arayuz();
        DbAccess dbAccess = new DbAccess("root", "", "rentacar", 3306);
        connection = dbAccess.getConnection();

        hafiza();

        dbCrud = new DbCrud(connection);

        jButton_GeriDon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle jButton3 click event
                AnaMenu anaMenu = new AnaMenu();
                anaMenu.setVisible(true);
                dispose();
            }
        });

        jButton_Ekle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String marka = jTextField_Marka.getText();
                String model = jTextField_Model.getText();
                int id = (int) jSpinner_ID.getValue();
                String tutar = jTextField_Tutar.getText();

                DefaultTableModel tModel = (DefaultTableModel) jTable_Araclar.getModel();
                tModel.addRow(new Object[]{id, marka, model, tutar});

                jTextField_Marka.setText("");
                jTextField_Model.setText("");
                jTextField_Tutar.setText("");

                insertCar(id, marka, model, Integer.parseInt(tutar));
            }
        });

        jButtonSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = (int) jSpinner_ID.getValue(); // Get the value from the jSpinner1 (ID)

                int rowIndex = satiriBul(id); // Find the row index based on the ID

                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "Geçersiz ID", "ID bulunamadı", JOptionPane.WARNING_MESSAGE);
                    return; // Exit the method if the ID is not found
                }

                DefaultTableModel model = (DefaultTableModel) jTable_Araclar.getModel();
                model.removeRow(rowIndex); // Remove the row from the table

                jSpinner_ID.setValue(currentID++); // Increment the current ID value and update the spinner

                // Delete the row from the MySQL database
                deleteCar(id);
            }
        });
    }


    private int satiriBul(int id) {
        DefaultTableModel model = (DefaultTableModel) jTable_Araclar.getModel();
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int existingID = (int) model.getValueAt(i, 0);
            if (existingID == id) {
                return i; // Return the row index if the ID is found
            }
        }

        return -1; // Return -1 if the ID is not found
    }

    private void Arayuz() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton_GeriDon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Araclar = new javax.swing.JTable();
        jLabel_ID = new javax.swing.JLabel();
        jSpinner_ID = new javax.swing.JSpinner();
        jLabel_Marka = new javax.swing.JLabel();
        jTextField_Marka = new javax.swing.JTextField();
        jLabel_Model = new javax.swing.JLabel();
        jTextField_Tutar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton_Ekle = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButtonSil = new javax.swing.JButton();
        jLabel_Tutar = new javax.swing.JLabel();
        jTextField_Model = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(0, 153, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("                                                                   ARAÇLAR");

        jButton_GeriDon.setText("<-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton_GeriDon, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jButton_GeriDon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 51, Short.MAX_VALUE)))
                                .addContainerGap())
        );

        jTable_Araclar.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "ID", "Marka", "Model", "G.Tutar"
                }
        ));
        jScrollPane1.setViewportView(jTable_Araclar);

        jLabel_ID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_ID.setText("ID:");

        jSpinner_ID.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel_Marka.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_Marka.setText("Marka:");

        jLabel_Model.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_Model.setText("Model:");

        jPanel4.setBackground(new java.awt.Color(0, 153, 102));

        jButton_Ekle.setText("Ekle");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_Ekle, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton_Ekle, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jButtonSil.setText("Sil");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonSil, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButtonSil, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                .addContainerGap())
        );

        jLabel_Tutar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_Tutar.setText("Günlük Tutar:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(49, 49, 49)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel_Tutar)
                                                        .addComponent(jLabel_Model)
                                                        .addComponent(jLabel_Marka))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField_Marka, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_Model, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField_Tutar, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jSpinner_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(28, 28, 28)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_Marka)
                                                        .addComponent(jTextField_Marka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel_Model)
                                                        .addComponent(jTextField_Model, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(jLabel_Tutar)
                                                                        .addComponent(jTextField_Tutar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(59, 59, 59))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(17, Short.MAX_VALUE))))
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
                new Araclar().setVisible(true);
            }
        });

    }

}



