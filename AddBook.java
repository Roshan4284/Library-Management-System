import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBook extends JFrame implements ActionListener {

    private JTextField txtId, txtCategory, txtTitle, txtAuthor, txtCopies;
    private JButton btnAdd, btnClear;

    private final String URL = "jdbc:mysql://localhost:3306/library";
    private final String USER = "root";
    private final String PASS = "Roshan@4284";

    public AddBook() {
        setTitle("Add Book");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel header = new JLabel("Add New Book", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(450, 50));
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(new JLabel("Book ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Category:"));
        txtCategory = new JTextField();
        panel.add(txtCategory);

        panel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        panel.add(txtTitle);

        panel.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        panel.add(txtAuthor);

        panel.add(new JLabel("Copies:"));
        txtCopies = new JTextField();
        panel.add(txtCopies);

        btnAdd = createButton("Add Book");
        btnClear = createButton("Clear");
        panel.add(btnAdd);
        panel.add(btnClear);

        add(panel, BorderLayout.CENTER);

        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 160), 2));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            addBookToDB();
        } else if (e.getSource() == btnClear) {
            txtId.setText("");
            txtCategory.setText("");
            txtTitle.setText("");
            txtAuthor.setText("");
            txtCopies.setText("");
        }
    }

    private void addBookToDB() {
        String id = txtId.getText();
        String category = txtCategory.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();
        String copiesStr = txtCopies.getText();

        if (id.isEmpty() || category.isEmpty() || title.isEmpty() || author.isEmpty() || copiesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int copies = Integer.parseInt(copiesStr);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            String sql = "INSERT INTO books (BOOKS_ID, CATEGORY, TITLE, AUTHOR, COPIES) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            pst.setString(2, category);
            pst.setString(3, title);
            pst.setString(4, author);
            pst.setInt(5, copies);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to Add Book!");
            }

            pst.close();
            con.close();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Copies must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBook().setVisible(true));
    }
}
