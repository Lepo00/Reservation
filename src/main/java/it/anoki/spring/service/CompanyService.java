package it.anoki.spring.service;

import it.anoki.spring.model.Company;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;

public interface CompanyService {

	public Company get(Long id) throws Exception;

	public Company save(Company c, Long idUser) throws Exception;

	public void delete(Long id) throws Exception;

	public Company update(Long id, String name, String description) throws Exception;

	public boolean reserve(Long idCompany, Long idRoom, Reservation reservation) throws Exception;

	public boolean isAdmin(Long idCompany, User u);

	public boolean isAdmin(String idCompany, User u);

}