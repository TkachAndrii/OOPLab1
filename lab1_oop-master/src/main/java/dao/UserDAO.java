// src/main/java/dao/UserDAO.java
package dao;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void insert(User user) throws Exception;
    Optional<User> findById(int id) throws Exception;
    Optional<User> findByUsername(String username) throws Exception;
    List<User> findAll() throws Exception;
    void update(User user) throws Exception;
    void delete(int id) throws Exception;
}
