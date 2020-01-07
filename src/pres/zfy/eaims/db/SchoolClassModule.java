package pres.zfy.eaims.db;

import pres.zfy.eaims.entity.SchoolClass;
import pres.zfy.eaims.utils.MySQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:班级数据库操作模块
 * @Author:赵富源
 * @CreateDate:2019.12.23 1:16
 */
public class SchoolClassModule {

    /**
     * @param schoolClass
     * @Author 赵富源
     * @Description 添加班级
     * @Return int
     */
    public static int addSchoolClass(SchoolClass schoolClass) {
        int status = 0;
        String SQL = "INSERT INTO class(class_code,class_grade,class_name,class_create_time) VALUES(?,?,?,?)";
        try {
            status = MySQLUtil.insertAndReturnKeys(SQL, schoolClass.getClassCode(), schoolClass.getClassGrade(), schoolClass.getClassName(), schoolClass.getClassCreateTime());
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
     * @Description 通过班级编号删除班级
     * @Return int
     */
    public static int delSchoolClassByClassCode(String code) {
        int status = 0;
        String SQL = "DELETE FROM class WHERE class_code=?";
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
     * @param code
     * @Author 赵富源
     * @Description 通过班级编号查询班级信息
     * @Return pres.zfy.sims.entity.SchoolClass
     */
    public static SchoolClass queryClassByClassCode(String code) {
        SchoolClass schoolClass = new SchoolClass();
        String SQL = "SELECT class.*,cmc_relation.cmcr_major_id,cmc_relation.cmcr_college_id FROM class \n" +
                "LEFT JOIN cmc_relation\n" +
                "\tON class.class_id=cmc_relation.cmcr_class_id\n" +
                "WHERE class_code=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code);
        try {
            if (rs.next()) {
                schoolClass.setClassId(rs.getInt("class_id"));
                schoolClass.setClassCode(rs.getString("class_code"));
                schoolClass.setClassGrade(rs.getString("class_grade"));
                schoolClass.setClassName(rs.getString("class_name"));
                schoolClass.setClassCreateTime(rs.getTimestamp("class_create_time"));
                schoolClass.setClassUpdateTime(rs.getTimestamp("class_update_time"));
                schoolClass.setMajorId(rs.getInt("cmcr_major_id"));
                schoolClass.setCollegeId(rs.getInt("cmcr_college_id"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return schoolClass;
    }

    /**
     * @param code
     * @param name
     * @Author 赵富源
     * @Description 根据班级编号或名称查询班级
     * @Return pres.zfy.sims.entity.SchoolClass
     */
    public static SchoolClass queryClassByClassCodeOrName(String code, String name) {
        SchoolClass schoolClass = new SchoolClass();
        String SQL = "SELECT * FROM class WHERE  class_code=? OR class_name=?";
        ResultSet rs = MySQLUtil.doDQL(SQL, code, name);
        try {
            if (rs.next()) {
                schoolClass.setClassId(rs.getInt("class_id"));
                schoolClass.setClassCode(rs.getString("class_code"));
                schoolClass.setClassGrade(rs.getString("class_grade"));
                schoolClass.setClassName(rs.getString("class_name"));
                schoolClass.setClassCreateTime(rs.getTimestamp("class_create_time"));
                schoolClass.setClassUpdateTime(rs.getTimestamp("class_update_time"));
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        } finally {
            MySQLUtil.closeAll();
        }
        return schoolClass;
    }

    /**
     * @param
     * @Author 赵富源
     * @Description 查询所有班级信息
     * @Return java.util.List<pres.zfy.sims.entity.SchoolClass>
     */
    public static List<SchoolClass> queryAllClasses() {
        List<SchoolClass> schoolClasses = new ArrayList<>();
        String SQL = "SELECT class.*,cmc_relation.cmcr_major_id,cmc_relation.cmcr_college_id FROM class \n" +
                "LEFT JOIN cmc_relation\n" +
                "\tON class.class_id=cmc_relation.cmcr_class_id";
        ResultSet rs = MySQLUtil.doDQL(SQL);
        try {
            while (rs.next()) {
                SchoolClass schoolClass = new SchoolClass();
                schoolClass.setClassId(rs.getInt("class_id"));
                schoolClass.setClassCode(rs.getString("class_code"));
                schoolClass.setClassGrade(rs.getString("class_grade"));
                schoolClass.setClassName(rs.getString("class_name"));
                schoolClass.setClassCreateTime(rs.getTimestamp("class_create_time"));
                schoolClass.setClassUpdateTime(rs.getTimestamp("class_update_time"));
                schoolClass.setMajorId(rs.getInt("cmcr_major_id"));
                schoolClass.setCollegeId(rs.getInt("cmcr_college_id"));
                schoolClasses.add(schoolClass);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }
        return schoolClasses;
    }

    /**
     * @param
     * @Author 赵富源
     * @Description 通过班级名称更新班级信息
     * @Return int
     */
    public static int updSchoolClassByClassCode(SchoolClass schoolClass) {
        int status = 0;
        String SQL = "UPDATE class SET class_grade=?,class_name=?,class_update_time=? WHERE class_code=?";
        try {
            status = MySQLUtil.doDML(SQL, schoolClass.getClassGrade(), schoolClass.getClassName(), schoolClass.getClassUpdateTime(), schoolClass.getClassCode());
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
     * @Description 通过班级ID更新班级信息
     * @Return int
     */
    public static int updSchoolClassById(SchoolClass schoolClass) {
        int status = 0;
        String SQL = "UPDATE class SET class_code=?,class_grade=?,class_name=?,class_update_time=? WHERE class_id=?";
        try {
            status = MySQLUtil.doDML(SQL, schoolClass.getClassCode(), schoolClass.getClassGrade(), schoolClass.getClassName(), schoolClass.getClassUpdateTime(), schoolClass.getClassId());
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
    *@Description 查询专业下的班级
    *@Return java.util.List<pres.zfy.sims.entity.SchoolClass>
    */
   public static List<SchoolClass> querySchoolClassesInMajor(int majorId){
        List<SchoolClass> schoolClassList=new ArrayList<>();
        String SQL="SELECT class.* \n" +
                "FROM class\n" +
                "LEFT JOIN cmc_relation\n" +
                "\ton class.class_id=cmc_relation.cmcr_class_id\n" +
                "WHERE cmc_relation.cmcr_major_id=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,majorId);
        try{
            while(rs.next()){
                SchoolClass schoolClass=new SchoolClass();
                schoolClass.setClassId(rs.getInt("class_id"));
                schoolClass.setClassCode(rs.getString("class_code"));
                schoolClass.setClassGrade(rs.getString("class_grade"));
                schoolClass.setClassName(rs.getString("class_name"));
                schoolClass.setClassCreateTime(rs.getTimestamp("class_create_time"));
                schoolClass.setClassUpdateTime(rs.getTimestamp("class_update_time"));
                schoolClassList.add(schoolClass);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return schoolClassList;
   }

   /**
   *@param name
   *@Author 赵富源
   *@Description 通过名称查询班级id
   *@Return int
   */
   public static int queryClassIdByName(String name){
        int classId=0;
        String SQL="SELECT class_id FROM class WHERE class_name=?";
        ResultSet rs=MySQLUtil.doDQL(SQL,name);
        try{
            if (rs.next()){
                classId=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
        }finally {
            MySQLUtil.closeAll();
        }
        return classId;
   }

   /**
   *@param id
   *@Author 赵富源
   *@Description 通过id查询班级名称
   *@Return java.lang.String
   */
   public static String queryClassNameById(int id){
       String name="无";
       String SQL="SELECT class_name FROM class WHERE class_id=?";
       ResultSet rs=MySQLUtil.doDQL(SQL,id);
       try{
           if(rs.next()){
               name=rs.getString(1);
           }
       } catch (SQLException e) {
           System.out.println(new Exception().getStackTrace()[0].getMethodName() + " 发生错误：" + e.getMessage());
       }finally {
           MySQLUtil.closeAll();
       }
       return name;
   }
}
