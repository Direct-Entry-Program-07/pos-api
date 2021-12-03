package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.entity.Item;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ItemDAOImpl2 implements ItemDAO {

    private Session session;

    public ItemDAOImpl2(Session session) {
        this.session = session;
    }

    @Override
    public void save(Item entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(Item entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void deleteById(String key) throws Exception {
        session.delete(session.get(Item.class, key));
    }

    @Override
    public Optional<Item> findById(String key) throws Exception {
        Item item = session.get(Item.class, key);
        return (item == null)? Optional.empty(): Optional.of(item);
    }

    @Override
    public List<Item> findAll() throws Exception {
        return session.createQuery("FROM Item").list();
    }

    @Override
    public long count() throws Exception {
        return session.createQuery("SELECT COUNT(i) FROM Item i", Long.class).uniqueResult();
    }

    @Override
    public boolean existsById(String key) throws Exception {
        return findById(key).isPresent();
    }

    @Override
    public List<Item> findAll(int page, int size) throws Exception {
        return session.createNativeQuery("SELECT * FROM item LIMIT ?1 OFFSET ?2")
                .setParameter(1, page)
                .setParameter(2, size)
                .addEntity(Item.class)
                .list();
    }

    @Override
    public String getLastItemCode() throws Exception {
        return session.createNativeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1", String.class).uniqueResult();
    }
}
