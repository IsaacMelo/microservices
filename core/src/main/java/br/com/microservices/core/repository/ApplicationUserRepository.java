package br.com.microservices.core.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.microservices.core.model.ApplicationUser;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);

}
