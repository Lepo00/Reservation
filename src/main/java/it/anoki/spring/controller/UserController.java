package it.anoki.spring.controller;

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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import it.anoki.spring.model.Reservation;
import it.anoki.spring.model.User;
import it.anoki.spring.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@GetMapping("/detail/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) throws Exception {
		User c = userService.get(id);
		return ResponseEntity.ok(c);
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping("/save")
	public ResponseEntity<?> newUser(@RequestBody User u) throws Exception {
		return ResponseEntity.ok(userService.save(u));
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam(required = false) String email,
			@RequestParam(required = false) String address, @RequestParam(required = false) String name)
			throws Exception {
		User user = userService.update(id, address, email, name);
		return ResponseEntity.ok(user);
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) throws Exception {
		userService.delete(id);
		return ResponseEntity.ok().body("User Deleted");
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping(path = "/reserve/{idRoom}")
	public ResponseEntity<?> reserve(@PathVariable Long idRoom, @RequestBody Reservation reservation) {
		try {
			boolean save = this.userService.reserve(idRoom, reservation);
			return save ? ResponseEntity.ok().body(reservation)
					: ResponseEntity.badRequest().body("Reservation Not Made!");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Reservation Not Made!");
		}
	}

	@ApiOperation(value = "", authorizations = { @Authorization(value = "jwtToken") })
	@PostMapping(path = "/upload/{id}")
	public ResponseEntity<User> uploadPhoto(@PathVariable Long id, @RequestParam("image") MultipartFile file)
			throws Exception {
		return ResponseEntity.ok(userService.uploadPhoto(id, file));
	}

}
