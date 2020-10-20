package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Venue;

public interface VenueService {

	public Optional<Venue> get(Long id) throws Exception;

	public Venue save(Venue c, Long idCompany) throws Exception;

	public void delete(Long id) throws Exception;

	public Venue update(Long id, String name, String address, Long numberRooms) throws Exception;

}