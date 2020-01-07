package pres.zfy.eaims.student;

import pres.zfy.eaims.db.ScmcRelationModule;
import pres.zfy.eaims.db.StudentModule;
import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description:删除学生信息
 * @Author:赵富源
 * @CreateDate:2019.12.24 16:26
 */
public class DelStudent extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton delBtn;
    JButton cancelBtn;

    public DelStudent() {
        row1 = new JPanel();
        code = new JLabel("学号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        delBtn = new JButton("删除");
        delBtn.addActionListener(new DelBtnListener());
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(new CancelBtnListener());
        row2.add(delBtn);
        row2.add(cancelBtn);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        setTitle("删除学生信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //删除按钮
    private class DelBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String code = codeText.getText();
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入要删除的学生学号");

            } else if (!RegexUntil.isNumber(code)) {
                SimsUtil.setErrorMessage("学号只能为数字");
            } else {
                delStudentByCode(code);
            }
        }
    }

    //取消按钮
    private class CancelBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    /**
    *@param code
    *@Author 赵富源
    *@Description 删除学生信息
    *@Return void
    */
    private void delStudentByCode(String code) {
        Student student = StudentModule.queryStudentByCode(code);
        if (student.getStudentCode() != null) {
            int confirm = SimsUtil.setConfirmMessage("确定要删除名字为 " + student.getStudentName() + " 的学生信息吗?");
            if (confirm == JOptionPane.OK_OPTION) {
                int status = ScmcRelationModule.delScmcRelation(student.getStudentId());
                if (status > 0) {
                    int status2 = StudentModule.delStudentByCode(code);
                    if (status2 > 0) {
                        SimsUtil.setInfoMessage("已删除名字为 " + student.getStudentName() + " 的学生信息");
                    } else {
                        SimsUtil.setErrorMessage("删除学生信息失败");
                    }
                } else {
                    SimsUtil.setErrorMessage("删除学生信息失败");
                }
            }
        } else {
            SimsUtil.setErrorMessage("学号不存在");
        }
    }
}
