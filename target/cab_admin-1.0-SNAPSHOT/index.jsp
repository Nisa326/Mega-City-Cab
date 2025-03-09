<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">

    <title>Sign In - Mega City Cab</title>
</head>
<body>
    <div class="container d-flex justify-content-center align-items-center vh-100">
        <div class="card p-4 shadow" style="width: 350px;">
            <h1 class="text-center mb-4">Mega City Cab</h1>
            <h2 class="text-center mb-4">Admin Sign In</h2>
            <form action="adminSignIn" method="post">
                <div class="mb-3">
                    <label for="signin-username" class="form-label">Username</label>
                    <input type="text" class="form-control" name="username" id="signin-username" required>
                </div>
                <div class="mb-3">
                    <label for="signin-password" class="form-label">Password</label>
                    <input type="password" class="form-control" name="password" id="signin-password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Sign In</button>
            </form>
        </div>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
