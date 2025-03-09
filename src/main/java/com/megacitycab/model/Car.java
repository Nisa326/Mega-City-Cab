package com.megacitycab.model;

public class Car {
    private int id;
    private int driver_id;
    private String model;
    private String brand;
    private String type;
    private String plateNumber;
    private int year;
    private String color;
    private String location;
    private String status; // Available, Booked, Maintenance
    private String imageURL;

    // Constructor
    public Car(int id, int driver_id, String model, String brand, String type, String plateNumber,
               int year, String color, String location, String status, String imageURL) {
        this.id = id;
        this.driver_id = driver_id;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.plateNumber = plateNumber;
        this.year = year;
        this.color = color;
        this.location = location;
        this.status = status;
        this.imageURL = imageURL;
    }

    // Default Constructor
    public Car() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDriverId() { return driver_id; }
    public void setDriverId(int driverId) { this.driver_id = driver_id; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getImageURL() { return imageURL; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
