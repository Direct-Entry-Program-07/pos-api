package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.entity.Item;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ItemDAOImpl2 extends CrudDAOImpl<Item, String> implements ItemDAO {

    private final Session session;

    public ItemDAOImpl2(Session session) {
        this.session = session;
    }

//    @Override
//    public void save(Item entity) {
//        session.save(entity);
//    }
//
//    @Override
//    public void update(Item entity) {
//        session.update(entity);
//    }
//
//    @Override
//    public void deleteById(String key) {
//        Item item = session.find(Item.class, key);
//        session.delete(item);
//    }
//
//    @Override
//    public Optional<Item> findById(String key) {
//        Item item = session.find(Item.class, key);
//        if (item == null){
//            return Optional.empty();
//        }else{
//            return Optional.of(item);
//        }
//    }
//
//    @Override
//    public List<Item> findAll() {
//        return session.createQuery("FROM Item i").list();
//    }
//
//    @Override
//    public long count() {
//        return session.createQuery("SELECT COUNT(i) FROM Item i", Long.class).uniqueResult();
//    }
//
//    @Override
//    public boolean existsById(String key) {
//        return findById(key).isPresent();
//    }
//
//    @Override
//    public List<Item> findAll(int page, int size) {
//        return session.createNativeQuery("SELECT * FROM item LIMIT ?1 OFFSET ?2")
//                .setParameter(1, size)
//                .setParameter(2, size * (page - 1))
//                .addEntity(Item.class)
//                .list();
//    }

    @Override
    public String getLastItemCode() {
        session.flush();
        return (String) session.createNativeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1").uniqueResult();
    }
}
