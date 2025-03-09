package com.megacitycab.model;
import java.sql.Timestamp;

public class Comment {
    private int id;
    private int user_id;
    private String email;
    private String comment;
    private Timestamp created_at;

    // Constructor
    public Comment(int id, int user_id, String email, String comment, Timestamp created_at) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
        this.comment = comment;
        this.created_at = created_at;
    }

      // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }
}
