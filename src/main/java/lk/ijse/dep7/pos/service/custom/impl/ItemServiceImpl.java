package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.service.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

@Scope("prototype")
@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (existItem(item.getCode())) {
                throw new RuntimeException(item.getCode() + " already exists");
            }
            itemDAO.save(fromItemDTO(item));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existItem(String code) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);

            return itemDAO.existsById(code);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateItem(ItemDTO item) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (!existItem(item.getCode())) {
                throw new RuntimeException("There is no such item associated with the id " + item.getCode());
            }
            itemDAO.update(fromItemDTO(item));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteItem(String code) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (!existItem(code)) {
                throw new RuntimeException("There is no such item associated with the id " + code);
            }
            itemDAO.deleteById(code);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public ItemDTO findItem(String code) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);

            return toItemDTO(itemDAO.findById(code).orElseThrow(() -> new RuntimeException("There is no such item associated with the id " + code)));
        } finally {
            em.close();
        }
    }

    @Override
    public List<ItemDTO> findAllItems() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);

            return toItemDTOList(itemDAO.findAll());
        } finally {
            em.close();
        }
    }

    @Override
    public List<ItemDTO> findAllItems(int page, int size) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);

            return toItemDTOList(itemDAO.findAll(page, size));
        } finally {
            em.close();
        }
    }

    @Override
    public String generateNewItemCode() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            itemDAO.setEntityManager(em);

            String code = itemDAO.getLastItemCode();
            if (code != null) {
                int newItemCode = Integer.parseInt(code.replace("I", "")) + 1;
                return String.format("I%03d", newItemCode);
            } else {
                return "I001";
            }
        } finally {
            em.close();
        }
    }

}
