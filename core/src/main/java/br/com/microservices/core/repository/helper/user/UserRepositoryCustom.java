package br.com.microservices.core.repository.helper.user;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;

import br.com.microservices.core.model.auth.User;

public interface UserRepositoryCustom {
	
	@Modifying
	public List<String> permissions(User user);
}
