package pres.zfy.eaims.student;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.db.StudentModule;
import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.utils.RegexUntil;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description:通过学号查询学生信息
 * @Author:赵富源
 * @CreateDate:2019.12.24 18:11
 */
public class QueryStudentByCode extends JFrame {
    JPanel row1;
    JPanel row2;
    JLabel code;
    JTextField codeText;
    JTextArea textArea;
    JButton searchBtn;

    public QueryStudentByCode() {
        row1 = new JPanel();
        code = new JLabel("学号：");
        row1.add(code);
        codeText = new JTextField(10);
        row1.add(codeText);
        searchBtn = new JButton("搜索");
        searchBtn.addActionListener(new SearchBtnListener());
        row1.add(searchBtn);
        add(row1);

        row2 = new JPanel();
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        JScrollPane pane = new JScrollPane(textArea);
        add(pane);
        add(row2);

        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setTitle("查询学生信息");
        setBounds(SimsUtil.setWidth(500, 2), SimsUtil.setHeight(300, 2), 500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //搜索按钮
    private class SearchBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String code = codeText.getText();
            if ("".equals(code)) {
                SimsUtil.setErrorMessage("请输入学号");
            } else if (!RegexUntil.isNumber(code)) {
                SimsUtil.setErrorMessage("学号只能为数字");
            } else {
                queryStudentByCode(code);
            }
        }
    }

    private void queryStudentByCode(String code) {
        Student student = StudentModule.queryStudentByCode(code);
        if (student.getStudentCode() != null) {
            String name = student.getStudentName();
            String gender = student.getStudentGender();
            String tel = student.getStudentTel();
            String joinedTime = String.valueOf(student.getStudentJoinedTime());
            String updTime = String.valueOf(student.getStudentUpdatedTime());
            String remark = student.getStudentRemark();
            String grade=student.getStudentGrade();
            String schoolClass= SchoolClassModule.queryClassNameById(student.getClassId());
            String major= MajorModule.queryMajorNameById(student.getMajorId());
            String college= CollegeModule.queryCollegeNameById(student.getCollegeId());
            textArea.setText("学号：" + code + "\n" + "姓名：" + name + "\n" + "性别：" + gender + "\n" + "电话号码：" + tel+"\n"
                    +"年级："+grade+"\n"+"所属班级："+schoolClass+"\n"+"所属专业："+major+"\n"+"所属学院："+college+"\n" + "入学时间："
                    + joinedTime + "\n" + "资料最近修改时间：" + updTime + "\n" + "备注：" + remark);
        } else {
            SimsUtil.setErrorMessage("学号不存在");
        }
    }
}
