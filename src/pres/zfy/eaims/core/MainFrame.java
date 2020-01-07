package pres.zfy.eaims.core;

import pres.zfy.eaims.college.*;
import pres.zfy.eaims.entity.LoginProfile;
import pres.zfy.eaims.major.*;
import pres.zfy.eaims.schoolClass.*;
import pres.zfy.eaims.student.*;
import pres.zfy.eaims.teacher.*;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:主模块
 * @Author:赵富源
 * @CreateDate:2019.12.15 22:11
 */
public class MainFrame extends JFrame {
    JMenuBar menuBar;

    JMenu teacherManagement;
    JMenu collegeManagement;
    JMenu majorManagement;
    JMenu schoolClassManagement;
    JMenu studentManagement;

    JMenuItem addTeacher;
    JMenuItem queryAllTeachers;
    JMenuItem queryTeacherByAccount;
    JMenuItem delTeacher;
    JMenuItem updateTeacher;
    JMenuItem updPassword;

    JMenuItem addCollege;
    JMenuItem queryAllColleges;
    JMenuItem queryCollegeByCode;
    JMenuItem delCollege;
    JMenuItem updCollege;

    JMenuItem addMajor;
    JMenuItem queryAllMajors;
    JMenuItem queryMajorByCode;
    JMenuItem delMajorByCode;
    JMenuItem updMajorByCode;

    JMenuItem addSchoolClass;
    JMenuItem delSchoolClass;
    JMenuItem queryAllClasses;
    JMenuItem queryClassByCode;
    JMenuItem updSchoolClass;

    JMenuItem addStudent;
    JMenuItem delStudent;
    JMenuItem queryAllStudents;
    JMenuItem queryStudentsByCode;
    JMenuItem updStudent;

    JLabel bg;
    JLabel welcomeInfo;

    LoginProfile profile = new LoginProfile();

    public MainFrame(LoginProfile loginProfile) {
        profile.tId = loginProfile.tId;
        profile.tAccount = loginProfile.tAccount;
        profile.tName = loginProfile.tName;
        profile.tGender = loginProfile.tGender;
        profile.tTel = loginProfile.tTel;
        profile.tEmail = loginProfile.tEmail;
        profile.tRemark = loginProfile.tRemark;

        menuBar = new JMenuBar();

        //教师管理模块
        teacherManagement = new JMenu("教师管理");
        addTeacher = new JMenuItem("添加新教师");
        delTeacher = new JMenuItem("删除教师");
        queryAllTeachers = new JMenuItem("查看所有教师信息");
        queryTeacherByAccount = new JMenuItem("查询教师信息");
        updateTeacher = new JMenuItem("更新教师信息");
        updPassword = new JMenuItem("修改密码");
        addTeacher.addActionListener(e -> {
            new AddTeacher();
        });
        teacherManagement.add(addTeacher);
        delTeacher.addActionListener(e -> {
            new DelTeacher();
        });
        teacherManagement.add(delTeacher);
        queryAllTeachers.addActionListener(e -> {
            new QueryAllTeachers();
        });
        teacherManagement.add(queryAllTeachers);
        queryTeacherByAccount.addActionListener(e -> {
            new QueryTeacherByAccount();
        });
        teacherManagement.add(queryTeacherByAccount);
        updateTeacher.addActionListener(e -> {
            new UpdateTeacher();
        });
        teacherManagement.add(updateTeacher);
        updPassword.addActionListener(e -> {
            new UpdatePassword(loginProfile.tId);
        });
        teacherManagement.add(updPassword);
        menuBar.add(teacherManagement);

        //院系管理模块
        collegeManagement = new JMenu("院系管理");
        addCollege = new JMenuItem("创建院系");
        delCollege = new JMenuItem("删除院系");
        queryAllColleges = new JMenuItem("查询所有院系信息");
        queryCollegeByCode = new JMenuItem("查询院系信息");
        updCollege = new JMenuItem("更新院系信息");
        addCollege.addActionListener(e -> {
            new AddCollege();
        });
        collegeManagement.add(addCollege);
        delCollege.addActionListener(e -> {
            new DelCollege();
        });
        collegeManagement.add(delCollege);
        queryAllColleges.addActionListener(e -> {
            new QueryAllColleges();
        });
        collegeManagement.add(queryAllColleges);
        queryCollegeByCode.addActionListener(e -> {
            new QueryCollegeByCode();
        });
        collegeManagement.add(queryCollegeByCode);
        updCollege.addActionListener(e -> {
            new UpdateCollege();
        });
        collegeManagement.add(updCollege);
        menuBar.add(collegeManagement);

        //专业管理模块
        majorManagement = new JMenu("专业管理");
        addMajor = new JMenuItem("创建新专业");
        delMajorByCode = new JMenuItem("删除专业");
        queryAllMajors = new JMenuItem("查询所有专业信息");
        queryMajorByCode = new JMenuItem("查询专业信息");
        updMajorByCode = new JMenuItem("更新专业信息");
        addMajor.addActionListener(e -> {
            new AddMajor();
        });
        majorManagement.add(addMajor);
        delMajorByCode.addActionListener(e -> {
            new DelMajor();
        });
        majorManagement.add(delMajorByCode);
        queryAllMajors.addActionListener(e -> {
            new QueryAllMajors();
        });
        majorManagement.add(queryAllMajors);
        queryMajorByCode.addActionListener(e -> {
            new QueryMajorByCode();
        });
        majorManagement.add(queryMajorByCode);
        updMajorByCode.addActionListener(e -> {
            new UpdateMajor();
        });
        majorManagement.add(updMajorByCode);
        menuBar.add(majorManagement);

        //班级管理模块
        schoolClassManagement = new JMenu("班级管理");
        addSchoolClass = new JMenuItem("添加班级");
        delSchoolClass = new JMenuItem("删除班级");
        queryAllClasses = new JMenuItem("查询所有班级信息");
        queryClassByCode = new JMenuItem("查询班级信息");
        updSchoolClass = new JMenuItem("更新班级信息");
        addSchoolClass.addActionListener(e -> {
            new AddSchoolClass();
        });
        schoolClassManagement.add(addSchoolClass);
        delSchoolClass.addActionListener(e -> {
            new DelSchoolClass();
        });
        schoolClassManagement.add(delSchoolClass);
        queryAllClasses.addActionListener(e -> {
            new QueryAllClasses();
        });
        schoolClassManagement.add(queryAllClasses);
        queryClassByCode.addActionListener(e -> {
            new QueryClassByCode();
        });
        schoolClassManagement.add(queryClassByCode);
        updSchoolClass.addActionListener(e -> {
            new UpdateSchoolClass();
        });
        schoolClassManagement.add(updSchoolClass);
        menuBar.add(schoolClassManagement);

        //学生管理模块
        studentManagement = new JMenu("学生管理");
        addStudent = new JMenuItem("添加学生");
        delStudent = new JMenuItem("删除学生信息");
        queryAllStudents = new JMenuItem("查询所有学生信息");
        queryStudentsByCode = new JMenuItem("查询学生信息");
        updStudent = new JMenuItem("修改学生信息");
        addStudent.addActionListener(e -> {
            new AddStudent();
        });
        studentManagement.add(addStudent);
        delStudent.addActionListener(e -> {
            new DelStudent();
        });
        studentManagement.add(delStudent);
        queryAllStudents.addActionListener(e -> {
            new QueryAllStudents();
        });
        studentManagement.add(queryAllStudents);
        queryStudentsByCode.addActionListener(e -> {
            new QueryStudentByCode();
        });
        studentManagement.add(queryStudentsByCode);
        updStudent.addActionListener(e -> {
            new UpdStudent();
        });
        studentManagement.add(updStudent);
        menuBar.add(studentManagement);

        setJMenuBar(menuBar);

        //主页样式
        //设置背景图片
        ImageIcon imageIcon = new ImageIcon("src/pres/zfy/eaims/images/bg.jpg");
        welcomeInfo = new JLabel("欢迎访问教务信息管理系统,当前用户："+loginProfile.tName);
        bg = new JLabel(imageIcon);
        welcomeInfo.setBounds(240,0,800,200);
        welcomeInfo.setFont(new java.awt.Font("TimesRoman", Font.ITALIC,27));
        welcomeInfo.setForeground(Color.black);
        add(welcomeInfo);
        bg.setBounds(0,0,1280,720);
        add(bg);


        init();

    }

    private void init() {
        setLayout(null);
        setTitle("E.A.I.M.S.教务信息管理系统");
        setBounds(SimsUtil.setWidth(1280, 2), SimsUtil.setHeight(720, 2), 1280, 720);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
