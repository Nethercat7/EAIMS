package pres.zfy.eaims.db;

import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:专业模块数据操作
 * @Author:赵富源
 * @CreateDate:2019.12.20 22:50
 */
public class MajorModule {
    /**
     * @param code
     * @Author 赵富源
     * @Description 通过专业编号或名称查询专业
     * @Return pres.zfy.sims.entity.College
     */
    public static Major queryMajorByCodeOrName(String code, String name) {
        Major major = new Major();
        String SQL = "SELECT major.*,mc_relation.mcr_college_id FROM major\n" +
                "LEFT JOIN mc_relation \n" +
                "\tON major.m_id=mc_relation.mcr_major_id\n" +
                "WHERE m_code=? OR m_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code, name);
        try {
            if (rs.next()) {
                major.setmId(rs.getInt("m_id"));
                major.setmCode(rs.getString("m_code"));
                major.setmName(rs.getString("m_name"));
                major.setmCreateTime(rs.getTimestamp("m_create_time"));
                major.setmUpdTime(rs.getTimestamp("m_update_time"));
                major.setCollegeId(rs.getInt("mcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return major;
    }

    /**
     * @param major
     * @Author 赵富源
     * @Description 创建专业
     * @Return int
     */
    public static int addMajor(Major major) {
        int status = 0;
        String SQL = "INSERT INTO major(m_code,m_name,m_create_time) VALUES(?,?,?)";
        try {
            status = MySQLUtil.insertAndReturnKeys(SQL, major.getmCode(), major.getmName(), major.getmCreateTime());
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
     * @Description 查询所有专业
     * @Return java.util.List<pres.zfy.sims.entity.Major>
     */
    public static List<Major> queryAllMajors() {
        List<Major> majors = new ArrayList<>();
        String SQL = "SELECT major.*,mc_relation.mcr_college_id FROM major\n" +
                "LEFT JOIN mc_relation \n" +
                "\tON major.m_id=mc_relation.mcr_major_id";
        ResultSet rs = MySQLUtil.doDQL(SQL);
        try {
            while (rs.next()) {
                Major major = new Major();
                major.setmId(rs.getInt("m_id"));
                major.setmCode(rs.getString("m_code"));
                major.setmName(rs.getString("m_name"));
                major.setmCreateTime(rs.getTimestamp("m_create_time"));
                major.setmUpdTime(rs.getTimestamp("m_update_time"));
                major.setCollegeId(rs.getInt("mcr_college_id"));
                majors.add(major);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return majors;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过专业编号查询专业
     * @Return pres.zfy.sims.entity.Major
     */
    public static Major queryMajorByCode(String code) {
        Major major = new Major();
        String SQL = "SELECT major.*,mc_relation.mcr_college_id FROM major\n" +
                "LEFT JOIN mc_relation \n" +
                "\tON major.m_id=mc_relation.mcr_major_id\n" +
                "WHERE m_code=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code);
        try {
            if (rs.next()) {
                major.setmId(rs.getInt("m_id"));
                major.setmCode(rs.getString("m_code"));
                major.setmName(rs.getString("m_name"));
                major.setmCreateTime(rs.getTimestamp("m_create_time"));
                major.setmUpdTime(rs.getTimestamp("m_update_time"));
                major.setCollegeId(rs.getInt("mcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return major;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过专业编号删除专业
     * @Return int
     */
    public static int delMajorByCode(String code) {
        int status = 0;
        String SQL = "DELETE FROM major WHERE m_code=?";
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
     * @param major
     * @Author 赵富源
     * @Description 根据专业编号修改专业信息
     * @Return int
     */
    public static int updMajorByCode(Major major) {
        int status = 0;
        String SQL = "UPDATE major SET m_name=?,m_update_time=? WHERE m_code=?";
        try {
            status = MySQLUtil.doDML(SQL, major.getmName(), major.getmUpdTime(), major.getmCode());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param major
     * @Author 赵富源
     * @Description 根据专业ID修改专业信息
     * @Return int
     */
    public static int updMajorById(Major major) {
        int status = 0;
        String SQL = "UPDATE major SET m_code=?,m_name=?,m_update_time=? WHERE m_id=?";
        try {
            status = MySQLUtil.doDML(SQL, major.getmCode(), major.getmName(), major.getmUpdTime(), major.getmId());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param collegeId
     * @Author 赵富源
     * @Description 查询院系下的专业
     * @Return java.util.List<pres.zfy.sims.entity.Major>
     */
    public static List<Major> queryMajorsInCollege(int collegeId) {
        List<Major> majors = new ArrayList<>();
        String SQL = "SELECT major.* FROM major\n" +
                "LEFT JOIN mc_relation\n" +
                " ON major.m_id=mc_relation.mcr_major_id\n" +
                "WHERE mc_relation.mcr_college_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, collegeId);
        try {
            while (rs.next()) {
                Major major = new Major();
                major.setmId(rs.getInt("m_id"));
                major.setmCode(rs.getString("m_code"));
                major.setmName(rs.getString("m_name"));
                major.setmCreateTime(rs.getTimestamp("m_create_time"));
                major.setmUpdTime(rs.getTimestamp("m_update_time"));
                majors.add(major);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return majors;
    }

    /**
     * @param name
     * @Author 赵富源
     * @Description 通过专业名称查询id
     * @Return int
     */
    public static int queryMajorIdByName(String name) {
        int majorId = 0;
        String SQL = "SELECT m_id FROM major WHERE m_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, name);
        try {
            if (rs.next()) {
                majorId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQLUtil.closeAll();
        }
        return majorId;
    }

    /**
    *@param id
    *@Author 赵富源
    *@Description 通过专业ID查询名称
    *@Return java.lang.String
    */
    public static String queryMajorNameById(int id) {
        String name = "无";
        String SQL = "SELECT m_name FROM major WHERE m_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, id);
        try {
            if (rs.next()) {
                name = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQLUtil.closeAll();
        }
        return name;
    }

    /**
    *@param name
    *@Author 赵富源
    *@Description 通过专业名称查询专业
    *@Return pres.zfy.sims.entity.Major
    */
    public static Major queryMajorByName(String name) {
        Major major = new Major();
        String SQL = "SELECT major.*,mc_relation.mcr_college_id FROM major\n" +
                "LEFT JOIN mc_relation \n" +
                "\tON major.m_id=mc_relation.mcr_major_id\n" +
                "WHERE m_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, name);
        try {
            if (rs.next()) {
                major.setmId(rs.getInt("m_id"));
                major.setmCode(rs.getString("m_code"));
                major.setmName(rs.getString("m_name"));
                major.setmCreateTime(rs.getTimestamp("m_create_time"));
                major.setmUpdTime(rs.getTimestamp("m_update_time"));
                major.setCollegeId(rs.getInt("mcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return major;
    }
}
