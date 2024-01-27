import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PresentPanel extends JFrame {
    private JPanel JPanel1;
    private JTable studentList;
    private JButton changeButton;
    private JButton closeButton;
    private JButton refreshButton;

    private LoginSystem loginSystem;
    private PresentReader presentReader;

    public PresentPanel(LoginSystem loginSystem) {
        super("Panel frekwencji");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.presentReader = new PresentReader(loginSystem);
        this.presentReader.readPresentsFromFile("present.txt");

        List<User> students = loginSystem.getStudents();

        String[] columnNames = {"Imię", "Nazwisko", "Frekwencja [%]"};
        Object[][] data = new Object[students.size()][3];

        for (int i = 0; i < students.size(); i++) {
            User student = students.get(i);
            int userId = student.getId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            double attendancePercentage = presentReader.calculateAttendancePercentage(userId);

            data[i][0] = firstName;
            data[i][1] = lastName;
            data[i][2] = attendancePercentage;
        }

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        studentList.setModel(tableModel);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTableData();
            }
        });
    }

    private void refreshTableData() {
        List<User> students = loginSystem.getStudents();
        DefaultTableModel tableModel = (DefaultTableModel) studentList.getModel();

        tableModel.setRowCount(0);
        this.presentReader.readPresentsFromFile("present.txt");
        for (User student : students) {
            int userId = student.getId();
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            double attendancePercentage = presentReader.calculateAttendancePercentage(userId);
            tableModel.addRow(new Object[]{firstName, lastName, attendancePercentage});
        }
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        PresentPanel presentPanel = new PresentPanel(loginSystem);
        presentPanel.setVisible(true);
    }
}
