package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface ItemService extends SuperService {

    void saveItem(ItemDTO item) ;

    long getItemsCount() ;

    boolean existItem(String code) ;

    void updateItem(ItemDTO item) ;

    void deleteItem(String code) ;

    ItemDTO findItem(String code) ;

    List<ItemDTO> findAllItems() ;

    List<ItemDTO> findAllItems(int page, int size) ;

    String generateNewItemCode() ;
}
