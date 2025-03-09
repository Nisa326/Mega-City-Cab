import com.megacitycab.model.Place;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ManagePlaceServlet")
public class ManagePlaceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Place> placeList = new ArrayList<>();
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, place_name FROM places";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Place place = new Place(
                    rs.getInt("id"),
                    rs.getString("place_name")
                );
                placeList.add(place);
            }

            // Set the place list as an attribute
            request.setAttribute("placeList", placeList);
            
            // Forward to the JSP page
            RequestDispatcher dispatcher = request.getRequestDispatcher("managePlace.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    // Handle POST requests for adding, updating, and deleting places
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "addPlace":
                    handleAddPlace(request, response);
                    break;
                case "updatePlace":
                    handleUpdatePlace(request, response);
                    break;
                case "deletePlace":
                    handleDeletePlace(request, response);
                    break;
                default:
                    response.sendRedirect("managePlace.jsp");
            }
        } else {
            response.sendRedirect("managePlace.jsp");
        }
    }

    private void handleAddPlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String placeName = request.getParameter("place_name");

        if (placeName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Place name is required.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Insert new place
            String sql = "INSERT INTO places (place_name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, placeName);
            stmt.executeUpdate();

            // After adding a new place, reload the data
            response.sendRedirect("ManagePlaceServlet"); // This will trigger a call to doGet()
        } catch (SQLException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void handleUpdatePlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String placeId = request.getParameter("placeId");
        String placeName = request.getParameter("place_name");

        if (placeId.isEmpty() || placeName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Update place
            String sql = "UPDATE places SET place_name=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, placeName);
            stmt.setInt(2, Integer.parseInt(placeId));
            stmt.executeUpdate();
            response.sendRedirect("ManagePlaceServlet");

        } catch (SQLException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void handleDeletePlace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String placeId = request.getParameter("placeId");

        if (placeId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required field.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Delete place
            String sql = "DELETE FROM places WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(placeId));
            stmt.executeUpdate();
            response.sendRedirect("ManagePlaceServlet");

        } catch (SQLException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
