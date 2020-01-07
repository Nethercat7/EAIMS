package pres.zfy.eaims.db;

import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description:班级关系模块
 * @Author:赵富源
 * @CreateDate:2020.1.5 4:01
 */
public class CmcRelationModule {
    /**
     * @param classId
     * @param majorId
     * @param collegeId
     * @Author 赵富源
     * @Description 添加班级关系
     * @Return int
     */
    public static int addCmcRelation(int classId, int majorId, int collegeId) {
        int status = 0;
        String SQL = "INSERT INTO cmc_relation(cmcr_class_id,cmcr_major_id,cmcr_college_id) VALUES(?,?,?)";
        try {
            status = MySQLUtil.doDML(SQL, classId, majorId, collegeId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param classId
     * @Author 赵富源
     * @Description 通过班级id删除关系
     * @Return int
     */
    public static int delCmcRelation(int classId) {
        int status = 0;
        String SQL = "DELETE FROM cmc_relation WHERE cmcr_class_id=?";
        try {
            status = MySQLUtil.doDML(SQL, classId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param classId
     * @Author 赵富源
     * @Description 通过班级Id查找关系表ID
     * @Return int
     */
    public static int queryCmcrIdByClassId(int classId) {
        int cmcrId = 0;
        String SQL = "SELECT cmcr_id FROM cmc_relation WHERE cmcr_class_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, classId);
        try {
            if (rs.next()) {
                cmcrId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return cmcrId;
    }

    /**
    *@param classId
    *@param majorId
    *@param collegeId
    *@Author 赵富源
    *@Description 更新班级关系
    *@Return int
    */
    public static int updCmcRelation(int classId, int majorId, int collegeId) {
        int status = 0;
        String SQL = "UPDATE cmc_relation SET cmcr_major_id=?,cmcr_college_id=? \n" +
                "WHERE cmcr_class_id=?";
        try {
            status = MySQLUtil.doDML(SQL, majorId, collegeId, classId);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }
        return status;
    }




}
