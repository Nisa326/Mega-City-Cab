import com.megacitycab.model.Booking;
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
        List<Booking> bookingList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM bookings";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("id"),
                    rs.getInt("userId"),
                    rs.getInt("driverId"),
                    rs.getString("pickupLocation"),
                    rs.getString("dropoffLocation"),
                    rs.getDate("rideDate"),
                    rs.getTime("rideTime"),
                    rs.getString("cabType"),
                    rs.getDouble("distance"),
                    rs.getDouble("price"),
                    rs.getDouble("tax"),
                    rs.getDouble("discount"),
                    rs.getDouble("totalPrice"),
                    rs.getString("status"),
                    rs.getDate("createdAt")
                );
                bookingList.add(booking);
            }

            request.setAttribute("bookingList", bookingList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageBooking.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
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
                    response.sendRedirect("manageBooking.jsp");
            }
        } else {
            response.sendRedirect("manageBooking.jsp");
        }
    }

private void addBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try (Connection conn = DBConnection.getConnection()) {
        // Get rideDate and rideTime parameters from the request
        String rideDate = request.getParameter("rideDate");
        String rideTime = request.getParameter("rideTime");

        // Log the received values for debugging
        System.out.println("Received rideDate: " + rideDate);
        System.out.println("Received rideTime: " + rideTime);

        // Validate the date format
//        if (rideDate == null || rideTime == null || !rideDate.matches("\\d{4}-\\d{2}-\\d{2}") || !rideTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date or time format. Expected format: " );
//            return;
//        }

        // Try to parse the date and time, catching any errors if the format is incorrect
        Date parsedRideDate = null;
        Time parsedRideTime = null;

        try {
            parsedRideDate = Date.valueOf(rideDate);  // Parse date in yyyy-MM-dd format
            parsedRideTime = Time.valueOf(rideTime);  // Parse time in HH:mm:ss format
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date or time format. Please use the correct format." + e);
            return;
        }

        String sql = "INSERT INTO bookings (userId, driverId, pickupLocation, dropoffLocation, rideDate, rideTime, cabType, distance, price, tax, discount, totalPrice, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, Integer.parseInt(request.getParameter("userId")));
        stmt.setInt(2, Integer.parseInt(request.getParameter("driverId")));
        stmt.setString(3, request.getParameter("pickupLocation"));
        stmt.setString(4, request.getParameter("dropoffLocation"));
        stmt.setDate(5, parsedRideDate);  // Set parsed rideDate
        stmt.setTime(6, parsedRideTime);  // Set parsed rideTime
        stmt.setString(7, request.getParameter("cabType"));
        stmt.setDouble(8, Double.parseDouble(request.getParameter("distance")));
        stmt.setDouble(9, Double.parseDouble(request.getParameter("price")));
        stmt.setDouble(10, Double.parseDouble(request.getParameter("tax")));
        stmt.setDouble(11, Double.parseDouble(request.getParameter("discount")));
        stmt.setDouble(12, Double.parseDouble(request.getParameter("totalPrice")));
        stmt.setString(13, request.getParameter("status"));
        stmt.setTimestamp(14, new Timestamp(System.currentTimeMillis()));

        stmt.executeUpdate();
        response.sendRedirect("ManageBookingServlet");
    } catch (SQLException e) {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
    } catch (IllegalArgumentException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date/time format." + e);
    }
}





    private void updateBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE bookings SET userId=?, driverId=?, pickupLocation=?, dropoffLocation=?, rideDate=?, rideTime=?, cabType=?, distance=?, price=?, tax=?, discount=?, totalPrice=?, status=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            // Use Timestamp for both date and time fields
            Timestamp rideTimestamp = Timestamp.valueOf(request.getParameter("rideDate") + " " + request.getParameter("rideTime"));
            
            stmt.setInt(1, Integer.parseInt(request.getParameter("userId")));
            stmt.setInt(2, Integer.parseInt(request.getParameter("driverId")));
            stmt.setString(3, request.getParameter("pickupLocation"));
            stmt.setString(4, request.getParameter("dropoffLocation"));
            stmt.setTimestamp(5, rideTimestamp);  // Set Timestamp for rideDate and rideTime
            stmt.setString(6, request.getParameter("cabType"));
            stmt.setDouble(7, Double.parseDouble(request.getParameter("distance")));
            stmt.setDouble(8, Double.parseDouble(request.getParameter("price")));
            stmt.setDouble(9, Double.parseDouble(request.getParameter("tax")));
            stmt.setDouble(10, Double.parseDouble(request.getParameter("discount")));
            stmt.setDouble(11, Double.parseDouble(request.getParameter("totalPrice")));
            stmt.setString(12, request.getParameter("status"));
            stmt.setInt(13, Integer.parseInt(request.getParameter("bookingId")));
            
            stmt.executeUpdate();
            response.sendRedirect("ManageBookingServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
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
