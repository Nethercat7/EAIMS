package pres.zfy.eaims.college;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

/**
 * @Description:通过院系编号查询院系功能
 * @Author:赵富源
 * @CreateDate:2019.12.19 1:00
 */
public class QueryCollegeByCode extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JTextArea textArea;
    JButton searchBtn;

    public QueryCollegeByCode() {
        row1 = new JPanel();
        code = new JLabel("院系编号：");
        row1.add(code);
        codeText = new JTextField(10);
        row1.add(codeText);
        searchBtn = new JButton("搜索");
        searchBtn.addActionListener(e -> {
            String code = codeText.getText();
            queryCollegeBycCode(code);
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
        setTitle("查询院系");
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
    private void queryCollegeBycCode(String code) {
        College college = CollegeModule.queryCollegeByCode(code);
        if (college.getcCode() != null) {
            String cCode = college.getcCode();
            String cName = college.getcName();
            Timestamp cCreateTime = college.getcCreateTime();
            Timestamp cUpdTime = college.getcUpdTime();
            textArea.setText("院系编号：" + cCode + "\n" + "院系名称：" + cName + "\n" + "创建时间：" + cCreateTime + "\n" + "资料最近修改时间：" + cUpdTime);
        } else {
            SimsUtil.setErrorMessage("该院系编号不存在");
        }
    }
}
