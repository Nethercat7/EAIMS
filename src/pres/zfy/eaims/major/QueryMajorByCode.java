package pres.zfy.eaims.major;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

/**
 * @Description:通过专业编号查询专业功能
 * @Author:赵富源
 * @CreateDate:2019.12.20 23:34
 */
public class QueryMajorByCode extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JTextArea textArea;
    JButton searchBtn;

    public QueryMajorByCode() {
        row1 = new JPanel();
        code = new JLabel("专业编号：");
        row1.add(code);
        codeText = new JTextField(10);
        row1.add(codeText);
        searchBtn = new JButton("搜索");
        searchBtn.addActionListener(e -> {
            String code = codeText.getText();
            queryMajorBymCode(code);
        });
        row1.add(searchBtn);
        add(row1);

        row2 = new JPanel();
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        row2.add(textArea);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setTitle("查询专业信息");
        setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(300, 2), 500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过院系编号查询院系
     * @Return void
     */
    private  void queryMajorBymCode(String code) {
        Major major = MajorModule.queryMajorByCode(code);
        if (major.getmCode() != null) {
            String mCode = major.getmCode();
            String mName = major.getmName();
            String collegeName= CollegeModule.queryCollegeNameById(major.getCollegeId());
            Timestamp mCreateTime = major.getmCreateTime();
            Timestamp mUpdTime = major.getmUpdTime();
            textArea.setText("专业编号：" + mCode + "\n" + "专业名称：" + mName + "\n" +"所属院系："+collegeName+"\n"+ "创建时间：" + mCreateTime + "\n" + "资料最近修改时间：" + mUpdTime);
        } else {
            SimsUtil.setErrorMessage("该专业编号不存在");
        }
    }
}
