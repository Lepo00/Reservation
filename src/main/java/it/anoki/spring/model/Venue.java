package it.anoki.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table
public class Venue extends AuditModel{
	
	@ManyToOne
	private Company company;
	
	@OneToMany
	@JoinColumn(name = "venue_id")
	private List<Room> rooms;
	
	@Column
	private String address;
	
	@Column(name = "number_rooms")
	private Long numberRooms;
	
	@Column(name="name")
	private String name;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getNumberRooms() {
		return numberRooms;
	}

	public void setNumberRooms(Long numberRooms) {
		this.numberRooms = numberRooms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
