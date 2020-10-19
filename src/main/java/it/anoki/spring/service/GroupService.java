package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Group;
import it.anoki.spring.model.Reservation;

public interface GroupService {

	public Optional<Group> get(Long id) throws Exception;

	public Group save(Group c) throws Exception;

	public void delete(Long id) throws Exception;

	public Group update(Long id, String name, String description) throws Exception;

	public boolean addUser(Long idGroup, Long idUser) throws Exception;

	public boolean reserve(Long idGroup, Long idRoom, Reservation reservation) throws Exception;

}