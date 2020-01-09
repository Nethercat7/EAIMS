package pres.zfy.eaims;

import pres.zfy.eaims.db.*;
import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.RandomInfoGenerateUntil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Description:用于批量向数据库生成数据
 * @Author:赵富源
 * @CreateDate:2020.1.8 3:18
 */
public class EaimsDataGenerate extends Exception {
    /**
     * @param count
     * @Author 赵富源
     * @Description 批量生成教师数据
     * @Return void
     */
    public static void generateTeacherInfo(int count) {
        if (count > 0) {
            System.out.println("正在生成教师信息...");
            for (int i = 0; i < count; i++) {
                Random random = new Random();
                long account = 0;
                if (!"".equals(TeacherModule.queryMaxAccount())) {//如果数据库中的教师数据为0，那么工号就从1开始
                    account = Long.parseLong(TeacherModule.queryMaxAccount()) + 1;
                } else {
                    account = 1;
                }
                String gender = RandomInfoGenerateUntil.randomGender();
                String name = RandomInfoGenerateUntil.randomChineseName(gender); //工号为最大工号+1
                String phoneNumber = RandomInfoGenerateUntil.randomChinaPhoneNumber();
                String email = RandomInfoGenerateUntil.randomEmailAddress();

                while (phoneNumber.equals(String.valueOf(TeacherModule.queryTeacherByTel(phoneNumber)))) { //确保电话号码不存在于表中
                    phoneNumber = RandomInfoGenerateUntil.randomChinaPhoneNumber();
                }
                while (email.equals(String.valueOf(TeacherModule.queryTeacherByEmail(email)))) { //确保电子邮箱不存在于表中
                    email = RandomInfoGenerateUntil.randomEmailAddress();
                }

                //获取院系信息
                List<Integer> collegeIdList = CollegeModule.queryAllCollegeId();
                if (collegeIdList.size() <= 0) {
                    System.out.println("院系数量为0，请保证至少有1个院系");
                    break;
                }

                //批量添加教师信息
                Teacher teacher = new Teacher();
                teacher.settAccount(String.valueOf(account));
                teacher.settPwd("asd123456");
                teacher.settName(name);
                teacher.settGender(gender);
                teacher.settTel(phoneNumber);
                teacher.settEmail(email);
                int teacherId = TeacherModule.addTeacher(teacher);
                //添加教师关系
                if (teacherId > 0) {
                    int status = 0;
                    if (collegeIdList.size() > 1) {//如果数据库中存在的数量等于1，那么collegeId数组的下标只能为0
                        status = TcRelationModule.addTcRelation(collegeIdList.get(random.nextInt(collegeIdList.size())), teacherId);//随机从所有存在的院系中挑选出1个院系作为所属院系
                    } else {
                        status = TcRelationModule.addTcRelation(collegeIdList.get(0), teacherId);
                    }

                    if (status <= 0) {
                        System.out.println("教师关系添加失败");
                        break;
                    }
                    System.out.println("成功生成" + i + "条教师数据");
                } else {
                    System.out.println("生成数据时发生异常");
                }
            }
        } else {
            System.out.println("生成的数量必须大于0");
        }
    }

    /**
     * @param count
     * @Author 赵富源
     * @Description 批量生成学生数据
     * @Return void
     */
    public static void generateStudentInfo(int count) {
        if (count > 0) {
            System.out.println("正在生成学生信息...");
            for (int i = 0; i < count; i++) {
                int status = 0;
                long account = 0;
                if (!"".equals(StudentModule.queryMaxStudentCode())) {
                    account = Long.parseLong(StudentModule.queryMaxStudentCode()) + 1;
                } else {
                    account = 1;
                }
                String gender = RandomInfoGenerateUntil.randomGender();
                String name = RandomInfoGenerateUntil.randomChineseName(gender);
                String phoneNumber = RandomInfoGenerateUntil.randomChinaPhoneNumber();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Timestamp joinedTime = Timestamp.valueOf(sdf.format(new Date()));
                Calendar calendar = Calendar.getInstance();
                String grade = String.valueOf(calendar.get(Calendar.YEAR));

                Random random = new Random();
                List<Integer> collegeIdList = CollegeModule.queryAllCollegeId();
                int collegeId = 0;
                if (collegeIdList.size() <= 0) {
                    System.out.println("院系数量为0，请保证至少有1个院系");
                    break;
                } else if (collegeIdList.size() > 1) {
                    collegeId = collegeIdList.get(random.nextInt(collegeIdList.size()));
                } else {
                    collegeId = collegeIdList.get(0);
                }

                //根据上面随机生成的院系ID来查询该院系下相关的专业ID
                List<Integer> majorIdList = McRelationModule.queryAllMajorInCollege(collegeId);
                int majorId = 0;
                if (majorIdList.size() <= 0) {
                    System.out.println("专业数量为0，请保证每个院系下至少有1个专业");
                    break;
                } else if (majorIdList.size() > 1) {
                    majorId = majorIdList.get(random.nextInt(majorIdList.size()));
                } else {
                    majorId = majorIdList.get(0);
                }

                List<Integer> classIdList = CmcRelationModule.queryAllClassIdInMajor(majorId);
                int classId = 0;
                if (classIdList.size() <= 0) {
                    System.out.println("班级数量为0，请保证每个专业下至少有1个班级");
                    break;
                } else if (classIdList.size() > 1) {
                    classId = classIdList.get(random.nextInt(classIdList.size()));
                } else {
                    classId = classIdList.get(0);
                }

                Student student = new Student();
                student.setStudentCode(String.valueOf(account));
                student.setStudentName(name);
                student.setStudentGender(gender);
                student.setStudentTel(phoneNumber);
                student.setStudentJoinedTime(joinedTime);
                student.setStudentGrade(grade);
                int studentId = StudentModule.addStudent(student);
                if (studentId > 0) {
                    status = ScmcRelationModule.addScmcRelation(studentId, classId, majorId, collegeId);
                    if (status <= 0) {
                        System.out.println("生成学生关系时发生异常");
                        break;
                    }
                    System.out.println("成功生成" + i + "条学生数据");
                } else {
                    System.out.println("生成数据时发生异常");
                }
            }
        } else {
            System.out.println("生成的数量必须大于0");
        }

    }
}
