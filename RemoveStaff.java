import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RemoveStaff extends JFrame implements ActionListener {

    private JTextField txtStaffID;
    private JButton btnRemove, btnClear;

    private final String URL = "jdbc:mysql://localhost:3306/library";
    private final String USER = "root";
    private final String PASS = "Roshan@4284";

    public RemoveStaff() {
        setTitle("Remove Staff");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel header = new JLabel("Remove Staff", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.setOpaque(true);
        header.setBackground(new Color(30, 144, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(400, 50));
        add(header, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBackground(new Color(245, 245, 245));

        panel.add(new JLabel("Staff ID:"));
        txtStaffID = new JTextField();
        panel.add(txtStaffID);

        btnRemove = createButton("Remove");
        btnClear = createButton("Clear");
        panel.add(btnRemove);
        panel.add(btnClear);

        add(panel, BorderLayout.CENTER);

        btnRemove.addActionListener(this);
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
        if (e.getSource() == btnRemove) removeStaffFromDB();
        else if (e.getSource() == btnClear) txtStaffID.setText("");
    }

    private void removeStaffFromDB() {
        String id = txtStaffID.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Staff ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            String sql = "DELETE FROM staff WHERE STAFF_ID=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);

            int rows = pst.executeUpdate();
            if (rows > 0) JOptionPane.showMessageDialog(this, "Staff Removed Successfully!");
            else JOptionPane.showMessageDialog(this, "No Staff found with ID: " + id);

            pst.close();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RemoveStaff().setVisible(true));
    }
}

