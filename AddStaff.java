import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddStaff extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField t1, t2, t3;
    JComboBox<String> roleBox;
    JButton b1, b2;

    public AddStaff() {
        setTitle("Add Staff");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        l1 = new JLabel("Staff ID:");
        l1.setBounds(30, 30, 100, 30);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(150, 30, 200, 30);
        add(t1);

        l2 = new JLabel("Name:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        t2 = new JTextField();
        t2.setBounds(150, 70, 200, 30);
        add(t2);

        l3 = new JLabel("Contact:");
        l3.setBounds(30, 110, 100, 30);
        add(l3);

        t3 = new JTextField();
        t3.setBounds(150, 110, 200, 30);
        add(t3);

        l4 = new JLabel("Role:");
        l4.setBounds(30, 150, 100, 30);
        add(l4);

        // ✅ Dropdown for roles
        String[] roles = {"Librarian", "Assistant", "Clerk", "Manager", "Technician"};
        roleBox = new JComboBox<>(roles);
        roleBox.setBounds(150, 150, 200, 30);
        add(roleBox);

        b1 = new JButton("Add");
        b1.setBounds(70, 200, 100, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(200, 200, 100, 30);
        b2.addActionListener(this);
        add(b2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String staffId = t1.getText();
            String name = t2.getText();
            String contact = t3.getText();
            String role = (String) roleBox.getSelectedItem();

            if (staffId.equals("") || name.equals("") || contact.equals("")) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            try {
                // ✅ DB Connection
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library",
                    "root",
                    "Roshan@4284"
                );

                String query = "INSERT INTO staff (STAFF_ID, NAME, CONTACT, role) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, staffId);
                pst.setString(2, name);
                pst.setString(3, contact);
                pst.setString(4, role);

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Staff Added Successfully");
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    roleBox.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to Add Staff");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == b2) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new AddStaff();
    }
}
