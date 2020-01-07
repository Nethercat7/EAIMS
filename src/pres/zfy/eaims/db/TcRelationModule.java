package pres.zfy.eaims.db;

import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:教师和院系关系模块
 * @Author:赵富源
 * @CreateDate:2020.1.4 19:21
 */
public class TcRelationModule {

    /**
     * @param collegeId
     * @param teacherId
     * @Author 赵富源
     * @Description 添加关系
     * @Return int
     */
    public static int addTcRelation(int collegeId, int teacherId) {
        int status = 0;
        String SQL = "INSERT INTO tc_relation(tcr_college_id,tcr_teacher_id) VALUES(?,?)";
        try {
            status = MySQLUtil.doDML(SQL, collegeId, teacherId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param teacherId
     * @Author 赵富源
     * @Description 通过教师ID删除所属关系
     * @Return int
     */
    public static int delTcRelation(int teacherId) {
        int status = 0;
        String SQL = "DELETE FROM tc_relation WHERE tcr_teacher_id=?";
        try {
            status = MySQLUtil.doDML(SQL, teacherId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param collegeId
    *@param teacherId
    *@Author 赵富源
    *@Description 更新教师与院系的关系
    *@Return int
    */
    public static int updTcRelation(int collegeId,int teacherId,int tcr_id){
        int status=0;
        String SQL="UPDATE tc_relation SET tcr_college_id=?,tcr_teacher_id=? WHERE tcr_id=?";
        try{
            status=MySQLUtil.doDML(SQL,collegeId,teacherId,tcr_id);
        }catch (Exception e){
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param id
    *@Author 赵富源
    *@Description 获取教师与院系关系表中的id
    *@Return int
    */
    public static int queryTcrIdByTeacherId(int id){
        int tcrId=0;
        String SQL="SELECT tcr_id FROM tc_relation WHERE tcr_teacher_id=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,id);
        try{
            if(rs.next()){
                tcrId=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MySQLUtil.closeAll();
        }
        return tcrId;
    }
}
