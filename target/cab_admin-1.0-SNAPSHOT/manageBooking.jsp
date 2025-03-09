<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Booking" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <title>Manage Bookings - Mega City Cab</title>
</head>
<body>
    
    <jsp:include page="navbar.jsp" />
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Bookings</h2>
            <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#formModal" onclick="setAddMode()">
                <i class="bi bi-person-plus"></i> Add Booking
            </button>
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchBooking" placeholder="Search Booking">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User ID</th>
                    <th>Driver ID</th>
                    <th>Pickup Location</th>
                    <th>Dropoff Location</th>
                    <th>Ride Date</th>
                    <th>Ride Time</th>
                    <th>Cab Type</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
                    if (bookingList != null && !bookingList.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                        for (Booking booking : bookingList) {
                %>
                <tr>
                    <td><%= booking.getId() %></td>
                    <td><%= booking.getUserId() %></td>
                    <td><%= booking.getDriverId() %></td>
                    <td><%= booking.getPickupLocation() %></td>
                    <td><%= booking.getDropoffLocation() %></td>
                    <td><%= sdf.format(booking.getRideDate()) %></td> 
                    <td><%= booking.getRideTime() != null ? sdfTime.format(booking.getRideTime()) : "" %></td>
                    <td><%= booking.getCabType() %></td>
                    <td><%= booking.getStatus() %></td>
                    <td>
                        <form action="ManageBookingServlet" method="post" style="display:inline;">
                            <input type="hidden" name="bookingId" value="<%= booking.getId() %>">
                            <input type="hidden" name="action" value="deleteBooking">
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
                    <td colspan="10" class="text-center">No bookings found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

    <%@ include file="bookingForm.jsp" %>
    
    <script>
        document.getElementById("searchBooking").addEventListener("keyup", function() {
            let searchValue = this.value.toLowerCase();
            let rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                let pickup = row.cells[3].textContent.toLowerCase();
                let dropoff = row.cells[4].textContent.toLowerCase();
                let date = row.cells[5].textContent.toLowerCase();
                let status = row.cells[8].textContent.toLowerCase();

                if (pickup.includes(searchValue) || dropoff.includes(searchValue) || date.includes(searchValue) || status.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
