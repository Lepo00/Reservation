package it.anoki.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.anoki.spring.model.User;
import it.anoki.spring.service.EmailService;
import it.anoki.spring.service.UserService;
import it.anoki.spring.util.JwtTokenUtil;

@Controller
public class ThymeleafController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private EmailService emailService;

	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(Model model, @RequestParam String inputName, @RequestParam String inputPassword) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(inputName, inputPassword));
			model.addAttribute("token",jwtTokenUtil.generateToken(inputName));
			return "success";
		}catch (BadCredentialsException e) {
			model.addAttribute("name","Login  failed");
			return "failed";
		}
	}
	
	@GetMapping("/user/upload")
	public String getUpload(Model model) {
		return "upload";
	}
	
	@PostMapping("/user/upload")
	public String postUpload(Model model, @RequestParam Long id, @RequestParam MultipartFile image) {
		try {
			User user= userService.uploadPhoto(id, image);
			model.addAttribute("user",user);
			return "viewPhoto";
		} catch (Exception e) {
			return "failedUpload";
		}
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		return "register";
	}
	
	@PostMapping("/register")
	public String postRegister(Model model, @ModelAttribute User user) {
		try {
			userService.save(user);
			model.addAttribute("user", user);
			emailService.sendSimpleMessage(user.getName(), "First Token for API", jwtTokenUtil.generateToken(user.getName()));
			return "userRegistered";
		}catch(Exception e) {
			model.addAttribute("name","Register  failed");
			return "failed";
		}
	}
}