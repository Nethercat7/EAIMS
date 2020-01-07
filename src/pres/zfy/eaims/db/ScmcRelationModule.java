package pres.zfy.eaims.db;

import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
*@Description:学生关系模块
*@Author:赵富源
*@CreateDate:2020.1.6 22:39
*/
public class ScmcRelationModule {
    /**
    *@param scmcr_student_id
    *@param scmcr_class_id
    *@param scmcr_major_id
    *@param scmcr_college_id
    *@Author 赵富源
    *@Description 添加学生关系
    *@Return int
    */
    public static int addScmcRelation(int scmcr_student_id,int scmcr_class_id,int scmcr_major_id,int scmcr_college_id){
        int status=0;
        String SQL="INSERT INTO scmc_relation(scmcr_student_id,scmcr_class_id,scmcr_major_id,scmcr_college_id) VALUES(?,?,?,?)";
        try{
            status= MySQLUtil.doDML(SQL,scmcr_student_id,scmcr_class_id,scmcr_major_id,scmcr_college_id);
        }catch (Exception e){
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param scmcr_student_id
    *@Author 赵富源
    *@Description 删除学生关系
    *@Return int
    */
    public static int delScmcRelation(int scmcr_student_id){
        int status=0;
        String SQL="DELETE FROM scmc_relation WHERE scmcr_student_id=?";
        try{
            status=MySQLUtil.doDML(SQL,scmcr_student_id);
        }catch (Exception e){
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param scmcr_student_id
    *@param scmcr_class_id
    *@param scmcr_major_id
    *@param scmcr_college_id
    *@Author 赵富源
    *@Description 更新学生关系
    *@Return int
    */
    public static int updScmcRelation(int scmcr_student_id,int scmcr_class_id,int scmcr_major_id,int scmcr_college_id){
        int status=0;
        String SQL="UPDATE scmc_relation SET scmcr_class_id=?,scmcr_major_id=?,scmcr_college_id=? where scmcr_student_id=?";
        try{
            status=MySQLUtil.doDML(SQL,scmcr_class_id,scmcr_major_id,scmcr_college_id,scmcr_student_id);
        }catch (Exception e){
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param scmcr_student_id
    *@Author 赵富源
    *@Description 查询ID
    *@Return int
    */
    public static int queryScmcrId(int scmcr_student_id){
        int scmcrId=0;
        String SQL="SELECT scmcr_id FROM scmc_relation WHERE scmcr_student_id=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,scmcr_student_id);
        try{
            if(rs.next()){
                scmcrId=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return scmcrId;
    }
}
