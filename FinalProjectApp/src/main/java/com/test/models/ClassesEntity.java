package com.test.models;

import javax.persistence.*;

/**
 * Created by julieschneider on 3/14/17.
 */
@Entity
@Table(name = "Classes", schema = "finalprojectapp", catalog = "")
public class ClassesEntity {
    private String name;
    private String classId;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "classID", nullable = false, length = 40)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassesEntity that = (ClassesEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        return result;
    }
}
