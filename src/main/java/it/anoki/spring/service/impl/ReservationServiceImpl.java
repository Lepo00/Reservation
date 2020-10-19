package it.anoki.spring.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.Room;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.ReservationRepository;
import it.anoki.spring.repository.RoomRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.ReservationService;
import it.anoki.spring.util.JwtTokenUtil;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public Optional<Reservation> get(Long id) throws Exception {
		return reservationRepository.findById(id);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		reservationRepository.deleteById(id);
	}

	@Override
	public boolean save(Reservation reservation, Long idUser, Long idRoom) throws Exception {
		Optional<User> user=userRepository.findById(idUser);
		Optional<Room> room=roomRepository.findById(idRoom);
		if(user.isPresent() && room.isPresent()) {
			reservation.setRoom(room.get());
			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			user.get().getReservations().add(reservation);
			reservationRepository.save(reservation);
			return true;
		}
		return false;
	}

	@Override
	public Reservation update(Long id, Date date,String description) throws Exception {
		Reservation res=null;
		Optional<Reservation> r= this.get(id);
		if(r.isPresent() && date!=null || description!=null){
			res= r.get();
			if(description != null)
				res.setDescription(description);
			if(date != null)
				res.setDate(date);
		}
		return reservationRepository.save(res);
	}
	
}
