package pres.zfy.eaims.core;

import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.LoginProfile;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:登录模块
 * @Author:赵富源
 * @CreateDate:2019.12.15 21:32
 */
public class LoginFrame extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JLabel account;
    JLabel pwd;
    JTextField accountText;
    JPasswordField pwdText;
    JButton loginBtn;
    JButton exitBtn;

    public LoginFrame() {
        row1 = new JPanel();
        account = new JLabel("工号：");
        accountText = new JTextField(10);
        row1.add(account);
        row1.add(accountText);
        add(row1);

        row2 = new JPanel();
        pwd = new JLabel("密码：");
        pwdText = new JPasswordField(10);
        row2.add(pwd);
        row2.add(pwdText);
        add(row2);

        row3 = new JPanel();
        loginBtn = new JButton("登入");
        loginBtn.addActionListener(e -> {
            String tAccount = accountText.getText();
            String tPwd = String.valueOf(pwdText.getPassword());
            if (tAccount.equals("")) {
                SimsUtil.setErrorMessage("请输入教师账号");
            } else if (tPwd.equals("")) {
                SimsUtil.setErrorMessage("请输入密码");
            } else {
                login(tAccount, tPwd);
            }
        });
        exitBtn = new JButton("退出");
        exitBtn.addActionListener(e -> {
            int status = SimsUtil.setConfirmMessage("确定退出吗?");
            if (status == JOptionPane.OK_OPTION) {
                dispose();
            }
        });
        row3.add(loginBtn);
        row3.add(exitBtn);
        add(row3);

        init();
    }

    private void init() {
        setLayout(new GridLayout(3, 1));
        setTitle("教师登入");
        setBounds(SimsUtil.setWidth(368, 2), SimsUtil.setHeight(208, 2), 368, 208);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param account
     * @param pwd
     * @Author 赵富源
     * @Description 登录验证
     * @Return void
     */
    private void login(String account, String pwd) {
        Teacher teacher = TeacherModule.queryTeacherByAccount(account);
        if (teacher.gettAccount() != null) {
            int tId = teacher.gettId();
            String tAccount = teacher.gettAccount();
            String tName = teacher.gettName();
            String tPwd = teacher.gettPwd();
            String tGender = teacher.gettGender();
            String tTel = teacher.gettTel();
            String tEmail = teacher.gettEmail();
            String tRemark = teacher.gettRemark();
            //判断密码是否正确
            if (!pwd.equals(tPwd)) {
                SimsUtil.setErrorMessage("密码错误");
            } else {
                SimsUtil.setInfoMessage("登入成功");
                //保存用户信息
                LoginProfile profile = new LoginProfile();
                profile.tId = tId;
                profile.tAccount = tAccount;
                profile.tName = tName;
                profile.tGender = tGender;
                profile.tTel = tTel;
                profile.tEmail = tEmail;
                profile.tRemark = tRemark;

                dispose();
                //开启主界面
                new MainFrame(profile);
            }
        } else {
            SimsUtil.setErrorMessage("该职工账号不存在");
        }
    }
}
