package br.com.microservices.core.repository.helper.user;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.microservices.core.model.auth.User;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<String> permissions(User user) {
		return manager.createQuery(
				"select distinct p.name from User u inner join u.groups g inner join g.permissions p where u = :user", String.class)
				.setParameter("user", user)
				.getResultList();
	}

}
