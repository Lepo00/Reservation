package it.anoki.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "reservation")
public class Reservation extends AuditModel {

	@Column(name = "date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date date;

	@Column(name = "createdBy", updatable = false)
	private String createdBy;

	@Column(name = "updatedBy")
	private String updatedBy;

	@Column(name = "description")
	private String description;

	@Column(name = "used_by", updatable = false)
	private String usedBy;

	@Column(name = "occupied_seats", updatable = false)
	private Integer occupiedSeats;

	@ManyToOne
	Room room;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Integer getOccupiedSeats() {
		return occupiedSeats;
	}

	public void setOccupiedSeats(Integer occupiedSeats) {
		this.occupiedSeats = occupiedSeats;
	}

}
