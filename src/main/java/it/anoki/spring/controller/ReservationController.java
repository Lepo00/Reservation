package it.anoki.spring.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import it.anoki.spring.model.Reservation;
import it.anoki.spring.service.ReservationService;
@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<Reservation> get(@PathVariable Long id) throws Exception {
		Optional<Reservation> c = reservationService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping("/save/{idUser}/{idRoom}")
	public ResponseEntity<?> newReservation(
			@PathVariable Long idUser,
			@PathVariable Long idRoom,
			@RequestBody Reservation r
			) throws Exception {
		try {
			boolean save= reservationService.save(r,idUser,idRoom,"ciao");
			return save ? ResponseEntity.ok(r) : ResponseEntity.badRequest().body("Reservation Not Saved!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Reservation Not Saved!");
		}
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateReservation(
			@PathVariable Long id,
			@RequestParam (required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date,
			@RequestParam (required = false) String description) {
		try {
			System.out.println("data: "+date);
			return ResponseEntity.ok(reservationService.update(id, date, description));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Reservation Not Updated!");
		}
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@DeleteMapping(path="delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
		try {
			reservationService.delete(id);
			return ResponseEntity.ok().body("Reservation Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
