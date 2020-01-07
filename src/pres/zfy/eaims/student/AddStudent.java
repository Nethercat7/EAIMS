package pres.zfy.eaims.student;

import pres.zfy.eaims.db.*;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.entity.SchoolClass;
import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description:添加学生功能
 * @Author:赵富源
 * @CreateDate:2019.12.23 23:35
 */
public class AddStudent extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JPanel row4;
    JPanel row5;
    JPanel row6;
    JPanel row7;
    JPanel row8;
    JPanel row9;
    JPanel row10;

    JLabel code;
    JLabel name;
    JLabel gender;
    JLabel tel;
    JLabel remark;
    JLabel college;
    JLabel major;
    JLabel schoolClass;
    JLabel grade;

    JTextField codeText;
    JTextField nameText;
    JTextField telText;

    JTextArea remarkText;

    ButtonGroup genderRadio;

    JRadioButton male;
    JRadioButton female;

    JButton addBtn;
    JButton cancelBtn;
    JButton remarkBtn;

    JComboBox<String> collegeComboBox;
    JComboBox<String> majorComboBox;
    JComboBox<String> schoolClassComboBox;
    JComboBox<String> gradeComboBox;


    public AddStudent() {
        row1 = new JPanel();
        code = new JLabel("学号：        ");
        codeText = new JTextField(13);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        name = new JLabel("姓名：        ");
        nameText = new JTextField(13);
        row2.add(name);
        row2.add(nameText);
        add(row2);

        row3 = new JPanel();
        gender = new JLabel("性别：        ");
        genderRadio = new ButtonGroup();
        male = new JRadioButton("男");
        female = new JRadioButton("女");
        row3.add(gender);
        genderRadio.add(male);
        genderRadio.add(female);
        row3.add(male);
        row3.add(female);
        add(row3);

        row4 = new JPanel();
        tel = new JLabel("电话号码：");
        telText = new JTextField(13);
        row4.add(tel);
        row4.add(telText);
        add(row4);

        row7 = new JPanel();
        grade = new JLabel("所属年级：");
        row7.add(grade);
        gradeComboBox = new JComboBox<>();
        //获取系统从现在往前7年的年份，默认值系统当前的年份。
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i <= 7; i++) {
            gradeComboBox.addItem(String.valueOf(calendar.get(Calendar.YEAR) - i));
        }
        row7.add(gradeComboBox);
        add(row7);

        row8 = new JPanel();
        college = new JLabel("所属院系：");
        collegeComboBox = new JComboBox<>();
        List<College> colleges = CollegeModule.queryAllColleges();
        for (int i = 0; i < colleges.size(); i++) {
            collegeComboBox.addItem(colleges.get(i).getcName());
        }
        row8.add(college);
        collegeComboBox.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                majorComboBox.removeAllItems();
                List<Major> majorList = MajorModule.queryMajorsInCollege(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
                for (int i = 0; i < majorList.size(); i++) {
                    majorComboBox.addItem(majorList.get(i).getmName());
                }
                majorComboBox.revalidate();
            }
        });
        row8.add(collegeComboBox);
        add(row8);

        row9 = new JPanel();
        major = new JLabel("所属专业：");
        majorComboBox = new JComboBox<>();
        row9.add(major);
        List<Major> majorList = MajorModule.queryMajorsInCollege(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
        for (int i = 0; i < majorList.size(); i++) {
            majorComboBox.addItem(majorList.get(i).getmName());
        }
        majorComboBox.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                schoolClassComboBox.removeAllItems();
                List<SchoolClass> schoolClassList = SchoolClassModule.querySchoolClassesInMajor(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
                for (int i = 0; i < schoolClassList.size(); i++) {
                    schoolClassComboBox.addItem(schoolClassList.get(i).getClassName());
                }
                schoolClassComboBox.revalidate();
            }
        });
        row9.add(majorComboBox);
        add(row9);

        row10=new JPanel();
        schoolClass=new JLabel("所属班级：");
        schoolClassComboBox=new JComboBox<>();
        row10.add(schoolClass);
        List<SchoolClass> schoolClassList=SchoolClassModule.querySchoolClassesInMajor(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
        for(int i=0;i<schoolClassList.size();i++){
            schoolClassComboBox.addItem(schoolClassList.get(i).getClassName());
        }
        row10.add(schoolClassComboBox);
        add(row10);

        row5 = new JPanel();
        remark = new JLabel("备注：");
        remarkBtn = new JButton("添加备注");
        remarkText = new JTextArea();
        row5.add(remark);
        remarkBtn.addActionListener(e -> {
            new RemarkArea(String.valueOf(remarkText.getText()));
        });
        row5.add(remarkBtn);
        remarkText.setVisible(false);
        add(row5);

        row6 = new JPanel();
        addBtn = new JButton("添加");
        cancelBtn = new JButton("取消");
        addBtn.addActionListener(e -> {
            String code = codeText.getText();
            String name = nameText.getText();
            String gender = "";
            if (male.isSelected()) {
                gender = male.getText();
            } else if (female.isSelected()) {
                gender = female.getText();
            }
            String tel = telText.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp joinedTime = Timestamp.valueOf(sdf.format(new Date()));
            String remark = remarkText.getText();
            //非空判断和格式验证
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入学号");
            } else if ("".equals(name)) {
                SimsUtil.setErrorMessage("请输入姓名");
            } else if ("".equals(gender)) {
                SimsUtil.setErrorMessage("请选择性别");
            } else if (!RegexUntil.isNumber(code)) {
                SimsUtil.setErrorMessage("学号只能为数字");
            } else if (!RegexUntil.onlyChineseOrLetter(name)) {
                SimsUtil.setErrorMessage("名字只能为汉字或字母");
            } else if (!"".equals(tel) && !RegexUntil.checkTelFormat(tel)) {
                SimsUtil.setErrorMessage("请输入正确的手机号码");
            } else if (code.length() > 13) {
                SimsUtil.setErrorMessage("学号最大长度为13位");
            } else if (name.length() > 13) {
                SimsUtil.setErrorMessage("姓名最大长度为13位");
            } else {
                Student student = new Student();
                student.setStudentCode(code);
                student.setStudentName(name);
                student.setStudentGender(gender);
                student.setStudentTel(tel);
                student.setStudentJoinedTime(joinedTime);
                student.setStudentRemark(remark);
                student.setStudentGrade(String.valueOf(gradeComboBox.getSelectedItem()));
                student.setClassId(SchoolClassModule.queryClassIdByName(String.valueOf(schoolClassComboBox.getSelectedItem())));
                student.setMajorId(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
                student.setCollegeId(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
                addStudentInfo(student);
            }
        });
        row6.add(addBtn);
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row6.add(cancelBtn);
        add(row6);

        init();
    }

    private void init() {
        setLayout(new GridLayout(10, 1));
        setTitle("添加学生");
        setBounds(SimsUtil.setWidth(420, 2), SimsUtil.setHeight(490, 2), 420, 490);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param studentInfo
     * @Author 赵富源
     * @Description 添加学生
     * @Return void
     */
    private void addStudentInfo(Student studentInfo) {
        Student student = StudentModule.queryStudentByCode(studentInfo.getStudentCode());
        if (student.getStudentCode() != null) {
            SimsUtil.setErrorMessage("学号已存在");
        } else {
            int studentId = StudentModule.addStudent(studentInfo);
            if (studentId > 0) {
                SimsUtil.setInfoMessage("已添加 " + studentInfo.getStudentName() + " 的信息");
                //清空备注
                remarkText.setText("");
                //添加学生关系
                int status= ScmcRelationModule.addScmcRelation(studentId,studentInfo.getClassId(),studentInfo.getMajorId(),studentInfo.getCollegeId());
                if(status<0){
                    SimsUtil.setErrorMessage("更新与班级、专业、学院的关系失败");
                }
            } else {
                SimsUtil.setErrorMessage("添加失败");
            }
        }
    }

    //备注子窗口
    private class RemarkArea extends JFrame {
        JPanel row;
        JTextArea remarkInfo;
        JScrollPane scrollPane;
        JButton confirmBtn;
        JButton cancelBtn;

        private RemarkArea(String remark) {
            remarkInfo = new JTextArea();
            remarkInfo.setText(remark);
            scrollPane = new JScrollPane(remarkInfo);
            add(scrollPane);

            row = new JPanel();
            confirmBtn = new JButton("确定");
            cancelBtn = new JButton("取消");
            confirmBtn.addActionListener(e -> {
                remarkText.setText(remarkInfo.getText());
                dispose();
            });
            row.add(confirmBtn);
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row.add(cancelBtn);
            add(row);

            init();
        }

        private void init() {
            setLayout(new GridLayout(2, 1));
            setTitle("添加备注");
            setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(500, 2), 500, 500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}

