package pres.zfy.eaims.schoolClass;

import pres.zfy.eaims.db.CmcRelationModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.entity.SchoolClass;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:删除班级功能
 * @Author:赵富源
 * @CreateDate:2019.12.23 19:56
 */
public class DelSchoolClass extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton delBtn;
    JButton cancelBtn;

    public DelSchoolClass() {
        row1 = new JPanel();
        code = new JLabel("班级编号：");
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
                SimsUtil.setErrorMessage("请输入要删除的班级编号");
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
        setTitle("删除班级");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 删除班级
     * @Return void
     */
    private void delCollegeByCode(String code) {
        SchoolClass schoolClass = SchoolClassModule.queryClassByClassCode(code);
        //判断是否存在
        if (schoolClass.getClassCode() != null) {
            String name = schoolClass.getClassName();
            int confirm = SimsUtil.setConfirmMessage("确定删除" + name + "吗？删除后将不可恢复，且与之相关联的学生信息将丢失。");
            if (confirm == JOptionPane.OK_OPTION) {
                int status = CmcRelationModule.delCmcRelation(schoolClass.getClassId());
                if (status > 0) {
                    int status2 = SchoolClassModule.delSchoolClassByClassCode(code);
                    if (status2 > 0) {
                        SimsUtil.setInfoMessage("删除成功");
                    } else {
                        SimsUtil.setErrorMessage("删除失败");
                    }
                }
            }
        } else {
            SimsUtil.setErrorMessage("班级编号不存在");
        }
    }
}
