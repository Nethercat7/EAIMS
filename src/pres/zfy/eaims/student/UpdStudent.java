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
 * @Description:通过学号更新学生
 * @Author:赵富源
 * @CreateDate:2019.12.24 18:32
 */
public class UpdStudent extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton confirmBtn;
    JButton cancelBtn;

    public UpdStudent() {
        row1 = new JPanel();
        code = new JLabel("学号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            String code = codeText.getText();
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入学号");
            } else if (!RegexUntil.isNumber(code)) {
                SimsUtil.setErrorMessage("学号只能为数字");
            } else {
                Student student = StudentModule.queryStudentByCode(code);
                if (student.getStudentCode() != null) {
                    new UpdFrame(student);
                    dispose();
                } else {
                    SimsUtil.setErrorMessage("学号不存在");
                }
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
        setTitle("更新学生信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //更新窗口
    private class UpdFrame extends JFrame {
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

        ButtonGroup choose;

        JRadioButton male;
        JRadioButton female;

        JTextField nameText;
        JTextField telText;
        JTextField codeText;

        JTextArea remarkText;

        JButton updBtn;
        JButton cancelBtn;
        JButton remarkBtn;

        JComboBox<String> collegeComboBox;
        JComboBox<String> majorComboBox;
        JComboBox<String> schoolClassComboBox;
        JComboBox<String> gradeComboBox;

        private UpdFrame(Student student) {
            row1 = new JPanel();
            code = new JLabel("学号：        ");
            codeText = new JTextField(13);
            codeText.setText(student.getStudentCode());
            row1.add(code);
            row1.add(codeText);
            add(row1);

            row2 = new JPanel();
            name = new JLabel("姓名：        ");
            nameText = new JTextField(13);
            nameText.setText(student.getStudentName());
            row2.add(name);
            row2.add(nameText);
            add(row2);

            row3 = new JPanel();
            gender = new JLabel("性别：        ");
            choose = new ButtonGroup();
            male = new JRadioButton("男");
            female = new JRadioButton("女");
            choose.add(male);
            choose.add(female);
            if (student.getStudentGender().equals(male.getText())) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }
            row3.add(gender);
            row3.add(male);
            row3.add(female);
            add(row3);

            row4 = new JPanel();
            tel = new JLabel("电话号码：");
            telText = new JTextField(13);
            telText.setText(student.getStudentTel());
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
            gradeComboBox.setSelectedItem(student.getStudentGrade());
            row7.add(gradeComboBox);
            add(row7);

            row8 = new JPanel();
            college = new JLabel("所属院系：");
            collegeComboBox = new JComboBox<>();
            java.util.List<College> colleges = CollegeModule.queryAllColleges();
            for (int i = 0; i < colleges.size(); i++) {
                collegeComboBox.addItem(colleges.get(i).getcName());
            }
            row8.add(college);
            collegeComboBox.setSelectedItem(CollegeModule.queryCollegeNameById(student.getCollegeId()));
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
            row8.add(collegeComboBox);
            add(row8);

            row9 = new JPanel();
            major = new JLabel("所属专业：");
            majorComboBox = new JComboBox<>();
            row9.add(major);
            java.util.List<Major> majorList = MajorModule.queryMajorsInCollege(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
            for (int i = 0; i < majorList.size(); i++) {
                majorComboBox.addItem(majorList.get(i).getmName());
            }
            majorComboBox.setSelectedItem(MajorModule.queryMajorNameById(student.getMajorId()));
            majorComboBox.addActionListener(e -> {
                if (e.getActionCommand().equals("comboBoxChanged")) {
                    schoolClassComboBox.removeAllItems();
                    java.util.List<SchoolClass> schoolClassList = SchoolClassModule.querySchoolClassesInMajor(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
                    for (int i = 0; i < schoolClassList.size(); i++) {
                        schoolClassComboBox.addItem(schoolClassList.get(i).getClassName());
                    }
                    schoolClassComboBox.revalidate();
                }
            });
            row9.add(majorComboBox);
            add(row9);

            row10 = new JPanel();
            schoolClass = new JLabel("所属班级：");
            schoolClassComboBox = new JComboBox<>();
            row10.add(schoolClass);
            schoolClassComboBox.setSelectedItem(SchoolClassModule.queryClassNameById(student.getClassId()));
            List<SchoolClass> schoolClassList = SchoolClassModule.querySchoolClassesInMajor(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
            for (int i = 0; i < schoolClassList.size(); i++) {
                schoolClassComboBox.addItem(schoolClassList.get(i).getClassName());
            }
            row10.add(schoolClassComboBox);
            add(row10);

            row5 = new JPanel();
            remark = new JLabel("备注：");
            remarkBtn = new JButton("修改备注");
            remarkText = new JTextArea();
            remarkText.setText(student.getStudentRemark());
            row5.add(remark);
            remarkBtn.addActionListener(e -> {
                new OpenRemarkArea();
            });
            row5.add(remarkBtn);
            add(row5);

            row6 = new JPanel();
            updBtn = new JButton("更新");
            updBtn.addActionListener(e -> {
                String code = codeText.getText();
                String name = nameText.getText();
                String gender = "";
                if (male.isSelected()) {
                    gender = male.getText();
                } else {
                    gender = female.getText();
                }
                String tel = telText.getText();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Timestamp updTime = Timestamp.valueOf(sdf.format(new Date()));
                String remark = remarkText.getText();
                if ("".equals(code)) {
                    SimsUtil.setErrorMessage("请输入学号");
                } else if ("".equals(name)) {
                    SimsUtil.setErrorMessage("请输入姓名");
                } else if ("".equals(schoolClassComboBox.getSelectedItem())) {
                    SimsUtil.setErrorMessage("请选择所属班级");
                } else if ("".equals(majorComboBox.getSelectedItem())) {
                    SimsUtil.setErrorMessage("请选择所属专业");
                } else if ("".equals(collegeComboBox.getSelectedItem())) {
                    SimsUtil.setErrorMessage("请选择所属院系");
                } else if (!RegexUntil.isNumber(code)) {
                    SimsUtil.setErrorMessage("学号只能为数字");
                } else if (!RegexUntil.onlyChineseOrLetter(name)) {
                    SimsUtil.setErrorMessage("姓名只能为汉字或字母");
                } else if (!"".equals(tel) && !RegexUntil.checkTelFormat(tel)) {
                    SimsUtil.setErrorMessage("请输入有效的电话号码");
                } else if (code.length() > 13) {
                    SimsUtil.setErrorMessage("学号最大长度为13位");
                } else if (name.length() > 13) {
                    SimsUtil.setErrorMessage("姓名最大长度为13位");
                } else {
                    student.setStudentCode(code);
                    student.setStudentName(name);
                    student.setStudentGender(gender);
                    student.setStudentTel(tel);
                    student.setStudentUpdatedTime(updTime);
                    student.setStudentRemark(remark);
                    student.setStudentGrade(String.valueOf(gradeComboBox.getSelectedItem()));
                    student.setClassId(SchoolClassModule.queryClassIdByName(String.valueOf(schoolClassComboBox.getSelectedItem())));
                    student.setMajorId(MajorModule.queryMajorIdByName(String.valueOf(majorComboBox.getSelectedItem())));
                    student.setCollegeId(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
                    updStudentInfo(student);
                }
            });
            cancelBtn = new JButton("取消");
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row6.add(updBtn);
            row6.add(cancelBtn);
            add(row6);

            init(student.getStudentName());
        }

        private void init(String name) {
            setLayout(new GridLayout(10, 1));
            setTitle(name);
            setBounds(SimsUtil.setWidth(330, 2), SimsUtil.setHeight(530, 2), 330, 530);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        //备注子窗口
        private class OpenRemarkArea extends JFrame {
            JPanel row;
            JTextArea textArea;
            JButton confirmBtn;
            JButton cancelBtn;

            private OpenRemarkArea() {
                textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setText(remarkText.getText());
                JScrollPane scrollPane = new JScrollPane(textArea);
                add(scrollPane);

                row = new JPanel();
                confirmBtn = new JButton("确定");
                cancelBtn = new JButton("取消");
                confirmBtn.addActionListener(e -> {
                    remarkText.setText(textArea.getText());
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

            void init() {
                setLayout(new GridLayout(2, 1));
                setTitle("添加备注");
                setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(500, 2), 500, 500);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setVisible(true);
            }
        }

        /**
         * @param student
         * @Author 赵富源
         * @Description 更新学生资料
         * @Return void
         */
        private void updStudentInfo(Student student) {
            int status = StudentModule.updStudentById(student);
            if (status > 0) {
                SimsUtil.setInfoMessage("成功更新 " + student.getStudentName() + " 的信息");
                int isExist = ScmcRelationModule.queryScmcrId(student.getStudentId());
                int status2 = 0;
                if (isExist > 0) {
                    status2 = ScmcRelationModule.updScmcRelation(student.getStudentId(), student.getClassId(), student.getMajorId(), student.getCollegeId());
                } else {
                    status2 = ScmcRelationModule.addScmcRelation(student.getStudentId(), student.getClassId(), student.getMajorId(), student.getCollegeId());
                }
                if (status2 < 0) {
                    SimsUtil.setErrorMessage("更新学生关系时发生错误");
                }
            } else {
                SimsUtil.setErrorMessage("更新学生信息时发生错误");
            }
        }
    }

}
