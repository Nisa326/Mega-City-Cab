<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

    <title>Admin Panel - Mega City Cab</title>
    <style>
        .card {
            border: 1px solid #ddd; /* Border for the card */
            margin-bottom: 1rem; /* Space between cards */
        }
        .card-body {
            padding: 2rem;
        }
        .card-title {
            margin-top: 1rem;
        }
        .card-icon {
            font-size: 2rem; /* Icon size */
        }
    </style>
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />

    <div class="container mt-4">
        <h2 class="text-center mb-4">Welcome to the Admin Panel</h2>
        <div class="row g-4">
            <!-- Manage Users -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-people-fill card-icon"></i>
                        <h5 class="card-title">Manage Users</h5>
                        <a href="ManageUserServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage User Comment -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-chat-square-dots-fill card-icon"></i>
                        <h5 class="card-title">Manage User Comment</h5>
                        <a href="ManageCommentServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Drivers -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-person-badge card-icon"></i>
                        <h5 class="card-title">Manage Drivers</h5>
                        <a href="ManageDriverServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Cars -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-car-front-fill card-icon"></i>
                        <h5 class="card-title">Manage Cars</h5>
                        <a href="ManageCarServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Bookings -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-bookmarks-fill card-icon"></i>
                        <h5 class="card-title">Manage Bookings</h5>
                        <a href="ManageBookingServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Places -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-geo-alt-fill card-icon"></i>
                        <h5 class="card-title">Manage Places</h5>
                        <a href="managePlace.jsp" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Payments -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-cash-stack card-icon"></i>
                        <h5 class="card-title">Manage Payments</h5>
                        <a href="#" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>

            <!-- Manage Admin -->
            <div class="col-md-4">
                <div class="card shadow-sm h-100">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="bi bi-shield-lock-fill card-icon"></i>
                        <h5 class="card-title">Manage Admin</h5>
                        <a href="ManageAdminServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
