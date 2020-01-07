package pres.zfy.eaims.teacher;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.TeacherModule;
import pres.zfy.eaims.entity.Teacher;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Description:查询所有教师功能
 * @Author:赵富源
 * @CreateDate:2019.12.16 19:07
 */
public class QueryAllTeachers extends JFrame {
    JTable teachersTable;

    public QueryAllTeachers() {
        Container container = getContentPane();
        List<Teacher> teachers = TeacherModule.queryAllTeacher();
        Object[][] tableData = new Object[teachers.size()][9];
        for (int i = 0; i < teachers.size(); i++) {
            tableData[i][0] = teachers.get(i).gettAccount();
            tableData[i][1] = teachers.get(i).gettName();
            tableData[i][2] = teachers.get(i).gettGender();
            tableData[i][3] = teachers.get(i).gettTel();
            tableData[i][4] = teachers.get(i).gettEmail();
            tableData[i][5] = CollegeModule.queryCollegeNameById(teachers.get(i).getCollegeId());
            tableData[i][6] = teachers.get(i).gettRemark();
            tableData[i][7] = teachers.get(i).gettJoinedTime();
            tableData[i][8] = teachers.get(i).gettUpdateTime();
        }
        String[] title = {"工号", "姓名", "性别", "电话号码", "电子邮箱", "所属院系", "备注", "入职时间", "资料最近修改时间"};
        teachersTable = new JTable(tableData, title);
        teachersTable.setEnabled(false);
        container.add(new JScrollPane(teachersTable));

        init();
    }

    private void init() {
        setTitle("所有教师信息");
        setBounds(SimsUtil.setWidth(1280, 2), SimsUtil.setHeight(720, 2), 1280, 720);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
