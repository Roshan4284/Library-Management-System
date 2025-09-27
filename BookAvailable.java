import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class BookAvailable extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private final String URL = "jdbc:mysql://localhost:3306/library";
    private final String USER = "root";
    private final String PASS = "Roshan@4284";

    public BookAvailable() {
        setTitle("Available Books");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel header = new JLabel("Available Books", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(600, 50));
        add(header, BorderLayout.NORTH);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Book ID", "Category", "Title", "Author", "Copies"});
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        loadBooks();
    }

    private void loadBooks() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");

            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("BOOKS_ID"),
                        rs.getString("CATEGORY"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getInt("COPIES")
                });
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookAvailable().setVisible(true));
    }
}
