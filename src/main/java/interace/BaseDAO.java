package interace;

import java.util.List;

public interface BaseDAO<T, I> {
    T save(T e);

    T findById(I id);

    List<T> findAll();

    void update(T e);

    void deleteByid(I id);
}

