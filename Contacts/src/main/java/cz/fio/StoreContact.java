package cz.fio;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name = "storeContact", value = "/contact")
public class StoreContact extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the GET request
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        // Check if any of the parameters are null or empty
        if (firstName == null || lastName == null || email == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing or empty parameters");
            return;
        }

        // Create a CSV line with the provided data
        String csvLine = String.join(",", firstName, lastName, email);

        // Check if the data already exists in the CSV file
        if (!isDataExists(csvLine)) {
            // Append the data to the CSV file
            appendToCSV(csvLine);
            response.getWriter().println("Data inserted successfully");
        } else {
            response.getWriter().println("Data already exists in the file");
        }
    }

    private boolean isDataExists(String newData) throws IOException {
        // Read all lines from the CSV file
        Path filePath = Paths.get(System.getProperty("java.io.tmpdir"), "contacts.csv");
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
}