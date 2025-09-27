
  import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginPage extends JFrame {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Roshan@4284";

    private JTextField userField;
    private JPasswordField passField;
    private JButton loginBtn;

    public LoginPage() {
        setTitle("Library Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel userLabel = new JLabel("User ID:");
        JLabel passLabel = new JLabel("Password:");

        userField = new JTextField(15);
        passField = new JPasswordField(15);

        loginBtn = new JButton("Login");

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        add(userLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        add(userField, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 1;
        add(passLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        add(passField, gbc);

        // Row 2
        gbc.gridx = 1; gbc.gridy = 2;
        add(loginBtn, gbc);

        // Action for login button
        loginBtn.addActionListener(e -> login());
    }

    private void login() {
        String userId = userField.getText().trim();
        String password = new String(passField.getPassword()).trim();

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both User ID and Password.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT * FROM admin WHERE USER_ID=? AND PASSWORD=?")) {

            pst.setString(1, userId);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new Dashboard().setVisible(true); // ✅ Correct class name
                dispose(); // Close login window
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User ID or Password.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
