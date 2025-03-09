<!-- User Form Modal -->
<div class="modal fade" id="formModal" tabindex="-1" aria-labelledby="formModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="formModalLabel">Add User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Sign Up Form -->
                <form action="ManageUserServlet" method="post" id="userForm">
                    <input type="hidden" name="action" id="formAction" value="addUser">
                    <input type="hidden" name="userId" id="signup-id">

                    <div class="mb-3">
                        <label for="signup-name" class="form-label">Full Name</label>
                        <input type="text" class="form-control" name="fullname" id="signup-name" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-NIC" class="form-label">NIC Number</label>
                        <input type="text" class="form-control" name="NIC" id="signup-NIC" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-gender" class="form-label">Gender</label>
                        <select class="form-control" name="gender" id="signup-gender" required>
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="signup-address" class="form-label">Address</label>
                        <input type="text" class="form-control" name="address" id="signup-address" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-email" class="form-label">Email Address</label>
                        <input type="email" class="form-control" name="email" id="signup-email" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-phone" class="form-label">Phone No</label>
                        <input type="tel" class="form-control" name="phone" id="signup-phone" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-imageURL" class="form-label">Image URL</label>
                        <input type="text" class="form-control" name="imageURL" id="signup-imageURL" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-username" class="form-label">User Name</label>
                        <input type="text" class="form-control" name="username" id="signup-username" required>
                    </div>
                    <div class="mb-3">
                        <label for="signup-password" class="form-label">Password</label>
                        <input type="password" class="form-control" name="password" id="signup-password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100" id="formSubmitButton">Add User</button>
                </form>
            </div>
        </div>
    </div>
</div>
