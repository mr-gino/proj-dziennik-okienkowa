import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DelGradePanel extends JFrame {
    private JPanel JPanel1;
    private JButton refreshButton;
    private JButton deleteButton;
    private JButton closeButton;
    private JList<Integer> gradesList;
    private JComboBox<User> studentsBox;

    private LoginSystem loginSystem;
    private GradesReader gradesReader;

    public DelGradePanel(LoginSystem loginSystem) {
        super("Panel usuwania ocen");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.gradesReader = new GradesReader(loginSystem);
        this.gradesReader.loadGradesFromFile("grades.txt");

        DefaultComboBoxModel<User> studentModel = new DefaultComboBoxModel<>();

        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentModel.addElement(student);
        }

        studentsBox.setModel(studentModel);
        studentsBox.setRenderer(new UserRenderer());

        studentsBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGradesForSelectedStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedGrade();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Zamknij okno
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshStudentList();
            }
        });
    }

    private void loadGradesForSelectedStudent() {
        User selectedStudent = (User) studentsBox.getSelectedItem();

        if (selectedStudent != null) {
            List<Integer> studentGrades = gradesReader.getGradesForUser(selectedStudent.getId());

            DefaultListModel<Integer> listModel = new DefaultListModel<>();
            for (Integer grade : studentGrades) {
                listModel.addElement(grade);
            }
            gradesList.setModel(listModel);
        }
    }

    private void refreshStudentList() {
        DefaultComboBoxModel<User> studentModel = (DefaultComboBoxModel<User>) studentsBox.getModel();
        studentModel.removeAllElements();

        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentModel.addElement(student);
        }
    }

    private void deleteSelectedGrade() {
        User selectedStudent = (User) studentsBox.getSelectedItem();
        int selectedIndex = gradesList.getSelectedIndex();

        if (selectedStudent != null && selectedIndex != -1) {
            gradesReader.removeGrade(selectedStudent.getId(), selectedIndex);
            gradesReader.saveGradesToFile("grades.txt");
            loadGradesForSelectedStudent();
            dispose();
        }
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

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        DelGradePanel delGradePanel = new DelGradePanel(loginSystem);
        delGradePanel.setVisible(true);
    }
}
