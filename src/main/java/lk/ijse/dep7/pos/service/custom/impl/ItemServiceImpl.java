package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.DAOFactory;
import lk.ijse.dep7.pos.dao.DAOType;
import lk.ijse.dep7.pos.dao.JPAUtil;
import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.service.custom.ItemService;
import org.hibernate.Session;

import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

public class ItemServiceImpl implements ItemService {

    private final ItemDAO itemDAO;

    public ItemServiceImpl() {
        itemDAO = DAOFactory.getInstance().getDAO(DAOType.ITEM);
    }

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            if (existItem(item.getCode())) {
                throw new RuntimeException(item.getCode() + " already exists");
            }
            itemDAO.save(fromItemDTO(item));

            session.getTransaction().commit();
        }
    }

    @Override
    public boolean existItem(String code) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);

            return itemDAO.existsById(code);
        }
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            if (!existItem(item.getCode())) {
                throw new RuntimeException("There is no such item associated with the id " + item.getCode());
            }
            itemDAO.update(fromItemDTO(item));

            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteItem(String code) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            if (!existItem(code)) {
                throw new RuntimeException("There is no such item associated with the id " + code);
            }
            itemDAO.deleteById(code);

            session.getTransaction().commit();
        }
    }

    @Override
    public ItemDTO findItem(String code) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);

            return toItemDTO(itemDAO.findById(code).orElseThrow(() -> new RuntimeException("There is no such item associated with the id " + code)));
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);

            return toItemDTOList(itemDAO.findAll());
        }
    }

    @Override
    public List<ItemDTO> findAllItems(int page, int size) throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);

            return toItemDTOList(itemDAO.findAll(page, size));
        }
    }

    @Override
    public String generateNewItemCode() throws Exception {
        try (Session session = JPAUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);

            String code = itemDAO.getLastItemCode();
            if (code != null) {
                int newItemCode = Integer.parseInt(code.replace("I", "")) + 1;
                return String.format("I%03d", newItemCode);
            } else {
                return "I001";
            }
        }
    }

}
