package it.anoki.spring.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Group;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.Room;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.GroupRepository;
import it.anoki.spring.repository.ReservationRepository;
import it.anoki.spring.repository.RoomRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.CompanyService;
import it.anoki.spring.service.GroupService;
import it.anoki.spring.service.ReservationService;
import it.anoki.spring.service.RoomService;
import it.anoki.spring.util.JwtTokenUtil;

@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupService groupService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private RoomRepository roomRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private RoomService roomService;

	@Override
	public Optional<Reservation> get(Long id) throws Exception {
		return reservationRepository.findById(id);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (reservationRepository.existsById(id) && this.checkUsedBy(reservationRepository.getOne(id).getUsedBy()))
			reservationRepository.deleteById(id);
		else
			throw new Exception();
	}

//	@Override
//	public boolean save(Reservation reservation, Long idUser, Long idRoom) throws Exception {
//		Optional<User> user = userRepository.findById(idUser);
//		Optional<Room> room = roomRepository.findById(idRoom);
//		if (user.isPresent() && room.isPresent()) {
//			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
//			reservation.setUsedBy(jwtTokenUtil.getUsernameFromToken());
//			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
//			reservation.setRoom(room.get());
//			user.get().getReservations().add(reservation);
//			reservationRepository.save(reservation);
//			return true;
//		}
//		return false;
//	}

	@Override
	public Reservation update(Long id, Date date, String description) throws Exception {
		Reservation res = null;
		Optional<Reservation> r = this.get(id);
		if (r.isPresent() && date != null || description != null) {
			res = r.get();
			if (description != null)
				res.setDescription(description);
			if (date != null)
				res.setDate(date);
			if (!this.checkUsedBy(res.getUsedBy())) {
				res = null;
			}
		}
		return reservationRepository.save(res);
	}

	@Override
	public boolean saveByUser(Reservation reservation, Long idUser, Long idRoom) throws Exception {
		User user = userRepository.getOne(idUser);
		Optional<Room> room = roomRepository.findById(idRoom);
		if (room.isPresent() && roomService.occupySeats(idRoom, 1)) {
			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUsedBy("user-" + jwtTokenUtil.getUsernameFromToken());
			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setOccupiedSeats(1);
			reservation.setRoom(room.get());
			user.getReservations().add(reservation);
			reservationRepository.save(reservation);
			return true;
		}
		return false;
	}

	@Override
	public boolean saveByGroup(Reservation reservation, Long idGroup, Long idRoom) throws Exception {
		User user = userRepository.findByName(jwtTokenUtil.getUsernameFromToken());
		Group group = groupRepository.getOne(idGroup);
		Optional<Room> room = roomRepository.findById(idRoom);

		if (!group.getUsers().contains(user) || !room.isPresent()
				|| !roomService.occupySeats(idRoom, group.getUsers().size())) {
			return false;
		} else {
			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUsedBy("group-" + idGroup);
			reservation.setOccupiedSeats(group.getUsers().size());
			reservation.setRoom(room.get());
			reservationRepository.save(reservation);
			return true;
		}
	}

	@Override
	public boolean saveByCompany(Reservation reservation, Long idCompany, Long idRoom) throws Exception {
		Optional<Room> room = roomRepository.findById(idRoom);
		if (room.isPresent() && roomService.occupySeats(idRoom, room.get().getNumberSeats())) {
			reservation.setCreatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUpdatedBy(jwtTokenUtil.getUsernameFromToken());
			reservation.setUsedBy("company-" + idCompany);
			reservation.setOccupiedSeats(room.get().getNumberSeats());
			reservation.setRoom(room.get());
			reservationRepository.save(reservation);
			return true;
		}
		return false;
	}

	public boolean checkUsedBy(String usedBy) throws Exception {
		User user = userRepository.findByName(jwtTokenUtil.getUsernameFromToken());
		String[] split = usedBy.split("-");
		return (usedBy.startsWith("group") && groupService.isInGroup(split[1], user)
				|| usedBy.startsWith("company") && companyService.isAdmin(split[1], user)
				|| user.equals(userRepository.findByName(split[1])));
	}

}
