import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class StudentPanel extends JFrame {
    private JPanel JPanel1;
    private JLabel typeLabel;
    private JLabel nameLabel;
    private JTextArea gradesArea;
    private JTextArea notesArea;
    private JLabel presentLabel;
    private JButton logoutButton;

    private LoginSystem loginSystem;
    private User currentUser;
    private GradesReader gradesReader;
    private PresentReader presentReader;
    private NotesReader notesReader;

    public StudentPanel(LoginSystem loginSystem, User currentUser) {
        super("Panel ucznia");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.currentUser = currentUser;
        this.gradesReader = new GradesReader(loginSystem);
        this.presentReader = new PresentReader(loginSystem);
        notesReader = new NotesReader(loginSystem);
        notesReader.readNotesFromFile("notes.txt");

        nameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        typeLabel.setText("Uczeń");

        gradesReader.loadGradesFromFile("grades.txt");
        presentReader.readPresentsFromFile("present.txt");

        List<Integer> studentGrades = gradesReader.getGradesForUser(currentUser.getId());
        gradesArea.setText(formatGrades(studentGrades));

        Map<Integer, List<String>> studentNotesMap = notesReader.getAllNotes();
        List<String> studentNotes = studentNotesMap.get(currentUser.getId());
        notesArea.setText(formatNotes(studentNotes));

        double attendancePercentage = presentReader.calculateAttendancePercentage(currentUser.getId());
        presentLabel.setText(attendancePercentage + "%");

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private String formatGrades(List<Integer> grades) {
        StringBuilder result = new StringBuilder();
        for (Integer grade : grades) {
            result.append(grade).append(", ");
        }
        return result.toString();
    }

    private String formatNotes(List<String> notes) {
        StringBuilder result = new StringBuilder();
        if (notes != null) {
            for (String note : notes) {
                result.append(note).append("\n");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        loginSystem.loadUsersFromFile("users.txt");
        User currentUser = loginSystem.findUserByLogin("student_login");

        StudentPanel studentPanel = new StudentPanel(loginSystem, currentUser);
        studentPanel.setVisible(true);
    }
}
