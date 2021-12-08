package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.service.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

@Transactional
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        if (existCustomer(customer.getId())) {
            throw new RuntimeException(customer.getId() + " already exists");
        }
        customerDAO.save(fromCustomerDTO(customer));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public long getCustomersCount() throws Exception {
        return customerDAO.count();
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public boolean existCustomer(String id) throws Exception {
        return customerDAO.existsById(id);
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        if (!existCustomer(customer.getId())) {
            throw new RuntimeException("There is no such customer associated with the id " + customer.getId());
        }
        customerDAO.update(fromCustomerDTO(customer));
    }

    @Override
    public void deleteCustomer(String id) throws Exception {
        if (!existCustomer(id)) {
            throw new RuntimeException("There is no such customer associated with the id " + id);
        }
        customerDAO.deleteById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public CustomerDTO findCustomer(String id) throws Exception {
        return toCustomerDTO(customerDAO.findById(id).orElseThrow(() -> new RuntimeException("There is no such customer associated with the id " + id)));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        return toCustomerDTOList(customerDAO.findAll());
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public List<CustomerDTO> findAllCustomers(int page, int size) throws Exception {
        return toCustomerDTOList(customerDAO.findAll(page, size));
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public String generateNewCustomerId() throws Exception {
        String id = customerDAO.getLastCustomerId();
        if (id != null) {
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C%03d", newCustomerId);
        } else {
            return "C001";
        }
    }

}
