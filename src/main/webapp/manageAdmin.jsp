<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Admin" %>
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
            <h2 class="text-center flex-grow-1">Manage Admin</h2>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal" onclick="setAddMode()">
                <i class="bi bi-person-plus"></i> Add Admin
            </button>
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchAdmin" placeholder="Search Admin">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>NIC</th>
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
                    List<Admin> adminList = (List<Admin>) request.getAttribute("adminList");
                    if (adminList != null && !adminList.isEmpty()) {
                        for (Admin admin : adminList) {
                %>
                <tr>
                    <td><%= admin.getId() %></td>
                    <td><%= admin.getFullname() %></td>
                    <td><%= admin.getNIC() %></td>
                    <td><%= admin.getGender() %></td>
                    <td><%= admin.getAddress() %></td>
                    <td><%= admin.getEmail() %></td>
                    <td><%= admin.getPhone() %></td>
                    <td><img src="<%= admin.getImageURL() %>" alt="Admin Image" width="50"></td>
                    <td><%= admin.getUsername() %></td>
                    <td><%= admin.getPassword() %></td>
                    <td>
                        <button class="btn btn-primary btn-sm" 
                            data-bs-toggle="modal" 
                            data-bs-target="#formModal"
                            onclick="setUpdateMode('<%= admin.getId() %>', '<%= admin.getFullname() %>', '<%= admin.getNIC() %>', '<%= admin.getGender() %>', '<%= admin.getAddress() %>', '<%= admin.getEmail() %>', '<%= admin.getPhone() %>', '<%= admin.getImageURL() %>', '<%= admin.getUsername() %>', '<%= admin.getPassword() %>')">
                            <i class="bi bi-pencil"></i> Update
                        </button>
                        <form action="ManageAdminServlet" method="post" style="display:inline;">
                            <input type="hidden" name="adminId" value="<%= admin.getId() %>">
                            <input type="hidden" name="action" value="deleteAdmin">
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
                    <td colspan="11" class="text-center">No users found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

    <%@ include file="adminForm.jsp" %>
    
    <!-- JavaScript for Search Functionality -->
    <script>
        document.getElementById("searchAdmin").addEventListener("keyup", function() {
            let searchValue = this.value.toLowerCase();
            let rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                let name = row.cells[1].textContent.toLowerCase();
                let NIC = row.cells[2].textContent.toLowerCase();
                let email = row.cells[4].textContent.toLowerCase();
                let phone = row.cells[5].textContent.toLowerCase();
                let username = row.cells[8].textContent.toLowerCase();

                if (name.includes(searchValue) || NIC.includes(searchValue) || email.includes(searchValue) || phone.includes(searchValue) || username.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });
    </script>


    <script>
        // Function to set the form to "Add User" mode
function setAddMode() {
    document.getElementById("formModalLabel").textContent = "Add Admin";
    document.getElementById("formAction").value = "addAdmin";
    document.getElementById("formSubmitButton").textContent = "Add Admin";
    
    // Clear input fields
    document.getElementById("signup-id").value = "";
    document.getElementById("signup-name").value = "";
    document.getElementById("signup-NIC").value = "";
    document.getElementById("signup-gender").value = "";
    document.getElementById("signup-address").value = "";
    document.getElementById("signup-email").value = "";
    document.getElementById("signup-phone").value = "";
    document.getElementById("signup-imageURL").value = "";
    document.getElementById("signup-username").value = "";
    document.getElementById("signup-password").value = "";
}

// Function to set the form to "Update User" mode
function setUpdateMode(id, name, NIC, gender, address, email, phone, imageURL, username, password) {
    document.getElementById("formModalLabel").textContent = "Update Admin";
    document.getElementById("formAction").value = "updateAdmin";
    document.getElementById("formSubmitButton").textContent = "Update Admin";

    // Pre-fill form fields
    document.getElementById("signup-id").value = id;
    document.getElementById("signup-name").value = name;
    document.getElementById("signup-NIC").value = NIC;
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
