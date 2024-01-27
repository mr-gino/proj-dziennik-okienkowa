import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddUserPanel extends JFrame {
    private JPanel JPanel1;
    private JTextField idField;
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField typeField;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton closeButton;
    private JButton clearButton;
    private JButton addButton;

    private LoginSystem loginSystem;
    private GradesReader gradesReader;
    private NotesReader notesReader;
    private PresentReader presentReader;

    public AddUserPanel(LoginSystem loginSystem) {
        super("Panel dodawania użytkownika");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.gradesReader = new GradesReader(loginSystem);
        this.notesReader = new NotesReader(loginSystem);
        this.presentReader = new PresentReader(loginSystem);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idField.setText("");
                nameField.setText("");
                surnameField.setText("");
                typeField.setText("");
                loginField.setText("");
                passwordField.setText("");
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int nId = Integer.parseInt(idField.getText());
                    String nFirstName = nameField.getText();
                    String nLastName = surnameField.getText();
                    String nPosition = typeField.getText();
                    String nLogin = loginField.getText();
                    String nPassword = new String(passwordField.getPassword());

                    loginSystem.createUserAndSaveToFile(nId, nFirstName, nLastName, nPosition, nLogin, nPassword);
                    gradesReader.addGradeToFile(nId);
                    presentReader.addPresentToFile(nId);

                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Wprowadź prawidłowy numer ID.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
