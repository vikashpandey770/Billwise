package SpringBoot.Billwise.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import SpringBoot.Billwise.entity.Customer;
import SpringBoot.Billwise.service.CustomerService;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer saveCustomer(
            @RequestBody Customer customer) {

        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(
            @PathVariable Long id) {

        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "Customer deleted successfully";
    }
}