import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveBook extends JFrame implements ActionListener {
    JLabel l1;
    JTextField t1;
    JButton b1, b2;

    public RemoveBook() {
        setTitle("Remove Book");
        setSize(400, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        l1 = new JLabel("Enter Book ID to Remove:");
        l1.setBounds(30, 30, 200, 30);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(200, 30, 150, 30);
        add(t1);

        b1 = new JButton("Remove");
        b1.setBounds(70, 100, 100, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(200, 100, 100, 30);
        b2.addActionListener(this);
        add(b2);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            String bookId = t1.getText();

            if (bookId.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter Book ID");
                return;
            }

            // ✅ Ask for confirmation
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to remove Book ID: " + bookId + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // ✅ Correct DB credentials
                    Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/library", 
                        "root", 
                        "Roshan@4284"
                    );
                    Statement st = con.createStatement();

                    int rows = st.executeUpdate("DELETE FROM books WHERE BOOKS_ID='" + bookId + "'");

                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Book Removed Successfully");
                    } else {
                        JOptionPane.showMessageDialog(this, "Book ID not found");
                    }

                    con.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }

        } else if (e.getSource() == b2) {
            dispose(); // close window
        }
    }

    public static void main(String[] args) {
        new RemoveBook();
    }
}

