package pres.zfy.eaims.db;

import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:院系管理模块数据操作
 * @Author:赵富源
 * @CreateDate:2019.12.18 23:43
 */
public class CollegeModule {
    /**
     * @param code
     * @Author 赵富源
     * @Description 通过院系编号或名称查询院系
     * @Return pres.zfy.sims.entity.College
     */
    public static College queryCollegeByCodeOrName(String code, String name) {
        College college = new College();
        String SQL = "SELECT * FROM college WHERE c_code=? OR c_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code, name);
        try {
            if (rs.next()) {
                college.setcId(rs.getInt("c_id"));
                college.setcCode(rs.getString("c_code"));
                college.setcName(rs.getString("c_name"));
                college.setcCreateTime(rs.getTimestamp("c_create_time"));
                college.setcUpdTime(rs.getTimestamp("c_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return college;
    }

    /**
     * @param code
     * @param name
     * @param createTime
     * @Author 赵富源
     * @Description 创建院系
     * @Return int
     */
    public static int addCollege(String code, String name, Timestamp createTime) {
        int status = 0;
        String SQL = "INSERT INTO college(c_code,c_name,c_create_time) VALUES(?,?,?)";
        try {
            status = MySQLUtil.doDML(SQL, code, name, createTime);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param
     * @Author 赵富源
     * @Description 查询所有院系
     * @Return java.util.List<pres.zfy.sims.entity.College>
     */
    public static List<College> queryAllColleges() {
        List<College> colleges = new ArrayList<>();
        String SQL = "SELECT * FROM college";
        ResultSet rs = MySQLUtil.doDQL(SQL);
        try {
            while (rs.next()) {
                College college = new College();
                college.setcId(rs.getInt("c_id"));
                college.setcCode(rs.getString("c_code"));
                college.setcName(rs.getString("c_name"));
                college.setcCreateTime(rs.getTimestamp("c_create_time"));
                college.setcUpdTime(rs.getTimestamp("c_update_time"));
                colleges.add(college);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return colleges;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过院系编号查询院系
     * @Return pres.zfy.sims.entity.College
     */
    public static College queryCollegeByCode(String code) {
        College college = new College();
        String SQL = "SELECT * FROM college WHERE c_code=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code);
        try {
            if (rs.next()) {
                college.setcId(rs.getInt("c_id"));
                college.setcCode(rs.getString("c_code"));
                college.setcName(rs.getString("c_name"));
                college.setcCreateTime(rs.getTimestamp("c_create_time"));
                college.setcUpdTime(rs.getTimestamp("c_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return college;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过院系编号删除院系
     * @Return int
     */
    public static int delCollegeByCode(String code) {
        int status = 0;
        String SQL = "DELETE FROM college WHERE c_code=?";
        try {
            status = MySQLUtil.doDML(SQL, code);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param college
     * @Author 赵富源
     * @Description 通过院系编号更新院系
     * @Return int
     */
    public static int updCollegeByCode(College college) {
        int status = 0;
        String SQL = "UPDATE college SET c_name=?,c_update_time=? WHERE c_code=?";
        try {
            status = MySQLUtil.doDML(SQL, college.getcName(), college.getcUpdTime(), college.getcCode());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param college
     * @Author 赵富源
     * @Description 通过ID修改院系信息
     * @Return int
     */
    public static int updCollegeById(College college) {
        int status = 0;
        String SQL = "UPDATE college SET c_code=?,c_name=?,c_update_time=? WHERE c_id=?";
        try {
            status = MySQLUtil.doDML(SQL, college.getcCode(), college.getcName(), college.getcUpdTime(), college.getcId());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param name
     * @Author 赵富源
     * @Description 通过院系名称查询院系ID
     * @Return int
     */
    public static int queryCollegeIdByName(String name) {
        int id = 0;
        String SQL = "SELECT c_id FROM college WHERE c_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, name);
        try {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }
        return id;
    }

    /**
     * @param id
     * @Author 赵富源
     * @Description 通过院系ID查询院系名字
     * @Return java.lang.String
     */
    public static String queryCollegeNameById(int id) {
        String name = "无";
        String SQL = "SELECT c_name FROM college WHERE c_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, id);
        try {
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }
        return name;
    }
}
