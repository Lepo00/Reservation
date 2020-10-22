package it.anoki.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.anoki.spring.util.JwtTokenUtil;

@Controller
public class ThymeleafController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(Model model, @RequestParam String inputName, @RequestParam String inputPassword) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputName, inputPassword));
			model.addAttribute("token",jwtTokenUtil.generateToken(inputName+ ""));
			return "success";
		}catch (BadCredentialsException e) {
			return "failed";
		}
	}

}