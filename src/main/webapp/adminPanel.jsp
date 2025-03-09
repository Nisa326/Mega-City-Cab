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
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />

    <div class="container mt-4">
        <h2 class="text-center">Welcome to the Admin Panel</h2>
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Users</h5>
                        <a href="ManageUserServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage User Comment</h5>
                        <a href="ManageCommentServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Drivers</h5>
                        <a href="ManageDriverServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Cars</h5>
                        <a href="ManageCarServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-car-front-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Bookings</h5>
                        <a href="ManageBookingServlet" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-car-front-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Places</h5>
                        <a href="managePlace.jsp" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-cash-stack" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Payments</h5>
                        <a href="#" class="btn btn-primary">Go</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <i class="bi bi-people-fill" style="font-size: 2rem;"></i>
                        <h5 class="card-title mt-2">Manage Admin</h5>
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
