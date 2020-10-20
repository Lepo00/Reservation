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
import it.anoki.spring.model.Group;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	GroupService groupService;

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<Group> get(@PathVariable Long id) throws Exception {
		Optional<Group> g = groupService.get(id);
		if (g.isPresent()) {
			return ResponseEntity.ok(g.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping("/save")
	public ResponseEntity<?> newGroup(@RequestBody Group g) throws Exception {
		try {
			return ResponseEntity.ok(groupService.save(g));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Group Not Saved!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateGroup(@PathVariable Long id, @RequestParam(required = false) String desc,
			@RequestParam(required = false) String name) {
		try {
			return ResponseEntity.ok(groupService.update(id, name, desc));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Group Not Updated!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<String> deleteGroup(@PathVariable Long id) {
		try {
			groupService.delete(id);
			return ResponseEntity.ok().body("Group Deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping(path = "/{idGroup}/user")
	public ResponseEntity<?> addUser(@PathVariable Long idGroup, @RequestParam Long idUser) throws Exception {
		try {
			boolean save = groupService.addUser(idGroup, idUser);
			return (save ? ResponseEntity.ok("User Added") : ResponseEntity.badRequest().body("User Not Added!"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("User Not Added!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping(path = "/{idGroup}/reserve/{idRoom}")
	public ResponseEntity<?> reserve(@PathVariable Long idGroup, @PathVariable Long idRoom,
			@RequestBody Reservation reservation) {
		try {
			boolean save = this.groupService.reserve(idGroup, idRoom, reservation);
			return save ? ResponseEntity.ok().body(reservation)
					: ResponseEntity.badRequest().body("Reservation Not Made!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Reservation Not Made!");
		}
	}
}
