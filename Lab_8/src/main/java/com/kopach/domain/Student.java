package com.kopach.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kopach.DTO.EntityInterface;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student", schema = "lab_8")
public class Student implements EntityInterface {
    private Long id;
    private String firstName;
    private String lastName;
    private GroupOfStudents groupByGroup;
    Set<Lecturer> lecturers = new HashSet<>();


    public Student(String catery, String mark, GroupOfStudents cpuByCpu) {
        this.firstName = catery;
        this.lastName = mark;
        this.groupByGroup = cpuByCpu;
    }

    public Student(Long id, String catery, String mark) {
        this.id = id;
        this.firstName = catery;
        this.lastName = mark;
    }

    public Student() {
    }


    @Id
    @Column(name = "student_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 50)
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

    @ManyToOne
    @JoinColumn(name = "group_of_student_id", referencedColumnName = "id_of_group", nullable = false)
    @JsonIgnore
    public GroupOfStudents getGroupByGroup() {
        return groupByGroup;
    }

    public void setGroupByGroup(GroupOfStudents groupByGroup) {
        this.groupByGroup = groupByGroup;
    }


    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "student_lecturer",
            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "lecturer_id", referencedColumnName = "lecturer_id", nullable = false), }
    )
    @JsonIgnore
    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void addStudentLecturer(Lecturer customerEntity){
        if(!getLecturers().contains(customerEntity)){
            getLecturers().add(customerEntity);
        }
        if(!customerEntity.getMobileSet().contains(this)){
            customerEntity.getMobileSet().add(this);
        }
    }

    public void deleteCustomerEntity(Lecturer customer){
        if(getLecturers().contains(customer)){
            getLecturers().remove(customer);
        }
        if(customer.getMobileSet().contains(this)){
            customer.getMobileSet().remove(this);
        }
    }



    @Override
    public String toString() {
        return "MobileEntity{" +
                "id=" + id +
                ", category='" + firstName + '\'' +
                ", mark1='" + lastName + '\'' +
                ", lecturers=" + lecturers +
                '}';
    }

    public String toStringJoinTable(){
        return "MobileEntity{" +
                "id=" + id +
                " lecturers=" + lecturers +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student that = (Student) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);}


    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }



}
