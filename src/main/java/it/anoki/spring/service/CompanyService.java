package it.anoki.spring.service;

import java.util.Optional;

import it.anoki.spring.model.Company;
public interface CompanyService {

	public Optional<Company> get(Long id) throws Exception;

	public Company save(String name, String description, Long idUser) throws Exception;
	
	public void delete(Long id) throws Exception;
	
	public Company update(Long id, String name, String description) throws Exception;

}