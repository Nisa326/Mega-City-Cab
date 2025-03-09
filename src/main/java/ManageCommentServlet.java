import com.megacitycab.model.Comment;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ManageCommentServlet")
public class ManageCommentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Comment> commentList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT id, user_id, email, comment, created_at FROM comments";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("email"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at")
                );
                commentList.add(comment);
            }

            request.setAttribute("commentList", commentList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("manageComment.jsp");
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
                case "addComment":
                    handleAddComment(request, response);
                    break;
                case "updateComment":
                    handleUpdateComment(request, response);
                    break;
                case "deleteComment":
                    handleDeleteComment(request, response);
                    break;
                default:
                    response.sendRedirect("manageComment.jsp");
            }
        } else {
            response.sendRedirect("manageComment.jsp");
        }
    }

    private void handleAddComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("user_id");
        String commentText = request.getParameter("comment_text");

        if (userId.isEmpty() || commentText.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Insert new comment
            String sql = "INSERT INTO comments (user_id, email, comment, created_at) VALUES (?, ?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(userId));
            stmt.setString(2, request.getParameter("email"));
            stmt.setString(3, commentText);
            stmt.executeUpdate();
            response.sendRedirect("ManageCommentServlet");

        } catch (SQLException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void handleUpdateComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commentId = request.getParameter("commentId");
        String commentText = request.getParameter("comment_text");

        if (commentId.isEmpty() || commentText.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required fields.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Update comment
            String sql = "UPDATE comments SET comment=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, commentText);
            stmt.setInt(2, Integer.parseInt(commentId));
            stmt.executeUpdate();
            response.sendRedirect("ManageCommentServlet");

        } catch (SQLException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }

    private void handleDeleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commentId = request.getParameter("commentId");

        if (commentId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required field.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Delete comment
            String sql = "DELETE FROM comments WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(commentId));
            stmt.executeUpdate();
            response.sendRedirect("ManageCommentServlet");

        } catch (SQLException | NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
