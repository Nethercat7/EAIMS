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
 * @Description:添加教师功能
 * @Author:赵富源
 * @CreateDate:2019.12.16 1:23
 */
public class AddTeacher extends JFrame {
    JPanel row1; //工号
    JPanel row2; //姓名
    JPanel row3; //密码
    JPanel row4; //性别
    JPanel row5; //电话号码
    JPanel row6; //电子邮箱
    JPanel row7; //备注
    JPanel row8; //添加删除按钮
    JPanel row9; //所属院系

    JLabel account;
    JLabel name;
    JLabel pwd;
    JLabel gender;
    JLabel tel;
    JLabel email;
    JLabel remark;
    JLabel college;

    ButtonGroup choose;

    JRadioButton male;
    JRadioButton female;

    JTextField accountText;
    JTextField nameText;
    JTextField telText;
    JTextField emailText;

    JTextArea remarkText;

    JPasswordField pwdText;

    JButton addBtn, cancelBtn, remarkBtn;

    JComboBox<String> collegeComboBox;

    public AddTeacher() {
        row1 = new JPanel();
        account = new JLabel("工号：      ");
        accountText = new JTextField(10);
        row1.add(account);
        row1.add(accountText);
        add(row1);

        row2 = new JPanel();
        name = new JLabel("姓名：        ");
        nameText = new JTextField(10);
        row2.add(name);
        row2.add(nameText);
        add(row2);

        row3 = new JPanel();
        pwd = new JLabel("密码：        ");
        pwdText = new JPasswordField(10);
        row3.add(pwd);
        row3.add(pwdText);
        add(row3);

        row4 = new JPanel();
        gender = new JLabel("性别：        ");
        choose = new ButtonGroup();
        male = new JRadioButton("男");
        female = new JRadioButton("女");
        choose.add(male);
        choose.add(female);
        row4.add(gender);
        row4.add(male);
        row4.add(female);
        add(row4);

        row5 = new JPanel();
        tel = new JLabel("电话号码：");
        telText = new JTextField(10);
        row5.add(tel);
        row5.add(telText);
        add(row5);

        row6 = new JPanel();
        email = new JLabel("电子邮箱：");
        emailText = new JTextField(10);
        row6.add(email);
        row6.add(emailText);
        add(row6);

        row9=new JPanel();
        college=new JLabel("所属院系：");
        collegeComboBox=new JComboBox<>();
        List<College> colleges=CollegeModule.queryAllColleges();
        for(int i=0;i<colleges.size();i++){
            collegeComboBox.addItem(colleges.get(i).getcName());
        }
        row9.add(college);
        row9.add(collegeComboBox);
        add(row9);

        row7 = new JPanel();
        remark = new JLabel("备注：        ");
        remarkText = new JTextArea();
        remarkText.setVisible(false);
        remarkBtn = new JButton("添加备注");
        remarkBtn.addActionListener(e -> {
            new RemarkTextArea();
        });
        row7.add(remark);
        row7.add(remarkBtn);
        add(row7);

        row8 = new JPanel();
        addBtn = new JButton("添加");
        addBtn.addActionListener(e -> {
            String teacherAccount = accountText.getText();
            String teacherName = nameText.getText();
            String teacherPwd = "";
            if (!"".equals(String.valueOf(pwdText.getPassword()))) {
                teacherPwd = String.valueOf(pwdText.getPassword());
            } else {
                teacherPwd = "asd123456";
            }
            String teacherGender = "";
            if (male.isSelected()) {
                teacherGender = male.getText();
            } else if (female.isSelected()) {
                teacherGender = female.getText();
            }
            String teacherTel = telText.getText();
            String teacherEmail = emailText.getText();
            String teacherRemark = remarkText.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Timestamp teacherJoinedTime = Timestamp.valueOf(sdf.format(new Date()));
            //空值判断
            if (teacherAccount.equals("")) {
                SimsUtil.setErrorMessage("请输入工号");
            } else if (teacherName.equals("")) {
                SimsUtil.setErrorMessage("请输入姓名");
            } else if (teacherGender.equals("")) {
                SimsUtil.setErrorMessage("请选择性别");
            } else if (!RegexUntil.isNumber(teacherAccount)) {
                SimsUtil.setErrorMessage("工号只能为数字");
            } else if (!RegexUntil.onlyChineseOrLetter(teacherName)) {
                SimsUtil.setErrorMessage("名字只能为汉字或字母");
            } else if (!RegexUntil.checkPwdFormat(teacherPwd)) {
                SimsUtil.setErrorMessage("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线");
            } else if (!"".equals(teacherTel) && !RegexUntil.checkTelFormat(teacherTel)) {
                SimsUtil.setErrorMessage("请输入有效的手机号码");
            } else if (!"".equals(teacherEmail) && !RegexUntil.checkEmailFormat(teacherEmail)) {
                SimsUtil.setErrorMessage("请输入有效的邮箱地址");
            } else if (teacherAccount.length() > 13) {
                SimsUtil.setErrorMessage("工号的长度不能超过13位");
            } else if (teacherName.length() > 13) {
                SimsUtil.setErrorMessage("姓名的长度不能超过13位");
            } else {
                Teacher teacher = new Teacher();
                teacher.settAccount(teacherAccount);
                teacher.settName(teacherName);
                teacher.settPwd(teacherPwd);
                teacher.settGender(teacherGender);
                teacher.settTel(teacherTel);
                teacher.settEmail(teacherEmail);
                teacher.settRemark(teacherRemark);
                teacher.settJoinedTime(teacherJoinedTime);
                String collegeName=String.valueOf(collegeComboBox.getSelectedItem());
                addTeacher(teacher,collegeName);
            }
        });
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row8.add(addBtn);
        row8.add(cancelBtn);
        add(row8);

        init();
    }

    private void init() {
        setLayout(new GridLayout(9, 1));
        setTitle("添加教师");
        setBounds(SimsUtil.setWidth(403, 2), SimsUtil.setHeight(504, 2), 403, 504);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param teacher
     * @Author 赵富源
     * @Description 添加教师
     * @Return void
     */
    private void addTeacher(Teacher teacher,String collegeName) {
        //判断账号，手机号，邮箱号是否存在
        Teacher accountItExist = TeacherModule.queryTeacherByAccount(teacher.gettAccount());
        Teacher telDoesItExist = new Teacher();
        Teacher emailDoesItExist = new Teacher();

        if (!"".equals(teacher.gettTel())) {
            telDoesItExist = TeacherModule.queryTeacherByTel(teacher.gettTel());
        }
        if (!"".equals(teacher.gettEmail())) {
            emailDoesItExist = TeacherModule.queryTeacherByEmail(teacher.gettEmail());
        }

        //通过判断的话就执行添加操作
        if (accountItExist.gettAccount() != null) {
            SimsUtil.setErrorMessage("工号已存在");
        } else if (telDoesItExist.gettAccount() != null) {
            SimsUtil.setErrorMessage("电话号码已存在");
        } else if (emailDoesItExist.gettAccount() != null) {
            SimsUtil.setErrorMessage("电子邮箱已存在");
        } else {
            //先添加教师，然后再添加教师与院系的关系。
            int teacherId = TeacherModule.addTeacher(teacher);
            if (teacherId > 0) {
                SimsUtil.setInfoMessage("添加成功");
                //添加与院系的关系
                int collegeId=CollegeModule.queryCollegeIdByName(collegeName);
                int status=TcRelationModule.addTcRelation(collegeId,teacherId);
                if(status<=0){
                    SimsUtil.setInfoMessage("添加所属院系时发生错误");
                }
            } else {
                SimsUtil.setErrorMessage("添加失败");
            }
        }
    }

    //添加备注子窗口
    public class RemarkTextArea extends JFrame {
        JPanel row;
        JTextArea textArea;
        JScrollPane scrollPane;
        JButton confirmBtn;
        JButton cancelBtn;

        public RemarkTextArea() {

            textArea = new JTextArea();
            textArea.setText(remarkText.getText());
            textArea.setLineWrap(true);
            scrollPane = new JScrollPane(textArea);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            add(scrollPane);

            row = new JPanel();
            confirmBtn = new JButton("确定");
            confirmBtn.addActionListener(e -> {
                remarkText.setText(textArea.getText());
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
            setTitle("添加备注");
            setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(500, 2), 500, 500);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}
