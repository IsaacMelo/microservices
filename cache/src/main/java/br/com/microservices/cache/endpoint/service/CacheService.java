package br.com.microservices.cache.endpoint.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.microservices.core.model.Cache;
import br.com.microservices.core.repository.CacheRepository;
import javassist.NotFoundException;

@Service
public class CacheService {
	@Autowired
	private CacheRepository cacheRepository;

	public Iterable<Cache> list(Pageable pageable) {
		return cacheRepository.findAll(pageable);
	}

	public Cache save(Cache cache) {
		return cacheRepository.save(cache);
	}

	public Cache findById(Long id) throws NotFoundException {
		Optional<Cache> cache = cacheRepository.findById(id);
		if (!cache.isPresent()) {
			throw new NotFoundException("Not Found Cache");
		}
		return cache.get();
	}
}
