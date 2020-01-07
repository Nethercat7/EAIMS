package pres.zfy.eaims.teacher;

import pres.zfy.eaims.db.TcRelationModule;
import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;


/**
 * @Description:删除教师功能
 * @Author:赵富源
 * @CreateDate:2019.12.16 23:55
 */
public class DelTeacher extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel account;
    JTextField accountText;
    JButton delBtn;
    JButton cancelBtn;

    public DelTeacher() {
        row1 = new JPanel();
        account = new JLabel("工号：");
        accountText = new JTextField(10);
        row1.add(account);
        row1.add(accountText);
        add(row1);

        row2 = new JPanel();
        delBtn = new JButton("删除");
        delBtn.addActionListener(e -> {
            String account = accountText.getText();
            if ("".equals(account)) {
                SimsUtil.setErrorMessage("请输入要删除的教师账号");
            } else if (!RegexUntil.isNumber(account)) {
                SimsUtil.setErrorMessage("工号只能为数字");
            } else {
                delTeacherByAccount(account);
            }
        });
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row2.add(delBtn);
        row2.add(cancelBtn);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        setTitle("删除教师");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param account
     * @Author 赵富源
     * @Description 通过工号删除教师
     * @Return void
     */
    private void delTeacherByAccount(String account) {
        Teacher teacher = TeacherModule.queryTeacherByAccount(account);
        //判断账号是否存在
        if (teacher.gettAccount() != null) {
            String name = teacher.gettName();
            int teacherId = teacher.gettId();
            int confirm = SimsUtil.setConfirmMessage("确定删除姓名为" + name + "的教师吗？删除后将不可恢复");
            if (confirm == JOptionPane.OK_OPTION) {
                //先删除关系，再删除教师
                int status = TcRelationModule.delTcRelation(teacherId);
                if (status > 0) {
                    int status2 = TeacherModule.delTeacherByAccount(teacher.gettAccount());
                    if (status2 > 0) {
                        SimsUtil.setInfoMessage("删除成功");
                    } else {
                        SimsUtil.setErrorMessage("删除失败");
                    }
                }
            }
        } else {
            SimsUtil.setErrorMessage("教师账号不存在");
        }
    }
}
