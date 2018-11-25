package com.kopach.service;

import com.kopach.Repository.StudentRepository;
import com.kopach.Repository.LecturerRepository;
import com.kopach.domain.Student;
import com.kopach.domain.Lecturer;
import com.kopach.exceptions.ExistsStudentsForLecturerException;
import com.kopach.exceptions.NoSuchStudentException;
import com.kopach.exceptions.NoSuchLecturerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class LecturerService {
    @Autowired
    LecturerRepository lecturerRepository;

    @Autowired
    StudentRepository studentRepository;

    public Set<Lecturer> getLecturersByStudentId(Long Mobile_id) throws NoSuchStudentException {
        Student Mobile = studentRepository.findById(Mobile_id).get();//2.0.0.M7
        if (Mobile == null) throw new NoSuchStudentException();
        return Mobile.getLecturers();
    }

    public Lecturer getLecturer(Long Customer_id) throws NoSuchLecturerException {
        Lecturer Customer = lecturerRepository.findById(Customer_id).get();//2.0.0.M7
        if (Customer == null) throw new NoSuchLecturerException();
        return Customer;
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    @Transactional
    public void createLecturer(Lecturer lecturer) {
        lecturerRepository.save(lecturer);
    }

    @Transactional
    public void updateLecturer(Lecturer uCustomer, Long Customer_id) throws NoSuchLecturerException {
        Lecturer Customer= lecturerRepository.findById(Customer_id).get();//2.0.0.M7
        if (Customer == null) throw new NoSuchLecturerException();
        //update
        Customer.setFirstName(uCustomer.getFirstName());
    }

    @Transactional
    public void deleteLecturer(Long Customer_id) throws NoSuchLecturerException, ExistsStudentsForLecturerException {
        Lecturer lecturer = lecturerRepository.findById(Customer_id).get();//2.0.0.M7

        if (lecturer == null) throw new NoSuchLecturerException();
        if (lecturer.getMobileSet().size() != 0) throw new ExistsStudentsForLecturerException();
        lecturerRepository.delete(lecturer);
    }
}
