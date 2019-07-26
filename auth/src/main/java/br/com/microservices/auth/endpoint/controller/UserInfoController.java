package br.com.microservices.auth.endpoint.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.core.model.auth.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("user")
@Api(value = "Endpoints to manage User's information")
public class UserInfoController {

	@GetMapping(path = "info", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Will retrieve the information from the user available in the token", response = User.class)
	public ResponseEntity<User> getUserInfo(Principal principal) {
		User user = (User) ((UsernamePasswordAuthenticationToken) principal)
				.getPrincipal();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
