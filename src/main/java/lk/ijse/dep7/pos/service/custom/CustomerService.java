package lk.ijse.dep7.pos.service.custom;

import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.service.SuperService;

import java.util.List;

public interface CustomerService extends SuperService {

    void saveCustomer(CustomerDTO customer) ;

    long getCustomersCount() ;

    boolean existCustomer(String id) ;

    void updateCustomer(CustomerDTO customer) ;

    void deleteCustomer(String id) ;

    CustomerDTO findCustomer(String id) ;

    List<CustomerDTO> findAllCustomers() ;

    List<CustomerDTO> findAllCustomers(int page, int size) ;

    String generateNewCustomerId() ;

}
