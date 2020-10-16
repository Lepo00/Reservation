package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Room;
import it.anoki.spring.model.Venue;
import it.anoki.spring.repository.RoomRepository;
import it.anoki.spring.repository.VenueRepository;
import it.anoki.spring.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private VenueRepository venueRepository;

	@Override
	public Optional<Room> get(Long id) throws Exception {
		return roomRepository.findById(id);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		roomRepository.deleteById(id);
	}

	@Override
	public boolean save(Room room, Long idVenue) throws Exception {
		Optional<Venue> ven=venueRepository.findById(idVenue);
		if(ven.isPresent() && ven.get().getRooms().size()<ven.get().getNumberRooms()) {
			ven.get().getRooms().add(room);
			roomRepository.save(room);
			return true;
		}
		else
			return false;
	}

	@Override
	public Room update(Long id, Long size, Long distanceMin, Long emergencyExits, Long noUsableLocations, String name,
			Long numberSeats) throws Exception {
		if(this.get(id).isPresent()){
			Room r= this.get(id).get();
			if(size != null)
				r.setSize(size);
			if(distanceMin != null)
				r.setDistanceMin(distanceMin);
			if(emergencyExits != null)
				r.setEmergencyExits(emergencyExits);
			if(noUsableLocations != null)
				r.setNoUsableLocations(noUsableLocations);
			if(name != null)
				r.setName(name);
			if(numberSeats != null)
				r.setNumberSeats(numberSeats);
			return roomRepository.save(r);
		}
		else
			return null;
	}

	
}
