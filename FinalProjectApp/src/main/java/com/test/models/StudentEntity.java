package com.test.models;

import javax.persistence.*;

/*
 * Created by Jeff Choi on 3/17/17.
 */
@Entity
@Table(name = "Student", schema = "finalprojectapp", catalog = "")
public class StudentEntity {
    private String firstName;
    private String lastName;
    private String userName;

    public StudentEntity() {}

    public StudentEntity(String firstName, String lastName, String userName, String password, String email, String testResults, String className, Double oppenness, Double emotionalRange, Double agreeableness, Double introExtro, Double conscientiousness) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.testResults = testResults;
        this.className = className;
        this.oppenness = oppenness;
        this.emotionalRange = emotionalRange;
        this.agreeableness = agreeableness;
        this.introExtro = introExtro;
        this.conscientiousness = conscientiousness;
    }

    private String password;
    private String email;
    private String testResults;
    private String className;
    private Double oppenness;
    private Double emotionalRange;
    private Double agreeableness;
    private Double introExtro;
    private Double conscientiousness;

    @Basic
    @Column(name = "firstName", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Basic
    @Column(name = "className", nullable = false, length = 5)
    public String getClassName() {
        return className;
    }

    public void setClassName (String className) {
        this.className = className;
    }

    @Basic
    @Column(name = "lastName", nullable = false, length = 60)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Id
    @Column(name = "userName", nullable = false, length = 40)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 40)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "testResults", nullable = true, length = 5)
    public String getTestResults() {
        return testResults;
    }

    public void setTestResults(String testResults) {
        this.testResults = testResults;
    }

    @Basic
    @Column(name = "oppenness", nullable = true, precision = 0)
    public Double getOppenness() {
        return oppenness;
    }

    public void setOppenness(Double oppenness) {
        this.oppenness = oppenness;
    }

    @Basic
    @Column(name = "emotionalRange", nullable = true, precision = 0)
    public Double getEmotionalRange() {
        return emotionalRange;
    }

    public void setEmotionalRange(Double emotionalRange) {
        this.emotionalRange = emotionalRange;
    }

    @Basic
    @Column(name = "agreeableness", nullable = true, precision = 0)
    public Double getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(Double agreeableness) {
        this.agreeableness = agreeableness;
    }

    @Basic
    @Column(name = "introExtro", nullable = true, precision = 0)
    public Double getIntroExtro() {
        return introExtro;
    }

    public void setIntroExtro(Double introExtro) {
        this.introExtro = introExtro;
    }

    @Basic
    @Column(name = "conscientiousness", nullable = true, precision = 0)
    public Double getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(Double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (testResults != null ? !testResults.equals(that.testResults) : that.testResults != null) return false;
        if (oppenness != null ? !oppenness.equals(that.oppenness) : that.oppenness != null) return false;
        if (emotionalRange != null ? !emotionalRange.equals(that.emotionalRange) : that.emotionalRange != null)
            return false;
        if (agreeableness != null ? !agreeableness.equals(that.agreeableness) : that.agreeableness != null)
            return false;
        if (introExtro != null ? !introExtro.equals(that.introExtro) : that.introExtro != null) return false;
        if (conscientiousness != null ? !conscientiousness.equals(that.conscientiousness) : that.conscientiousness != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (testResults != null ? testResults.hashCode() : 0);
        result = 31 * result + (oppenness != null ? oppenness.hashCode() : 0);
        result = 31 * result + (emotionalRange != null ? emotionalRange.hashCode() : 0);
        result = 31 * result + (agreeableness != null ? agreeableness.hashCode() : 0);
        result = 31 * result + (introExtro != null ? introExtro.hashCode() : 0);
        result = 31 * result + (conscientiousness != null ? conscientiousness.hashCode() : 0);
        return result;
    }
}
