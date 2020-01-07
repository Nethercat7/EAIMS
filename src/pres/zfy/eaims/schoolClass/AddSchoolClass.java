package pres.zfy.eaims.schoolClass;

import pres.zfy.eaims.db.CmcRelationModule;
import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.entity.SchoolClass;
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
 * @Description:添加班级功能
 * @Author:赵富源
 * @CreateDate:2019.12.23 2:02
 */
public class AddSchoolClass extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JPanel row4;
    JPanel row5;
    JPanel row6;
    JLabel classCode;
    JLabel classGrade;
    JLabel className;
    JLabel college;
    JLabel major;
    JTextField classCodeText;
    JTextField classNameText;
    JComboBox<String> classGradeComboBox;
    JComboBox<String> collegeComboBox;
    JComboBox<String> majorComboBox;
    JButton addBtn;
    JButton cancelBtn;

    public AddSchoolClass() {
        row1 = new JPanel();
        classCode = new JLabel("班级编号：");
        classCodeText = new JTextField(10);
        row1.add(classCode);
        row1.add(classCodeText);
        add(row1);

        row2 = new JPanel();
        classGrade = new JLabel("所属年级：");
        row2.add(classGrade);
        classGradeComboBox = new JComboBox<>();
        //获取系统从现在往前7年的年份，默认值系统当前的年份。
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i <= 7; i++) {
            classGradeComboBox.addItem(String.valueOf(calendar.get(Calendar.YEAR) - i));
        }
        classGradeComboBox.setPreferredSize(new Dimension(113, 19));
        row2.add(classGradeComboBox);
        add(row2);

        row3 = new JPanel();
        className = new JLabel("班级名称：");
        classNameText = new JTextField(10);
        row3.add(className);
        row3.add(classNameText);
        add(row3);

        row5 = new JPanel();
        college = new JLabel("所属院系：");
        collegeComboBox = new JComboBox<>();
        List<College> colleges = CollegeModule.queryAllColleges();
        for (int i = 0; i < colleges.size(); i++) {
            collegeComboBox.addItem(colleges.get(i).getcName());
        }
        row5.add(college);
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
        row5.add(collegeComboBox);
        add(row5);


        row6 = new JPanel();
        major = new JLabel("所属专业：");
        majorComboBox = new JComboBox<>();
        row6.add(major);
        List<Major> majorList = MajorModule.queryMajorsInCollege(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
        for (int i = 0; i < majorList.size(); i++) {
            majorComboBox.addItem(majorList.get(i).getmName());
        }
        row6.add(majorComboBox);
        add(row6);

        row4 = new JPanel();
        addBtn = new JButton("添加");
        cancelBtn = new JButton("取消");
        addBtn.addActionListener(e -> {
            String code = classCodeText.getText();
            String grade = String.valueOf(classGradeComboBox.getSelectedItem());
            String name = classNameText.getText();
            int collegeId = CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem()));
            int majorId = MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem()));
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入班级编号");
            } else if ("".equals(name)) {
                SimsUtil.setErrorMessage("请输入班级名称");
            } else if (majorId <= 0) {
                SimsUtil.setErrorMessage("请选择所属专业");
            } else if (!RegexUntil.onlyLetterOrNumber(code)) {
                SimsUtil.setErrorMessage("班级编号只能为字母或数字");
            } else if (code.length() > 30) {
                SimsUtil.setErrorMessage("班级编号最大长度为30");
            } else if (name.length() > 30) {
                SimsUtil.setErrorMessage("班级名称最大长度为30");
            } else {
                addSchoolClass(code, grade, name, majorId, collegeId);
            }
        });
        row4.add(addBtn);
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row4.add(cancelBtn);
        add(row4);

        init();
    }

    private void init() {
        setLayout(new GridLayout(7, 1));
        setTitle("添加班级");
        setBounds(SimsUtil.setWidth(410, 2), SimsUtil.setHeight(340, 2), 410, 340);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param code
     * @param grade
     * @param name
     * @Author 赵富源
     * @Description 添加班级
     * @Return void
     */
    private void addSchoolClass(String code, String grade, String name, int majorId, int collegeId) {
        SchoolClass schoolClass = SchoolClassModule.queryClassByClassCodeOrName(code, name);
        if (schoolClass.getClassCode() != null && schoolClass.getClassCode().equals(code)) {
            SimsUtil.setErrorMessage("班级编号：" + code + " 已存在");
        } else if (schoolClass.getClassName() != null && schoolClass.getClassName().equals(name)) {
            SimsUtil.setErrorMessage("班级名称：" + name + " 已存在");
        } else {
            schoolClass.setClassCode(code);
            schoolClass.setClassGrade(grade);
            schoolClass.setClassName(name);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            schoolClass.setClassCreateTime(Timestamp.valueOf(sdf.format(new Date())));
            int classId = SchoolClassModule.addSchoolClass(schoolClass);
            if (classId > 0) {
                SimsUtil.setInfoMessage("班级：" + name + " 添加成功");
                int status = CmcRelationModule.addCmcRelation(classId,majorId,collegeId);
                if(status<=0){
                    SimsUtil.setErrorMessage("创建班级关系失败");
                }
            } else {
                SimsUtil.setErrorMessage("添加班级失败");
            }
        }
    }
}
