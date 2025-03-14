<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Booking" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Manage Payments - Mega City Cab</title>
</head>
<body>

    <jsp:include page="navbar.jsp" />

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Payments</h2>
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchPayment" placeholder="Search Payment">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User ID</th>
                    <th>Driver ID</th>
                    <th>Ride Date</th>
                    <th>Ride Time</th>
                    <th>Price</th>
                    <th>Tax</th>
                    <th>Discount</th>
                    <th>Total Price</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Booking> bookingList = (List<Booking>) request.getAttribute("bookingList");
                    if (bookingList != null && !bookingList.isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
                        for (Booking booking : bookingList) {
                %>
                <tr>
                    <td><%= booking.getId() %></td>
                    <td><%= booking.getUserId() %></td>
                    <td><%= booking.getDriverId() %></td>
                    <td><%= booking.getRideDate() != null ? sdf.format(booking.getRideDate()) : "" %></td>
                    <td><%= booking.getRideTime() != null ? sdfTime.format(booking.getRideTime()) : "" %></td>
                    <td><%= booking.getPrice() %></td>
                    <td><%= booking.getTax() %></td>
                    <td><%= booking.getDiscount() %></td>
                    <td><%= booking.getTotalPrice() %></td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="9" class="text-center">No bookings found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>
            
<script>
    document.getElementById("searchPayment").addEventListener("keyup", function () {
        let searchValue = this.value.toLowerCase();
        let rows = document.querySelectorAll("tbody tr");

        rows.forEach(row => {
            let id = row.cells[0].textContent.toLowerCase();
            let userId = row.cells[1].textContent.toLowerCase();
            let driverId = row.cells[2].textContent.toLowerCase();
            let rideDate = row.cells[3].textContent.toLowerCase();
            let rideTime = row.cells[4].textContent.toLowerCase();
            let totalPrice = row.cells[8].textContent.toLowerCase();

            if (id.includes(searchValue) || userId.includes(searchValue) || driverId.includes(searchValue) || 
                rideDate.includes(searchValue) || rideTime.includes(searchValue) || totalPrice.includes(searchValue)) {
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
