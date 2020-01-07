package pres.zfy.eaims.entity;

import java.sql.Timestamp;

/**
 * @Description:学生实体类
 * @Author:赵富源
 * @CreateDate:2019.12.23 23:31
 */
public class Student {
    private int studentId;
    private String studentCode;
    private String studentName;
    private String studentGender;
    private String studentTel;
    private Timestamp studentJoinedTime;
    private Timestamp studentUpdatedTime;
    private String studentRemark;
    private String studentGrade;

    private int classId;
    private int majorId;
    private int collegeId;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentTel() {
        return studentTel;
    }

    public void setStudentTel(String studentTel) {
        this.studentTel = studentTel;
    }

    public Timestamp getStudentJoinedTime() {
        return studentJoinedTime;
    }

    public void setStudentJoinedTime(Timestamp studentJoinedTime) {
        this.studentJoinedTime = studentJoinedTime;
    }

    public Timestamp getStudentUpdatedTime() {
        return studentUpdatedTime;
    }

    public void setStudentUpdatedTime(Timestamp studentUpdatedTime) {
        this.studentUpdatedTime = studentUpdatedTime;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(String studentGrade) {
        this.studentGrade = studentGrade;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getMajorId() {
        return majorId;
    }

    public void setMajorId(int majorId) {
        this.majorId = majorId;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }

}

