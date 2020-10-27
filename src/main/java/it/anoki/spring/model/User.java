package it.anoki.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User extends AuditModel {

	@Column(name = "name", unique = true)
	@NotNull
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@Column(name = "password")
	@NotNull
	private String password;

	@Column(nullable = true)
	private String photo;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<Reservation> reservations;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
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

}
