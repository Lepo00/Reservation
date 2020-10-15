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

import it.anoki.spring.model.Venue;
import it.anoki.spring.service.VenueService;
@RestController
@RequestMapping("/venue")
public class VenueController {
	
	@Autowired
	VenueService venueService;
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Venue> get(@PathVariable Long id) throws Exception {
		Optional<Venue> c = venueService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/save/{idCompany}")
	public ResponseEntity<?> newVenue(
			@PathVariable Long idCompany,
			@RequestBody Venue v
			) throws Exception {
		try {
			return ResponseEntity.ok(venueService.save(v,idCompany));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Venue Not Saved!");
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateVenue(@PathVariable Long id,
			@RequestParam (required = false) String address,
			@RequestParam (required = false) String name,
			@RequestParam (required = false) Long numberRooms) {
		try {
			return ResponseEntity.ok(venueService.update(id, name, address, numberRooms));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Venue Not Updated!");
		}
	}
	
	@DeleteMapping(path="delete/{id}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long id){
		try {
			venueService.delete(id);
			return ResponseEntity.ok().body("Venue Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
