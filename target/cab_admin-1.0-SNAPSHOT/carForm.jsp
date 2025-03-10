<%@page import="com.megacitycab.model.Driver"%>
<%@page import="java.util.List"%>
<!-- Car Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="formModalLabel">Add Car</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Car Management Form -->
                <form action="ManageCarServlet" method="post" id="carForm">
                    <input type="hidden" name="action" id="formAction" value="addCar">
                    <input type="hidden" name="carId" id="car-id">

<!--                    <div class="mb-3">
                        <label for="car-driver_id" class="form-label">Driver ID</label>
                        <input type="number" class="form-control" name="driver_id" id="car-driver_id" required>
                    </div>-->

                    <div class="mb-3">
                        <label for="car-driver_id" class="form-label">Driver</label>
                        <select class="form-control" name="driver_id" id="car-driver_id" required>
                            <option value="">Select Driver</option>
                            <% 
                                List<Driver> driverList = (List<Driver>) request.getAttribute("driverList");
                                if (driverList != null) {
                                    for (Driver driver : driverList) {
                            %>
                            <option value="<%= driver.getId() %>"><%= driver.getFullname() %></option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="car-model" class="form-label">Model</label>
                        <input type="text" class="form-control" name="model" id="car-model" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-brand" class="form-label">Brand</label>
                        <input type="text" class="form-control" name="brand" id="car-brand" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-type" class="form-label">Type</label>
                        <input type="text" class="form-control" name="type" id="car-type" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-plateNumber" class="form-label">Plate Number</label>
                        <input type="text" class="form-control" name="plateNumber" id="car-plateNumber" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-year" class="form-label">Year</label>
                        <input type="number" class="form-control" name="year" id="car-year" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-color" class="form-label">Color</label>
                        <input type="text" class="form-control" name="color" id="car-color" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-location" class="form-label">Location</label>
                        <input type="text" class="form-control" name="location" id="car-location" required>
                    </div>
                    <div class="mb-3">
                        <label for="car-status" class="form-label">Status</label>
                        <select class="form-control" name="status" id="car-status" required>
                            <option value="">Select Status</option>
                            <option value="Available">Available</option>
                            <option value="Booked">Booked</option>
                            <option value="Maintenance">Maintenance</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="car-imageURL" class="form-label">Image URL</label>
                        <input type="text" class="form-control" name="imageURL" id="car-imageURL" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100" id="formSubmitButton">Add Car</button>
                </form>
            </div>
        </div>
    </div>
</div>
