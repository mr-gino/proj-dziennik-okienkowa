import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class NotesPanel extends JFrame {
    private JPanel JPanel1;
    private JTable JTable;
    private JButton closeButton;
    private JButton deleteButton;
    private JButton addButton;
    private JButton refreshButton;

    private LoginSystem loginSystem;
    private DefaultTableModel tableModel;

    public NotesPanel(LoginSystem loginSystem) {
        super("Panel Uwag");
        setContentPane(this.JPanel1);
        this.loginSystem = loginSystem;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        addButton.addActionListener(new ActionListener() {
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

        NotesReader notesReader = new NotesReader(loginSystem);
        notesReader.readNotesFromFile("notes.txt");

        String[] columnNames = {"Imię i Nazwisko", "Uwagi"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable.setModel(tableModel);
        Map<Integer, List<String>> allNotes = notesReader.getAllNotes();


        for (Map.Entry<Integer, List<String>> entry : allNotes.entrySet()) {
            int userId = entry.getKey();
            User user = loginSystem.getUserById(userId);

            if (user != null) {
                String fullName = user.getFirstName() + " " + user.getLastName();
                List<String> notesList = entry.getValue();

                for (String note : notesList) {
                    tableModel.addRow(new Object[]{fullName, note});
                }
            }
        }
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NotesReader notesReader = new NotesReader(loginSystem);
                notesReader.readNotesFromFile("notes.txt");

                tableModel.setRowCount(0);

                Map<Integer, List<String>> allNotes = notesReader.getAllNotes();

                for (Map.Entry<Integer, List<String>> entry : allNotes.entrySet()) {
                    int userId = entry.getKey();
                    User user = loginSystem.getUserById(userId);

                    if (user != null) {
                        String fullName = user.getFirstName() + " " + user.getLastName();
                        List<String> notesList = entry.getValue();

                        for (String note : notesList) {
                            tableModel.addRow(new Object[]{fullName, note});
                        }
                    }
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DelNotePanel delNotePanel = new DelNotePanel(loginSystem);
                delNotePanel.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();

        NotesPanel notesPanel = new NotesPanel(loginSystem);
        notesPanel.setVisible(true);
    }
}
