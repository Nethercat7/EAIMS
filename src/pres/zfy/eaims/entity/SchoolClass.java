package pres.zfy.eaims.entity;

import java.sql.Timestamp;

/**
 * @Description:班级实体类
 * @Author:赵富源
 * @CreateDate:2019.12.23 1:14
 */
public class SchoolClass {
    private int classId;
    private String classCode;
    private String classGrade;
    private String className;
    private Timestamp classCreateTime;
    private Timestamp classUpdateTime;

    //cmc_relation
    private int majorId;
    private int collegeId;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Timestamp getClassCreateTime() {
        return classCreateTime;
    }

    public void setClassCreateTime(Timestamp classCreateTime) {
        this.classCreateTime = classCreateTime;
    }

    public Timestamp getClassUpdateTime() {
        return classUpdateTime;
    }

    public void setClassUpdateTime(Timestamp classUpdateTime) {
        this.classUpdateTime = classUpdateTime;
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
