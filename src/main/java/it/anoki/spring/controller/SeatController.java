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

import it.anoki.spring.model.Seat;
import it.anoki.spring.service.SeatService;
@RestController
@RequestMapping("/seat")
public class SeatController {
	
	@Autowired
	SeatService seatService;
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<Seat> get(@PathVariable Long id) throws Exception {
		Optional<Seat> g = seatService.get(id);
		if (g.isPresent()) {
			return ResponseEntity.ok(g.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/save/{idRoom}")
	public ResponseEntity<?> newSeat(
			@PathVariable Long idRoom,
			@RequestBody Seat seat
			) throws Exception {
		try {
			boolean save= seatService.save(seat,idRoom);
			return save ? ResponseEntity.ok(seat) : ResponseEntity.badRequest().body("Seat Not Saved!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Seat Not Saved!");
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateSeat(@PathVariable Long id,
			@RequestParam (required = false) String equipment,
			@RequestParam (required = false) Boolean taken,
			@RequestParam (required = false) Integer number) {
		try {
			return ResponseEntity.ok(seatService.update(id,taken,equipment, number));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Seat Not Updated!");
		}
	}
	
	@DeleteMapping(path="delete/{id}")
    public ResponseEntity<String> deleteSeat(@PathVariable Long id){
		try {
			seatService.delete(id);
			return ResponseEntity.ok().body("Seat Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
