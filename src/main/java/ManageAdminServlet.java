import com.megacitycab.model.Admin;
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

@WebServlet("/ManageAdminServlet")
public class ManageAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Admin> adminList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, fullname, NIC, gender, address, email, phone, imageURL, username, password FROM admin";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin(
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
                adminList.add(admin);
            }

            request.setAttribute("adminList", adminList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageAdmin.jsp");
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
                case "addAdmin":
                    addAdmin(request, response);
                    break;
                case "updateAdmin":
                    updateAdmin(request, response);
                    break;
                case "deleteAdmin":
                    deleteAdmin(request, response);
                    break;
                default:
                    response.sendRedirect("manageAdmin.jsp");
            }
        } else {
            response.sendRedirect("manageAdmin.jsp");
        }
    }

    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            String checkSql = "SELECT COUNT(*) FROM admin WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                return;
            }
        
            String sql = "INSERT INTO admin (fullname, NIC, gender, address, email, phone, imageURL, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                response.sendRedirect("ManageAdminServlet");
                
            } else {               
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "User registration failed.");
            }
        } catch (SQLException e) {           
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error : " + e.getMessage() );
        }
    }

    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int adminId;
        try {
            adminId = Integer.parseInt(request.getParameter("adminId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Admin ID.");
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
            String checkSql = "SELECT id FROM admin WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int existingAdminId = rs.getInt("id");
                if (existingAdminId != adminId) { // If the ID is different, username is taken
                    response.sendError(HttpServletResponse.SC_CONFLICT, "Username already exists.");
                    return;
                }
            }
            String sql = "UPDATE admin SET fullname=?, NIC=?, gender=?, address=?, email=?, phone=?, imageURL=?, username=?, password=? WHERE id=?";
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
            stmt.setInt(10, adminId);

            stmt.executeUpdate();
            response.sendRedirect("ManageAdminServlet");
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
    
    private void deleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int adminId = Integer.parseInt(request.getParameter("adminId"));

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM admin WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, adminId);
                stmt.executeUpdate();
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Admin ID.");
            return;
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete admin.");
            return;
        }
        response.sendRedirect("ManageAdminServlet");
    }

}
