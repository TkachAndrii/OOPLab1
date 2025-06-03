package dao.impl;

import dao.impl.impl.HotelDAOImpl;
import db.DBConnection;
import model.Hotel;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HotelDAOImplTest {

    private static Connection connection;
    private static HotelDAOImpl hotelDAO;

    @BeforeAll
    static void setupDB() throws Exception {
        connection = DBConnection.getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE hotels (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    address VARCHAR(255),
                    city VARCHAR(255),
                    rating DECIMAL(2,1),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

        }
        hotelDAO = new HotelDAOImpl();
    }


    @AfterEach
    void cleanUp() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM hotels");
        }
    }

    @Test
    void testInsertAndFindById() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Test Hotel");
        hotel.setAddress("123 Street");
        hotel.setCity("Test City");
        hotel.setRating(BigDecimal.valueOf(4.5));

        hotelDAO.insert(hotel);

        assertNotNull(hotel.getId());

        Hotel fetched = hotelDAO.findById(hotel.getId());
        assertNotNull(fetched);
        assertEquals("Test Hotel", fetched.getName());
        assertEquals("123 Street", fetched.getAddress());
        assertEquals("Test City", fetched.getCity());
        assertEquals(BigDecimal.valueOf(4.5), fetched.getRating());
    }

    @Test
    void testFindAll() throws Exception {
        Hotel h1 = new Hotel();
        h1.setName("H1");
        h1.setAddress("A1");
        h1.setCity("C1");
        h1.setRating(BigDecimal.valueOf(3.0));
        hotelDAO.insert(h1);

        Hotel h2 = new Hotel();
        h2.setName("H2");
        h2.setAddress("A2");
        h2.setCity("C2");
        h2.setRating(BigDecimal.valueOf(4.0));
        hotelDAO.insert(h2);

        List<Hotel> all = hotelDAO.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdate() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Old Name");
        hotel.setAddress("Old Addr");
        hotel.setCity("Old City");
        hotel.setRating(BigDecimal.valueOf(2.0));
        hotelDAO.insert(hotel);

        hotel.setName("New Name");
        hotel.setAddress("New Addr");
        hotel.setCity("New City");
        hotel.setRating(BigDecimal.valueOf(5.0));
        hotelDAO.update(hotel);

        Hotel updated = hotelDAO.findById(hotel.getId());
        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDelete() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("To Delete");
        hotel.setAddress("Addr");
        hotel.setCity("City");
        hotel.setRating(BigDecimal.valueOf(3.5));
        hotelDAO.insert(hotel);

        hotelDAO.delete(hotel.getId());

        Hotel deleted = hotelDAO.findById(hotel.getId());
        assertNull(deleted);
    }
}
