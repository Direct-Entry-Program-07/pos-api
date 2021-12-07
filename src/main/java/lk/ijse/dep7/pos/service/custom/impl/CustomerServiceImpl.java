package lk.ijse.dep7.pos.service.custom.impl;

import lk.ijse.dep7.pos.dao.JPAUtil;
import lk.ijse.dep7.pos.dao.custom.CustomerDAO;
import lk.ijse.dep7.pos.dto.CustomerDTO;
import lk.ijse.dep7.pos.service.custom.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

import static lk.ijse.dep7.pos.service.util.EntityDTOMapper.*;

@Scope("prototype")
@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (existCustomer(customer.getId())) {
                throw new RuntimeException(customer.getId() + " already exists");
            }
            customerDAO.save(fromCustomerDTO(customer));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public long getCustomersCount() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            return customerDAO.count();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existCustomer(String id) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            return customerDAO.existsById(id);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (!existCustomer(customer.getId())) {
                throw new RuntimeException("There is no such customer associated with the id " + customer.getId());
            }
            customerDAO.update(fromCustomerDTO(customer));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteCustomer(String id) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);
            em.getTransaction().begin();

            if (!existCustomer(id)) {
                throw new RuntimeException("There is no such customer associated with the id " + id);
            }
            customerDAO.deleteById(id);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public CustomerDTO findCustomer(String id) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            return toCustomerDTO(customerDAO.findById(id).orElseThrow(() -> new RuntimeException("There is no such customer associated with the id " + id)));
        } finally {
            em.close();
        }
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            return toCustomerDTOList(customerDAO.findAll());
        } finally {
            em.close();
        }
    }

    @Override
    public List<CustomerDTO> findAllCustomers(int page, int size) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            return toCustomerDTOList(customerDAO.findAll(page, size));
        } finally {
            em.close();
        }
    }

    @Override
    public String generateNewCustomerId() throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            customerDAO.setEntityManager(em);

            String id = customerDAO.getLastCustomerId();
            if (id != null) {
                int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
                return String.format("C%03d", newCustomerId);
            } else {
                return "C001";
            }
        } finally {
            em.close();
        }

    }

}
