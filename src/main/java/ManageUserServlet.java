import com.megacitycab.model.User;
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

@WebServlet("/ManageUserServlet")
public class ManageUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, fullname, NIC, gender, address, email, phone, imageURL, username, password FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("fullname"),
                    rs.getString("NIC"),
                    rs.getString("gender"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getInt("phone"),
                    rs.getString("imageURL"),
                    rs.getString("username"),
                    rs.getString("password")
                );
                userList.add(user);
            }

            request.setAttribute("userList", userList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageUsers.jsp");
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
                case "addUser":
                    addUser(request, response);
                    break;
                case "updateUser":
                    updateUser(request, response);
                    break;
                case "deleteUser":
                    deleteUser(request, response);
                    break;
                default:
                    response.sendRedirect("manageUsers.jsp");
            }
        } else {
            response.sendRedirect("manageUsers.jsp");
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String NIC = request.getParameter("NIC");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String imageURL = request.getParameter("imageURL");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (fullname.isEmpty() || NIC.isEmpty() || gender.isEmpty() || address.isEmpty() || email.isEmpty() || phone.isEmpty() || imageURL.isEmpty() || 
                username.isEmpty() || password.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            
              // Check if username already exists
            String checkSql = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                return;
            }
            String sql = "INSERT INTO users (fullname, NIC, gender, address, email, phone, imageURL, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullname);
            stmt.setString(2, NIC);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, email);
            stmt.setString(6, phone);
            stmt.setString(7, imageURL);
            stmt.setString(8, username);
            stmt.setString(9, password); // Hash before storing in production

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.sendRedirect("ManageUserServlet");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User registration failed.");
            }
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId;
        try {
            userId = Integer.parseInt(request.getParameter("userId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID.");
            return;
        }

        String fullname = request.getParameter("fullname");
        String NIC = request.getParameter("NIC");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String imageURL = request.getParameter("imageURL");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (fullname == null || email == null || username == null || NIC == null ||
            fullname.isEmpty() || email.isEmpty() || username.isEmpty() || NIC.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) { 
             // Check if the new username is already taken by another admin
            String checkSql = "SELECT id FROM users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                int existingUserId = rs.getInt("id");
                if (existingUserId != userId) { // If the ID is different, username is taken
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                    return;
                }
            }            
            String sql = "UPDATE users SET fullname=?, NIC=?, gender=?, address=?, email=?, phone=?, imageURL=?, username=?, password=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullname);
            stmt.setString(2, NIC);
            stmt.setString(3, gender);
            stmt.setString(4, address);
            stmt.setString(5, email);
            stmt.setString(6, phone);
            stmt.setString(7, imageURL);
            stmt.setString(8, username);
            stmt.setString(9, password);
            stmt.setInt(10, userId);

            stmt.executeUpdate();
            response.sendRedirect("ManageUserServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM users WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID.");
            return;
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete user.");
            return;
        }
        response.sendRedirect("ManageUserServlet");
    }

}
