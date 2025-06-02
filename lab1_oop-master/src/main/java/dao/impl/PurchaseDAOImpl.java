package dao.impl;

import dao.PurchaseDAO;
import db.DBConnection;
import model.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAOImpl implements PurchaseDAO {

    private static final String INSERT_SQL =
            "INSERT INTO purchases (user_id, hotel_id, room_number, from_date, to_date, total_price) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, user_id, hotel_id, room_number, from_date, to_date, total_price, created_at " +
                    "FROM purchases WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, user_id, hotel_id, room_number, from_date, to_date, total_price, created_at " +
                    "FROM purchases";
    private static final String UPDATE_SQL =
            "UPDATE purchases SET user_id = ?, hotel_id = ?, room_number = ?, from_date = ?, to_date = ?, total_price = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM purchases WHERE id = ?";

    @Override
    public void insert(Purchase purchase) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, purchase.getUserId());
            pstmt.setInt(2, purchase.getHotelId());
            pstmt.setString(3, purchase.getRoomNumber());
            pstmt.setDate(4, purchase.getFromDate());
            pstmt.setDate(5, purchase.getToDate());
            pstmt.setBigDecimal(6, purchase.getTotalPrice());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    purchase.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Purchase findById(int id) throws Exception {
        Purchase purchase = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    purchase = new Purchase();
                    purchase.setId(rs.getInt("id"));
                    purchase.setUserId(rs.getInt("user_id"));
                    purchase.setHotelId(rs.getInt("hotel_id"));
                    purchase.setRoomNumber(rs.getString("room_number"));
                    purchase.setFromDate(rs.getDate("from_date"));
                    purchase.setToDate(rs.getDate("to_date"));
                    purchase.setTotalPrice(rs.getBigDecimal("total_price"));
                    purchase.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }
        return purchase;
    }

    @Override
    public List<Purchase> findAll() throws Exception {
        List<Purchase> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(rs.getInt("id"));
                purchase.setUserId(rs.getInt("user_id"));
                purchase.setHotelId(rs.getInt("hotel_id"));
                purchase.setRoomNumber(rs.getString("room_number"));
                purchase.setFromDate(rs.getDate("from_date"));
                purchase.setToDate(rs.getDate("to_date"));
                purchase.setTotalPrice(rs.getBigDecimal("total_price"));
                purchase.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(purchase);
            }
        }
        return list;
    }

    @Override
    public void update(Purchase purchase) throws Exception {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_SQL)) {

            pstmt.setInt(1, purchase.getUserId());
            pstmt.setInt(2, purchase.getHotelId());
            pstmt.setString(3, purchase.getRoomNumber());
            pstmt.setDate(4, purchase.getFromDate());
            pstmt.setDate(5, purchase.getToDate());
            pstmt.setBigDecimal(6, purchase.getTotalPrice());
            pstmt.setInt(7, purchase.getId());
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