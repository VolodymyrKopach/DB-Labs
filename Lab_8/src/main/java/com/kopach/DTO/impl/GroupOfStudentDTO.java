package com.kopach.DTO.impl;

import com.kopach.DTO.DTO;
import com.kopach.controller.StudentController;
import com.kopach.domain.Student;
import com.kopach.domain.GroupOfStudents;
import com.kopach.exceptions.NoSuchStudentException;
import com.kopach.exceptions.NoSuchGroupOfStudentException;
import com.kopach.exceptions.NoSuchLecturerException;
import org.springframework.hateoas.Link;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class GroupOfStudentDTO extends DTO<GroupOfStudents> {
    public GroupOfStudentDTO(GroupOfStudents cpu, Link link) throws NoSuchGroupOfStudentException, NoSuchLecturerException, NoSuchStudentException {
        super(cpu, link);
        add(linkTo(methodOn(StudentController.class).getStudentsByGroupId(getEntity().getId())).withRel("mobile"));
    }

    public Long getStudentId() {
        return getEntity().getId();
    }

    public Integer getNumberOfGroup() {
        return getEntity().getNumberOfGroup();
    }
  

    public Set<Student> getStudents() {
        return getEntity().getStudent();
    }
}
