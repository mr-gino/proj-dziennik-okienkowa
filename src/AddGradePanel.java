import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddGradePanel extends JFrame {
    private JPanel JPanel1;
    private JComboBox<User> studentsBox;
    private JRadioButton a1RadioButton;
    private JRadioButton a2RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton a4RadioButton;
    private JRadioButton a5RadioButton;
    private JRadioButton a6RadioButton;
    private JButton addButton;
    private JButton closeButton;

    private LoginSystem loginSystem;
    private GradesReader gradesReader;

    public AddGradePanel(LoginSystem loginSystem, GradesReader gradesReader) {
        super("Panel dodawania oceny");
        this.loginSystem = loginSystem;
        this.gradesReader = gradesReader;

        setContentPane(this.JPanel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        setSize(width, height);

        DefaultComboBoxModel<User> studentsModel = new DefaultComboBoxModel<>();
        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentsModel.addElement(student);
        }

        studentsBox.setModel(studentsModel);
        studentsBox.setRenderer(new UserRenderer());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGrade();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addGrade() {
        User selectedUser = (User) studentsBox.getSelectedItem();
        int grade = getSelectedGrade();

        if (selectedUser != null && grade > 0) {
            gradesReader.addGrade(selectedUser.getId(), grade);
            gradesReader.saveGradesToFile("grades.txt");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Wybierz ucznia i ocenę przed dodaniem oceny.");
        }
    }

    private int getSelectedGrade() {
        if (a1RadioButton.isSelected()) return 1;
        if (a2RadioButton.isSelected()) return 2;
        if (a3RadioButton.isSelected()) return 3;
        if (a4RadioButton.isSelected()) return 4;
        if (a5RadioButton.isSelected()) return 5;
        if (a6RadioButton.isSelected()) return 6;
        return 0;
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
        GradesReader gradesReader = new GradesReader(loginSystem);
        AddGradePanel addGradePanel = new AddGradePanel(loginSystem, gradesReader);
        addGradePanel.setVisible(true);
    }
}
