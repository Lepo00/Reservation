package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.User;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> get(Long id) throws Exception {
		return userRepository.findById(id);
	}

	@Override
	public User save(User c) throws Exception {
		return userRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		userRepository.deleteById(id);
	}

	@Override
	public User update(Long id, String address, String email, String name) throws Exception {
		Optional<User> u = this.get(id);
		User user = null;
		if (this.get(id).isPresent()) {
			user = u.get();
			if (address != null)
				user.setAddress(address);
			if (email != null)
				user.setEmail(email);
			if (name != null)
				user.setName(name);
		}
		return userRepository.save(user);
	}

}
