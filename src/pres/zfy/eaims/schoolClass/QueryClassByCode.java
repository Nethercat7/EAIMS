package pres.zfy.eaims.schoolClass;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.entity.SchoolClass;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;

/**
 * @Description:通过班级编号查询班级功能
 * @Author:赵富源
 * @CreateDate:2019.12.23 22:04
 */
public class QueryClassByCode extends JFrame {
    JPanel row1, row2;
    JLabel code;
    JTextField codeText;
    JTextArea textArea;
    JButton searchBtn;

    public QueryClassByCode() {
        row1 = new JPanel();
        code = new JLabel("班级编号：");
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
        setTitle("查询班级信息");
        setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(300, 2), 500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    /**
     * @param code
     * @Author 赵富源
     * @Description 通过班级编号查询班级
     * @Return void
     */
    private void queryMajorBymCode(String code) {
        SchoolClass schoolClass = SchoolClassModule.queryClassByClassCode(code);
        if (schoolClass.getClassCode() != null) {
            String classCode = schoolClass.getClassCode();
            String classGrade = schoolClass.getClassGrade();
            String className = schoolClass.getClassName();
            Timestamp classCreateTime = schoolClass.getClassCreateTime();
            Timestamp classUpdateTime = schoolClass.getClassUpdateTime();
            String majorName= MajorModule.queryMajorNameById(schoolClass.getMajorId());
            String collegeName= CollegeModule.queryCollegeNameById(schoolClass.getCollegeId());
            textArea.setText("班级编号：" + classCode + "\n" + "所属年级：" + classGrade + "\n" + "班级名称：" + className + "\n"
                    +"所属专业："+majorName+"\n"+"所属院系："+collegeName+"\n"+ "创建时间：" + classCreateTime + "\n" + "资料最近修改时间：" + classUpdateTime);
        } else {
            SimsUtil.setErrorMessage("班级不存在");
        }
    }
}
