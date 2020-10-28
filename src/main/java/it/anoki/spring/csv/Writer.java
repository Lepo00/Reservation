package it.anoki.spring.csv;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemWriter;

import it.anoki.spring.model.User;
import it.anoki.spring.service.UserService;

public class Writer implements ItemWriter<User> {

	private final UserService userService;

	public Writer(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void write(List<? extends User> users) throws Exception {
		for (User user : users) {
			user.setId((long) (Math.random() * 1000));
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			user.setPhoto("default");
			user.setEmail("email@email.email");
			System.out.println("write: " + user.toString());
			this.userService.save(user);
		}
	}

}