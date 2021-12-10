package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.custom.ItemDAO;
import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.exception.DuplicateEntityException;
import lk.ijse.dep7.pos.exception.EntityNotFoundException;
import lk.ijse.dep7.pos.service.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

@Transactional
@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDAO;

    @Override
    public void saveItem(ItemDTO item)  {
        if (existItem(item.getCode())) {
            throw new DuplicateEntityException(item.getCode() + " already exists");
        }
        itemDAO.save(fromItemDTO(item));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public boolean existItem(String code)  {
        return itemDAO.existsById(code);
    }

    @Override
    public void updateItem(ItemDTO item)  {
        if (!existItem(item.getCode())) {
            throw new EntityNotFoundException("There is no such item associated with the id " + item.getCode());
        }
        itemDAO.update(fromItemDTO(item));
    }

    @Override
    public void deleteItem(String code)  {
        if (!existItem(code)) {
            throw new EntityNotFoundException("There is no such item associated with the id " + code);
        }
        itemDAO.deleteById(code);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public ItemDTO findItem(String code)  {
        return toItemDTO(itemDAO.findById(code).orElseThrow(() -> new EntityNotFoundException("There is no such item associated with the id " + code)));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<ItemDTO> findAllItems()  {
        return toItemDTOList(itemDAO.findAll());
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<ItemDTO> findAllItems(int page, int size)  {
        return toItemDTOList(itemDAO.findAll(page, size));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public String generateNewItemCode()  {
        String code = itemDAO.getLastItemCode();
        if (code != null) {
            int newItemCode = Integer.parseInt(code.replace("I", "")) + 1;
            return String.format("I%03d", newItemCode);
        } else {
            return "I001";
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public long getItemsCount()  {
        return itemDAO.count();
    }

}
