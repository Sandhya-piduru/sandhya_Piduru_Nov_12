/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop02.business;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import workshop02.model.Customer;
import workshop02.model.DiscountCode;



/**
 *
 * @author issuser
 */
@Stateless
public class CustomerBean {
    @PersistenceContext private EntityManager em;
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Optional<Customer> findByCustomerId(Integer custId) {
    Customer c = em.find(Customer.class, custId);
    return(Optional.ofNullable(c));
    }
    
    public void addNewCustomer(Customer customer) throws CustomerException {
        DiscountCode discountCode = em.find(DiscountCode.class, customer.getDiscountCode().getDiscountCode());
        if (null == discountCode)
           throw new CustomerException("Discount Code not found");
         
        //new
        customer.setDiscountCode(discountCode);
        em.persist(customer);
    }
    
}
