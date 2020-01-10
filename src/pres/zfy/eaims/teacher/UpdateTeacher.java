package pres.zfy.eaims.teacher;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.TcRelationModule;
import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:更新教师功能
 * @Author:赵富源
 * @CreateDate:2019.12.17 0:23
 */
public class UpdateTeacher extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel account;
    JTextField accountText;
    JButton confirmBtn;
    JButton cancelBtn;

    String currentAccount;
    String currentTel;
    String currentEmail;

    public UpdateTeacher() {
        row1 = new JPanel();
        account = new JLabel("工号：");
        accountText = new JTextField(10);
        row1.add(account);
        row1.add(accountText);
        add(row1);

        row2 = new JPanel();
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            String account = accountText.getText();
            if ("".equals(account)) {
                SimsUtil.setErrorMessage("请输入工号");
            } else if (!RegexUntil.isNumber(account)) {
                SimsUtil.setErrorMessage("工号只能为数字");
            } else {
                Teacher teacher = TeacherModule.queryTeacherByAccount(account);
                if (teacher.gettAccount() != null) {
                    currentAccount = teacher.gettAccount();
                    currentTel = teacher.gettTel();
                    currentEmail = teacher.gettEmail();
                    new TeacherInfo(teacher);
                    dispose();
                } else {
                    SimsUtil.setErrorMessage("工号不存在");
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
        setTitle("更新教师信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //子窗口
    private class TeacherInfo extends JFrame {
        JPanel row1;
        JPanel row2;
        JPanel row3;
        JPanel row4;
        JPanel row5;
        JPanel row6;
        JPanel row7;
        JPanel row8;
        JLabel name;
        JLabel gender;
        JLabel tel;
        JLabel email;
        JLabel remark;
        JLabel account;
        JLabel college;
        ButtonGroup choose;
        JRadioButton male;
        JRadioButton female;
        JTextField nameText;
        JTextField telText;
        JTextField emailText;
        JTextField accountText;
        String remarkTextInfo;
        JButton updBtn;
        JButton cancelBtn;
        JButton remarkBtn;
        JComboBox<String> collegeName;

        private TeacherInfo(Teacher teacher) {
            row7 = new JPanel();
            account = new JLabel("工号：        ");
            accountText = new JTextField(13);
            accountText.setText(teacher.gettAccount());
            row7.add(account);
            row7.add(accountText);
            add(row7);

            row1 = new JPanel();
            name = new JLabel("姓名：        ");
            nameText = new JTextField(13);
            nameText.setText(teacher.gettName());
            row1.add(name);
            row1.add(nameText);
            add(row1);

            row2 = new JPanel();
            gender = new JLabel("性别：        ");
            choose = new ButtonGroup();
            male = new JRadioButton("男");
            female = new JRadioButton("女");
            if (teacher.gettGender().equals("男")) {
                male.setSelected(true);
            } else {
                female.setSelected(true);
            }
            choose.add(male);
            choose.add(female);
            row2.add(gender);
            row2.add(male);
            row2.add(female);
            add(row2);

            row3 = new JPanel();
            tel = new JLabel("电话号码：");
            telText = new JTextField(13);
            telText.setText(teacher.gettTel());
            row3.add(tel);
            row3.add(telText);
            add(row3);

            row4 = new JPanel();
            email = new JLabel("电子邮箱：");
            emailText = new JTextField(13);
            emailText.setText(teacher.gettEmail());
            row4.add(email);
            row4.add(emailText);
            add(row4);

            row8 = new JPanel();
            college = new JLabel("所属院系：");
            collegeName = new JComboBox<>();
            List<College> collegeList = CollegeModule.queryAllColleges();
            for (int i = 0; i < collegeList.size(); i++) {
                collegeName.addItem(collegeList.get(i).getcName());
            }
            collegeName.setSelectedItem(CollegeModule.queryCollegeNameById(teacher.getCollegeId()));
            row8.add(college);
            row8.add(collegeName);
            add(row8);

            row5 = new JPanel();
            remark = new JLabel("备注：        ");
            remarkTextInfo = teacher.gettRemark();
            remarkBtn = new JButton("更改备注");
            remarkBtn.addActionListener(e -> {
                new RemarkTextArea(remarkTextInfo);
            });
            row5.add(remark);
            row5.add(remarkBtn);
            add(row5);

            row6 = new JPanel();
            updBtn = new JButton("更新");
            updBtn.addActionListener(e -> {
                String account = accountText.getText();
                String name = nameText.getText();
                String gender = "";
                //判断单选框选中的值
                if (male.isSelected()) {
                    gender = male.getText();
                } else if (female.isSelected()) {
                    gender = female.getText();
                }
                String tel = telText.getText();
                String email = emailText.getText();
                String remark = remarkTextInfo;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Timestamp updTime = Timestamp.valueOf(sdf.format(new Date()));
                int collegeId = CollegeModule.queryCollegeIdByName(String.valueOf(collegeName.getSelectedItem()));
                if ("".equals(account)) {
                    SimsUtil.setErrorMessage("请输入工号");
                } else if ("".equals(name)) {
                    SimsUtil.setErrorMessage("请输入姓名");
                } else if (!RegexUntil.isNumber(account)) {
                    SimsUtil.setErrorMessage("工号只能为数字");
                } else if (!RegexUntil.onlyChineseOrLetter(name)) {
                    SimsUtil.setErrorMessage("姓名只能为汉字或字母");
                } else if (!"".equals(tel) && !RegexUntil.checkTelFormat(tel)) {
                    SimsUtil.setErrorMessage("请输入有效的电话号码");
                } else if (!"".equals(email) && !RegexUntil.checkEmailFormat(email)) {
                    SimsUtil.setErrorMessage("请输入有效的电子邮箱");
                } else if (account.length() > 13) {
                    SimsUtil.setErrorMessage("工号的长度不能大于13位");
                } else if (name.length() > 13) {
                    SimsUtil.setErrorMessage("名字不能大于13位");
                } else if (TeacherModule.queryTeacherIdByAccount(account) > 0 && !account.equals(currentAccount)) {
                    SimsUtil.setErrorMessage("工号已存在");
                } else if (TeacherModule.queryTeacherIdByTel(tel) > 0 && !tel.equals(currentTel)) {
                    SimsUtil.setErrorMessage("电话号码已存在");
                } else if (TeacherModule.queryTeacherIdByEmail(email) > 0 && !email.equals(currentEmail)) {
                    SimsUtil.setErrorMessage("电子邮箱已存在");
                } else {
                    teacher.settAccount(account);
                    teacher.settName(name);
                    teacher.settGender(gender);
                    teacher.settTel(tel);
                    teacher.settEmail(email);
                    teacher.settRemark(remark);
                    teacher.settUpdateTime(updTime);
                    teacher.setCollegeId(collegeId);
                    updTeacher(teacher);
                }
            });
            cancelBtn = new JButton("取消");
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row6.add(updBtn);
            row6.add(cancelBtn);
            add(row6);

            init(teacher.gettName());
        }

        private void init(String title) {
            setLayout(new GridLayout(9, 1));
            setTitle(title);
            setBounds(SimsUtil.setWidth(380, 2), SimsUtil.setHeight(600, 2), 380, 600);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        /**
         * @param teacher
         * @Author 赵富源
         * @Description 更新教师信息
         * @Return void
         */
        private void updTeacher(Teacher teacher) {
            int status = TeacherModule.updTeacherById(teacher);
            if (status > 0) {
                SimsUtil.setInfoMessage("成功更新" + teacher.gettName() + "的资料");
                dispose();
                int status2;
                int tcrId = TcRelationModule.queryTcrIdByTeacherId(teacher.gettId());
                //如果原先已经有所属院系信息就更新，没有则添加
                if (tcrId > 0) {
                    status2 = TcRelationModule.updTcRelation(teacher.getCollegeId(), teacher.gettId(), tcrId);
                } else {
                    status2 = TcRelationModule.addTcRelation(teacher.getCollegeId(), teacher.gettId());
                }
                if (status2 <= 0) {
                    SimsUtil.setErrorMessage("更新所属院系信息失败");
                }
            } else {
                SimsUtil.setErrorMessage("更新" + teacher.gettName() + "的资料失败");
            }
        }

        //添加备注子窗口
        private class RemarkTextArea extends JFrame {
            JPanel row;
            JTextArea textArea;
            JScrollPane scrollPane;
            JButton confirmBtn;
            JButton cancelBtn;

            private RemarkTextArea(String remarkText) {
                textArea = new JTextArea();
                textArea.setLineWrap(true);
                textArea.setText(remarkText);
                scrollPane = new JScrollPane(textArea);
                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                        HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.
                        VERTICAL_SCROLLBAR_AS_NEEDED);
                add(scrollPane);

                row = new JPanel();
                confirmBtn = new JButton("确定");
                confirmBtn.addActionListener(e -> {
                    remarkTextInfo = textArea.getText();
                    dispose();
                });
                cancelBtn = new JButton("取消");
                cancelBtn.addActionListener(e -> {
                    dispose();
                });
                row.add(confirmBtn);
                row.add(cancelBtn);
                add(row);

                init();
            }

            private void init() {
                setLayout(new GridLayout(2, 1));
                setTitle("更新备注");
                setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(500, 2), 500, 500);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setVisible(true);
            }
        }
    }
}
