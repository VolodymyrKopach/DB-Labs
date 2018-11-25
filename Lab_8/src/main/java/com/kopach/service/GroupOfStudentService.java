package com.kopach.service;

import com.kopach.Repository.GroupOfStudentRepository;
import com.kopach.Repository.StudentRepository;
import com.kopach.domain.GroupOfStudents;
import com.kopach.exceptions.ExistsStudentsForGroupOfStudentException;
import com.kopach.exceptions.NoSuchGroupOfStudentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupOfStudentService {
    @Autowired
    GroupOfStudentRepository groupOfStudentRepository;
    private boolean ascending;

    @Autowired
    StudentRepository studentRepository;

    public List<GroupOfStudents> getAllGroupOfStudents() {
        return groupOfStudentRepository.findAll();
    }

    public GroupOfStudents getGroupOfStudent(Long student_id) throws NoSuchGroupOfStudentException {
//        Organization Organization =diagnosisRepository.findOne(diagnosis_id);//1.5.9
        GroupOfStudents groupOfStudents = groupOfStudentRepository.findById(student_id).get();//2.0.0.M7
        System.out.println(groupOfStudents);
        if (groupOfStudents == null) throw new NoSuchGroupOfStudentException();
        return groupOfStudents;
    }

    @Transactional
    public void createGroupOfStudent(GroupOfStudents cpu) {
        groupOfStudentRepository.save(cpu);
    }

    @Transactional
    public void updateGroupOfStudent(GroupOfStudents groupOfStudents, Long student_id) throws NoSuchGroupOfStudentException {
        GroupOfStudents groupOfStudents1 = groupOfStudentRepository.findById(student_id).get();//2.0.0.M7

        if (groupOfStudents1 == null) throw new NoSuchGroupOfStudentException();
        groupOfStudents1.setStudent(groupOfStudents.getStudent());
        groupOfStudentRepository.save(groupOfStudents1);
    }

    @Transactional
    public void updateGroupOfStudent(Long student_id) throws NoSuchGroupOfStudentException, ExistsStudentsForGroupOfStudentException {
        GroupOfStudents groupOfStudents = groupOfStudentRepository.findById(student_id).get();//2.0.0.M7
        if (groupOfStudents == null) throw new NoSuchGroupOfStudentException();
        if (groupOfStudents.getStudent().size() != 0) throw new ExistsStudentsForGroupOfStudentException();
        groupOfStudentRepository.delete(groupOfStudents);
    }


}
