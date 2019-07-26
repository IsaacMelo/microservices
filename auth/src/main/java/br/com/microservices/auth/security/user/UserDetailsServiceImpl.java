package br.com.microservices.auth.security.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.microservices.core.model.auth.User;
import br.com.microservices.core.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private final UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		log.debug("Searching in the DB the user by username '{}'", username);

		Optional<User> userOptional = userRepository.findByUsername(username);
		
		User user = userOptional.orElseThrow(() -> new  UsernameNotFoundException(String.format("Application user '%s' not found", username)));
		
		log.debug("ApplicationUser found '{}'", userOptional);
		
		return new CustomUserDetails(user, getPermissions(user));
	}
	
	private Collection<? extends GrantedAuthority> getPermissions(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		List<String> permissions = userRepository.permissions(user);
		permissions.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		return authorities;
	}

}
