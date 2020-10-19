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
			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUsedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setRoom(room.get());
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
			res.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			if(description != null)
				res.setDescription(description);
			if(date != null)
				res.setDate(date);
		}
		return reservationRepository.save(res);
	}

	@Override
	public boolean saveByUser(Reservation r, Long idUser, Long idRoom) throws Exception {
		Optional<User> user=userRepository.findById(idUser);
		Optional<Room> room=roomRepository.findById(idRoom);
		if(user.isPresent() && room.isPresent()) {
			r.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			r.setUsedBy("user-"+jwtTokenUtil.getUsernameFromToken());
			r.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			r.setRoom(room.get());
			user.get().getReservations().add(r);
			reservationRepository.save(r);
			return true;
		}
		return false;
	}

	@Override
	public boolean saveByGroup(Reservation r, Long idUser, Long idRoom) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveByCompany(Reservation r, Long idUser, Long idRoom) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
}
