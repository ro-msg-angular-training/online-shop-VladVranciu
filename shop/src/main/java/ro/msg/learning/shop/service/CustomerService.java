package ro.msg.learning.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.msg.learning.shop.model.Customer;
import ro.msg.learning.shop.model.DTO.CustomerDTO;
import ro.msg.learning.shop.repository.CustomerRepository;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    SwitchProduct switchProduct=new SwitchProduct();

    public Customer register(CustomerDTO customerDTO){
        Customer customer=switchProduct.fromCustomerDTOtoCustomer(customerDTO);
        customerRepository.save(customer);
        return customer;
    }
}
