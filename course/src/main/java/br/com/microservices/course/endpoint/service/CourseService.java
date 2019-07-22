package br.com.microservices.course.endpoint.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.microservices.core.model.Course;
import br.com.microservices.core.repository.CourseRepository;
import javassist.NotFoundException;

@Service
public class CourseService {
	private final Logger log = LoggerFactory.getLogger(CourseService.class);

	@Autowired
	private CourseRepository courseRepository;

	public Iterable<Course> list(Pageable pageable) {
		log.info("Listing all courses");
		return courseRepository.findAll(pageable);
	}
	
	public Course save(Course course) {
		return courseRepository.save(course);
	}
	
	public Course findById(Long id) throws NotFoundException {
		Optional<Course> course = courseRepository.findById(id);
		if(!course.isPresent()) {
			throw new NotFoundException("Not Found Course");
		}
		return course.get();
	}
}
