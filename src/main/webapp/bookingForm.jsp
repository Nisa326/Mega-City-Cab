<!-- Booking Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="bookingModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="bookingModalLabel">Book a Ride</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Booking Form -->
                <form action="ManageBookingServlet" method="post" id="bookingForm">
                    <input type="hidden" name="action" value="addBooking">
                    <input type="hidden" name="bookingId" id="booking-id">

                    <div class="mb-3">
                        <label for="booking-userId" class="form-label">User ID</label>
                        <input type="number" class="form-control" name="userId" id="booking-userId" required>
                    </div>
                    <div class="mb-3">
                        <label for="booking-driverId" class="form-label">Driver ID</label>
                        <input type="number" class="form-control" name="driverId" id="booking-driverId" required>
                    </div>
                    <div class="mb-3">
                        <label for="booking-pickup" class="form-label">Pickup Location</label>
                        <input type="text" class="form-control" name="pickupLocation" id="booking-pickup" required>
                    </div>
                    <div class="mb-3">
                        <label for="booking-dropoff" class="form-label">Dropoff Location</label>
                        <input type="text" class="form-control" name="dropoffLocation" id="booking-dropoff" required>
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
                    <button type="submit" class="btn btn-primary w-100" id="bookingSubmitButton">Book Ride</button>
                </form>
            </div>
        </div>
    </div>
</div>
