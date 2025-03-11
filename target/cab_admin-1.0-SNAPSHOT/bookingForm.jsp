<%@page import="com.megacitycab.model.Driver"%>
<%@page import="com.megacitycab.model.Car"%>
<%@page import="com.megacitycab.model.User"%>
<%@page import="com.megacitycab.model.Place"%>
<%@page import="java.util.List"%>

<!-- Booking Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="formModalLabel">Add Booking</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Booking Form -->
                <form action="ManageBookingServlet" method="post" id="bookingForm">
                    <input type="hidden" name="action" id="formAction" value="addBooking">
                    <input type="hidden" name="bookingId" id="booking-id">

                    <div class="mb-3">
                        <label for="booking-userId" class="form-label">User Name</label>
                        <select class="form-control" name="userId" id="booking-userId" required>
                            <option value="">Select User</option>
                            <% 
                                List<User> userList = (List<User>) request.getAttribute("userList");
                                if (userList != null) {
                                    for (User user : userList) {
                            %>
                            <option value="<%= user.getId() %>"><%= user.getFullname() %></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="booking-driverId" class="form-label">Select Car / Driver Name</label>
                        <select class="form-control" name="driverId" id="booking-driverId" required>
                            <option value="">Select Car Number Plate</option>
                            <% 
                                List<Car> carList = (List<Car>) request.getAttribute("carList");
                                if (carList != null) {
                                    for (Car car : carList) {
                            %>
                            <option value="<%= car.getId() %>"><%= car.getPlateNumber() %></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="booking-pickup" class="form-label">Pickup Location</label>
                        <select class="form-control" name="pickupLocation" id="booking-pickup" required>
                            <option value="">Select Pickup Location</option>
                            <% 
                                List<Place> placeList = (List<Place>) request.getAttribute("placeList");
                                if (placeList != null) {
                                    for (Place place : placeList) {
                            %>
                            <option value="<%= place.getPlaceName() %>"><%= place.getPlaceName() %></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="booking-dropoff" class="form-label">Dropoff Location</label>
                        <select class="form-control" name="dropoffLocation" id="booking-dropoff" required>
                            <option value="">Select Dropoff Location</option>
                            <% 
                               // List<Place> placeList = (List<Place>) request.getAttribute("placeList");
                                if (placeList != null) {
                                    for (Place place : placeList) {
                            %>
                            <option value="<%= place.getPlaceName() %>"><%= place.getPlaceName() %></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>


                    <div class="mb-3">
                        <label for="booking-date" class="form-label">Ride Date</label>
                        <input type="date" class="form-control" name="rideDate" id="booking-date" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-time" class="form-label">Ride Time</label>
                        <input type="time" class="form-control" name="rideTime" id="booking-time" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-cabType" class="form-label">Cab Type</label>
                        <select class="form-control" name="cabType" id="booking-cabType" required>
                            <option value="">Select Cab Type</option>
                            <option value="Standard">Standard</option>
                            <option value="Luxury">Luxury</option>
                            <option value="Van">Van</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="booking-distance" class="form-label">Distance (km)</label>
                        <input type="number" step="0.1" class="form-control" name="distance" id="booking-distance" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-price" class="form-label">Price</label>
                        <input type="number" step="0.01" class="form-control" name="price" id="booking-price" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-tax" class="form-label">Tax</label>
                        <input type="number" step="0.01" class="form-control" name="tax" id="booking-tax" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-discount" class="form-label">Discount</label>
                        <input type="number" step="0.01" class="form-control" name="discount" id="booking-discount">
                    </div>

                    <div class="mb-3">
                        <label for="booking-totalPrice" class="form-label">Total Price</label>
                        <input type="number" step="0.01" class="form-control" name="totalPrice" id="booking-totalPrice" required>
                    </div>

                    <div class="mb-3">
                        <label for="booking-status" class="form-label">Status</label>
                        <select class="form-control" name="status" id="booking-status" required>
                            <option value="Pending">Pending</option>
                            <option value="Confirmed">Confirmed</option>
                            <option value="Completed">Completed</option>
                            <option value="Cancelled">Cancelled</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary w-100" id="formSubmitButton">Add Booking</button>
                </form>
            </div>
        </div>
    </div>
</div>
