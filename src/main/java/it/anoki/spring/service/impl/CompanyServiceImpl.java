package it.anoki.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import it.anoki.spring.model.Company;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.CompanyRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.CompanyService;
import it.anoki.spring.service.ReservationService;
import javassist.NotFoundException;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Company get(Long id) throws Exception {
		if (!companyRepository.existsById(id))
			throw new NotFoundException("Company not found");
		return companyRepository.getOne(id);
	}

	@Override
	public Company save(Company c, Long idUser) throws Exception {
		if (!userRepository.existsById(idUser))
			throw new NotFoundException("Company not found");
		c.setUser(userRepository.getOne(idUser));
		return companyRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		if (!companyRepository.existsById(id))
			throw new NotFoundException("Company not found");
		companyRepository.deleteById(id);
	}

	@Override
	public Company update(Long id, String name, String desc) throws Exception {
		Company company = this.get(id);
		if (desc == null && name == null)
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		if (desc != null)
			company.setDescription(desc);
		if (name != null)
			company.setName(name);
		return companyRepository.save(company);
	}

	@Override
	public boolean reserve(Long idCompany, Long idRoom, Reservation reservation) throws Exception {
		if (!companyRepository.existsById(idCompany))
			return false;
		return reservationService.saveByCompany(reservation, idCompany, idRoom);
	}

	@Override
	public boolean isAdmin(Long idCompany, User u) {
		return (companyRepository.existsById(idCompany) && companyRepository.getOne(idCompany).getUser().equals(u));
	}

	@Override
	public boolean isAdmin(String idCompany, User u) {
		Long id = Long.parseLong(idCompany);
		return (companyRepository.existsById(id) && companyRepository.getOne(id).getUser().equals(u));
	}

}
