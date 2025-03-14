import com.megacitycab.model.Booking;
import com.megacitycab.model.Car;
import com.megacitycab.model.User;
import com.megacitycab.model.Place;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ManagePaymentServlet")
public class ManagePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Booking> bookingList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        List<Car> carList = new ArrayList<>();
        List<Place> placeList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connection error.");
                return;
            }

            // Fetch all bookings
            String bookingSql = "SELECT * FROM bookings";
            try (PreparedStatement bookingStmt = conn.prepareStatement(bookingSql);
                 ResultSet bookingRs = bookingStmt.executeQuery()) {
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
                        bookingRs.getTimestamp("createdAt")
                    );
                    bookingList.add(booking);
                }
            }

            // Fetch all users
            String userSql = "SELECT * FROM users";
            try (PreparedStatement userStmt = conn.prepareStatement(userSql);
                 ResultSet userRs = userStmt.executeQuery()) {
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
            }

            // Fetch all cars
            String carSql = "SELECT * FROM cars";
            try (PreparedStatement carStmt = conn.prepareStatement(carSql);
                 ResultSet carRs = carStmt.executeQuery()) {
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
            }

            // Fetch all places
            String placeSql = "SELECT * FROM places";
            try (PreparedStatement placeStmt = conn.prepareStatement(placeSql);
                 ResultSet placeRs = placeStmt.executeQuery()) {
                while (placeRs.next()) {
                    Place place = new Place(
                        placeRs.getInt("id"),
                        placeRs.getString("place_name")
                    );
                    placeList.add(place);
                }
            }

            request.setAttribute("bookingList", bookingList);
            request.setAttribute("userList", userList);
            request.setAttribute("carList", carList);
            request.setAttribute("placeList", placeList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("managePayment.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
