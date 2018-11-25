package com.kopach.Repository;

import com.kopach.domain.GroupOfStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupOfStudentRepository extends JpaRepository<GroupOfStudents, Long> {

}
