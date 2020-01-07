package pres.zfy.eaims.entity;

import java.sql.Timestamp;

/**
 * @Description:院系实体类
 * @Author:赵富源
 * @CreateDate:2019.12.18 0:14
 */
public class College {
    private int cId;
    private String cCode;
    private String cName;
    private Timestamp cCreateTime;
    private Timestamp cUpdTime;

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Timestamp getcCreateTime() {
        return cCreateTime;
    }

    public void setcCreateTime(Timestamp cCreateTime) {
        this.cCreateTime = cCreateTime;
    }

    public Timestamp getcUpdTime() {
        return cUpdTime;
    }

    public void setcUpdTime(Timestamp cUpdTime) {
        this.cUpdTime = cUpdTime;
    }
}
