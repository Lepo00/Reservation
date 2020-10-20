package it.anoki.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.anoki.spring.model.Company;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<Company> get(@PathVariable Long id) throws Exception {
		Optional<Company> c = companyService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping("/save/{idUser}")
	public ResponseEntity<?> newCompany(@PathVariable Long idUser, @RequestBody Company c) throws Exception {
		try {
			return ResponseEntity.ok(companyService.save(c, idUser));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Company Not Saved!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestParam(required = false) String desc,
			@RequestParam(required = false) String name) {
		try {
			return ResponseEntity.ok(companyService.update(id, name, desc));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Company Not Updated!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
		try {
			companyService.delete(id);
			return ResponseEntity.ok().body("Company Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping(path = "/{idCompany}/reserve/{idRoom}")
	public ResponseEntity<?> reserve(@PathVariable Long idCompany, @PathVariable Long idRoom,
			@RequestBody Reservation reservation) {
		try {
			boolean save = this.companyService.reserve(idCompany, idRoom, reservation);
			return save ? ResponseEntity.ok().body(reservation)
					: ResponseEntity.badRequest().body("Reservation Not Made!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Reservation Not Made!");
		}
	}
}
