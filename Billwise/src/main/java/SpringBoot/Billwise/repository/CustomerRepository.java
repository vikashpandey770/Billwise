package SpringBoot.Billwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	
}
