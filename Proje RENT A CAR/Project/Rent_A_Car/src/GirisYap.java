import com.aribilgi.java.Database.DbAccess;
import com.aribilgi.java.Database.DbCrud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GirisYap extends JFrame {

    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPasswordField jPasswordField1;
    private JTextField jTextField1;

    public GirisYap() {
        araYuz();
        setupListeners();
    }

    private void araYuz() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jTextField1 = new JTextField();
        jLabel4 = new JLabel();
        jPasswordField1 = new JPasswordField();
        jButton1 = new JButton();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        jLabel1.setIcon(new ImageIcon(getClass().getResource("Resimler/photo-1565043666747-69f6646db940.jpg"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("            Giriş Yap");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Kullanıcı Adı");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("Şifre");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Giriş");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 544, GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                                .addComponent(jPasswordField1, GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jTextField1, GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(152, 152, 152)
                                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordField1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(98, Short.MAX_VALUE))
        );

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setContentPane(jPanel1);

        pack();
        setVisible(true);
    }


    private void setupListeners() {//girisyao butonu
        jButton1.addActionListener(new ActionListener() {
            private Object dispose;

            @Override
            public void actionPerformed(ActionEvent e) {
                String kullaniciAlani = jTextField1.getText();
                String sifreAlani = new String(jPasswordField1.getPassword());

                if (kullanicilar(kullaniciAlani, sifreAlani)) {
                    AnaMenu anaMenu = new AnaMenu();
                    anaMenu.setVisible(true);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(GirisYap.this, "Hatalı kullanıcı adı veya şifre girdiniz.");
                }
            }
        });
    }
    
    private boolean kullanicilar(String kullaniciAlani, String sifreAlani) {
    
        DbAccess dbAccess = new DbAccess("root", "", "rentacar", 3306);
        if (dbAccess.getConnection() == null) {
            JOptionPane.showMessageDialog(this, "Veritabanına bağlanılamadı.");
            return false;
        }

        DbCrud dbCrud = new DbCrud(dbAccess.getConnection());
        try {
            String query = "SELECT * FROM oturumacmasayfasi WHERE kullaniciadi = ? AND sifre = ?";
            dbCrud.preparedStatement = dbAccess.getConnection().prepareStatement(query);
            dbCrud.preparedStatement.setString(1, kullaniciAlani);
            dbCrud.preparedStatement.setString(2, sifreAlani);
            ResultSet resultSet = dbCrud.preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                dbCrud.preparedStatement.close();
                dbAccess.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GirisYap();
        });
    }
}




