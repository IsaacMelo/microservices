package br.com.microservices.course.endpoint.controller;

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

import br.com.microservices.core.model.Course;
import br.com.microservices.course.endpoint.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("v1/course")
@Api(value = "Endpoints to manage course")
public class CourseController {
	@Autowired
    private CourseService courseService;
	
	 @Autowired
	 private ApplicationContext context;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "List all available courses", response = Course[].class)
    public ResponseEntity<Iterable<Course>> list(Pageable pageable) {
        return new ResponseEntity<>(courseService.list(pageable), HttpStatus.OK);
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Save courses", response = Course[].class)
    public ResponseEntity<Course> save(Course course) {
        return new ResponseEntity<>(courseService.save(course), HttpStatus.CREATED);
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Find courses by id", response = Course[].class)
    public ResponseEntity<Course> findById(@RequestParam Long id) throws NotFoundException {
        return new ResponseEntity<>(courseService.findById(id), HttpStatus.OK);
    }
    
    @GetMapping(path = "/template")
    @ApiOperation(value = "Template boletos", response = String.class)
    public String findTemplate() {
		Environment env = context.getEnvironment();
		String property = env.getProperty("template.boleto.net");
		
        return property;
    }
}
