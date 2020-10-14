package it.anoki.spring.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "group")
public class Group extends AuditModel{
	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;
	
	@ManyToMany
	//@JoinTable(name="shipment_details", joinColumns = @JoinColumn(name="shipment_id"),
	//inverseJoinColumns=@JoinColumn(name="pallet_id"))
	private List<User> users;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDesc(String description) {
		this.description = description;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


}
