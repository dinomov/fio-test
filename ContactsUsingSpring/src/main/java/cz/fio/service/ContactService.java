package cz.fio.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    private static final String CSV_FILE_PATH = System.getProperty("java.io.tmpdir") + File.separator + "contacts.csv";

    public String addContact(String firstName, String lastName, String email) throws IOException {
        // Create a CSV line with the provided data
        String csvLine = String.join(",", firstName, lastName, email);

        // Check if the contact already exists in the CSV file
        if (!isDataExists(csvLine)) {
            // If not, add the contact to the CSV file
            appendToCSV(csvLine);
            return "Contact added successfully!";
        } else {
            return "Contact already exists!";
        }
    }

    private boolean isDataExists(String newData) throws IOException {
        // Read all lines from the CSV file
        Path filePath = Paths.get(CSV_FILE_PATH);
        if (Files.exists(filePath)) {
            return Files.lines(filePath, StandardCharsets.UTF_8)
                    .anyMatch(line -> line.equals(newData));
        }
        return false;
    }

    private void appendToCSV(String newData) throws IOException {
        // Append the new data to the CSV file
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), "contacts.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath.toString(), true))) {
            writer.println(newData);
        }
    }

    public List<String> readContactLinesFromCSV() {
        List<String> contactLines = new ArrayList<>();

        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), "contacts.csv");
        if (!Files.exists(filePath)) {
            return contactLines;
        }

        String fileName = System.getProperty("java.io.tmpdir") + "/contacts.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contactLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactLines;
    }
}
