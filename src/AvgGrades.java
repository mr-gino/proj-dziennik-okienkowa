import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AvgGrades extends JFrame {
    private JPanel JPanel1;
    private JComboBox<User> studentsBox;
    private JButton avgButton;
    private JButton closeButton;
    private JLabel avgLabel;

    private GradesReader gradesReader;
    private LoginSystem loginSystem;

    public AvgGrades(LoginSystem loginSystem) {
        super("Średnia ocen");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        gradesReader = new GradesReader(loginSystem);

        avgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplayAverage();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        fillStudentsBox();
    }

    private void fillStudentsBox() {
        DefaultComboBoxModel<User> studentModel = new DefaultComboBoxModel<>();

        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentModel.addElement(student);
        }

        studentsBox.setModel(studentModel);
        studentsBox.setRenderer(new UserRenderer());
    }

    private void calculateAndDisplayAverage() {
        User selectedStudent = (User) studentsBox.getSelectedItem();

        if (selectedStudent != null) {
            int userId = selectedStudent.getId();

            gradesReader.loadGradesFromFile("grades.txt");

            double average = gradesReader.calculateAverageGrade(userId);

            avgLabel.setText("" + average);
        }
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        AvgGrades avgGrades = new AvgGrades(loginSystem);
        avgGrades.setVisible(true);
    }

    class UserRenderer extends JLabel implements ListCellRenderer<User> {
        @Override
        public java.awt.Component getListCellRendererComponent(JList<? extends User> list, User value, int index,
                                                               boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getFirstName() + " " + value.getLastName());
            }
            return this;
        }
    }
}
