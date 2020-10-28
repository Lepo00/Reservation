package it.anoki.spring.csv;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.User;
import it.anoki.spring.service.UserService;

@Service
public class Writer implements ItemWriter<User> {

	@Autowired
	private UserService userService;

	@Override
	public void write(List<? extends User> users) throws Exception {
		for (User user : users) {
			user.setPhoto("default");
			user.setEmail("email@email.email");
			System.out.println("write: " + user.toString());
			this.userService.save(user);
		}
	}

}