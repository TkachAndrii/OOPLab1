package dao;

import model.Hotel;
import java.util.List;

public interface HotelDAO {
    void insert(Hotel hotel) throws Exception;
    Hotel findById(int id) throws Exception;
    List<Hotel> findAll() throws Exception;
    void update(Hotel hotel) throws Exception;
    void delete(int id) throws Exception;
}