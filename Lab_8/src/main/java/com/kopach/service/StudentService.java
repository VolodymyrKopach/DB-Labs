package com.kopach.service;

import com.kopach.Repository.GroupOfStudentRepository;
import com.kopach.Repository.StudentRepository;
import com.kopach.Repository.LecturerRepository;
import com.kopach.domain.GroupOfStudents;
import com.kopach.domain.Student;
import com.kopach.domain.Lecturer;
import com.kopach.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupOfStudentRepository groupOfStudentRepository;

    @Autowired
    LecturerRepository lecturerRepository;

    public Set<Student> getStudentByGroupId(Long student_id) throws NoSuchGroupOfStudentException {
        GroupOfStudents groupOfStudents = groupOfStudentRepository.findById(student_id).get();//2.0.0.M7
        if (groupOfStudents == null) throw new NoSuchGroupOfStudentException();
        return groupOfStudents.getStudent();
    }

    public Student getStudents(Long student_id) throws NoSuchStudentException {
        Student student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (student == null) throw new NoSuchStudentException();
        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Set<Student> getStudentsByLecturerId(Long lecturer_id) throws NoSuchLecturerException {
        Lecturer Customer = lecturerRepository.findById(lecturer_id).get();//2.0.0.M7
        if (Customer == null) throw new NoSuchLecturerException();
        return Customer.getMobileSet();
    }

    @Transactional
    public void createStudent(Student Mobile, Long Cpu_id) throws NoSuchGroupOfStudentException {
        if (Cpu_id > 0) {
            GroupOfStudents groupOfStudents = groupOfStudentRepository.findById(Cpu_id).get();//2.0.0.M7
            if (groupOfStudents == null) throw new NoSuchGroupOfStudentException();
            Mobile.setGroupByGroup(groupOfStudents);
        }
        studentRepository.save(Mobile);
    }

    @Transactional
    public void updateStudent(Student uStudent, Long Student_id, Long student_id) throws NoSuchGroupOfStudentException, NoSuchStudentException {
        GroupOfStudents groupOfStudents = groupOfStudentRepository.findById(student_id).get();//2.0.0.M7
        if (student_id > 0) {
            if (groupOfStudents == null) throw new NoSuchGroupOfStudentException();
        }
        Student student = studentRepository.findById(Student_id).get();//2.0.0.M7
        if (student == null) throw new NoSuchStudentException();
        //update
        student.setFirstName(uStudent.getFirstName());
        student.setLastName(uStudent.getLastName());
        if (student_id > 0) student.setGroupByGroup(groupOfStudents);
        else student.setGroupByGroup(null);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long student_id) throws NoSuchStudentException, ExistsLecturerForStudentException {
        Student student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (student == null) throw new NoSuchStudentException();
        if (student.getLecturers().size() != 0) throw new ExistsLecturerForStudentException();
        studentRepository.delete(student);
    }

    @Transactional
    public void addLecturerForStudent(Long student_id, Long lecturer_id)
            throws NoSuchStudentException, NoSuchLecturerException, AlreadyExistsLecturerInStudentException, LecturerAbsentException {
        Student student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (student == null) throw new NoSuchStudentException();
        Lecturer lecturer = lecturerRepository.findById(lecturer_id).get();//2.0.0.M7
        if (lecturer == null) throw new NoSuchLecturerException();
        if (student.getLecturers().contains(lecturer) == true) throw new AlreadyExistsLecturerInStudentException();
        student.getLecturers().add(lecturer);
        studentRepository.save(student);
    }

    @Transactional
    public void removeLecturerForStudent(Long student_id, Long lecturer_id)
            throws NoSuchStudentException, NoSuchLecturerException, StudentHasNotLecturerException {
//        Artist Artist = patientRepository.findOne(patient_id);//1.5.9
        Student student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (student == null) throw new NoSuchStudentException();
//        Project Project = medicineRepository.findOne(medicine_id);//1.5.9
        Lecturer lecturer= lecturerRepository.findById(lecturer_id).get();//2.0.0.M7
        if (lecturer == null) throw new NoSuchLecturerException();
        if (student.getLecturers().contains(lecturer) == false) throw new StudentHasNotLecturerException();
        student.getLecturers().remove(lecturer);
        studentRepository.save(student);
    }
}
