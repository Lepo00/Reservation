package it.anoki.spring.csv;

import org.springframework.batch.item.ItemProcessor;

import it.anoki.spring.model.User;

public class Processor implements ItemProcessor<User, User> {

	@Override
	public User process(User item) throws Exception {
		System.out.print("PROCESSOR:\t");
		System.out.println(item.toString());
		return item;
	}

}