package com.test.models;

import javax.persistence.*;

/*
 * Created by Jeff Choi on 3/15/17.
 */
@Entity
@Table(name = "Classes", schema = "finalprojectapp", catalog = "")
public class ClassesEntity {
    private String name;
    private String classId;
    private String schoolName;
    private String teacherUser;
    private TeacherEntity theTeacher;

    @Basic
    @Column(name = "name", nullable = true, length = 100)
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

    @Basic
    @Column(name = "schoolName", nullable = true, length = 60)
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Basic
    @Column(name = "teacherUser", nullable = true, length = 40)
    public String getTeacherUser() {
        return teacherUser;
    }

    public void setTeacherUser(String teacherUser) {
        this.teacherUser = teacherUser;
    }

    @ManyToOne
    @JoinColumn(name = "teacherUser")
    public TeacherEntity getTeacher() {
        return theTeacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.theTeacher = teacher;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassesEntity that = (ClassesEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;
        if (schoolName != null ? !schoolName.equals(that.schoolName) : that.schoolName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        return result;
    }
}
