package it.anoki.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.anoki.spring.model.User;
import it.anoki.spring.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService, AuthenticationManager {

	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByName(username);
		List<GrantedAuthority> grantedAuths = new ArrayList<>();
	    grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.getName(), bCryptPasswordEncoder.encode(user.getPassword()),grantedAuths);   
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal() + "";
	    String password = authentication.getCredentials() + "";

	    User user = userRepository.findByName(username);
	    if (user == null) {
	        throw new BadCredentialsException("1000");
	    }
	    if (!(password.equals(user.getPassword()))) {
	        throw new BadCredentialsException("1000");
	    }
        return authentication;
	}
		
}