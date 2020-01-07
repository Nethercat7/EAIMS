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
 * @Description:修改班级信息
 * @Author:赵富源
 * @CreateDate:2019.12.23 22:29
 */
public class UpdateSchoolClass extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton confirmBtn;
    JButton cancelBtn;

    public UpdateSchoolClass() {
        row1 = new JPanel();
        code = new JLabel("班级编号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            String code = codeText.getText();
            if (!"".equals(code)) {
                SchoolClass schoolClass = SchoolClassModule.queryClassByClassCode(code);
                if (schoolClass.getClassCode() != null) {
                    new SchoolClassInfo(schoolClass);
                    dispose();
                } else {
                    SimsUtil.setErrorMessage("班级不存在");
                }
            } else {
                SimsUtil.setErrorMessage("请输入班级编号");
            }
        });
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row2.add(confirmBtn);
        row2.add(cancelBtn);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        setTitle("更新班级信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private class SchoolClassInfo extends JFrame {
        JPanel row1;
        JPanel row2;
        JPanel row3;
        JPanel row4;
        JPanel row5;
        JPanel row6;
        JLabel name;
        JLabel grade;
        JLabel sCode;
        JLabel college;
        JLabel major;
        JTextField nameText;
        JTextField sCodeText;
        JButton updBtn;
        JButton cancelBtn;
        JComboBox<String> gradeComboBox;
        JComboBox<String> collegeComboBox;
        JComboBox<String> majorComboBox;

        private SchoolClassInfo(SchoolClass schoolClass) {
            row4 = new JPanel();
            sCode = new JLabel("班级编号：");
            sCodeText = new JTextField(10);
            sCodeText.setText(schoolClass.getClassCode());
            row4.add(sCode);
            row4.add(sCodeText);
            add(row4);

            row1 = new JPanel();
            name = new JLabel("班级名称：");
            nameText = new JTextField(10);
            nameText.setText(schoolClass.getClassName());
            row1.add(name);
            row1.add(nameText);
            add(row1);

            row2 = new JPanel();
            grade = new JLabel("所属年级：");
            row2.add(grade);
            gradeComboBox = new JComboBox<>();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i <= 7; i++) {
                gradeComboBox.addItem(String.valueOf(calendar.get(Calendar.YEAR) - i));
            }
            gradeComboBox.setSelectedItem(schoolClass.getClassGrade());
            gradeComboBox.setPreferredSize(new Dimension(113, 19));
            row2.add(gradeComboBox);
            add(row2);

            row5 = new JPanel();
            college = new JLabel("所属院系：");
            collegeComboBox = new JComboBox<>();
            java.util.List<College> colleges = CollegeModule.queryAllColleges();
            for (int i = 0; i < colleges.size(); i++) {
                collegeComboBox.addItem(colleges.get(i).getcName());
            }
            row5.add(college);
            collegeComboBox.addActionListener(e -> {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    majorComboBox.removeAllItems();
                    java.util.List<Major> majorList = MajorModule.queryMajorsInCollege(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
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

            row3 = new JPanel();
            updBtn = new JButton("更新");
            cancelBtn = new JButton("取消");
            updBtn.addActionListener(e -> {
                String sCode = sCodeText.getText();
                String name = nameText.getText();
                String grade = String.valueOf(gradeComboBox.getSelectedItem());
                if ("".equals(sCode)) {
                    SimsUtil.setErrorMessage("请输入班级编号");
                } else if ("".equals(name)) {
                    SimsUtil.setErrorMessage("请输入班级名称");
                } else if (!RegexUntil.onlyLetterOrNumber(sCode)) {
                    SimsUtil.setErrorMessage("班级编号只能为字母或数字");
                } else if (sCode.length() > 30) {
                    SimsUtil.setErrorMessage("班级编号最大长度为30");
                } else if (name.length() > 30) {
                    SimsUtil.setErrorMessage("班级名称最大长度为30");
                } else {
                    schoolClass.setClassId(schoolClass.getClassId());
                    schoolClass.setClassCode(sCode);
                    schoolClass.setClassGrade(grade);
                    schoolClass.setClassName(name);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    schoolClass.setClassUpdateTime(Timestamp.valueOf(sdf.format(new Date())));
                    schoolClass.setMajorId(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
                    schoolClass.setCollegeId(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
                    updSchoolClassInfo(schoolClass);
                }
            });
            row3.add(updBtn);
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row3.add(cancelBtn);
            add(row3);


            init(schoolClass.getClassName());
        }

        private void init(String title) {
            setLayout(new GridLayout(6, 1));
            setTitle(title);
            setBounds(SimsUtil.setWidth(300, 2), SimsUtil.setHeight(320, 2), 300, 320);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        /**
         * @param schoolClass
         * @Author 赵富源
         * @Description 更新班级信息
         * @Return void
         */
        private void updSchoolClassInfo(SchoolClass schoolClass) {
            int status = SchoolClassModule.updSchoolClassById(schoolClass);
            if (status > 0) {
                SimsUtil.setInfoMessage("更新 " + schoolClass.getClassName() + " 信息成功");
                setTitle(schoolClass.getClassName());
                //更新关系
                int isExist = CmcRelationModule.queryCmcrIdByClassId(schoolClass.getClassId());
                int status2 = 0;
                if (isExist > 0) {
                    status2 = CmcRelationModule.updCmcRelation(schoolClass.getClassId(), schoolClass.getMajorId(), schoolClass.getCollegeId());
                } else {
                    status2 = CmcRelationModule.addCmcRelation(schoolClass.getClassId(), schoolClass.getMajorId(), schoolClass.getCollegeId());
                }
                if (status2 <= 0) {
                    SimsUtil.setErrorMessage("更新所属院系和专业时发生错误");
                }
            }
        }
    }
}
