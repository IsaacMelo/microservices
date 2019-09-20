package br.com.microservices.cache.endpoint.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.microservices.cache.endpoint.service.CacheService;
import br.com.microservices.core.model.Cache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("v1/cache")
@Api(value = "Endpoints to manage cache")
public class CacheController {
	@Autowired
    private CacheService cacheService;
	
	 @Autowired
	 private ApplicationContext context;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "List all available cache", response = Cache[].class)
    public ResponseEntity<Iterable<Cache>> list(Pageable pageable) {
        return new ResponseEntity<>(cacheService.list(pageable), HttpStatus.OK);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save cache", response = Cache[].class)
    public ResponseEntity<Cache> save(Cache cache) {
        return new ResponseEntity<>(cacheService.save(cache), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find cache by id", response = Cache[].class)
    public ResponseEntity<Cache> findById(@RequestParam Long id) throws NotFoundException {
        return new ResponseEntity<>(cacheService.findById(id), HttpStatus.OK);
    }
    
    @GetMapping(path = "/template")
    @ApiOperation(value = "Template boletos", response = String.class)
    public String findTemplate() {
		Environment env = context.getEnvironment();
		String property = env.getProperty("template.boleto.net");
		
        return property;
    }
}
