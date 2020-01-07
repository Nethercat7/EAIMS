package pres.zfy.eaims.college;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;


/**
 * @Description:删除院系功能
 * @Author:赵富源
 * @CreateDate:2019.12.19 1:08
 */
public class DelCollege extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton delBtn;
    JButton cancelBtn;

    public DelCollege() {
        row1 = new JPanel();
        code = new JLabel("院系编号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        delBtn = new JButton("删除");
        delBtn.addActionListener(e -> {
            String code = codeText.getText();
            if (!"".equals(code)) {
                delCollegeByCode(code);
            } else {
                SimsUtil.setErrorMessage("请输入要删除的院系编号");
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
        setTitle("删除院系");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void delCollegeByCode(String code) {
        College college = CollegeModule.queryCollegeByCode(code);
        //判断院系是否存在
        if (college.getcCode() != null) {
            String name = college.getcName();
            int confirm = SimsUtil.setConfirmMessage("确定删除" + name + "吗？删除后将不可恢复，且与之相关联的专业，班级和学生信息将丢失。");
            if (confirm == JOptionPane.OK_OPTION) {
                int status = CollegeModule.delCollegeByCode(code);
                if (status > 0) {
                    SimsUtil.setInfoMessage("删除成功");
                }
            }
        } else {
            SimsUtil.setErrorMessage("院系编号不存在");
        }
    }
}
