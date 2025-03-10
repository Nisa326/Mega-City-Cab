package com.megacitycab.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Booking {
    private int id;
    private int userId;
    private int driverId;
    private String pickupLocation;
    private String dropoffLocation;
    private Date rideDate;
    private Time rideTime;
    private String cabType;
    private double distance;
    private double price;
    private double tax;
    private double discount;
    private double totalPrice;
    private String status;
    private Timestamp createdAt;

    
    public Booking(int id, int userId, int driverId, String pickupLocation, String dropoffLocation,
                   Date rideDate, Time rideTime, String cabType, double distance, double price, 
                   double tax, double discount, double totalPrice, String status, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.driverId = driverId;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.cabType = cabType;
        this.distance = distance;
        this.price = price;
        this.tax = tax;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getDriverId() { return driverId; }
    public void setDriverId(int driverId) { this.driverId = driverId; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }

    public Date getRideDate() { return rideDate; }
    public void setRideDate(Date rideDate) { this.rideDate = rideDate; }

    public Time getRideTime() { return rideTime; }
    public void setRideTime(Time rideTime) { this.rideTime = rideTime; }

    public String getCabType() { return cabType; }
    public void setCabType(String cabType) { this.cabType = cabType; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    
}
