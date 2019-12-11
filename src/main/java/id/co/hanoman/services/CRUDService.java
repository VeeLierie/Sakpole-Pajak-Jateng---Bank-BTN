package id.co.hanoman.services;

import java.util.List;

public interface CRUDService<T> {
    List<?> listAll();

    T getById(Integer id);
    
    T getByType(String type);

    T saveOrUpdate(T domainObject);

    void delete(Integer id);
}
