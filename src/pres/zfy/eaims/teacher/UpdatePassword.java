package pres.zfy.eaims.teacher;

import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:修改密码功能
 * @Author:赵富源
 * @CreateDate:2019.12.26 0:31
 */
public class UpdatePassword extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JPanel row4;

    JLabel oldPwd;
    JLabel newPwd;
    JLabel confirmPwd;

    JPasswordField oldPwdText;
    JPasswordField newPwdText;
    JPasswordField confirmPwdText;

    JButton updBtn;
    JButton cancelBtn;

    int id;

    public UpdatePassword(int id) {
        this.id = id;
        row1 = new JPanel();
        oldPwd = new JLabel("原密码：    ");
        oldPwdText = new JPasswordField(10);
        row1.add(oldPwd);
        row1.add(oldPwdText);
        add(row1);

        row2 = new JPanel();
        newPwd = new JLabel("新密码：    ");
        newPwdText = new JPasswordField(10);
        row2.add(newPwd);
        row2.add(newPwdText);
        add(row2);

        row3 = new JPanel();
        confirmPwd = new JLabel("确认密码：");
        confirmPwdText = new JPasswordField(10);
        row3.add(confirmPwd);
        row3.add(confirmPwdText);
        add(row3);

        row4 = new JPanel();
        updBtn = new JButton("修改");
        cancelBtn = new JButton("取消");
        updBtn.addActionListener(e -> {
            String oldPwd = String.valueOf(oldPwdText.getPassword());
            String newPwd = String.valueOf(newPwdText.getPassword());
            String confirmPwd = String.valueOf(confirmPwdText.getPassword());
            if ("".equals(oldPwd)) {
                SimsUtil.setErrorMessage("请输入原密码");
            } else if ("".equals(newPwd)) {
                SimsUtil.setErrorMessage("请输入新密码");
            } else if ("".equals(confirmPwd)) {
                SimsUtil.setErrorMessage("请确认新密码");
            } else if (!RegexUntil.checkPwdFormat(oldPwd) || !RegexUntil.checkPwdFormat(newPwd)) {
                SimsUtil.setErrorMessage("密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线");
            } else if (!newPwd.equals(confirmPwd)) {
                SimsUtil.setErrorMessage("两次输入的密码不一致");
            } else {
                updPwd(id, oldPwd, newPwd);
            }
        });
        row4.add(updBtn);
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row4.add(cancelBtn);
        add(row4);

        init();
    }

    private void init() {
        setLayout(new GridLayout(4, 1));
        setTitle("修改密码");
        setBounds(SimsUtil.setWidth(260, 2), SimsUtil.setHeight(350, 2), 260, 350);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param id
     * @param oldPwd
     * @param newPwd
     * @Author 赵富源
     * @Description 更新密码
     * @Return void
     */
    private void updPwd(int id, String oldPwd, String newPwd) {
        //判断原密码是否正确
        Teacher teacher = TeacherModule.queryTeacherById(id);
        if (teacher.gettPwd().equals(oldPwd)) {
            int status = TeacherModule.updPasswordById(newPwd, id);
            if (status > 0) {
                SimsUtil.setInfoMessage("更新密码成功");
                dispose();
            } else {
                SimsUtil.setErrorMessage("更新密码失败");
            }
        } else {
            SimsUtil.setErrorMessage("原密码错误");
        }
    }
}
