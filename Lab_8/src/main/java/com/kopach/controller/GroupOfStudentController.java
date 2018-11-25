package com.kopach.controller;

import com.kopach.DTO.impl.GroupOfStudentDTO;
import com.kopach.domain.GroupOfStudents;

import com.kopach.exceptions.*;
import com.kopach.service.GroupOfStudentService;
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
public class GroupOfStudentController {
    @Autowired
    GroupOfStudentService groupOfStudentService;

    @GetMapping(value = "/api/Group_of_student")
    public ResponseEntity<Set<GroupOfStudentDTO>> getAllGroupOfStudents() throws NoSuchStudentException, NoSuchLecturerException, NoSuchGroupOfStudentException {
        List<GroupOfStudents> GroupOfStudentSet = groupOfStudentService.getAllGroupOfStudents();
        Link link = linkTo(methodOn(GroupOfStudentController.class).getAllGroupOfStudents()).withSelfRel();

        Set<GroupOfStudentDTO> groupOfStudentDTOS = new HashSet<>();
        for (GroupOfStudents entity : GroupOfStudentSet) {
            Link selfLink = new Link(link.getHref() + "/" + entity.getId()).withSelfRel();
            GroupOfStudentDTO dto = new GroupOfStudentDTO(entity, selfLink);
            groupOfStudentDTOS.add(dto);
        }

        return new ResponseEntity<>(groupOfStudentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/api/Group_of_student/{group_id}")
    public ResponseEntity<GroupOfStudentDTO> getGroupOfStudent(@PathVariable Long group_id) throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        GroupOfStudents groupOfStudent = groupOfStudentService.getGroupOfStudent(group_id);
        Link link = linkTo(methodOn(GroupOfStudentController.class).getGroupOfStudent(group_id)).withSelfRel();
        System.out.println(groupOfStudent);
        GroupOfStudentDTO cpuDTO = new GroupOfStudentDTO(groupOfStudent, link);

        return new ResponseEntity<>(cpuDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/Group_of_student/{group_id}")
    public  ResponseEntity<GroupOfStudentDTO> addGroupOfStudent(@RequestBody GroupOfStudents groupOfStudent) throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        groupOfStudentService.createGroupOfStudent(groupOfStudent);
        Link link = linkTo(methodOn(GroupOfStudentController.class).getGroupOfStudent(groupOfStudent.getId())).withSelfRel();

        GroupOfStudentDTO groupOfStudentDTO = new GroupOfStudentDTO(groupOfStudent, link);

        return new ResponseEntity<>(groupOfStudentDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/Group_id/{group_id}")
    public  ResponseEntity<GroupOfStudentDTO> updateGroupOfStudent(@RequestBody GroupOfStudents uGroupOfStudent, @PathVariable Long group_id) throws NoSuchGroupOfStudentException, NoSuchStudentException, NoSuchLecturerException {
        groupOfStudentService.updateGroupOfStudent(uGroupOfStudent, group_id);
        GroupOfStudents groupOfStudent = groupOfStudentService.getGroupOfStudent(group_id);
        Link link = linkTo(methodOn(GroupOfStudentController.class).getGroupOfStudent(group_id)).withSelfRel();

        GroupOfStudentDTO groupOfStudentDTO = new GroupOfStudentDTO(groupOfStudent, link);

        return new ResponseEntity<>(groupOfStudentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/Group_of_student/{group_id}")
    public  ResponseEntity deleteGroupOfStudent(@PathVariable Long group_id) throws NoSuchGroupOfStudentException, ExistsStudentsForLecturerException, ExistsStudentsForGroupOfStudentException {
        groupOfStudentService.updateGroupOfStudent(group_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
