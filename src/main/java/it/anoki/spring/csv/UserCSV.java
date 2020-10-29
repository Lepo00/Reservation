package it.anoki.spring.csv;

import org.springframework.beans.BeanUtils;

import it.anoki.spring.model.User;

public class UserCSV {

	private String name;

	private String email;

	private String address;

	private String password;

	private String photo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public static UserCSV build(User entity) {

		UserCSV result = new UserCSV();
		BeanUtils.copyProperties(entity, result);
		return result;
	}
}
