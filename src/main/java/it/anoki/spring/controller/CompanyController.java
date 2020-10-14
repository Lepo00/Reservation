package it.anoki.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.anoki.spring.model.Company;
import it.anoki.spring.service.CompanyService;
@RestController
@RequestMapping("/company")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Company> get(@PathVariable Long id) throws Exception {
		Optional<Company> c = companyService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> newCompany(
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long idUser
			) throws Exception {
		/*System.out.println("idUser:\t"+idUser);
		System.out.println("company:"+name+", "+description);*/
		try {
			return ResponseEntity.ok(companyService.save(name,description,idUser));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Company Not Saved!");
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateCompany(@PathVariable Long id,
			@RequestParam (required = false) String desc,
			@RequestParam (required = false) String name) {
		try {
			return ResponseEntity.ok(companyService.update(id, name, desc));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Company Not Updated!");
		}
	}
	
	@DeleteMapping(path="delete/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
		try {
			companyService.delete(id);
			return ResponseEntity.ok().body("Company Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
