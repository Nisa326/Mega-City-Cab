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

    <style>
        /* Background image styling */
        .background {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-image: url('https://img.freepik.com/free-vector/taxi-app-concept_23-2148496604.jpg?t=st=1741618532~exp=1741622132~hmac=28afe9451ee253e5c09df8631d6d376422e0ae212cbfa69cddfaca10cf7b3533&w=996');
            background-size: cover;
            background-position: center;
            filter: blur(2px); /* Apply blur effect to the background */
            z-index: -1; /* Places the background behind the content */
        }

        /* Content container styling */
        .container {
            position: relative;
            z-index: 1; /* Keeps the form content in front of the background */
        }

        .card {
            background-color: rgba(255, 255, 255, 1); /* Slightly transparent background for the card */
            border: 0.1px solid black; /* Black border for the card */
            border-radius: 20px; /* Optional: rounded corners */
            transition: border 0.5s ease; /* Transition effect for the border */
        }

        /* Fade out border on hover */
        .card:hover {
            border-color: rgba(0, 0, 0, 0); /* Makes the border fade out */
        }

        body {
            margin: 0;
            height: 100vh;
            overflow: hidden; /* Prevents scrolling */
        }
    </style>
</head>
<body>
    <!-- Background Image with Blur -->
    <div class="background"></div>

    <!-- Content Section -->
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
