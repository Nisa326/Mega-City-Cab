import com.megacitycab.model.Car;
import com.megacitycab.model.Driver;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageCarServlet")
public class ManageCarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private List<Driver> getDriverList() throws SQLException {
    List<Driver> driverList = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, fullname, NIC, license, year, created, gender, address, email, phone, imageURL, username, password FROM drivers";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                 Driver driver = new Driver(
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("NIC"),
                    rs.getString("license"),
                    rs.getInt("year"),
                    rs.getDate("created"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getInt("phone"),
                    rs.getString("imageURL"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                driverList.add(driver);
            }
    }
    return driverList;
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Car> carList = new ArrayList<>();
        List<Driver> driverList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, driver_id, model, brand, type, plateNumber, year, color, location, status, imageURL FROM cars";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Car car = new Car(
                    rs.getInt("id"),
                    rs.getInt("driver_id"),
                    rs.getString("model"),
                    rs.getString("brand"),
                    rs.getString("type"),
                    rs.getString("plateNumber"),
                    rs.getInt("year"),
                    rs.getString("color"),
                    rs.getString("location"),
                    rs.getString("status"),
                    rs.getString("imageURL")
                );
                carList.add(car);
            }
            
                // Fetch drivers
            driverList = getDriverList();

            request.setAttribute("carList", carList);
            request.setAttribute("driverList", driverList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageCars.jsp");
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
                case "addCar":
                    addCar(request, response);
                    break;
                case "updateCar":
                    updateCar(request, response);
                    break;
                case "deleteCar":
                    deleteCar(request, response);
                    break;
                default:
                    response.sendRedirect("manageCars.jsp");
            }
        } else {
            response.sendRedirect("manageCars.jsp");
        }
    }

    private void addCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String driverId = request.getParameter("driver_id");
        String model = request.getParameter("model");
        String brand = request.getParameter("brand");
        String type = request.getParameter("type");
        String plateNumber = request.getParameter("plateNumber");
        String year = request.getParameter("year");
        String color = request.getParameter("color");
        String location = request.getParameter("location");
        String status = request.getParameter("status");
        String imageURL = request.getParameter("imageURL");

        if (driverId.isEmpty() || model.isEmpty() || brand.isEmpty() || type.isEmpty() || plateNumber.isEmpty() || year.isEmpty() || color.isEmpty() || location.isEmpty() || status.isEmpty() || imageURL.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            
             // Check if Plate Number already exists
            String checkSql = "SELECT COUNT(*) FROM cars WHERE plateNumber = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, plateNumber);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Plate Number already exists.");
                return;
            }
            
            String sql = "INSERT INTO cars (driver_id, model, brand, type, plateNumber, year, color, location, status, imageURL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(driverId));
            stmt.setString(2, model);
            stmt.setString(3, brand);
            stmt.setString(4, type);
            stmt.setString(5, plateNumber);
            stmt.setInt(6, Integer.parseInt(year));
            stmt.setString(7, color);
            stmt.setString(8, location);
            stmt.setString(9, status);
            stmt.setString(10, imageURL);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("ManageCarServlet");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Car registration failed.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    private void updateCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int carId;
        try {
            carId = Integer.parseInt(request.getParameter("carId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid car ID.");
            return;
        }

        String driverId = request.getParameter("driver_id");
        String model = request.getParameter("model");
        String brand = request.getParameter("brand");
        String type = request.getParameter("type");
        String plateNumber = request.getParameter("plateNumber");
        String year = request.getParameter("year");
        String color = request.getParameter("color");
        String location = request.getParameter("location");
        String status = request.getParameter("status");
        String imageURL = request.getParameter("imageURL");

        if (model.isEmpty() || brand.isEmpty() || type.isEmpty() || plateNumber.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            
                // Check if the Plate number is already assigned to another car
          String checkSql = "SELECT id FROM cars WHERE plateNumber = ? AND id <> ?";
          PreparedStatement checkStmt = conn.prepareStatement(checkSql);
          checkStmt.setString(1, plateNumber);
          checkStmt.setInt(2, carId);
          ResultSet rs = checkStmt.executeQuery();

          if (rs.next()) {
              response.sendError(HttpServletResponse.SC_CONFLICT, "Plate Number already exists.");
              return;
          }
            String sql = "UPDATE cars SET driver_id=?, model=?, brand=?, type=?, plateNumber=?, year=?, color=?, location=?, status=?, imageURL=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(driverId));
            stmt.setString(2, model);
            stmt.setString(3, brand);
            stmt.setString(4, type);
            stmt.setString(5, plateNumber);
            stmt.setInt(6, Integer.parseInt(year));
            stmt.setString(7, color);
            stmt.setString(8, location);
            stmt.setString(9, status);
            stmt.setString(10, imageURL);
            stmt.setInt(11, carId);

            stmt.executeUpdate();
            response.sendRedirect("ManageCarServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
    
    private void deleteCar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int carId = Integer.parseInt(request.getParameter("carId"));

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM cars WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, carId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete car.");
            return;
        }
        response.sendRedirect("ManageCarServlet");
    }
}
