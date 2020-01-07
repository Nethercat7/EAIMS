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
 * @Description:创建专业功能
 * @Author:赵富源
 * @CreateDate:2019.12.20 22:48
 */
public class AddMajor extends JFrame {
    JPanel row1;
    JPanel row2;
    JPanel row3;
    JPanel row4;
    JLabel mCode;
    JLabel mName;
    JLabel college;
    JTextField mCodeText;
    JTextField mNameText;
    JButton addBtn;
    JButton cancelBtn;
    JComboBox<String> collegeComboBox;

    public AddMajor() {
        row1 = new JPanel();
        mCode = new JLabel("专业编号：");
        mCodeText = new JTextField(10);
        row1.add(mCode);
        row1.add(mCodeText);
        add(row1);

        row2 = new JPanel();
        mName = new JLabel("专业名称：");
        mNameText = new JTextField(10);
        row2.add(mName);
        row2.add(mNameText);
        add(row2);

        row4 = new JPanel();
        college = new JLabel("所属院系：");
        collegeComboBox = new JComboBox<>();
        List<College> colleges = CollegeModule.queryAllColleges();
        for (int i = 0; i < colleges.size(); i++) {
            collegeComboBox.addItem(colleges.get(i).getcName());
        }
        row4.add(college);
        row4.add(collegeComboBox);
        add(row4);

        row3 = new JPanel();
        addBtn = new JButton("添加");
        cancelBtn = new JButton("取消");
        addBtn.addActionListener(e -> {
            String code = mCodeText.getText();
            String name = mNameText.getText();
            int collegeId = CollegeModule.queryCollegeIdByName(String.valueOf(collegeComboBox.getSelectedItem()));
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入专业编号");
            } else if ("".equals(name)) {
                SimsUtil.setErrorMessage("请输入专业名称");
            } else if (!RegexUntil.onlyLetterOrNumber(code)) {
                SimsUtil.setErrorMessage("专业编号只能为字母或数字");
            } else if (!RegexUntil.onlyChineseOrLetter(name)) {
                SimsUtil.setErrorMessage("专业名称只能为汉字或字母");
            } else if (code.length() > 30) {
                SimsUtil.setErrorMessage("专业编号最大长度为30位");
            } else if (name.length() > 30) {
                SimsUtil.setErrorMessage("专业名称最大长度为30位");
            } else {
                addMajor(code, name, collegeId);
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
        setLayout(new GridLayout(4, 1));
        setTitle("创建专业");
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
    private void addMajor(String code, String name, int collegeId) {
        Major major = MajorModule.queryMajorByCodeOrName(code, name);
        if (major.getmCode() != null && major.getmCode().equals(code)) {
            SimsUtil.setErrorMessage("专业编号已存在");
        } else if (major.getmName() != null && major.getmName().equals(name)) {
            SimsUtil.setErrorMessage("专业名称已存在");
        } else {
            major.setmCode(code);
            major.setmName(name);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            major.setmCreateTime(Timestamp.valueOf(sdf.format(new Date())));
            int majorId = MajorModule.addMajor(major);
            if (majorId > 0) {
                SimsUtil.setInfoMessage("专业" + name + "创建成功");
                //创建于院系的关系
                int status = McRelationModule.addMcRelation(collegeId,majorId);
                if(status<=0){
                    SimsUtil.setErrorMessage("添加所属院系时发生错误");
                }
            } else {
                SimsUtil.setErrorMessage("专业创建失败");
            }
        }
    }
}
