import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GradesPanel extends JFrame {
    private JPanel JPanel1;
    private JTable table1;
    private JButton addButton;
    private JButton deleteButton;
    private JButton closeButton;
    private JButton refreshButton;
    private JButton avgButton;

    private LoginSystem loginSystem;
    private GradesReader gradesReader;

    public GradesPanel(LoginSystem loginSystem) {
        super("Panel ocen");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.gradesReader = new GradesReader(loginSystem);
        this.gradesReader.loadGradesFromFile("grades.txt");

        refreshTable();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGradePanel addGradePanel = new AddGradePanel(loginSystem,gradesReader);
                addGradePanel.setVisible(true);
                refreshTable();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelGradePanel delGradePanel = new DelGradePanel(loginSystem);
                delGradePanel.setVisible(true);
                refreshTable();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        avgButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
    }

    private void refreshTable() {
        List<Grades> allGrades = gradesReader.getAllGrades();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Imię i Nazwisko", "Oceny"}, 0);

        for (Grades grades : allGrades) {
            int userId = grades.getUserId();
            User user = loginSystem.getUserById(userId);
            String fullName = user.getFirstName() + " " + user.getLastName();
            List<Integer> gradesData = grades.getGrades();
            String gradesString = gradesData.isEmpty() ? "Brak ocen" : String.join(", ", gradesData.stream().map(Object::toString).toArray(String[]::new));

            model.addRow(new Object[]{fullName, gradesString});
        }

        table1.setModel(model);
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem(); // Pamiętaj, aby dostarczyć prawidłowy LoginSystem
        GradesPanel gradesPanel = new GradesPanel(loginSystem);
        gradesPanel.setVisible(true);
    }
}
