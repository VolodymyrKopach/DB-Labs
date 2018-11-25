package com.kopach.DTO.impl;

import com.kopach.DTO.DTO;
import com.kopach.domain.GroupOfStudents;
import com.kopach.domain.Student;
import com.kopach.exceptions.NoSuchStudentException;
import com.kopach.exceptions.NoSuchGroupOfStudentException;
import com.kopach.exceptions.NoSuchLecturerException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class StudentDTO extends DTO<Student> {
    public StudentDTO(Student mobile, Link link) throws NoSuchLecturerException, NoSuchGroupOfStudentException, NoSuchStudentException {
        super(mobile, link);
    }

    public Long getStudentId() {
        return getEntity().getId();
    }

    public String getFirstName() {
        return getEntity().getFirstName();
    }

    public String getLastName() {
        return getEntity().getLastName();
    }

    public GroupOfStudents getGroup() {
        return getEntity().getGroupByGroup();
    }


}
