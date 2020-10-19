package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Venue;
import it.anoki.spring.model.Company;
import it.anoki.spring.repository.VenueRepository;
import it.anoki.spring.repository.CompanyRepository;
import it.anoki.spring.service.VenueService;

@Service
public class VenueServiceImpl implements VenueService {

	@Autowired
	private VenueRepository venueRepository;
	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Optional<Venue> get(Long id) throws Exception {
		return venueRepository.findById(id);
	}

	@Override
	public Venue save(Venue v, Long idCompany) throws Exception {
		Optional<Company> comp = companyRepository.findById(idCompany);
		if (comp.isPresent())
			v.setCompany(comp.get());
		else
			v = null;
		return venueRepository.save(v);
	}

	@Override
	public void delete(Long id) throws Exception {
		venueRepository.deleteById(id);
	}

	@Override
	public Venue update(Long id, String name, String address, Long numberRooms) throws Exception {
		Optional<Venue> venue = this.get(id);
		Venue v = null;
		if (venue.isPresent()) {
			v = venue.get();
			if (address != null)
				v.setAddress(address);
			if (name != null)
				v.setName(name);
			if (numberRooms != null)
				v.setNumberRooms(numberRooms);
		}
		return venueRepository.save(v);
	}

}
