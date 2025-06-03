package dao.impl;

import model.Purchase;
import java.util.List;

public interface PurchaseDAO {
    void insert(Purchase purchase) throws Exception;
    Purchase findById(int id) throws Exception;
    List<Purchase> findAll() throws Exception;
    void update(Purchase purchase) throws Exception;
    void delete(int id) throws Exception;
}