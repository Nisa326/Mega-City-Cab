package com.megacitycab.model;

public class Admin {
    private int id;
    private String fullname;
    private String NIC;
    private String gender;
    private String address;
    private String email;
    private int phone;
    private String imageURL;
    private int OTP;
    private String username;
    private String password;

    // Constructor
    public Admin(int id, String fullname, String NIC, String gender, String address, String email, int phone, String imageURL, String username, String password) {
        this.id = id;
        this.fullname = fullname;
        this.NIC = NIC;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.imageURL = imageURL;
        this.OTP = OTP;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getNIC() { return NIC; }
    public void setNIC(String NIC) { this.NIC = NIC; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getPhone() { return phone; }
    public void setPhone(int phone) { this.phone = phone; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    public int getOTP() { return OTP; }
    public void setOTP(int OTP) { this.OTP = OTP; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

