package pres.zfy.eaims.teacher;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

/**
 * @Description:查询教师功能
 * @Author:赵富源
 * @CreateDate:2019.12.16 22:22
 */
public class QueryTeacherByAccount extends JFrame {
    JPanel row1;//条件区域
    JPanel row2;//数据数据显示区域
    JLabel account;
    JTextField accountText;
    JTextArea textArea;
    JButton searchBtn;
    JScrollPane scrollPane;

    public QueryTeacherByAccount() {
        row1 = new JPanel();
        account = new JLabel("工号：");
        row1.add(account);
        accountText = new JTextField(10);
        row1.add(accountText);
        searchBtn = new JButton("搜索");
        searchBtn.addActionListener(e -> {
            String account = accountText.getText();
            if ("".equals(account)) {
                SimsUtil.setErrorMessage("请输入工号");
            } else if (!RegexUntil.isNumber(account)) {
                SimsUtil.setErrorMessage("工号只能为数字");
            } else {
                queryTeacherInfo(account);
            }
        });
        row1.add(searchBtn);
        add(row1);

        row2 = new JPanel();
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        row2.add(scrollPane);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setTitle("查询教师信息");
        setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(300, 2), 500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param account
     * @Author 赵富源
     * @Description 通过工号查询教师
     * @Return void
     */
    private void queryTeacherInfo(String account) {
        Teacher teacher = TeacherModule.queryTeacherByAccount(account);
        if (teacher.gettAccount() != null) {
            String tAccount = teacher.gettAccount();
            String name = teacher.gettName();
            String gender = teacher.gettGender();
            String tel = teacher.gettTel();
            String email = teacher.gettEmail();
            String remark = teacher.gettRemark();
            String collegeName = CollegeModule.queryCollegeNameById(teacher.getCollegeId());
            Timestamp joinedTime = teacher.gettJoinedTime();
            Timestamp updateTime = teacher.gettUpdateTime();
            textArea.setText("工号：" + tAccount + "\n" + "姓名：" + name + "\n" + "性别：" + gender + "\n" + "电话号码：" + tel + "\n" + "电子邮箱：" + email + "\n" + "所属院系："
                    + collegeName + "\n" + "备注：" + remark + "\n"+"入职时间："+joinedTime+"\n"+"资料最近修改时间："+updateTime);
        } else {
            SimsUtil.setErrorMessage("该教师账号不存在");
        }
    }
}
