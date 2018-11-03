package com.kopach;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student", schema = "lab_7_1", catalog = "")
public class StudentEntity {
    private int studentId;
    private String lastName;
    private String firstName;
    private String specialty;
    private List<LecturerEntity> lecturerEntities;
    private GroupOfStudentEntity groupOfStudentByNameOfGroup;

    public StudentEntity() {
    }

    public StudentEntity(String lastName, String firstName, String specialty, GroupOfStudentEntity groupOfStudentByNameOfGroup) {
       // this.studentId = studentId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.specialty = specialty;
        this.groupOfStudentByNameOfGroup = groupOfStudentByNameOfGroup;
    }

    @Id
    @Column(name = "student_id", nullable = false)
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 25)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 25)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "specialty", nullable = true, length = 45)
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (studentId != that.studentId) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (specialty != null ? !specialty.equals(that.specialty) : that.specialty != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "name_of_group", referencedColumnName = "name_of_group", nullable = false)
    public GroupOfStudentEntity getGroupOfStudentByNameOfGroup() {
        return groupOfStudentByNameOfGroup;
    }

    public void setGroupOfStudentByNameOfGroup(GroupOfStudentEntity groupOfStudentByNameOfGroup) {
        this.groupOfStudentByNameOfGroup = groupOfStudentByNameOfGroup;
    }

    @ManyToMany(mappedBy = "studentEntities")
    public List<LecturerEntity> getLecturerEntities() {
        return lecturerEntities;
    }

    public void addLecturerEntity(LecturerEntity lecturerEntity){
        if(!getLecturerEntities().contains(lecturerEntity)){
            getLecturerEntities().add(lecturerEntity);
        }
        if(!lecturerEntity.getStudentEntities().contains(this)){
            lecturerEntity.getStudentEntities().add(this);
        }
    }

    public void deleteLecturerEntity(LecturerEntity lecturerEntity){
        if(getLecturerEntities().contains(lecturerEntity)){
            getLecturerEntities().remove(lecturerEntity);
        }
        if(lecturerEntity.getStudentEntities().contains(this)){
            lecturerEntity.getStudentEntities().remove(this);
        }
    }

    public void setLecturerEntities(List<LecturerEntity> lecturerEntities) {
        this.lecturerEntities = lecturerEntities;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "studentId=" + studentId +
                ", lecturerId=" + lecturerEntities.get(0).getLecturerId() +
                '}';
    }
}
