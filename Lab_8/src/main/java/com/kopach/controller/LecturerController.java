package com.kopach.controller;

import com.kopach.DTO.impl.LecturerDTO;
import com.kopach.domain.Lecturer;
import com.kopach.exceptions.ExistsStudentsForLecturerException;
import com.kopach.exceptions.NoSuchStudentException;
import com.kopach.exceptions.NoSuchLecturerException;
import com.kopach.service.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LecturerController {
    @Autowired
    LecturerService lecturerService;

    @GetMapping(value = "/api/Lecturer/Student/{student_id}")
    public ResponseEntity<Set<LecturerDTO>> getLecturerByStudentID(@PathVariable Long student_id) throws NoSuchStudentException, NoSuchLecturerException {
        Set<Lecturer> LecturerSet = lecturerService.getLecturersByStudentId(student_id);
        Link link = linkTo(methodOn(LecturerController.class).getAllLecturers()).withSelfRel();

        Set<LecturerDTO> lecturersDTO = new HashSet<>();
        for (Lecturer entity : LecturerSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            LecturerDTO dto = new LecturerDTO(entity, selfLink);
            lecturersDTO.add(dto);
        }

        return new ResponseEntity<>(lecturersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Lecturer/{lecturer_id}")
    public ResponseEntity<LecturerDTO> getLecturer(@PathVariable Long lecturer_id) throws NoSuchLecturerException, NoSuchStudentException {
        Lecturer lecturer = lecturerService.getLecturer(lecturer_id);
        Link link = linkTo(methodOn(LecturerController.class).getLecturer(lecturer_id)).withSelfRel();

        LecturerDTO customerDTO = new LecturerDTO(lecturer, link);

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Lecturer")
    public ResponseEntity<Set<LecturerDTO>> getAllLecturers() throws NoSuchLecturerException, NoSuchStudentException {
        List<Lecturer> LecturerSet = lecturerService.getAllLecturers();
        Link link = linkTo(methodOn(LecturerController.class).getAllLecturers()).withSelfRel();

        Set<LecturerDTO> lecturerDTOS = new HashSet<>();
        for (Lecturer entity : LecturerSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            LecturerDTO dto = new LecturerDTO(entity, selfLink);
            lecturerDTOS.add(dto);
        }

        return new ResponseEntity<>(lecturerDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/api/Lecturer")
    public ResponseEntity<LecturerDTO> addLecturer(@RequestBody Lecturer newLecturer) throws NoSuchLecturerException, NoSuchStudentException {
        lecturerService.createLecturer(newLecturer);
        Link link = linkTo(methodOn(LecturerController.class).getLecturer(newLecturer.getId())).withSelfRel();

        LecturerDTO lecturerDTO = new LecturerDTO(newLecturer, link);

        return new ResponseEntity<>(lecturerDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/Lecturer/{lecturer_id}")
    public ResponseEntity<LecturerDTO> updateLecturer(@RequestBody Lecturer uCustomer, @PathVariable Long lecturer_id) throws NoSuchLecturerException, NoSuchStudentException {
        lecturerService.updateLecturer(uCustomer, lecturer_id);
        Lecturer lecturer = lecturerService.getLecturer(lecturer_id);
        Link link = linkTo(methodOn(LecturerController.class).getLecturer(lecturer_id)).withSelfRel();

        LecturerDTO lecturerDTO = new LecturerDTO(lecturer, link);

        return new ResponseEntity<>(lecturerDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Lecturer/{lecturer_id}")
    public  ResponseEntity deleteLecturer(@PathVariable Long lecturer_id) throws ExistsStudentsForLecturerException, NoSuchLecturerException {
        lecturerService.deleteLecturer(lecturer_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
