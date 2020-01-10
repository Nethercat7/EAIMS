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
 * @Description:更新院系功能
 * @Author:赵富源
 * @CreateDate:2019.12.19 01:30
 */
public class UpdateCollege extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton confirmBtn;
    JButton cancelBtn;

    String currentCode;//更新之前的院系代码，用于判断是否存在
    String currentName;//更新之前院系名称，用于判断是否存在

    public UpdateCollege() {
        row1 = new JPanel();
        code = new JLabel("院系编号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            String code = codeText.getText();
            if (!"".equals(code)) {
                College college = CollegeModule.queryCollegeByCode(code);
                if (college.getcCode() != null) {
                    currentCode = college.getcCode();
                    currentName = college.getcName();
                    new CollegeInfo(college);
                    dispose();
                } else {
                    SimsUtil.setErrorMessage("工号不存在");
                }
            } else {
                SimsUtil.setErrorMessage("请输入工号");
            }
        });
        cancelBtn = new JButton("取消");
        cancelBtn.addActionListener(e -> {
            dispose();
        });
        row2.add(confirmBtn);
        row2.add(cancelBtn);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new GridLayout(2, 1));
        setTitle("更新院系信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //子窗口
    private class CollegeInfo extends JFrame {
        JPanel row1;
        JPanel row2;
        JPanel row3;
        JLabel name;
        JLabel cCode;
        JTextField nameText;
        JTextField cCodeText;
        JButton updBtn;
        JButton cancelBtn;

        private CollegeInfo(College college) {
            row3 = new JPanel();
            cCode = new JLabel("院系编号：");
            cCodeText = new JTextField(10);
            cCodeText.setText(college.getcCode());
            row3.add(cCode);
            row3.add(cCodeText);
            add(row3);

            row1 = new JPanel();
            name = new JLabel("院系名称：");
            nameText = new JTextField(10);
            nameText.setText(college.getcName());
            row1.add(name);
            row1.add(nameText);
            add(row1);

            row2 = new JPanel();
            updBtn = new JButton("更新");
            cancelBtn = new JButton("取消");
            updBtn.addActionListener(e -> {
                if ("".equals(cCodeText.getText())) {
                    SimsUtil.setErrorMessage("请输入院系编号");
                } else if ("".equals(nameText.getText())) {
                    SimsUtil.setErrorMessage("请输入院系名称");
                } else if (!RegexUntil.onlyLetterOrNumber(cCodeText.getText())) {
                    SimsUtil.setErrorMessage("院系编号只能为字母或数字");
                } else if (!RegexUntil.onlyChineseOrLetter(nameText.getText())) {
                    SimsUtil.setErrorMessage("院系名称只能为汉字或字母");
                } else if (cCodeText.getText().length() > 30) {
                    SimsUtil.setErrorMessage("院系编号最大长度为30");
                } else if (nameText.getText().length() > 70) {
                    SimsUtil.setErrorMessage("院系名称最大长度为70");
                } else if (CollegeModule.queryCollegeIdByCode(cCodeText.getText()) != 0 && !cCodeText.getText().equals(currentCode)) {
                    SimsUtil.setErrorMessage("院系代码已经存在");
                } else if (CollegeModule.queryCollegeIdByName(nameText.getText()) != 0 && !nameText.getText().equals(currentName)) {
                    SimsUtil.setErrorMessage("院系名称已经存在");
                } else {
                    college.setcId(CollegeModule.queryCollegeByCode(codeText.getText()).getcId());
                    college.setcCode(cCodeText.getText());
                    college.setcName(nameText.getText());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    college.setcUpdTime(Timestamp.valueOf(sdf.format(new Date())));
                    int status = CollegeModule.updCollegeById(college);
                    if (status > 0) {
                        SimsUtil.setInfoMessage("更新成功");
                        dispose();
                    } else {
                        SimsUtil.setErrorMessage("更新失败");
                    }
                }
            });
            row2.add(updBtn);
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row2.add(cancelBtn);
            add(row2);

            init(college.getcName());
        }

        private void init(String title) {
            setLayout(new GridLayout(3, 1));
            setTitle(title);
            setBounds(SimsUtil.setWidth(330, 2), SimsUtil.setHeight(320, 2), 330, 320);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }
    }
}
