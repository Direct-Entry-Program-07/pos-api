package lk.ijse.dep7.pos.api2;

import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.exception.DuplicateEntityException;
import lk.ijse.dep7.pos.exception.EntityNotFoundException;
import lk.ijse.dep7.pos.service.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/v2/customers")
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

//    @ExceptionHandler(EntityNotFoundException.class)
//    public void handleNotFoundException(EntityNotFoundException ex){
//        ex.initCause(new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage()));
//        throw ex;
//    }
//
//    @ExceptionHandler(DuplicateEntityException.class)
//    public void handleDuplicateEntityException(DuplicateEntityException ex){
//        ex.initCause(new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage()));
//        throw ex;
//    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDTO> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"page", "size"})
    public ResponseEntity<List<CustomerDTO>> getAllCustomersByPageAndSize(@RequestParam int page,
                                                                         @RequestParam int size) {
        if (page <= 0 || size <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page or size");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Count", customerService.getCustomersCount() + "");
        return new ResponseEntity<List<CustomerDTO>>(customerService.findAllCustomers(page,size), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id:C\\d{3}}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDTO getCustomer(@PathVariable String id) {
        return customerService.findCustomer(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveCustomer(@RequestBody CustomerDTO customer) {

        if (customer.getId() == null || !customer.getId().matches("C\\d{3}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer id");
        } else if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer name");
        } else if (customer.getAddress() == null || customer.getAddress().trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer address");
        }

        customerService.saveCustomer(customer);
        return customer.getId();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id:C\\d{3}}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateCustomer(@RequestBody CustomerDTO customer, @PathVariable String id) {
        if (customer.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request pay load");
        } else if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer name");
        } else if (customer.getAddress() == null || customer.getAddress().trim().length() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid customer address");
        }

        customer.setId(id);
        customerService.updateCustomer(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id:C\\d{3}}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
    }
}
