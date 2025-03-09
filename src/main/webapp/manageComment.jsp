<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacitycab.model.Comment" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <title>Manage Comments - Mega City Cab</title>
</head>
<body>
    
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />
    
    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="adminPanel.jsp" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>
            <h2 class="text-center flex-grow-1">Manage Comments</h2>
            
        </div>

        <div class="mb-3">
            <input type="text" class="form-control" id="searchComment" placeholder="Search Comment">
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>User ID</th>
                    <th>Email</th>
                    <th>Comment</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Comment> commentList = (List<Comment>) request.getAttribute("commentList");
                    if (commentList != null && !commentList.isEmpty()) {
                        for (Comment comment : commentList) {
                %>
                <tr>
                    <td><%= comment.getId() %></td>
                    <td><%= comment.getUserId() %></td>
                    <td><%= comment.getEmail() %></td>
                    <td><%= comment.getComment() %></td>
                    <td><%= comment.getCreatedAt() %></td>
                    <td>
                        
                        <form action="ManageCommentServlet" method="post" style="display:inline;">
                            <input type="hidden" name="commentId" value="<%= comment.getId() %>">
                            <input type="hidden" name="action" value="deleteComment">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="bi bi-trash"></i> Delete
                            </button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="6" class="text-center">No comments found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>

   
    
    <!-- JavaScript for Search Functionality -->
    <script>
        document.getElementById("searchComment").addEventListener("keyup", function() {
            let searchValue = this.value.toLowerCase();
            let rows = document.querySelectorAll("tbody tr");

            rows.forEach(row => {
                let userId = row.cells[1].textContent.toLowerCase();
                let email = row.cells[2].textContent.toLowerCase();
                let commentText = row.cells[3].textContent.toLowerCase();

                if (userId.includes(searchValue) || email.includes(searchValue) || commentText.includes(searchValue)) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            });
        });
    </script>

    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
