package br.com.microservices.core.repository;


import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.microservices.core.model.Course;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
}
