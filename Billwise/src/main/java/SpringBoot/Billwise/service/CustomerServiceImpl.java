package SpringBoot.Billwise.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringBoot.Billwise.entity.Customer;
import SpringBoot.Billwise.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Customer not found"));
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {

        Customer existing =
                customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Customer not found"));

        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());

        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {

        Customer customer =
                customerRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Customer not found"));

        customerRepository.delete(customer);
    }
}