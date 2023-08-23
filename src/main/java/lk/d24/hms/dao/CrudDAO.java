package lk.d24.hms.dao;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    List<T> getAll();
    T search(String id);
    boolean add(T entity);
    boolean update(T entity);
    boolean delete(String id);
    String generateNextID();
}