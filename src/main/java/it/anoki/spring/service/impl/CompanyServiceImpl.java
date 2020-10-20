package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Company;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.CompanyRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.CompanyService;
import it.anoki.spring.service.ReservationService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private ReservationService reservationService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<Company> get(Long id) throws Exception {
		return companyRepository.findById(id);
	}

	@Override
	public Company save(Company c, Long idUser) throws Exception {
		Optional<User> u = userRepository.findById(idUser);
		if (u.isPresent())
			c.setUser(u.get());
		else
			c = null;
		return companyRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		companyRepository.deleteById(id);
	}

	@Override
	public Company update(Long id, String name, String desc) throws Exception {
		Optional<Company> company = this.get(id);
		Company c = null;
		if (company.isPresent() && desc != null || name != null) {
			c = company.get();
			if (desc != null)
				c.setDescription(desc);
			if (name != null)
				c.setName(name);
		}
		return companyRepository.save(c);
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
