package it.anoki.spring.service;

import java.util.Date;
import java.util.Optional;

import it.anoki.spring.model.Reservation;
public interface ReservationService {

	public Optional<Reservation> get(Long id) throws Exception;

	public boolean save(Reservation r, Long idUser, Long idRoom, String usedBy) throws Exception;
	
	public void delete(Long id) throws Exception;
	
	public Reservation update(Long id, Date date, String description) throws Exception;

}