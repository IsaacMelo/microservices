package br.com.microservices.core.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.microservices.core.model.auth.User;
import br.com.microservices.core.repository.helper.user.UserRepositoryCustom;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

	Optional<User> findByUsername(String username);

}
