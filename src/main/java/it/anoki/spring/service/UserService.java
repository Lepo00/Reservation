package it.anoki.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;

public interface UserService {

	public User get(Long id) throws Exception;

	public User save(User c) throws Exception;

	public void delete(Long id) throws Exception;

	public User update(Long id, String address, String email, String name) throws Exception;

	public boolean reserve(Long idRoom, Reservation reservation) throws Exception;
	
	public User uploadPhoto(Long id, MultipartFile file) throws Exception;
	
	public void saveAll(List<? extends User> users) throws Exception;
	
	public List<User> getAll() throws Exception;
	
}