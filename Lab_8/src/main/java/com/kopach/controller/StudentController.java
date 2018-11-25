package com.kopach.controller;

import com.kopach.DTO.impl.StudentDTO;
import com.kopach.domain.Student;
import com.kopach.exceptions.*;
import com.kopach.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;
// get Student by class id
    @GetMapping(value = "/api/student/group_of_student/{group_id}")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroupId(@PathVariable Long group_id) throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        Set<Student> StudentSet= studentService.getStudentByGroupId(group_id);

        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        List<StudentDTO> StudentsDTO = new ArrayList<>();
        for (Student entity : StudentSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            StudentDTO dto = new StudentDTO(entity, selfLink);
            StudentsDTO.add(dto);
        }

        return new ResponseEntity<>(StudentsDTO, HttpStatus.OK);
    }
// get Student
    @GetMapping(value = "/api/student/{student_id}")
    public ResponseEntity<StudentDTO> getStudents(@PathVariable Long student_id) throws NoSuchStudentException, NoSuchLecturerException, NoSuchGroupOfStudentException {
        Student student = studentService.getStudents(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudents(student_id)).withSelfRel();

        StudentDTO studentDTO = new StudentDTO(student, link);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Student")
    public ResponseEntity<Set<StudentDTO>> getAllStudents() throws NoSuchStudentException, NoSuchLecturerException, NoSuchGroupOfStudentException {
        List<Student> studentSet = studentService.getAllStudents();
        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        Set<StudentDTO> studentDTOS = new HashSet<>();
        for (Student entity : studentSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            StudentDTO dto = new StudentDTO(entity, selfLink);
            studentDTOS.add(dto);
        }

        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Student/Lecturer/{Lecturer_id}")
    public ResponseEntity<Set<StudentDTO>> getStudentsByLecturerID(@PathVariable Long Lecturer_id) throws NoSuchLecturerException, NoSuchStudentException, NoSuchGroupOfStudentException {
        Set<Student> studentSet = studentService.getStudentsByLecturerId(Lecturer_id);
        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        Set<StudentDTO> studentDTOS = new HashSet<>();
        for (Student entity : studentSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            StudentDTO dto = new StudentDTO(entity, selfLink);
            studentDTOS.add(dto);
        }

        return new ResponseEntity<>(studentDTOS, HttpStatus.OK);
    }
// add Student
    @PostMapping(value = "/api/Student/Number_of_group/{group_id}")
    public  ResponseEntity<StudentDTO> addStudent(@RequestBody Student student, @PathVariable Long group_id)
            throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        studentService.createStudent(student, group_id);
        Link link = linkTo(methodOn(StudentController.class).getStudents(student.getId())).withSelfRel();

        StudentDTO studentDTO = new StudentDTO(student, link);

        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }
//update Student
    @PutMapping(value = "/api/Student/{student_id}/Group_of_student/{group_id}")
    public  ResponseEntity<StudentDTO> updateStudent(@RequestBody Student student,
                                                     @PathVariable Long student_id, @PathVariable Long group_id)
            throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        studentService.updateStudent(student, student_id, group_id);
        Student students = studentService.getStudents(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudents(student_id)).withSelfRel();

        StudentDTO studentDTO = new StudentDTO(students, link);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Student/{student_id}")
    public  ResponseEntity deleteStudent(@PathVariable Long student_id) throws NoSuchStudentException, ExistsLecturerForStudentException, ExistsLecturerForStudentException {
        studentService.deleteStudent(student_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/Student/{student_id}/Lecturer/{lecturer_id}")
    public  ResponseEntity<StudentDTO> addLecturerForStudent(@PathVariable Long student_id, @PathVariable Long lecturer_id)
            throws NoSuchStudentException, NoSuchLecturerException, NoSuchGroupOfStudentException, AlreadyExistsLecturerInStudentException, LecturerAbsentException {
        studentService.addLecturerForStudent(student_id,lecturer_id);
        Student students = studentService.getStudents(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudents(student_id)).withSelfRel();

        StudentDTO studentDTO = new StudentDTO(students, link);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Student/{student_id}/{lecturer_id}")
    public  ResponseEntity<StudentDTO> removeLecturerForStudent(@PathVariable Long student_id, @PathVariable Long lecturer_id)
            throws NoSuchStudentException, NoSuchLecturerException, NoSuchGroupOfStudentException, StudentHasNotLecturerException {
        studentService.removeLecturerForStudent(student_id,lecturer_id);
        Student students = studentService.getStudents(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudents(lecturer_id)).withSelfRel();

        StudentDTO studentDTO = new StudentDTO(students, link);

        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

}

