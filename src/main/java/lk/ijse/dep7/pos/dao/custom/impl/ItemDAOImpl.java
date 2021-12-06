package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.entity.Item;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    private final Session session;

    public ItemDAOImpl(Session session) {
        super(session);
        this.session = session;
    }

    @Override
    public String getLastItemCode() {
        session.flush();
        return (String) session.createNativeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1").uniqueResult();
    }
}
