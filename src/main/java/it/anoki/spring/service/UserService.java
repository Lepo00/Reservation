package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;
public interface UserService {

	public Optional<User> get(Long id) throws Exception;

	public User save(User c) throws Exception;
	
	public void delete(Long id) throws Exception;
	
	public User update(Long id, String address, String email, String name) throws Exception;
	
	public boolean reserve(Long idRoom, Reservation reservation) throws Exception;

}