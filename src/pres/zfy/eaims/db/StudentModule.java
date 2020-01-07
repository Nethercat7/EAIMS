package pres.zfy.eaims.db;

import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:学生管理数据库操作模块
 * @Author:赵富源
 * @CreateDate:2019.12.24 0:23
 */
public class StudentModule {

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过学号查询学生信息
     * @Return pres.zfy.sims.entity.Student
     */
    public static Student queryStudentByCode(String code) {
        Student student = new Student();
        String SQL = "SELECT student.*,scmc_relation.scmcr_class_id,scmc_relation.scmcr_major_id,scmc_relation.scmcr_college_id\n" +
                "FROM student\n" +
                "LEFT JOIN scmc_relation\n" +
                "\tON student.student_id=scmc_relation.scmcr_student_id\n" +
                "WHERE student_code=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code);
        try {
            if (rs.next()) {
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentCode(rs.getString("student_code"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentGender(rs.getString("student_gender"));
                student.setStudentTel(rs.getString("student_tel"));
                student.setStudentJoinedTime(rs.getTimestamp("student_joined_time"));
                student.setStudentUpdatedTime(rs.getTimestamp("student_updated_time"));
                student.setStudentRemark(rs.getString("student_remark"));
                student.setStudentGrade(rs.getString("student_grade"));
                student.setClassId(rs.getInt("scmcr_class_id"));
                student.setMajorId(rs.getInt("scmcr_major_id"));
                student.setCollegeId(rs.getInt("scmcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return student;
    }

    /**
     * @param student
     * @Author 赵富源
     * @Description 添加学生信息
     * @Return int
     */
    public static int addStudent(Student student) {
        int status = 0;
        String SQL = "INSERT INTO student(student_code,student_name,student_gender,student_tel,student_grade,student_joined_time,student_remark) VALUES(?,?,?,?,?,?,?)";
        try {
            status = MySQLUtil.insertAndReturnKeys(SQL, student.getStudentCode(), student.getStudentName(), student.getStudentGender(), student.getStudentTel(), student.getStudentGrade(), student.getStudentJoinedTime(), student.getStudentRemark());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过学号删除学生信息
     * @Return int
     */
    public static int delStudentByCode(String code) {
        int status = 0;
        String SQL = "DELETE FROM student WHERE student_code=?";
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
     * @param
     * @Author 赵富源
     * @Description 查询所有学生信息
     * @Return java.util.List<pres.zfy.sims.entity.Student>
     */
    public static List<Student> queryALLStudents() {
        List<Student> students = new ArrayList<>();
        String SQL = "SELECT student.*,scmc_relation.scmcr_class_id,scmc_relation.scmcr_major_id,scmc_relation.scmcr_college_id\n" +
                "FROM student\n" +
                "LEFT JOIN scmc_relation\n" +
                "\tON student.student_id=scmc_relation.scmcr_student_id";
        ResultSet rs = MySQLUtil.doDQL(SQL);
        try {
            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentCode(rs.getString("student_code"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentGender(rs.getString("student_gender"));
                student.setStudentTel(rs.getString("student_tel"));
                student.setStudentJoinedTime(rs.getTimestamp("student_joined_time"));
                student.setStudentUpdatedTime(rs.getTimestamp("student_updated_time"));
                student.setStudentRemark(rs.getString("student_remark"));
                student.setStudentGrade(rs.getString("student_grade"));
                student.setClassId(rs.getInt("scmcr_class_id"));
                student.setMajorId(rs.getInt("scmcr_major_id"));
                student.setCollegeId(rs.getInt("scmcr_college_id"));
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            MySQLUtil.closeAll();
        }
        return students;
    }

    /**
     * @param student
     * @Author 赵富源
     * @Description 通过id更新学生信息
     * @Return int
     */
    public static int updStudentById(Student student) {
        int status = 0;
        String SQL = "UPDATE student SET student_code=?,student_name=?,student_gender=?,student_tel=?,student_grade=?,student_updated_time=?,student_remark=? WHERE student_id=?";
        try {
            status = MySQLUtil.doDML(SQL, student.getStudentCode(), student.getStudentName(), student.getStudentGender(), student.getStudentTel(), student.getStudentGrade(), student.getStudentUpdatedTime(), student.getStudentRemark(), student.getStudentId());
        } catch (Exception e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return status;
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过学生学号查询ID；
     * @Return int
     */
    public static int queryStudentId(String code) {
        int id = 0;
        String SQL = "SELECT student_id FROM student WHERE student_code=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code);
        try {
            if (rs.next()) {
                id = rs.getInt("student_id");
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return id;
    }

    /**
     * @param tel
     * @Author 赵富源
     * @Description 根据电话号码查询学生信息
     * @Return pres.zfy.sims.entity.Student
     */
    public static Student queryStudentByTel(String tel) {
        Student student = new Student();
        String SQL = "SELECT * FROM student where student_tel=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, tel);
        try {
            if (rs.next()) {
                student.setStudentId(rs.getInt("student_id"));
                student.setStudentCode(rs.getString("student_code"));
                student.setStudentName(rs.getString("student_name"));
                student.setStudentGender(rs.getString("student_gender"));
                student.setStudentTel(rs.getString("student_tel"));
                student.setStudentJoinedTime(rs.getTimestamp("student_joined_time"));
                student.setStudentUpdatedTime(rs.getTimestamp("student_updated_time"));
                student.setStudentRemark(rs.getString("student_remark"));
                student.setStudentGrade(rs.getString("student_grade"));
                student.setClassId(rs.getInt("scmcr_class_id"));
                student.setMajorId(rs.getInt("scmcr_major_id"));
                student.setCollegeId(rs.getInt("scmcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return student;
    }

}
