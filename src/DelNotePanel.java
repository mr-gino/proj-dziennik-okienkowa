import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DelNotePanel extends JFrame {
    private JPanel JPanel1;
    private JList<String> list1;
    private JComboBox<User> studentBox;
    private JButton deleteButton;
    private JButton closeButton;
    private JButton refreshButton;

    private LoginSystem loginSystem;
    private NotesReader notesReader;

    public DelNotePanel(LoginSystem loginSystem) {
        super("Panel usuwania uwag");
        setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 550, height = 500;
        this.setSize(width, height);


        this.loginSystem = loginSystem;
        this.notesReader = new NotesReader(loginSystem);
        this.notesReader.readNotesFromFile("notes.txt");

        DefaultComboBoxModel<User> studentModel = new DefaultComboBoxModel<>();

        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentModel.addElement(student);
        }

        studentBox.setModel(studentModel);
        studentBox.setRenderer(new UserRenderer());

        studentBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadNotesForSelectedStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedNote();
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
                refreshStudentList();
            }
        });
    }

    private void loadNotesForSelectedStudent() {
        User selectedStudent = (User) studentBox.getSelectedItem();

        if (selectedStudent != null) {
            List<String> studentNotes = notesReader.getNotesForUser(selectedStudent.getId());

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String note : studentNotes) {
                listModel.addElement(note);
            }
            list1.setModel(listModel);
        }
    }

    private void refreshStudentList() {
        DefaultComboBoxModel<User> studentModel = (DefaultComboBoxModel<User>) studentBox.getModel();
        studentModel.removeAllElements();

        List<User> students = loginSystem.getStudents();

        for (User student : students) {
            studentModel.addElement(student);
        }
    }

    private void deleteSelectedNote() {
        User selectedStudent = (User) studentBox.getSelectedItem();
        int selectedIndex = list1.getSelectedIndex();

        if (selectedStudent != null && selectedIndex != -1) {
            notesReader.removeNote(selectedStudent.getId(), selectedIndex);
            notesReader.saveNotesToFile("notes.txt");
            loadNotesForSelectedStudent();
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

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        DelNotePanel delNotePanel = new DelNotePanel(loginSystem);
        delNotePanel.setVisible(true);
    }
}
