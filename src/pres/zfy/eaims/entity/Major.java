package pres.zfy.eaims.entity;

import java.sql.Timestamp;

/**
 * @Description:专业实体类
 * @Author:赵富源
 * @CreateDate:2019.12.20 22:46
 */
public class Major {
    private int mId;
    private String mCode;
    private String mName;
    private Timestamp mCreateTime;
    private Timestamp mUpdTime;
    private int collegeId;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Timestamp getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(Timestamp mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public Timestamp getmUpdTime() {
        return mUpdTime;
    }

    public void setmUpdTime(Timestamp mUpdTime) {
        this.mUpdTime = mUpdTime;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(int collegeId) {
        this.collegeId = collegeId;
    }
}
