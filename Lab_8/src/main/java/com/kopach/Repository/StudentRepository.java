package com.kopach.Repository;

import com.kopach.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
