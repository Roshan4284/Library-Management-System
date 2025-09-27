import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    private JButton btnBooksAvailable, btnAddBooks, btnRemoveBooks;
    private JButton btnStaffDetails, btnAddStaff, btnRemoveStaff;
    private JButton btnEditAdmin, btnLogout;

    public Dashboard() {
        setTitle("Library Management System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Header
        JLabel lblHeader = new JLabel("Library Management System", SwingConstants.CENTER);
        lblHeader.setOpaque(true);
        lblHeader.setBackground(new Color(30, 144, 255));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblHeader.setPreferredSize(new Dimension(500, 50));
        add(lblHeader, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Buttons
        btnBooksAvailable = createButton("Books Available");
        btnAddBooks = createButton("Add Books");
        btnRemoveBooks = createButton("Remove Books");
        btnStaffDetails = createButton("Staff Details");
        btnAddStaff = createButton("Add Staff");
        btnRemoveStaff = createButton("Remove Staff");
        btnEditAdmin = createButton("Edit Admin");
        btnLogout = createButton("Logout");

        JButton[] buttons = {btnBooksAvailable, btnAddBooks, btnRemoveBooks,
                btnStaffDetails, btnAddStaff, btnRemoveStaff, btnEditAdmin, btnLogout};

        // Add buttons in 2 columns
        int row = 0, col = 0;
        for (JButton button : buttons) {
            gbc.gridx = col;
            gbc.gridy = row;
            mainPanel.add(button, gbc);
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
            button.addActionListener(this);
        }

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 100, 160), 2),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBooksAvailable) new BookAvailable().setVisible(true);
        else if (e.getSource() == btnAddBooks) new AddBook().setVisible(true);
        else if (e.getSource() == btnRemoveBooks) new RemoveBook().setVisible(true);
        else if (e.getSource() == btnStaffDetails) new StaffDetails().setVisible(true);
        else if (e.getSource() == btnAddStaff) new AddStaff().setVisible(true);
        else if (e.getSource() == btnRemoveStaff) new RemoveStaff().setVisible(true);
        else if (e.getSource() == btnEditAdmin) new EditAdmin().setVisible(true);
        else if (e.getSource() == btnLogout) {
            dispose();
            new LoginPage().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
