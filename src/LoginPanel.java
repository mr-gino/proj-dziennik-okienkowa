import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame {
    private JPanel JPanel1;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton clearButton;
    private JLabel outLabel;

    private LoginSystem loginSystem;

    public LoginPanel() {
        super("Panel logowania");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        loginSystem = new LoginSystem();
        loginSystem.loadUsersFromFile("users.txt");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginField.setText("");
                passwordField.setText("");
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                User currentUser = loginSystem.findUserByLogin(login);

                if (currentUser == null || !loginSystem.authenticateUser(currentUser, password)) {
                    outLabel.setText("Niepoprawny login lub hasło.");
                } else {

                }
            }
        });
    }

    public static void main(String[] args) {
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setVisible(true);
    }
}
