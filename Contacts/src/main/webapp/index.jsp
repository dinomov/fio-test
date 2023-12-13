<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Contacts</title>
    <script>
        function submitForm() {
            var firstName = document.getElementById("firstName").value;
            var lastName = document.getElementById("lastName").value;
            var email = document.getElementById("email").value;

            // Adjust the URL and append query parameters
            var url = "contact?" +
                "firstName=" + encodeURIComponent(firstName) +
                "&lastName=" + encodeURIComponent(lastName) +
                "&email=" + encodeURIComponent(email);

            // Make a GET request with Fetch API
            fetch(url)
                .then(response => response.text()) // Read the response as text
                .then(result => {
                    // Handle the result, e.g., show an alert
                    alert("Result: " + result);

                    displayContactList();
                })
                .catch(error => {
                    console.error("Error:", error);
                });

            // Prevent the form from submitting in the traditional way
            return false;
        }

        function displayContactList() {
            // Make a GET request to the ContactListServlet
            fetch('contactList')
                .then(response => response.json())
                .then(contactLines => {
                    // Display the contact lines in the HTML
                    var contactList = document.getElementById('contactList');
                    contactList.innerHTML = "";
                    contactLines.forEach(line => {
                        var listItem = document.createElement('li');
                        listItem.textContent = line;
                        contactList.appendChild(listItem);
                    });
                })
                .catch(error => {
                    console.error("Error:", error);
                });
        }

        window.onload = displayContactList;
    </script>
</head>
<body>
<h1>Contact Form</h1>

<form onsubmit="return submitForm()">
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" required><br>

    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <input type="submit" value="Store Contact">
</form>
<br/>
<h1><%= "Contacts" %>
</h1>
<br/>
<ul id="contactList"></ul>
</body>
</html>