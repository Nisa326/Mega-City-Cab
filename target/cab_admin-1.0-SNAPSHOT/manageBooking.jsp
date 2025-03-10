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
                    <th>Total</th>
                    <th>Status</th>
                    <th>Actions</th>
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
                    <td><%= booking.getPickupLocation() %></td>
                    <td><%= booking.getDropoffLocation() %></td>
                    <td><%= booking.getRideDate() != null ? sdf.format(booking.getRideDate()) : "" %></td> 
                    <td><%= booking.getRideTime() != null ? sdfTime.format(booking.getRideTime()) : "" %></td>
                    <td><%= booking.getCabType() %></td>
                    <td><%= booking.getTotalPrice() %></td>
                    <td><%= booking.getStatus() %></td>
                    <td>
                        <button class="btn btn-primary btn-sm" 
                            data-bs-toggle="modal" 
                            data-bs-target="#formModal"
                            onclick="setUpdateMode('<%= booking.getId() %>', '<%= booking.getUserId() %>', '<%= booking.getDriverId() %>', '<%= booking.getPickupLocation() %>', '<%= booking.getDropoffLocation() %>', '<%= sdf.format(booking.getRideDate()) %>', '<%= booking.getRideTime() != null ? sdfTime.format(booking.getRideTime()) : "" %>', '<%= booking.getCabType() %>', '<%= booking.getDistance() %>', '<%= booking.getPrice() %>', '<%= booking.getTax() %>', '<%= booking.getDiscount() %>', '<%= booking.getTotalPrice() %>', '<%= booking.getStatus() %>')">
                            <i class="bi bi-pencil"></i> Update
                        </button>              
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
                    <td colspan="11" class="text-center">No bookings found.</td>
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
                let status = row.cells[9].textContent.toLowerCase(); // Corrected column index

                if (pickup.includes(searchValue) || dropoff.includes(searchValue) || date.includes(searchValue) || status.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });

        function setAddMode() {
            document.getElementById("formModalLabel").textContent = "Add Booking";
            document.getElementById("formAction").value = "addBooking";
            document.getElementById("formSubmitButton").textContent = "Add Booking";
            
            // Clear input fields
            document.querySelectorAll("#formModal input").forEach(input => input.value = "");
        }

        function setUpdateMode(id, userId, driverId, pickup, dropoff, rideDate, rideTime, cabType, distance, price, tax, discount, totalPrice, status) {
            document.getElementById("formModalLabel").textContent = "Update Booking";
            document.getElementById("formAction").value = "updateBooking";
            document.getElementById("formSubmitButton").textContent = "Update Booking";

            document.getElementById("booking-id").value = id;
            document.getElementById("booking-userId").value = userId;
            document.getElementById("booking-driverId").value = driverId;
            document.getElementById("booking-pickup").value = pickup;
            document.getElementById("booking-dropoff").value = dropoff;
            document.getElementById("booking-date").value = rideDate; // Ensure date format is compatible
            document.getElementById("booking-time").value = rideTime; // Ensure time format is compatible
            document.getElementById("booking-cabType").value = cabType;
            document.getElementById("booking-distance").value = distance;
            document.getElementById("booking-price").value = price;
            document.getElementById("booking-tax").value = tax;
            document.getElementById("booking-discount").value = discount;
            document.getElementById("booking-totalPrice").value = totalPrice;
            document.getElementById("booking-status").value = status;
        }

        
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
