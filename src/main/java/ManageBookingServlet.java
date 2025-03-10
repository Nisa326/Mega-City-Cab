import com.megacitycab.model.Booking;
import com.megacitycab.model.Car;
import com.megacitycab.model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;

@WebServlet("/ManageBookingServlet")
public class ManageBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Create lists to hold data for bookings, users, and cars
    List<Booking> bookingList = new ArrayList<>();
    List<User> userList = new ArrayList<>();
    List<Car> carList = new ArrayList<>();

    // Connect to the database and retrieve data
    try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Database connection is null.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.");
            return;
        }

        // Fetch all bookings
        String bookingSql = "SELECT * FROM bookings";
        PreparedStatement bookingStmt = conn.prepareStatement(bookingSql);
        ResultSet bookingRs = bookingStmt.executeQuery();
        while (bookingRs.next()) {
            Booking booking = new Booking(
                bookingRs.getInt("id"),
                bookingRs.getInt("userId"),
                bookingRs.getInt("driverId"),
                bookingRs.getString("pickupLocation"),
                bookingRs.getString("dropoffLocation"),
                bookingRs.getDate("rideDate"),
                bookingRs.getTime("rideTime"),
                bookingRs.getString("cabType"),
                bookingRs.getDouble("distance"),
                bookingRs.getDouble("price"),
                bookingRs.getDouble("tax"),
                bookingRs.getDouble("discount"),
                bookingRs.getDouble("totalPrice"),
                bookingRs.getString("status"),
                bookingRs.getTimestamp("createdAt") // Using Timestamp for createdAt
            );
            bookingList.add(booking);
        }

        // Fetch all users
        String userSql = "SELECT * FROM users"; // Assuming 'users' table contains the users
        PreparedStatement userStmt = conn.prepareStatement(userSql);
        ResultSet userRs = userStmt.executeQuery();
        while (userRs.next()) {
            User user = new User(
                userRs.getInt("id"),
                userRs.getString("fullname"),
                userRs.getString("NIC"),
                userRs.getString("gender"),
                userRs.getString("address"),
                userRs.getString("email"),
                userRs.getInt("phone"),
                userRs.getString("imageURL"),
                userRs.getString("username"),
                userRs.getString("password")
            );
            userList.add(user);
        }

        // Fetch all cars
        String carSql = "SELECT * FROM cars"; // Assuming 'cars' table contains car info
        PreparedStatement carStmt = conn.prepareStatement(carSql);
        ResultSet carRs = carStmt.executeQuery();
        while (carRs.next()) {
            Car car = new Car(
                carRs.getInt("id"),
                carRs.getInt("driver_id"),
                carRs.getString("model"),
                carRs.getString("brand"),
                carRs.getString("type"),
                carRs.getString("plateNumber"),
                carRs.getInt("year"),
                carRs.getString("color"),
                carRs.getString("location"),
                carRs.getString("status"),
                carRs.getString("imageURL")
            );
            carList.add(car);
        }

        // Set attributes for use in the JSP
        request.setAttribute("bookingList", bookingList);
        request.setAttribute("userList", userList);
        request.setAttribute("carList", carList);

        // Forward the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageBooking.jsp");
        dispatcher.forward(request, response);

    } catch (SQLException e) {
        // Log the error and send internal server error response
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
    }
}


@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action != null) {
        switch (action) {                
            case "addBooking":
                addBooking(request, response);
                break;
            case "updateBooking":
                updateBooking(request, response);
                break;
            case "deleteBooking":
                deleteBooking(request, response);
                break;
            default:
                System.out.println("Unknown action: " + action);  // Log unknown action
                response.sendRedirect("manageBooking.jsp");                    
        }
    } else {
        response.sendRedirect("manageBooking.jsp");
    }
}


    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve input parameters
        String rideDateStr = request.getParameter("rideDate");
        String rideTimeStr = request.getParameter("rideTime");
        String userIdStr = request.getParameter("userId");
        String driverIdStr = request.getParameter("driverId");
        String pickupLocation = request.getParameter("pickupLocation");
        String dropoffLocation = request.getParameter("dropoffLocation");
        String cabType = request.getParameter("cabType");
        String distanceStr = request.getParameter("distance");
        String priceStr = request.getParameter("price");
        String taxStr = request.getParameter("tax");
        String discountStr = request.getParameter("discount");
        String totalPriceStr = request.getParameter("totalPrice");
        String status = request.getParameter("status");
        
        // Validate required fields
        if (userIdStr == null || driverIdStr == null || pickupLocation == null || dropoffLocation == null || cabType == null ||
            distanceStr == null || priceStr == null || taxStr == null || discountStr == null || totalPriceStr == null || status == null ||
            rideDateStr == null || rideTimeStr == null || userIdStr.isEmpty() || driverIdStr.isEmpty() || pickupLocation.isEmpty() || 
            dropoffLocation.isEmpty() || cabType.isEmpty() || distanceStr.isEmpty() || priceStr.isEmpty() || taxStr.isEmpty() ||
            discountStr.isEmpty() || totalPriceStr.isEmpty() || status.isEmpty() || rideDateStr.isEmpty() || rideTimeStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }
        
        try (Connection conn = DBConnection.getConnection()) {
        if (conn == null) {
            System.out.println("Database connection is null.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.");
            return;
        }

        // Validate userId and driverId as integers
        int userId, driverId;
        try {
            userId = Integer.parseInt(userIdStr);
            driverId = Integer.parseInt(driverIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID or driver ID.");
            return;
        }

        // Validate numeric fields (distance, price, tax, discount, totalPrice)
        double distance, price, tax, discount, totalPrice;
        try {
            distance = Double.parseDouble(distanceStr);
            price = Double.parseDouble(priceStr);
            tax = Double.parseDouble(taxStr);
            discount = Double.parseDouble(discountStr);
            totalPrice = Double.parseDouble(totalPriceStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid numeric values for distance, price, tax, discount, or total price.");
            return;
        }

        // Validate date format (yyyy-mm-dd)
        if (!rideDateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format. Expected yyyy-mm-dd.");
            return;
        }

        // Validate time format (HH:mm:ss)
        if (rideTimeStr.matches("\\d{2}:\\d{2}")) {
            rideTimeStr += ":00";  // Ensure correct format HH:mm:ss
        }
        if (!rideTimeStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid time format. Expected HH:mm:ss.");
            return;
        }

        // Convert date and time
        Date rideDate = Date.valueOf(rideDateStr);
        Time rideTime = Time.valueOf(rideTimeStr);

        // Prepare SQL query for insertion
        String sql = "INSERT INTO bookings (userId, driverId, pickupLocation, dropoffLocation, rideDate, rideTime, cabType, distance, price, tax, discount, totalPrice, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, driverId);
            stmt.setString(3, pickupLocation);
            stmt.setString(4, dropoffLocation);
            stmt.setDate(5, rideDate);
            stmt.setTime(6, rideTime);
            stmt.setString(7, cabType);
            stmt.setDouble(8, distance);
            stmt.setDouble(9, price);
            stmt.setDouble(10, tax);
            stmt.setDouble(11, discount);
            stmt.setDouble(12, totalPrice);
            stmt.setString(13, status);
            stmt.setTimestamp(14, new Timestamp(System.currentTimeMillis()));

            // Execute update
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Booking added successfully.");
                response.sendRedirect("ManageBookingServlet");
            } else {
                System.out.println("Failed to add booking.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to add booking.");
            }
        }

        } catch (SQLException e) {
            // Handle SQL exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle invalid date/time format
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date/time format: " + e.getMessage());
        }
}



private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try (Connection conn = DBConnection.getConnection()) {
        String rideDateStr = request.getParameter("rideDate");
        String rideTimeStr = request.getParameter("rideTime");

        // Log received values for debugging
        System.out.println("Updating Booking - Received rideDate: " + rideDateStr);
        System.out.println("Updating Booking - Received rideTime: " + rideTimeStr);

        // Validate input
        if (rideDateStr == null || rideTimeStr == null || rideDateStr.isEmpty() || rideTimeStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ride date and time are required.");
            return;
        }

        // Append seconds if rideTime is in HH:mm format
        if (rideTimeStr.matches("\\d{2}:\\d{2}")) { 
            rideTimeStr += ":00";  // Ensure correct format HH:mm:ss
        }

        // Validate final format
        if (!rideTimeStr.matches("\\d{2}:\\d{2}:\\d{2}")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid time format. Expected HH:mm:ss.");
            return;
        }

        // Convert to SQL Date and Time
        Date rideDate = Date.valueOf(rideDateStr);
        Time rideTime = Time.valueOf(rideTimeStr);

        String sql = "UPDATE bookings SET userId=?, driverId=?, pickupLocation=?, dropoffLocation=?, rideDate=?, rideTime=?, cabType=?, distance=?, price=?, tax=?, discount=?, totalPrice=?, status=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, Integer.parseInt(request.getParameter("userId")));
        stmt.setInt(2, Integer.parseInt(request.getParameter("driverId")));
        stmt.setString(3, request.getParameter("pickupLocation"));
        stmt.setString(4, request.getParameter("dropoffLocation"));
        stmt.setDate(5, rideDate);
        stmt.setTime(6, rideTime);
        stmt.setString(7, request.getParameter("cabType"));
        stmt.setDouble(8, Double.parseDouble(request.getParameter("distance")));
        stmt.setDouble(9, Double.parseDouble(request.getParameter("price")));
        stmt.setDouble(10, Double.parseDouble(request.getParameter("tax")));
        stmt.setDouble(11, Double.parseDouble(request.getParameter("discount")));
        stmt.setDouble(12, Double.parseDouble(request.getParameter("totalPrice")));
        stmt.setString(13, request.getParameter("status"));
        stmt.setInt(14, Integer.parseInt(request.getParameter("bookingId")));

        stmt.executeUpdate();
        response.sendRedirect("ManageBookingServlet");
    } catch (SQLException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date/time format: " + e.getMessage());
    }
}


    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM bookings WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(request.getParameter("bookingId")));
            stmt.executeUpdate();
            response.sendRedirect("ManageBookingServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete booking.");
        }
    }
}
