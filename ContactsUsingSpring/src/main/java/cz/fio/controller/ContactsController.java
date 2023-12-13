package cz.fio.controller;

import cz.fio.service.ContactService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-javista")
public class ContactsController {

    private final ContactService contactService;

    @GetMapping("/contact")
    public String storeContact(@RequestParam String firstName, @RequestParam String lastName,
                               @RequestParam String email,
                               HttpServletResponse response) throws IOException {
        // Custom validation logic
        if (firstName == null || lastName == null || email == null ||
                firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Missing or empty parameters";
        }
        return contactService.addContact(firstName, lastName, email);
    }

    @GetMapping("/contactList")
    public List<String> getAllContact() {
        return contactService.readContactLinesFromCSV();
    }
}
