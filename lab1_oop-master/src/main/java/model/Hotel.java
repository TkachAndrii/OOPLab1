package model;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class Hotel {
    private int id;
    private String name;
    private String address;
    private String city;
    private BigDecimal rating;      // наприклад, 4.5
    private Timestamp createdAt;

    public Hotel() { }

    public Hotel(int id, String name, String address, String city, BigDecimal rating, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.rating = rating;
        this.createdAt = createdAt;
    }

    public Hotel(String name, String address, String city, BigDecimal rating) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.rating = rating;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public BigDecimal getRating() {
        return rating;
    }
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}