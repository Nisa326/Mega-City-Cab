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

@WebServlet("/ManageDriverServlet")
public class ManageDriverServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

            request.setAttribute("driverList", driverList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageDrivers.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "addDriver":
                    addDriver(request, response);
                    break;
                case "updateDriver":
                    updateDriver(request, response);
                    break;
                case "deleteDriver":
                    deleteDriver(request, response);
                    break;
                default:
                    response.sendRedirect("manageDrivers.jsp");
            }
        } else {
            response.sendRedirect("manageDrivers.jsp");
        }
    }

    private void addDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String NIC = request.getParameter("NIC");
        String license = request.getParameter("license");
        String year = request.getParameter("year");
        String created = request.getParameter("created");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String imageURL = request.getParameter("imageURL");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (fullname.isEmpty() || NIC.isEmpty() || license.isEmpty() || year.isEmpty() || created.isEmpty() || gender.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || imageURL.isEmpty() || username.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }
       
        try (Connection conn = DBConnection.getConnection()) {
            
              // Check if username already exists
            String checkSql = "SELECT COUNT(*) FROM drivers WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                return;
            }
            
            String sql = "INSERT INTO drivers (fullname, NIC, license, year, created, gender, address, email, phone, imageURL, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullname);
            stmt.setString(2, NIC);
            stmt.setString(3, license);
            stmt.setString(4, year);
            stmt.setString(5, created);
            stmt.setString(6, gender);
            stmt.setString(7, address);
            stmt.setString(8, email);
            stmt.setString(9, phone);
            stmt.setString(10, imageURL);
            stmt.setString(11, username);
            stmt.setString(12, password);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("ManageDriverServlet");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Driver registration failed.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    private void updateDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int driverId;
        try {
            driverId = Integer.parseInt(request.getParameter("driverId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid driver ID.");
            return;
        }

        String fullname = request.getParameter("fullname");
        String NIC = request.getParameter("NIC");
        String license = request.getParameter("license");
        String year = request.getParameter("year");
        String created = request.getParameter("created");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String imageURL = request.getParameter("imageURL");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (fullname.isEmpty() || NIC.isEmpty() || license.isEmpty() || year.isEmpty() || created.isEmpty() || gender.isEmpty() || address.isEmpty() || phone.isEmpty() || imageURL.isEmpty() || username.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            
            // Check if the new username is already taken by another admin
            String checkSql = "SELECT id FROM drivers WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingDriverId = rs.getInt("id");
                if (existingDriverId != driverId) { // If the ID is different, username is taken
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                    return;
                }
            }
            String sql = "UPDATE drivers SET fullname=?, NIC=?, license=?, year=?, created=?, gender=?, address=?, email=?, phone=?, imageURL=?, username=?, password=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullname);
            stmt.setString(2, NIC);
            stmt.setString(3, license);
            stmt.setString(4, year);
            stmt.setString(5, created);
            stmt.setString(6, gender);
            stmt.setString(7, address);
            stmt.setString(8, email);
            stmt.setString(9, phone);
            stmt.setString(10, imageURL);
            stmt.setString(11, username);
            stmt.setString(12, password);
            stmt.setInt(13, driverId);

            stmt.executeUpdate();
            response.sendRedirect("ManageDriverServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
    
    private void deleteDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int driverId = Integer.parseInt(request.getParameter("driverId"));

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM drivers WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, driverId);
                stmt.executeUpdate();
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Driver ID.");
            return;
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user.");
            return;
        }
        response.sendRedirect("ManageDriverServlet");
    }
    
}
