<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Place" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Manage Places - Mega City Cab</title>
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Places</h2>
        </div>

        <!-- Add Place Form -->
        <form action="ManagePlaceServlet" method="post">
            <input type="hidden" name="action" value="addPlace">
            <div class="mb-3">
                <label for="place_name" class="form-label">Place Name</label>
                <input type="text" class="form-control" id="place_name" name="place_name" required>
            </div>
            <button type="submit" class="btn btn-primary">Add Place</button>
        </form>

        <!-- Place List Table -->
        <table class="table table-striped mt-4">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Place Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Place> placeList = (List<Place>) request.getAttribute("placeList");
                    if (placeList != null && !placeList.isEmpty()) {
                        for (Place place : placeList) {
                %>
                <tr>
                    <td><%= place.getId() %></td>
                    <td><%= place.getPlaceName() %></td>
                    <td>
                        <!-- Update Place Form -->
                        <form action="ManagePlaceServlet" method="post" style="display:inline;">
                            <input type="hidden" name="placeId" value="<%= place.getId() %>">
                            <input type="hidden" name="action" value="updatePlace">
                            <input type="text" name="place_name" value="<%= place.getPlaceName() %>" required>
                            <button type="submit" class="btn btn-warning btn-sm">Update</button>
                        </form>
                        <!-- Delete Place Form -->
                        <form action="ManagePlaceServlet" method="post" style="display:inline;">
                            <input type="hidden" name="placeId" value="<%= place.getId() %>">
                            <input type="hidden" name="action" value="deletePlace">
                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="3" class="text-center">No places found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
