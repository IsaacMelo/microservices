package br.com.microservices.auth.security.filter;

import static java.util.Collections.emptyList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import br.com.microservices.core.model.auth.User;
import br.com.microservices.core.property.JwtConfiguration;
import br.com.microservices.security.token.creator.TokenCreator;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private final AuthenticationManager authenticationManager;
	private final JwtConfiguration jwtConfiguration;
	private final TokenCreator tokenCreator;

	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfiguration jwtConfiguration, TokenCreator tokenCreator) {
		this.authenticationManager = authenticationManager;
		this.jwtConfiguration = jwtConfiguration;
		this.tokenCreator = tokenCreator;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		log.debug("Attempting authentication. . .");
		User user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		if (user == null)
			throw new UsernameNotFoundException("Unable to retrieve the username or password");

		log.debug("Creating the authentication object for the user '{}' and calling UserDetailServiceImpl loadUserByUsername", user.getUsername());

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword(), emptyList());

		usernamePasswordAuthenticationToken.setDetails(user);

		return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) {
		log.info("Authentication was successful for the user '{}', generating JWE token", auth.getName());

		SignedJWT signedJWT = tokenCreator.createSignedJWT(auth);

		String encryptedToken = null;
			try {
				encryptedToken = tokenCreator.encryptToken(signedJWT);
			} catch (JOSEException e) {
				log.error(e.getMessage());
				throw new RuntimeException(e.getMessage());
			}

		log.debug("Token generated successfully, adding it to the response header");

		response.addHeader("Access-Control-Expose-Headers", "XSRF-TOKEN, " + jwtConfiguration.getHeader().getName());

		response.addHeader(jwtConfiguration.getHeader().getName(),
				jwtConfiguration.getHeader().getPrefix() + encryptedToken);
	}

}
