package dao;

import model.User;
import org.junit.jupiter.api.*;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOImplTest {

    private static Connection h2Connection;
    private UserDAOImpl userDAO;

    @BeforeAll
    static void setupInMemoryDB() throws Exception {
        h2Connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE users (
                  id INT AUTO_INCREMENT PRIMARY KEY,
                  username VARCHAR(255),
                  password VARCHAR(255),
                  full_name VARCHAR(255),
                  email VARCHAR(255),
                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    @BeforeEach
    void initDAO() {
        userDAO = new UserDAOImpl() {
            @Override
            protected Connection getConnection() throws SQLException {
                return h2Connection;
            }
        };
    }

    @AfterEach
    void cleanTable() throws SQLException {
        try (Statement stmt = h2Connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM users");
            // Скидаємо AUTO_INCREMENT
            stmt.executeUpdate("ALTER TABLE users ALTER COLUMN id RESTART WITH 1");
        }
    }

    @Test
    void testInsertAndFindById() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        userDAO.insert(user);
        assertTrue(user.getId() > 0, "After insert() we need to get a non-zero ID");

        Optional<User> fetchedOpt = userDAO.findById(user.getId());
        assertTrue(fetchedOpt.isPresent(), "findById should return an Optional with the user");
        User fetched = fetchedOpt.get();
        assertEquals("testuser", fetched.getUsername());
        assertEquals("Test User", fetched.getFullName());
        assertEquals("test@example.com", fetched.getEmail());
    }

    @Test
    void testUpdate() throws Exception {
        User user = new User();
        user.setUsername("user1");
        user.setPassword("pass");
        user.setFullName("User One");
        user.setEmail("user1@example.com");
        userDAO.insert(user);

        user.setFullName("Updated User One");
        userDAO.update(user);

        Optional<User> updatedOpt = userDAO.findById(user.getId());
        assertTrue(updatedOpt.isPresent());
        assertEquals("Updated User One", updatedOpt.get().getFullName());
    }

    @Test
    void testFindAll() throws Exception {
        List<User> listEmpty = userDAO.findAll();
        assertNotNull(listEmpty);
        assertTrue(listEmpty.isEmpty());

        User u1 = new User();
        u1.setUsername("a"); u1.setPassword("p"); u1.setFullName("A"); u1.setEmail("a@e");
        userDAO.insert(u1);

        User u2 = new User();
        u2.setUsername("b"); u2.setPassword("p"); u2.setFullName("B"); u2.setEmail("b@e");
        userDAO.insert(u2);

        List<User> listTwo = userDAO.findAll();
        assertEquals(2, listTwo.size());
    }

    @Test
    void testDelete() throws Exception {
        User user = new User();
        user.setUsername("deluser");
        user.setPassword("delpass");
        user.setFullName("Delete Me");
        user.setEmail("del@example.com");
        userDAO.insert(user);

        int idToDelete = user.getId();
        userDAO.delete(idToDelete);

        Optional<User> afterDelete = userDAO.findById(idToDelete);
        assertFalse(afterDelete.isPresent(), "After delete() findById should return an empty Optional");
    }
}
