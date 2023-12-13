package cz.fio;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/contactList")
public class ContactListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // Read contact data from the CSV file as a list of strings
        List<String> contactLines = readContactLinesFromCSV();

        // Convert the list to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContactLines = objectMapper.writeValueAsString(contactLines);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Send the JSON response
        response.getWriter().write(jsonContactLines);
    }

    private List<String> readContactLinesFromCSV() {
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
