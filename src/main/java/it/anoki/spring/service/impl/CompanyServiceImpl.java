package it.anoki.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.Company;
import it.anoki.spring.model.User;
import it.anoki.spring.repository.CompanyRepository;
import it.anoki.spring.repository.UserRepository;
import it.anoki.spring.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyRepository companyRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public Optional<Company> get(Long id) throws Exception {
		return companyRepository.findById(id);
	}

	@Override
	public Company save(Company c, Long idUser) throws Exception {
		Optional<User> u=userRepository.findById(idUser);
		if(u.isPresent())
			c.setUser(u.get());
		else
			c=null;
		return companyRepository.save(c);
	}

	@Override
	public void delete(Long id) throws Exception {
		companyRepository.deleteById(id);
	}

	@Override
	public Company update(Long id, String name,String desc) throws Exception {
		if(this.get(id).isPresent()){
			Company c= this.get(id).get();
			if(desc != null)
				c.setDescription(desc);
			if(name != null)
				c.setName(name);
			return companyRepository.save(c);
		}
		else
			return null;
	}

	
}
