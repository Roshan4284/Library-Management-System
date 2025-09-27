import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditAdmin extends JFrame implements ActionListener {

    JLabel lblUserId, lblName, lblPass, lblContact;
    JTextField txtUserId, txtName, txtContact;
    JPasswordField txtPass;
    JButton btnUpdate, btnClear;

    Connection con;
    PreparedStatement pst;

    public EditAdmin() {
        // Frame setup
        setTitle("Edit Admin");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Components
        lblUserId = new JLabel("User ID:");
        txtUserId = new JTextField();

        lblName = new JLabel("Name:");
        txtName = new JTextField();

        lblPass = new JLabel("Password:");
        txtPass = new JPasswordField();

        lblContact = new JLabel("Contact:");
        txtContact = new JTextField();

        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");

        // Add components
        add(lblUserId); add(txtUserId);
        add(lblName); add(txtName);
        add(lblPass); add(txtPass);
        add(lblContact); add(txtContact);
        add(btnUpdate); add(btnClear);

        // Action listeners
        btnUpdate.addActionListener(this);
        btnClear.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            updateAdmin();
        } else if (e.getSource() == btnClear) {
            txtUserId.setText("");
            txtName.setText("");
            txtPass.setText("");
            txtContact.setText("");
        }
    }

    private void updateAdmin() {
        String userId = txtUserId.getText();
        String name = txtName.getText();
        String password = new String(txtPass.getPassword());
        String contact = txtContact.getText();

        if (userId.isEmpty() || name.isEmpty() || password.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "Roshan@4284");

            String sql = "UPDATE admin SET NAME=?, PASSWORD=?, CONTACT=? WHERE USER_ID=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, password);
            pst.setString(3, contact);
            pst.setString(4, userId);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Admin updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No admin found with USER_ID: " + userId);
            }

            pst.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EditAdmin();
    }
}
