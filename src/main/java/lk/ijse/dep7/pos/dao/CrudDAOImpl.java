package lk.ijse.dep7.pos.dao;

import lk.ijse.dep7.pos.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID> {

    @Override
    public void save(T entity) {

    }

    @Override
    public void update(T entity) {

    }

    @Override
    public void deleteById(ID key) {

    }

    @Override
    public Optional<T> findById(ID key) {
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(ID key) {
        return false;
    }

    @Override
    public List<T> findAll(int page, int size) {
        return null;
    }
}
