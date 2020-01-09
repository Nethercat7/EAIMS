package pres.zfy.eaims.db;

import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
*@Description:专业与院系的关系模块
*@Author:赵富源
*@CreateDate:2020.1.5 2:14
*/
public class McRelationModule {
    /**
    *@param collegeId
    *@param majorId
    *@Author 赵富源
    *@Description 添加专业与院系的关系
    *@Return int
    */
    public static int addMcRelation(int collegeId,int majorId){
        int status = 0;
        String SQL = "INSERT INTO mc_relation(mcr_college_id,mcr_major_id) VALUES(?,?)";
        try {
            status = MySQLUtil.doDML(SQL, collegeId, majorId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param majorId
    *@Author 赵富源
    *@Description 通过专业id删除相关的院系信息
    *@Return int
    */
    public static int delMcRelation(int majorId){
        int status = 0;
        String SQL = "DELETE FROM mc_relation WHERE mcr_major_id=?";
        try {
            status = MySQLUtil.doDML(SQL, majorId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
    *@param collegeId
    *@param majorId
    *@Author 赵富源
    *@Description 更新专业与所属院系的关系
    *@Return int
    */
    public static int updMcRelation(int collegeId,int majorId){
        int status=0;
        String SQL="UPDATE mc_relation SET mcr_college_id=? WHERE mcr_major_id=?";
        try{
            status=MySQLUtil.doDML(SQL,collegeId,majorId);
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
    *@Description 获取专业与院系关系表中的id
    *@Return int
    */
    public static int queryMcrIdByMajorId(int id){
        int mcrId=0;
        String SQL="SELECT mcr_id FROM mc_relation WHERE mcr_major_id=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,id);
        try{
            if(rs.next()){
                mcrId=rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            MySQLUtil.closeAll();
        }
        return mcrId;
    }

    /**
    *@param collegeId
    *@Author 赵富源
    *@Description 查询院系下的所有专业ID
    *@Return java.util.List<java.lang.Integer>
    */
    public static List<Integer> queryAllMajorInCollege(int collegeId){
        List<Integer> idList=new ArrayList<>();
        String SQL="SELECT mcr_major_id FROM mc_relation\n" +
                "LEFT JOIN college\n" +
                "\tON mc_relation.mcr_college_id=college.c_id\n" +
                "WHERE college.c_id=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,collegeId);
        try{
            while(rs.next()){
                int id=rs.getInt(1);
                idList.add(id);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return idList;
    }
}
