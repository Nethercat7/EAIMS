package pres.zfy.eaims.major;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.McRelationModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:更新专业信息功能
 * @Author:赵富源
 * @CreateDate:2019.12.20 23:51
 */
public class UpdateMajor extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JButton confirmBtn;
    JButton cancelBtn;

    public UpdateMajor() {
        row1 = new JPanel();
        code = new JLabel("专业编号：");
        codeText = new JTextField(10);
        row1.add(code);
        row1.add(codeText);
        add(row1);

        row2 = new JPanel();
        confirmBtn = new JButton("确定");
        confirmBtn.addActionListener(e -> {
            String code = codeText.getText();
            if (!"".equals(code)) {
                Major major = MajorModule.queryMajorByCode(code);
                if (major.getmCode() != null) {
                    new CollegeInfo(major);
                    dispose();
                } else {
                    SimsUtil.setErrorMessage("专业不存在");
                }
            } else {
                SimsUtil.setErrorMessage("请输入专业编号");
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
        setTitle("更新专业信息");
        setBounds(SimsUtil.setWidth(344, 2), SimsUtil.setHeight(174, 2), 344, 174);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //子窗口
    private static class CollegeInfo extends JFrame {
        JPanel row1;
        JPanel row2;
        JPanel row3;
        JPanel row4;
        JLabel name;
        JLabel mCode;
        JLabel college;
        JTextField nameText;
        JTextField mCodeText;
        JButton updBtn;
        JButton cancelBtn;
        JComboBox<String> collegeComboBox;

        private CollegeInfo(Major major) {
            row3 = new JPanel();
            mCode = new JLabel("专业编号：");
            mCodeText = new JTextField(10);
            mCodeText.setText(major.getmCode());
            row3.add(mCode);
            row3.add(mCodeText);
            add(row3);

            row1 = new JPanel();
            name = new JLabel("专业名称：");
            nameText = new JTextField(10);
            nameText.setText(major.getmName());
            row1.add(name);
            row1.add(nameText);
            add(row1);

            //所属院系
            row4 = new JPanel();
            college = new JLabel("所属院系：");
            collegeComboBox = new JComboBox<>();
            List<College> colleges = CollegeModule.queryAllColleges();
            for (int i = 0; i < colleges.size(); i++) {
                collegeComboBox.addItem(colleges.get(i).getcName());
            }
            collegeComboBox.setSelectedItem(CollegeModule.queryCollegeNameById(major.getCollegeId()));
            row4.add(college);
            row4.add(collegeComboBox);
            add(row4);

            //更新和取消按钮
            row2 = new JPanel();
            updBtn = new JButton("更新");
            cancelBtn = new JButton("取消");
            updBtn.addActionListener(e -> {
                if ("".equals(mCodeText.getText())) {
                    SimsUtil.setErrorMessage("请输入专业编号");
                } else if ("".equals(nameText.getText())) {
                    SimsUtil.setErrorMessage("请输入专业名称");
                } else if (!RegexUntil.onlyLetterOrNumber(mCodeText.getText())) {
                    SimsUtil.setErrorMessage("专业编号只能为字母或者数字");
                } else if (!RegexUntil.onlyChineseOrLetter(nameText.getText())) {
                    SimsUtil.setErrorMessage("专业名称只能为汉字或者字母");
                } else if (mCodeText.getText().length() > 30) {
                    SimsUtil.setErrorMessage("专业代码最大为30位");
                } else if (nameText.getText().length() > 30) {
                    SimsUtil.setErrorMessage("专业名称最大为30位");
                } else {
                    major.setmCode(mCodeText.getText());
                    major.setmName(nameText.getText());
                    major.setCollegeId(CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem())));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    major.setmUpdTime(Timestamp.valueOf(sdf.format(new Date())));
                    updMajorInfo(major);
                }
            });
            row2.add(updBtn);
            cancelBtn.addActionListener(e -> {
                dispose();
            });
            row2.add(cancelBtn);
            add(row2);

            init(major.getmName());
        }

        private void init(String title) {
            setLayout(new GridLayout(4, 1));
            setTitle(title);
            setBounds(SimsUtil.setWidth(270, 2), SimsUtil.setHeight(270, 2), 270, 270);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setVisible(true);
        }

        /**
         * @param major
         * @Author 赵富源
         * @Description 更新专业信息
         * @Return void
         */
        private void updMajorInfo(Major major) {
            int status = MajorModule.updMajorById(major);
            if (status > 0) {
                SimsUtil.setInfoMessage("更新 " + major.getmName() + " 的数据成功");
                setTitle(major.getmName());
                int isExit = McRelationModule.queryMcrIdByMajorId(major.getmId());
                int status2 = 0;
                if (isExit > 0) {
                    status2 = McRelationModule.updMcRelation(major.getCollegeId(), major.getmId());
                } else {
                    status2 = McRelationModule.addMcRelation(major.getCollegeId(), major.getmId());
                }
                if (status2 <= 0) {
                    SimsUtil.setErrorMessage("更新所属院系关系时发生错误");
                }
            }
        }
    }
}
