package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Purchase {
    private int id;
    private int userId;
    private int hotelId;
    private String roomNumber;
    private Date fromDate;
    private Date toDate;
    private BigDecimal totalPrice;
    private Timestamp createdAt;

    public Purchase() { }

    public Purchase(int id, int userId, int hotelId,
                    String roomNumber, Date fromDate, Date toDate,
                    BigDecimal totalPrice, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public Purchase(int userId, int hotelId,
                    String roomNumber, Date fromDate, Date toDate,
                    BigDecimal totalPrice) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.totalPrice = totalPrice;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getFromDate() {
        return fromDate;
    }
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}