package it.anoki.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.anoki.spring.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT t FROM User t WHERE t.name = ?1")
	User findByName(String username);
}