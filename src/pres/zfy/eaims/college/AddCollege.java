package pres.zfy.eaims.college;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:创建院系功能
 * @Author:赵富源
 * @CreateDate:2019.12.18 23:31
 */
public class AddCollege extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;

    JLabel cCode;
    JLabel cName;

    JTextField cCodeText;
    JTextField cNameText;

    JButton addBtn;
    JButton cancelBtn;

    public AddCollege() {
        row1 = new JPanel();
        cCode = new JLabel("院系编号：");
        cCodeText = new JTextField(10);
        row1.add(cCode);
        row1.add(cCodeText);
        add(row1);

        row2 = new JPanel();
        cName = new JLabel("院系名称：");
        cNameText = new JTextField(10);
        row2.add(cName);
        row2.add(cNameText);
        add(row2);

        row3 = new JPanel();
        addBtn = new JButton("创建");
        cancelBtn = new JButton("取消");
        addBtn.addActionListener(e -> {
            String code = cCodeText.getText();
            String name = cNameText.getText();
            //非空判断
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入院系编号");
            } else if ("".equals(name)) {
                SimsUtil.setErrorMessage("请输入院系名称");
            } else if (!RegexUntil.onlyLetterOrNumber(code)) {
                SimsUtil.setErrorMessage("院系编号只能为字母或数字");
            } else if (!RegexUntil.onlyChineseOrLetter(name)) {
                SimsUtil.setErrorMessage("院系名称只能为汉字或字母");
            } else if (code.length() > 30) {
                SimsUtil.setErrorMessage("院系编号最大长度为30");
            } else if (name.length() > 70) {
                SimsUtil.setErrorMessage("院系名称最大长度为70");
            } else {
                addCollege(code, name);
            }
        });
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row3.add(addBtn);
        row3.add(cancelBtn);
        add(row3);

        init();
    }

    private void init() {
        setLayout(new GridLayout(3, 1));
        setTitle("创建院系");
        setBounds(SimsUtil.setWidth(321, 2), SimsUtil.setHeight(226, 2), 321, 226);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param code
     * @param name
     * @Author 赵富源
     * @Description 创建学院
     * @Return void
     */
    private void addCollege(String code, String name) {
        College college = CollegeModule.queryCollegeByCodeOrName(code, name);
        //判断学院代号和名称是否已存在
        if (college.getcCode() != null && college.getcCode().equals(code)) {
            SimsUtil.setErrorMessage("学院编号已经存在");
        } else if (college.getcName() != null && college.getcName().equals(name)) {
            SimsUtil.setErrorMessage("学院名称已经存在");
        } else {
            //获取当前的系统时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp createTime = Timestamp.valueOf(sdf.format(new Date()));
            int status = CollegeModule.addCollege(code, name, createTime);
            if (status > 0) {
                SimsUtil.setInfoMessage("院系创建成功");
            } else {
                SimsUtil.setErrorMessage("院系创建失败");
            }
        }
    }
}
