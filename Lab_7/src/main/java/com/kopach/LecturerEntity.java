package com.kopach;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lecturer", schema = "lab_6", catalog = "")
public class LecturerEntity {
    private int lecturerId;
    private String firstName;
    private String lastName;
    private String degree;
    private List<StudentEntity> studentEntities;

    public LecturerEntity() {
    }

    public LecturerEntity(int lecturerId, String firstName, String lastName, String degree, List<StudentEntity> studentEntities) {
        this.lecturerId = lecturerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.degree = degree;
        this.studentEntities = studentEntities;
    }

    @Id
    @Column(name = "lecturer_id", nullable = false)
    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "degree", nullable = false, length = 45)
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LecturerEntity that = (LecturerEntity) o;

        if (lecturerId != that.lecturerId) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (degree != null ? !degree.equals(that.degree) : that.degree != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = lecturerId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "student_and_lecturer", catalog = "", schema = "lab_6",
            joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturer_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false))
    public List<StudentEntity> getStudentEntities() {
        return studentEntities;
    }

    public void setStudentEntities(List<StudentEntity> studentEntities) {
        this.studentEntities = studentEntities;
    }

    @Override
    public String toString() {
        return "LecturerEntity{" +
                "lecturerId=" + lecturerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", degree='" + degree + '\'' +
                ", studentEntities=" + studentEntities +
                '}';
    }
}
