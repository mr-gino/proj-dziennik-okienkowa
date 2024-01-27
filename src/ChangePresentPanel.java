import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ChangePresentPanel extends JFrame {
    private JPanel JPanel1;
    private JComboBox<User> studentBox;
    private JButton addButton;
    private JButton deleteButton;
    private JButton closeButton;

    private LoginSystem loginSystem;
    private PresentReader presentReader;

    public ChangePresentPanel(LoginSystem loginSystem) {
        super("Panel edycji frekwencji");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.presentReader = new PresentReader(loginSystem);
        this.presentReader.readPresentsFromFile("present.txt");

        List<User> students = loginSystem.getStudents();

        DefaultComboBoxModel<User> studentModel = new DefaultComboBoxModel<>();
        for (User student : students) {
            studentModel.addElement(student);
        }
        studentBox.setModel(studentModel);
        studentBox.setRenderer(new UserRenderer());

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAttendance();
                dispose();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAttendance();
                dispose();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addAttendance() {
        User selectedStudent = (User) studentBox.getSelectedItem();
        if (selectedStudent != null) {
            int userId = selectedStudent.getId();
            presentReader.addAttendance(userId);
            presentReader.savePresentsToFile("present.txt");
        }
    }

    private void deleteAttendance() {
        User selectedStudent = (User) studentBox.getSelectedItem();
        if (selectedStudent != null) {
            int userId = selectedStudent.getId();
            presentReader.addAbsence(userId);
            presentReader.savePresentsToFile("present.txt");
        }
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        ChangePresentPanel changePresentPanel = new ChangePresentPanel(loginSystem);
        changePresentPanel.setVisible(true);
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
