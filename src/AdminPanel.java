import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPanel extends JFrame {
    private JPanel JPanel1;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JTable usersTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton logoutButton;
    private JButton refreshButton;

    private LoginSystem loginSystem;
    private User currentUser;

    public AdminPanel(LoginSystem loginSystem, User currentUser) {
        super("Panel Dyrektorski");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.currentUser = currentUser;

        nameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        typeLabel.setText("Dyrektor");

        refreshTable();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUserPanel addUserPanel = new AddUserPanel(loginSystem);
                addUserPanel.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();  // Dodane
            }
        });
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.loadUsersFromFile("users.txt");
        User currentUser = loginSystem.findUserByLogin("admin_login");

        AdminPanel adminPanel = new AdminPanel(loginSystem, currentUser);
        adminPanel.setVisible(true);
    }

    private void refreshTable() {
        List<User> userList = loginSystem.getUsers();

        Object[][] data = new Object[userList.size()][4];
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getFirstName();
            data[i][2] = user.getLastName();
            data[i][3] = user.getPosition();
        }

        String[] columnNames = {"ID", "Imię", "Nazwisko", "Rodzaj konta"};
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        usersTable.setModel(tableModel);
    }
}
