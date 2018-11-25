package com.kopach.DTO.impl;

import com.kopach.DTO.DTO;
import com.kopach.domain.Lecturer;
import com.kopach.exceptions.NoSuchLecturerException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class LecturerDTO extends DTO<Lecturer> {
    public LecturerDTO(Lecturer customer, Link link) throws NoSuchLecturerException {
        super(customer, link);
    }

    public Long getLecturerId() {
        return getEntity().getId();
    }

    public String getFirstName() {
        return getEntity().getFirstName();
    }

    public String getLastName() {
        return getEntity().getLastName();
    }
}
