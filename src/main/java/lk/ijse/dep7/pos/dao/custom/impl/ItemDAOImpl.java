package lk.ijse.dep7.pos.dao.custom.impl;

import lk.ijse.dep7.pos.dao.CrudDAOImpl;
import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.entity.Item;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class ItemDAOImpl extends CrudDAOImpl<Item, String> implements ItemDAO {

    @Override
    public String getLastItemCode() {
        em.flush();
        return (String) em.createNativeQuery("SELECT code FROM item ORDER BY code DESC LIMIT 1").getSingleResult();
    }
}
