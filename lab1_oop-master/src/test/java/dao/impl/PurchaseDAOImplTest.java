package dao;

import model.Purchase;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseDAOImplTest {

    private static Connection h2Connection;
    private PurchaseDAOImpl purchaseDAO;

    @BeforeAll
    static void setupInMemoryDB() throws Exception {
        h2Connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE purchases (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  user_id INT,
                  hotel_id INT,
                  room_number VARCHAR(50),
                  from_date DATE,
                  to_date DATE,
                  total_price DECIMAL(10,2),
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    @BeforeEach
    void initDAO() {
        purchaseDAO = new PurchaseDAOImpl() {
            @Override
            protected Connection getConnection() throws SQLException {
                return h2Connection;
            }
        };
    }

    @AfterEach
    void cleanTable() throws SQLException {
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM purchases");
            stmt.executeUpdate("ALTER TABLE purchases ALTER COLUMN id RESTART WITH 1");
        }
    }

    @Test
    void testInsertAndFindById() throws Exception {
        Purchase p = new Purchase();
        p.setUserId(1);
        p.setHotelId(1);
        p.setRoomNumber("101");
        p.setFromDate(Date.valueOf("2024-01-01"));
        p.setToDate(Date.valueOf("2024-01-05"));
        p.setTotalPrice(new BigDecimal("500.00"));

        purchaseDAO.insert(p);
        assertTrue(p.getId() > 0, "After insert(), an ID should appear");

        Purchase fetched = purchaseDAO.findById(p.getId());
        assertNotNull(fetched);
        assertEquals("101", fetched.getRoomNumber());
        assertEquals(new BigDecimal("500.00"), fetched.getTotalPrice());
    }

    @Test
    void testUpdate() throws Exception {
        Purchase p = new Purchase();
        p.setUserId(1);
        p.setHotelId(1);
        p.setRoomNumber("102");
        p.setFromDate(Date.valueOf("2024-02-01"));
        p.setToDate(Date.valueOf("2024-02-10"));
        p.setTotalPrice(new BigDecimal("750.00"));
        purchaseDAO.insert(p);

        p.setRoomNumber("103");
        p.setTotalPrice(new BigDecimal("800.00"));
        purchaseDAO.update(p);

        Purchase updated = purchaseDAO.findById(p.getId());
        assertEquals("103", updated.getRoomNumber());
        assertEquals(new BigDecimal("800.00"), updated.getTotalPrice());
    }

    @Test
    void testFindAll() throws Exception {
        List<Purchase> emptyList = purchaseDAO.findAll();
        assertNotNull(emptyList);
        assertTrue(emptyList.isEmpty());

        Purchase p1 = new Purchase();
        p1.setUserId(1); p1.setHotelId(1);
        p1.setRoomNumber("201");
        p1.setFromDate(Date.valueOf("2024-03-01"));
        p1.setToDate(Date.valueOf("2024-03-05"));
        p1.setTotalPrice(new BigDecimal("300.00"));
        purchaseDAO.insert(p1);

        Purchase p2 = new Purchase();
        p2.setUserId(2); p2.setHotelId(1);
        p2.setRoomNumber("202");
        p2.setFromDate(Date.valueOf("2024-03-10"));
        p2.setToDate(Date.valueOf("2024-03-15"));
        p2.setTotalPrice(new BigDecimal("450.00"));
        purchaseDAO.insert(p2);

        List<Purchase> listTwo = purchaseDAO.findAll();
        assertEquals(2, listTwo.size());
    }

    @Test
    void testDelete() throws Exception {
        Purchase p = new Purchase();
        p.setUserId(1);
        p.setHotelId(1);
        p.setRoomNumber("103");
        p.setFromDate(Date.valueOf("2024-04-01"));
        p.setToDate(Date.valueOf("2024-04-05"));
        p.setTotalPrice(new BigDecimal("600.00"));
        purchaseDAO.insert(p);

        int id = p.getId();
        purchaseDAO.delete(id);
        Purchase deleted = purchaseDAO.findById(id);
        assertNull(deleted, "After delete(), the findById method should return null");
    }
}
