package pres.zfy.eaims.entity;

import java.sql.Timestamp;

/**
 * @Description:教师实体类
 * @Author:赵富源
 * @CreateDate:2019.12.17 0:45
 */
public class Teacher {
    private int tId;
    private String tAccount;
    private String tName;
    private String tPwd;
    private String tGender;
    private String tTel;
    private String tEmail;
    private String tRemark;
    private Timestamp tJoinedTime;
    private Timestamp tUpdateTime;

    private int collegeId;

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String gettAccount() {
        return tAccount;
    }

    public void settAccount(String tAccount) {
        this.tAccount = tAccount;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettPwd() {
        return tPwd;
    }

    public void settPwd(String tPwd) {
        this.tPwd = tPwd;
    }

    public String gettGender() {
        return tGender;
    }

    public void settGender(String tGender) {
        this.tGender = tGender;
    }

    public String gettTel() {
        return tTel;
    }

    public void settTel(String tTel) {
        this.tTel = tTel;
    }

    public String gettEmail() {
        return tEmail;
    }

    public void settEmail(String tEmail) {
        this.tEmail = tEmail;
    }

    public String gettRemark() {
        return tRemark;
    }

    public void settRemark(String tRemark) {
        this.tRemark = tRemark;
    }

    public Timestamp gettJoinedTime() {
        return tJoinedTime;
    }

    public void settJoinedTime(Timestamp tJoinedTime) {
        this.tJoinedTime = tJoinedTime;
    }

    public Timestamp gettUpdateTime() {
        return tUpdateTime;
    }

    public void settUpdateTime(Timestamp tUpdateTime) {
        this.tUpdateTime = tUpdateTime;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }
}
