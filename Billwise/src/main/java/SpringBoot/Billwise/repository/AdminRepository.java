package SpringBoot.Billwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringBoot.Billwise.entity.Admin;

public interface AdminRepository  extends JpaRepository<Admin,Long>{

	Optional<Admin> findByEmail(String email);
	
}
