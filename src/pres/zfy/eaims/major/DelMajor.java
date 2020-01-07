package pres.zfy.eaims.major;

import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.McRelationModule;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;


/**
 * @Description:删除专业功能
 * @Author:赵富源
 * @CreateDate:2019.12.20 23:44
 */
public class DelMajor extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton delBtn;
    JButton cancelBtn;

    public DelMajor() {
        row1 = new JPanel();
        code = new JLabel("专业编号：");
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
                SimsUtil.setErrorMessage("请输入要删除的专业编号");
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
        setTitle("删除专业");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void delCollegeByCode(String code) {
        Major major = MajorModule.queryMajorByCode(code);
        //判断是否存在
        if (major.getmCode() != null) {
            String name = major.getmName();
            int confirm = SimsUtil.setConfirmMessage("确定删除" + name + "吗？删除后将不可恢复，且与之相关联的班级和学生信息将丢失。");
            if (confirm == JOptionPane.OK_OPTION) {
                //先删除与院系关系，再删除专业
                int status = McRelationModule.delMcRelation(major.getmId());
                if (status > 0) {
                    int status2 = MajorModule.delMajorByCode(code);
                    if (status2 > 0) {
                        SimsUtil.setInfoMessage("删除成功");
                    } else {
                        SimsUtil.setInfoMessage("删除失败");
                    }
                }
            }
        } else {
            SimsUtil.setErrorMessage("专业编号不存在");
        }
    }
}
