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

import it.anoki.spring.model.User;
import it.anoki.spring.service.UserService;
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<User> get(@PathVariable Long id) throws Exception {
		Optional<User> c = userService.get(id);
		if (c.isPresent()) {
			return ResponseEntity.ok(c.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> newUser(@RequestBody User u) throws Exception {
		try {
			User save = userService.save(u);
			return ResponseEntity.ok(save);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("User Not Saved!");
		}
	}
	
	@PutMapping(path = "/update/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id,
			@RequestParam (required = false) String email,
			@RequestParam (required = false) String address,
			@RequestParam (required = false) String name) {
		try {
			return ResponseEntity.ok(userService.update(id, address, email,name));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("User Not Updated!");
		}
	}
	
	@DeleteMapping(path="delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
		try {
			userService.delete(id);
			return ResponseEntity.ok().body("deleted");
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
