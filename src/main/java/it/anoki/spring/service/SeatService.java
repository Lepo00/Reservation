package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Seat;

public interface SeatService {

	public Optional<Seat> get(Long id) throws Exception;

	public boolean save(Seat c, Long idRoom) throws Exception;

	public void delete(Long id) throws Exception;

	public Seat update(Long id, Boolean taken, String equipment, Integer number) throws Exception;

}