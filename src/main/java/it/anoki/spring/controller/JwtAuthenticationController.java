package it.anoki.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.anoki.spring.model.JwtAuthenticationReq;
import it.anoki.spring.model.User;
import it.anoki.spring.service.EmailService;
import it.anoki.spring.service.UserService;
import it.anoki.spring.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;

	@PostMapping("/token")
	public ResponseEntity<String> createAuthenticationToken(@RequestBody JwtAuthenticationReq authenticationRequest)
			throws Exception {
		try {
			Authentication user = authenticate(authenticationRequest.getUsername(),
					authenticationRequest.getPassword());

			final String token = jwtTokenUtil.generateToken(user.getPrincipal() + "");

			User u = userService.getFromName(user.getPrincipal() + "");
			emailService.sendSimpleMessage(u.getEmail(), "Token for API", token);

			return ResponseEntity.ok(token);
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().body("Invalid credentials");
		}
	}

	private Authentication authenticate(String username, String password) throws Exception {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}