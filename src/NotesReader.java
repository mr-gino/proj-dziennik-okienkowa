import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotesReader {
    private Map<Integer, Notes> notesMap;
    private LoginSystem loginSystem;

    public NotesReader(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.notesMap = new HashMap<>();
    }

    public void readNotesFromFile(String filePath) {
        notesMap = new HashMap<>();

        System.out.println("Rozpoczęto wczytywanie notatek z pliku: " + filePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(";");
                if (parts.length == 2) {
                    int userId = Integer.parseInt(parts[0]);
                    String[] notesArray = parts[1].split(",");
                    List<String> notesList = new ArrayList<>();
                    for (String note : notesArray) {
                        notesList.add(note);
                    }
                    Notes notes = new Notes(userId);
                    notes.setNotes(notesList);
                    notesMap.put(userId, notes);
                }
            }
        } catch (IOException e) {
            System.out.println("Błąd odczytu pliku: " + e.getMessage());
        }
    }

    public Map<Integer, List<String>> getAllNotes() {
        Map<Integer, List<String>> allNotes = new HashMap<>();
        for (Map.Entry<Integer, Notes> entry : notesMap.entrySet()) {
            int userId = entry.getKey();
            Notes notes = entry.getValue();
            List<String> notesList = notes.getNotes();
            allNotes.put(userId, notesList);
        }
        return allNotes;
    }

    public void removeNote(int userId, int noteIndex) {
        if (notesMap.containsKey(userId)) {
            Notes notes = notesMap.get(userId);
            List<String> notesList = notes.getNotes();

            if (noteIndex >= 0 && noteIndex < notesList.size()) {
                notesList.remove(noteIndex);
            } else {
                System.out.println("Nieprawidłowy indeks uwagi.");
            }
        } else {
            System.out.println("Brak uwag.");
        }
    }

    public List<String> getNotesForUser(int userId) {
        if (notesMap.containsKey(userId)) {
            Notes notes = notesMap.get(userId);
            return notes.getNotes();
        } else {
            return new ArrayList<>();
        }
    }

    public void addNoteToList(int userId, String note) {
        if (notesMap.containsKey(userId)) {
            Notes notes = notesMap.get(userId);
            notes.addNoteToList(note);
        } else {
            Notes notes = new Notes(userId);
            notes.addNoteToList(note);
            notesMap.put(userId, notes);
        }

        saveNotesToFile("notes.txt");
    }

    public void saveNotes() {
        saveNotesToFile("notes.txt");
    }

    public void saveNotesToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Integer, Notes> entry : notesMap.entrySet()) {
                int userId = entry.getKey();
                Notes notes = entry.getValue();
                List<String> notesList = notes.getNotes();

                StringBuilder line = new StringBuilder();
                line.append(userId).append(";");
                line.append(String.join(",", notesList));

                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania notatek do pliku: " + e.getMessage());
        }
    }
}

