package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Room;
public interface RoomService {

	public Optional<Room> get(Long id) throws Exception;

	public boolean save(Room c, Long idVenue) throws Exception;
	
	public void delete(Long id) throws Exception;
	
	public Room update(Long id, Long size, Long distanceMin, Long emergencyExits,Long noUsableLocations,String name,Integer numberSeats) throws Exception;

}