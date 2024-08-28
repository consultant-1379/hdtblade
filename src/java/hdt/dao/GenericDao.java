package hdt.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

    T findById(ID id);  

    List<T> findAll();

    void insertAll(List<T> entityList);

    T merge(T entity);

    T insert(T entity);

    void remove(T entity);

    void clear();
}
