package dao.impl;

import dao.HotelDAO;
import model.Hotel;
import db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAOImpl implements HotelDAO {

    private static final String INSERT_SQL =
            "INSERT INTO hotels (name, address, city, rating) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, address, city, rating, created_at FROM hotels WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, name, address, city, rating, created_at FROM hotels";
    private static final String UPDATE_SQL =
            "UPDATE hotels SET name = ?, address = ?, city = ?, rating = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM hotels WHERE id = ?";

    @Override
    public void insert(Hotel hotel) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, hotel.getName());
            pstmt.setString(2, hotel.getAddress());
            pstmt.setString(3, hotel.getCity());
            pstmt.setBigDecimal(4, hotel.getRating());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    hotel.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Hotel findById(int id) throws Exception {
        Hotel hotel = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    hotel = new Hotel();
                    hotel.setId(rs.getInt("id"));
                    hotel.setName(rs.getString("name"));
                    hotel.setAddress(rs.getString("address"));
                    hotel.setCity(rs.getString("city"));
                    hotel.setRating(rs.getBigDecimal("rating"));
                    hotel.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        return hotel;
    }

    @Override
    public List<Hotel> findAll() throws Exception {
        List<Hotel> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Hotel hotel = new Hotel();
                hotel.setId(rs.getInt("id"));
                hotel.setName(rs.getString("name"));
                hotel.setAddress(rs.getString("address"));
                hotel.setCity(rs.getString("city"));
                hotel.setRating(rs.getBigDecimal("rating"));
                hotel.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(hotel);
            }
        }
        return list;
    }

    @Override
    public void update(Hotel hotel) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {

            pstmt.setString(1, hotel.getName());
            pstmt.setString(2, hotel.getAddress());
            pstmt.setString(3, hotel.getCity());
            pstmt.setBigDecimal(4, hotel.getRating());
            pstmt.setInt(5, hotel.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_SQL)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}