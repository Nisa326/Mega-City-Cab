<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Car" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <title>Manage Cars - Mega City Cab</title>
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Cars</h2>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal" onclick="setAddMode()">
                <i class="bi bi-car-front"></i> Add Car
            </button>
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchCar" placeholder="Search Car">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Driver ID</th>
                    <th>Model</th>
                    <th>Brand</th>
                    <th>Type</th>
                    <th>Plate Number</th>
                    <th>Year</th>
                    <th>Color</th>
                    <th>Location</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Car> carList = (List<Car>) request.getAttribute("carList");
                    if (carList != null && !carList.isEmpty()) {
                        for (Car car : carList) {
                %>
                <tr>
                    <td><%= car.getId() %></td>
                    <td><%= car.getDriverId() %></td>
                    <td><%= car.getModel() %></td>
                    <td><%= car.getBrand() %></td>
                    <td><%= car.getType() %></td>
                    <td><%= car.getPlateNumber() %></td>
                    <td><%= car.getYear() %></td>
                    <td><%= car.getColor() %></td>
                    <td><%= car.getLocation() %></td>
                    <td><%= car.getStatus() %></td>
                    <td><img src="<%= car.getImageURL() %>" alt="Car Image" width="50"></td>
                    <td>
                        <button class="btn btn-primary btn-sm" 
                            data-bs-toggle="modal" 
                            data-bs-target="#formModal"
                            onclick="setUpdateMode('<%= car.getId() %>', '<%= car.getDriverId() %>', '<%= car.getModel() %>', '<%= car.getBrand() %>', '<%= car.getType() %>', '<%= car.getPlateNumber() %>', '<%= car.getYear() %>', '<%= car.getColor() %>', '<%= car.getLocation() %>', '<%= car.getStatus() %>', '<%= car.getImageURL() %>')">
                            <i class="bi bi-pencil"></i> Update
                        </button>
                        <form action="ManageCarServlet" method="post" style="display:inline;">
                            <input type="hidden" name="carId" value="<%= car.getId() %>">
                            <input type="hidden" name="action" value="deleteCar">
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
                    <td colspan="12" class="text-center">No cars found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

    <%@ include file="carForm.jsp" %>
    
    <!-- JavaScript for Search Functionality -->
    <script>
        document.getElementById("searchCar").addEventListener("keyup", function() {
            let searchValue = this.value.toLowerCase();
            let rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                let model = row.cells[2].textContent.toLowerCase();
                let brand = row.cells[3].textContent.toLowerCase();
                let plate = row.cells[5].textContent.toLowerCase();
                let location = row.cells[8].textContent.toLowerCase();
                
                if (model.includes(searchValue) || brand.includes(searchValue) || plate.includes(searchValue) || location.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });
    </script>

    <script>
    function setAddMode() {
        document.getElementById("formModalLabel").textContent = "Add Car";
        document.getElementById("formAction").value = "addCar";
        document.getElementById("formSubmitButton").textContent = "Add Car";

        document.getElementById("car-id").value = "";
        document.getElementById("car-driver_id").value = "";
        document.getElementById("car-model").value = "";
        document.getElementById("car-brand").value = "";
        document.getElementById("car-type").value = "";
        document.getElementById("car-plateNumber").value = "";
        document.getElementById("car-year").value = "";
        document.getElementById("car-color").value = "";w
        document.getElementById("car-location").value = "";
        document.getElementById("car-status").value = "";
        document.getElementById("car-imageURL").value = "";
    }

    function setUpdateMode(id, driver_id, model, brand, type, plateNumber, year, color, location, status, imageURL) {
        document.getElementById("formModalLabel").textContent = "Update Car";
        document.getElementById("formAction").value = "updateCar";
        document.getElementById("formSubmitButton").textContent = "Update Car";

        document.getElementById("car-id").value = id;
        document.getElementById("car-driver_id").value = driver_id;
        document.getElementById("car-model").value = model;
        document.getElementById("car-brand").value = brand;
        document.getElementById("car-type").value = type;
        document.getElementById("car-plateNumber").value = plateNumber;
        document.getElementById("car-year").value = year;
        document.getElementById("car-color").value = color;
        document.getElementById("car-location").value = location;
        document.getElementById("car-status").value = status;
        document.getElementById("car-imageURL").value = imageURL;
    }

    </script>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
