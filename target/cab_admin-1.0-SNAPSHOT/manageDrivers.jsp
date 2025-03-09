<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Driver" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <title>Admin Panel - Mega City Cab</title>
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Drivers</h2>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal" onclick="setAddMode()">
                <i class="bi bi-person-plus"></i> Add Driver
            </button>
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchDriver" placeholder="Search Driver">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>NIC</th>
                    <th>License</th>
                    <th>Year</th>
                    <th>Created</th>
                    <th>Gender</th>
                    <th>Address</th>
                    <th>Email</th>  
                    <th>Phone</th>  
                    <th>Image</th>                   
                    <th>Username</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Driver> driverList = (List<Driver>) request.getAttribute("driverList");
                    if (driverList != null && !driverList.isEmpty()) {
                        for (Driver driver : driverList) {
                %>
                <tr>
                    <td><%= driver.getId() %></td>
                    <td><%= driver.getFullname() %></td>
                    <td><%= driver.getNIC() %></td>
                    <td><%= driver.getLicense() %></td>
                    <td><%= driver.getYear() %></td>
                    <td><%= driver.getCreated()%></td>
                    <td><%= driver.getGender() %></td>
                    <td><%= driver.getAddress() %></td>
                    <td><%= driver.getEmail() %></td>
                    <td><%= driver.getPhone() %></td>
                    <td><img src="<%= driver.getImageURL() %>" alt="Driver Image" width="50"></td>
                    <td><%= driver.getUsername() %></td>
                    <td><%= driver.getPassword() %></td>
                    <td>
                        <button class="btn btn-primary btn-sm" 
                            data-bs-toggle="modal" 
                            data-bs-target="#formModal"
                            onclick="setUpdateMode('<%= driver.getId() %>', '<%= driver.getFullname() %>', '<%= driver.getNIC() %>', '<%= driver.getLicense() %>', '<%= driver.getYear() %>', '<%= driver.getCreated() %>', '<%= driver.getGender() %>', '<%= driver.getAddress() %>', '<%= driver.getEmail() %>', '<%= driver.getPhone() %>', '<%= driver.getImageURL() %>', '<%= driver.getUsername() %>', '<%= driver.getUsername() %>')">
                            <i class="bi bi-pencil"></i> Update
                        </button>
                        <form action="ManageDriverServlet" method="post" style="display:inline;">
                            <input type="hidden" name="driverId" value="<%= driver.getId() %>">
                            <input type="hidden" name="action" value="deleteDriver">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="bi bi-trash"></i> Delete
                            </button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="14" class="text-center">No drivers found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

    <%@ include file="driverForm.jsp" %>
    
    <script>
        document.getElementById("searchDriver").addEventListener("keyup", function() {
            let searchValue = this.value.toLowerCase();
            let rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                let name = row.cells[1].textContent.toLowerCase();
                let NIC = row.cells[2].textContent.toLowerCase();
                let email = row.cells[7].textContent.toLowerCase();
                let phone = row.cells[8].textContent.toLowerCase();
                let username = row.cells[10].textContent.toLowerCase();

                if (name.includes(searchValue) || NIC.includes(searchValue) || email.includes(searchValue) || phone.includes(searchValue) || username.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });
    </script>

    <script>
function setAddMode() {
    document.getElementById("formModalLabel").textContent = "Add Driver";
    document.getElementById("formAction").value = "addDriver";
    document.getElementById("formSubmitButton").textContent = "Add Driver";
    
    document.getElementById("signup-id").value = "";
    document.getElementById("signup-name").value = "";
    document.getElementById("signup-NIC").value = "";
    document.getElementById("signup-license").value = "";
    document.getElementById("signup-year").value = "";
    document.getElementById("signup-created").value = "";
    document.getElementById("signup-gender").value = "";
    document.getElementById("signup-address").value = "";
    document.getElementById("signup-email").value = "";
    document.getElementById("signup-phone").value = "";
    document.getElementById("signup-imageURL").value = "";
    document.getElementById("signup-username").value = "";
    document.getElementById("signup-password").value = "";
}

function setUpdateMode(id, name, NIC, license, year, created, gender, address, email, phone, imageURL, username, password) {
    document.getElementById("formModalLabel").textContent = "Update Driver";
    document.getElementById("formAction").value = "updateDriver";
    document.getElementById("formSubmitButton").textContent = "Update Driver";
    
    document.getElementById("signup-id").value = id;
    document.getElementById("signup-name").value = name;
    document.getElementById("signup-NIC").value = NIC;
    document.getElementById("signup-license").value = license;
    document.getElementById("signup-year").value = year;
    document.getElementById("signup-created").value = created;
    document.getElementById("signup-gender").value = gender;
    document.getElementById("signup-address").value = address;
    document.getElementById("signup-email").value = email;
    document.getElementById("signup-phone").value = phone;
    document.getElementById("signup-imageURL").value = imageURL;
    document.getElementById("signup-username").value = username;
    document.getElementById("signup-password").value = password;
}
    </script>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
