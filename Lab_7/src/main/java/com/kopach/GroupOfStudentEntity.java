package com.kopach;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "group_of_student", schema = "lab_6", catalog = "")
public class GroupOfStudentEntity {
    private String nameOfGroup;

    public GroupOfStudentEntity() {
    }

    public GroupOfStudentEntity(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    @Id
    @Column(name = "name_of_group", nullable = false, length = 25)
    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupOfStudentEntity that = (GroupOfStudentEntity) o;

        if (nameOfGroup != null ? !nameOfGroup.equals(that.nameOfGroup) : that.nameOfGroup != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nameOfGroup != null ? nameOfGroup.hashCode() : 0;
    }
}
