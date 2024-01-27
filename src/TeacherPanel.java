import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherPanel extends JFrame {
    private JPanel JPanel1;
    private JLabel nameLabel;
    private JLabel typeLabel;
    private JButton gradesButton;
    private JButton presentButton;
    private JButton notesButton;
    private JButton logoutButton;

    private LoginSystem loginSystem;
    private User currentUser;

    public TeacherPanel(LoginSystem loginSystem, User currentUser) {
        super("Panel nauczyciela");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.currentUser = currentUser;

        nameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        typeLabel.setText("Nauczyciel");

        gradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        presentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        notesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.loadUsersFromFile("users.txt");
        User currentUser = loginSystem.findUserByLogin("teacher_login");

        TeacherPanel teacherPanel = new TeacherPanel(loginSystem, currentUser);
        teacherPanel.setVisible(true);
    }
}
