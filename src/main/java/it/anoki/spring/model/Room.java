package it.anoki.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "room")
public class Room extends AuditModel{
	
	@Column
	private Long size;
	
	@Column(name = "distance_min")
	private Long distanceMin;
	
	@Column(name = "emergency_exits")
	private Long emergencyExits;
	
	@Column(name = "no_usable_locations")
	private Long noUsableLocations;
	
	@Column
	private String name;
	
	@Column(name = "number_seats")
	private Long numberSeats;
	
	@OneToMany
	@JoinColumn(name = "room_id")
	private List<Seat> seats;

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Long getDistanceMin() {
		return distanceMin;
	}

	public void setDistanceMin(Long distanceMin) {
		this.distanceMin = distanceMin;
	}

	public Long getEmergencyExits() {
		return emergencyExits;
	}

	public void setEmergencyExits(Long emergencyExits) {
		this.emergencyExits = emergencyExits;
	}

	public Long getNoUsableLocations() {
		return noUsableLocations;
	}

	public void setNoUsableLocations(Long noUsableLocations) {
		this.noUsableLocations = noUsableLocations;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNumberSeats() {
		return numberSeats;
	}

	public void setNumberSeats(Long numberSeats) {
		this.numberSeats = numberSeats;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
	
	
}
