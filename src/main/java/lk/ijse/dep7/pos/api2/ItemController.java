package lk.ijse.dep7.pos.api2;

import lk.ijse.dep7.pos.dto.ItemDTO;
import lk.ijse.dep7.pos.exception.DuplicateEntityException;
import lk.ijse.dep7.pos.exception.EntityNotFoundException;
import lk.ijse.dep7.pos.service.custom.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin
@RequestMapping("/api/v2/items")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDTO> getAllItems() {
        return itemService.findAllItems();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<List<ItemDTO>> getAllItemsByPageAndSize(@RequestParam int page,
                                                                 @RequestParam int size) {
        if (page <= 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page or size");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Count", itemService.getItemsCount() + "");
        return new ResponseEntity<>(itemService.findAllItems(page, size), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{code:I\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemDTO getItem(@PathVariable String code) {
        return itemService.findItem(code);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveItem(@RequestBody ItemDTO item) {

        if (item.getCode() == null || !item.getCode().matches("I\\d{3}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid item code");
        } else if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid description");
        } else if (item.getQtyOnHand() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid qty");
        } else if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid unit price");
        }

        itemService.saveItem(item);
        return item.getCode();
    }

    @PutMapping(value = "/{code:I\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateItem(@RequestBody ItemDTO item, @PathVariable String code) {

        if (item.getCode() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request payload");
        } else if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid description");
        } else if (item.getQtyOnHand() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid qty");
        } else if (item.getUnitPrice() == null || item.getUnitPrice().compareTo(new BigDecimal("0")) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid unit price");
        }

        item.setCode(code);
        itemService.updateItem(item);
    }

    @DeleteMapping(value = "/{code:I\\d{3}}")
    public void deleteItem(@PathVariable String code) {
        itemService.deleteItem(code);
    }
}
