package com.test.models;

import javax.persistence.*;

/*
 * Created by Jeff Choi on 3/17/17.
 */
@Entity
@Table(name = "Student_Classes", schema = "finalprojectapp", catalog = "")
@IdClass(StudentClassesEntityPK.class)
public class StudentClassesEntity {
    private String studentId;
    private String classId;
    private String groupId;

    @Id
    @Column(name = "studentID", nullable = false, length = 40)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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
    @Column(name = "groupID", nullable = true, length = 40)
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentClassesEntity that = (StudentClassesEntity) o;

        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (classId != null ? classId.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        return result;
    }
}
