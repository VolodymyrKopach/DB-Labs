package com.kopach.domain;

import com.kopach.DTO.EntityInterface;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lecturer", schema = "lab_8")
public class Lecturer implements EntityInterface {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Student> students = new HashSet<>();
    @Id
    @Column(name = "lecturer_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @ManyToMany(targetEntity = Student.class, mappedBy="lecturers")
    public Set<Student> getMobileSet() {
        return students;
    }

    public void setMobileSet(Set<Student> mobiles) {
        this.students = mobiles;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer that = (Lecturer) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
    @Override
    public String toString(){
        return "Id= " + id + ", firstName= " + firstName
                + ", lastName= " + lastName;
    }
}
