package it.anoki.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "seat")
public class Seat extends AuditModel{
	
	@Column(name = "taken")
	Boolean taken;
	
	@Column(name = "equipment")
	String equipment;
	
	@Column(name = "number")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Integer number;

	public Boolean isTaken() {
		return taken;
	}

	public void setTaken(Boolean taken) {
		this.taken = taken;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
