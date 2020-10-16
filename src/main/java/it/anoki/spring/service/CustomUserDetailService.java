package it.anoki.spring.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        String roles = 
        		"ROLE_USER;" +
        		"ROLE_EDIT;" + 
        		"ROLE_DELETE;";
        String[] tokens = roles.split(";");
        for(String role : tokens) {
        	grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User("user1", bCryptPasswordEncoder.encode("123"), grantedAuthorities);   
	}
	
	public boolean login(String username, String password) {
		return false;
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
	    List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
	}
		
}
