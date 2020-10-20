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
import it.anoki.spring.model.Room;
import it.anoki.spring.service.RoomService;

@RestController
@RequestMapping("/room")
public class RoomController {

	@Autowired
	RoomService roomService;

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<Room> get(@PathVariable Long id) throws Exception {
		Optional<Room> c = roomService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping("/save/{idVenue}")
	public ResponseEntity<?> newRoom(@PathVariable Long idVenue, @RequestBody Room r) throws Exception {
		try {
			boolean save = roomService.save(r, idVenue);
			return save ? ResponseEntity.ok(r) : ResponseEntity.badRequest().body("Room Not Saved!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Room Not Saved!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestParam(required = false) Long size,
			@RequestParam(required = false) Long distanceMin, @RequestParam(required = false) Long emergencyExits,
			@RequestParam(required = false) Long noUsableLocations, @RequestParam(required = false) String name,
			@RequestParam(required = false) Integer numberSeats) {
		try {
			return ResponseEntity.ok(
					roomService.update(id, size, distanceMin, emergencyExits, noUsableLocations, name, numberSeats));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Room Not Updated!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
		try {
			roomService.delete(id);
			return ResponseEntity.ok().body("Room Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

}
