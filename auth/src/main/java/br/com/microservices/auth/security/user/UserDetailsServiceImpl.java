package br.com.microservices.auth.security.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.microservices.core.model.ApplicationUser;
import br.com.microservices.core.repository.ApplicationUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private final ApplicationUserRepository applicationUserRepository;
	
	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		log.debug("Searching in the DB the user by username '{}'", username);

		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);

		log.debug("ApplicationUser found '{}'", applicationUser);

		if (applicationUser == null)
			throw new UsernameNotFoundException(String.format("Application user '%s' not found", username));

		return new CustomUserDetails(applicationUser);
	}

}
