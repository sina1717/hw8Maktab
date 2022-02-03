package repository;

import java.util.List;

public interface BaseRepository<T> {

    int save(T t);

    void update(T t);

    List<T> findAll();

    void delete(int id);

    T findById(int id);
}
