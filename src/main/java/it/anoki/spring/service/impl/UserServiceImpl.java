package it.anoki.spring.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.ReservationService;
import it.anoki.spring.service.UserService;
import it.anoki.spring.util.JwtTokenUtil;
import javassist.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private ReservationService reservationService;

	@Override
	public User get(Long id) throws Exception {
		if (!userRepository.existsById(id))
			throw new NotFoundException("User not found");
		return userRepository.getOne(id);
	}

	@Override
	public User save(User c) throws Exception {
		if (c == null)
			throw new NotFoundException("User not saved!!!");
		return userRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (!userRepository.existsById(id))
			throw new NotFoundException("User not found");
		userRepository.deleteById(id);
	}

	@Override
	public User update(Long id, String address, String email, String name) throws Exception {
		User user = this.get(id);
		if (address != null)
			user.setAddress(address);
		if (email != null)
			user.setEmail(email);
		if (name != null)
			user.setName(name);
		return userRepository.save(user);
	}

	@Override
	public boolean reserve(Long idRoom, Reservation reservation) throws Exception {
		User user = userRepository.findByName(jwtTokenUtil.getUsernameFromToken());
		return reservationService.saveByUser(reservation, user.getId(), idRoom);
	}

	@Override
	public User uploadPhoto(Long id, MultipartFile file) throws Exception {
		if (!userRepository.existsById(id) || file.getOriginalFilename().isEmpty())
			throw new NotFoundException("User not found");
		User user = userRepository.getOne(id);
		String basePath = System.getProperty("java.io.tmpdir");
		String originalFilename = file.getOriginalFilename();
		String destPath = basePath + File.separator + originalFilename;
		user.setPhotos(destPath);
		return userRepository.save(user);
	}

}
