package pres.zfy.eaims.db;

import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:教师模块数据操作
 * @Author:赵富源
 * @CreateDate:2019.12.16 12:11
 */
public class TeacherModule {
    /**
     * @param id
     * @Author 赵富源
     * @Description 通过id查询教师名字
     * @Return java.lang.String
     */
    public static String queryTeacherNameById(int id) {
        String name = null;
        String SQL = "SELECT t_name FROM teacher WHERE t_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, id);
        try {
            if (rs.next()) {
                name = rs.getString("t_name");
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return name;
    }

    /**
     * @param teacher
     * @Author 赵富源
     * @Description 添加教师
     * @Return int
     */
    public static int addTeacher(Teacher teacher) {
        int keys = 0;
        try {
            String SQL = "INSERT INTO teacher(t_account,t_name,t_password,t_gender,t_tel,t_email,t_remark,t_joined_time) VALUES(?,?,?,?,?,?,?,?)";
            keys = MySQLUtil.insertAndReturnKeys(SQL, teacher.gettAccount(), teacher.gettName(), teacher.gettPwd(), teacher.gettGender(), teacher.gettTel(), teacher.gettEmail(), teacher.gettRemark(), teacher.gettJoinedTime());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return keys;
    }

    /**
     * @param account
     * @Author 赵富源
     * @Description 根据工号查询教师
     * @Return pres.zfy.sims.entity.Teacher
     */
    public static Teacher queryTeacherByAccount(String account) {
        Teacher teacher = new Teacher();
        String SQL = "SELECT teacher.*,tc_relation.tcr_college_id FROM teacher\n" +
                "LEFT JOIN tc_relation \n" +
                "\tON teacher.t_id=tc_relation.tcr_teacher_id\n" +
                "WHERE t_account=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, account);
        try {
            if (rs.next()) {
                teacher.settId(rs.getInt("t_id"));
                teacher.settAccount(rs.getString("t_account"));
                teacher.settName(rs.getString("t_name"));
                teacher.settPwd(rs.getString("t_password"));
                teacher.settGender(rs.getString("t_gender"));
                teacher.settTel(rs.getString("t_tel"));
                teacher.settEmail(rs.getString("t_email"));
                teacher.settRemark(rs.getString("t_remark"));
                teacher.setCollegeId(rs.getInt("tcr_college_id"));
                teacher.settJoinedTime(rs.getTimestamp("t_joined_time"));
                teacher.settUpdateTime(rs.getTimestamp("t_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return teacher;
    }

    /**
     * @param tel
     * @Author 赵富源
     * @Description 根据电话号码查询教师
     * @Return pres.zfy.sims.entity.Teacher
     */
    public static Teacher queryTeacherByTel(String tel) {
        Teacher teacher = new Teacher();
        String SQL = "SELECT * FROM teacher WHERE t_tel=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, tel);
        try {
            if (rs.next()) {
                teacher.settId(rs.getInt("t_id"));
                teacher.settAccount(rs.getString("t_account"));
                teacher.settName(rs.getString("t_name"));
                teacher.settPwd(rs.getString("t_password"));
                teacher.settGender(rs.getString("t_gender"));
                teacher.settTel(rs.getString("t_tel"));
                teacher.settEmail(rs.getString("t_email"));
                teacher.settRemark(rs.getString("t_remark"));
                teacher.setCollegeId(rs.getInt("tcr_college_id"));
                teacher.settJoinedTime(rs.getTimestamp("t_joined_time"));
                teacher.settUpdateTime(rs.getTimestamp("t_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return teacher;
    }

    /**
     * @param email
     * @Author 赵富源
     * @Description 根据电子邮箱查询教师
     * @Return pres.zfy.sims.entity.Teacher
     */
    public static Teacher queryTeacherByEmail(String email) {
        Teacher teacher = new Teacher();
        String SQL = "SELECT * FROM teacher WHERE t_email=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, email);
        try {
            if (rs.next()) {
                teacher.settId(rs.getInt("t_id"));
                teacher.settAccount(rs.getString("t_account"));
                teacher.settName(rs.getString("t_name"));
                teacher.settPwd(rs.getString("t_password"));
                teacher.settGender(rs.getString("t_gender"));
                teacher.settTel(rs.getString("t_tel"));
                teacher.settEmail(rs.getString("t_email"));
                teacher.settRemark(rs.getString("t_remark"));
                teacher.setCollegeId(rs.getInt("tcr_college_id"));
                teacher.settJoinedTime(rs.getTimestamp("t_joined_time"));
                teacher.settUpdateTime(rs.getTimestamp("t_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return teacher;
    }

    /**
     * @param
     * @Author 赵富源
     * @Description 查询所有教师
     * @Return java.util.List<pres.zfy.sims.entity.Teacher>
     */
    public static List<Teacher> queryAllTeacher() {
        List<Teacher> teachers = new ArrayList<>();
        String SQL = "SELECT teacher.*,tc_relation.tcr_college_id FROM teacher\n" +
                "LEFT JOIN tc_relation \n" +
                "\tON teacher.t_id=tc_relation.tcr_teacher_id";
        ResultSet rs = MySQLUtil.doDQL(SQL);
        try {
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.settId(rs.getInt("t_id"));
                teacher.settAccount(rs.getString("t_account"));
                teacher.settName(rs.getString("t_name"));
                teacher.settPwd(rs.getString("t_password"));
                teacher.settGender(rs.getString("t_gender"));
                teacher.settTel(rs.getString("t_tel"));
                teacher.settEmail(rs.getString("t_email"));
                teacher.settRemark(rs.getString("t_remark"));
                teacher.setCollegeId(rs.getInt("tcr_college_id"));
                teacher.settJoinedTime(rs.getTimestamp("t_joined_time"));
                teacher.settUpdateTime(rs.getTimestamp("t_update_time"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return teachers;
    }

    /**
     * @param account
     * @Author 赵富源
     * @Description 根据工号删除教师
     * @Return int
     */
    public static int delTeacherByAccount(String account) {
        int status = 0;
        String SQL = "DELETE FROM teacher WHERE t_account=?";
        try {
            status = MySQLUtil.doDML(SQL, account);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param teacher
     * @Author 赵富源
     * @Description 通过工号更新教师
     * @Return int
     */
    public static int updTeacherByAccount(Teacher teacher) {
        int status = 0;
        try {
            String SQL = "UPDATE teacher SET t_name=?,t_gender=?,t_tel=?,t_email=?,t_remark=?,t_update_time=? WHERE t_account=?";
            status = MySQLUtil.doDML(SQL, teacher.gettName(), teacher.gettGender(), teacher.gettTel(), teacher.gettEmail(), teacher.gettRemark(), teacher.gettUpdateTime(), teacher.gettAccount());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param pwd
     * @param id
     * @Author 赵富源
     * @Description 通过ID修改密码
     * @Return int
     */
    public static int updPasswordById(String pwd, int id) {
        int status = 0;
        String SQL = "UPDATE teacher SET t_password=? WHERE t_id=?";
        try {
            status = MySQLUtil.doDML(SQL, pwd, id);
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param id
     * @Author 赵富源
     * @Description 通过ID查询教师信息
     * @Return pres.zfy.sims.entity.Teacher
     */
    public static Teacher queryTeacherById(int id) {
        Teacher teacher = new Teacher();
        String SQL = "SELECT * FROM teacher WHERE t_id=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, id);
        try {
            if (rs.next()) {
                teacher.settId(rs.getInt("t_id"));
                teacher.settAccount(rs.getString("t_account"));
                teacher.settName(rs.getString("t_name"));
                teacher.settPwd(rs.getString("t_password"));
                teacher.settGender(rs.getString("t_gender"));
                teacher.settTel(rs.getString("t_tel"));
                teacher.settEmail(rs.getString("t_email"));
                teacher.settRemark(rs.getString("t_remark"));
                teacher.setCollegeId(rs.getInt("tcr_college_id"));
                teacher.settJoinedTime(rs.getTimestamp("t_joined_time"));
                teacher.settUpdateTime(rs.getTimestamp("t_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return teacher;
    }

    /**
    *@param
    *@Author 赵富源
    *@Description 查询最大职工号
    *@Return java.lang.String
    */
    public static String queryMaxAccount(){
        String account="";
        String SQL="SELECT MAX(t_account) FROM teacher";
        ResultSet rs= MySQLUtil.doDQL(SQL);
        try{
            if(rs.next()){
                account=rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return account;
    }
}
