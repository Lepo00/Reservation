package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Room;
import it.anoki.spring.model.Seat;
import it.anoki.spring.repository.RoomRepository;
import it.anoki.spring.repository.SeatRepository;
import it.anoki.spring.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	SeatRepository seatRepository;
	@Autowired
	RoomRepository roomRepository;

	
	@Override
	public Optional<Seat> get(Long id) throws Exception {
		return seatRepository.findById(id);
	}

	@Override
	public boolean save(Seat s, Long idRoom) throws Exception {
		Optional<Room> room= roomRepository.findById(idRoom);
		if(room.isPresent() && s!=null && room.get().getSeats().size()<room.get().getNumberSeats()) {
			room.get().getSeats().add(s);
			seatRepository.save(s);
			return true;
		}
		return false;
	}

	@Override
	public void delete(Long id) throws Exception {
		seatRepository.deleteById(id);
	}

	@Override
	public Seat update(Long id, Boolean taken,String equipment, Integer number) throws Exception {
		Seat seat=null;
		if(this.get(id).isPresent() && number!=null || equipment!=null || taken!=null){
			seat= this.get(id).get();
			if(equipment != null)
				seat.setEquipment(equipment);
			if(number != null)
				seat.setNumber(number);
			if(taken!=null)
				seat.setTaken(taken);
		}
		return seatRepository.save(seat);
	}
}
