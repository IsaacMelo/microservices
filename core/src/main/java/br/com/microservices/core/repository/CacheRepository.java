package br.com.microservices.core.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.microservices.core.model.Cache;

public interface CacheRepository extends PagingAndSortingRepository<Cache, Long> {
}
