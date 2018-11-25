package com.kopach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kopach.DTO.EntityInterface;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "group_of_student", schema = "lab_8")
public class GroupOfStudents implements EntityInterface {
    private Long id;
    private int numberOfGroup;

    public GroupOfStudents(Long id, int volume) {
        this.id = id;
        this.numberOfGroup = volume;
    }

    private Set<Student> studentByGroupOfStudent;

    @Id
    @Column(name = "id_of_group", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupOfStudents(Long id) {
        this.id = id;
    }

    public GroupOfStudents() {
    }

    @Basic
    @Column(name = "number_of_group", nullable = true)
    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    public void setNumberOfGroup(int numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }


    @OneToMany(
            mappedBy = "groupByGroup",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    public Set<Student> getStudent() {
        return studentByGroupOfStudent;
    }
    public void setStudent(Set<Student> studentByGroup)
    {
        this.studentByGroupOfStudent = studentByGroup;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupOfStudents that = (GroupOfStudents) o;
        return id == that.id &&
                Objects.equals(numberOfGroup, that.numberOfGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numberOfGroup);
    }
    @Override
    public String toString() {
        return "GroupOfStudents{" +
                "id=" + id +
                ", numberOfGroup='" + numberOfGroup + '\'' +
                '}';
    }

}
