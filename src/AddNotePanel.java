import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddNotePanel extends JFrame {
    private JPanel JPanel1;
    private JComboBox<User> studentsBox;
    private JTextArea noteArea1;
    private JButton addButton;
    private JButton clearButton;
    private JButton leaveButton;


    private LoginSystem loginSystem;
    private NotesReader notesReader;


    public AddNotePanel(LoginSystem loginSystem) {
        super("Panel dodawania uwag");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);

        this.loginSystem = loginSystem;
        this.notesReader = new NotesReader(loginSystem);
        this.notesReader.readNotesFromFile("notes.txt");

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
                User selectedUser = (User) studentsBox.getSelectedItem();
                String noteText = noteArea1.getText();
                notesReader.addNoteToList(selectedUser.getId(), noteText);
                notesReader.saveNotes();
                dispose();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addNote() {
        User selectedStudent = (User) studentsBox.getSelectedItem();

        if (selectedStudent != null) {
            String noteText = noteArea1.getText();

            NotesReader notesReader = new NotesReader(loginSystem);
            notesReader.readNotesFromFile("notes.txt");
            notesReader.addNoteToList(selectedStudent.getId(), noteText);
            notesReader.saveNotesToFile("notes.txt");

            JOptionPane.showMessageDialog(null, "Dodano nową uwagę dla ucznia: " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Wybierz ucznia przed dodaniem uwagi.");
        }
    }

    class UserRenderer extends JLabel implements ListCellRenderer<User> {
        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getFirstName() + " " + value.getLastName());
            }
            return this;
        }
    }

    private void clearFields() {
        noteArea1.setText("");
    }

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();

        AddNotePanel addNotePanel = new AddNotePanel(loginSystem);
        addNotePanel.setVisible(true);
    }
}
