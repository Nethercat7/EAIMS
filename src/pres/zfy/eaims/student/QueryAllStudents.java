package pres.zfy.eaims.student;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.db.StudentModule;
import pres.zfy.eaims.entity.Student;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Description:查询所有学生信息
 * @Author:赵富源
 * @CreateDate:2019.12.24 17:54
 */
public class QueryAllStudents extends JFrame {
    JTable teachersTable;

    public QueryAllStudents() {
        Container container = getContentPane();
        List<Student> students = StudentModule.queryALLStudents();
        Object[][] tableData = new Object[students.size()][11];
        for (int i = 0; i < students.size(); i++) {
            tableData[i][0] = students.get(i).getStudentCode();
            tableData[i][1] = students.get(i).getStudentName();
            tableData[i][2] = students.get(i).getStudentGender();
            tableData[i][3] = students.get(i).getStudentTel();
            tableData[i][4] = students.get(i).getStudentGrade();
            tableData[i][5] = SchoolClassModule.queryClassNameById(students.get(i).getClassId());
            tableData[i][6] = MajorModule.queryMajorNameById(students.get(i).getMajorId());
            tableData[i][7] = CollegeModule.queryCollegeNameById(students.get(i).getCollegeId());
            tableData[i][8] = students.get(i).getStudentJoinedTime();
            tableData[i][9] = students.get(i).getStudentUpdatedTime();
            tableData[i][10] = students.get(i).getStudentRemark();
        }
        String[] title = {"学号", "姓名", "性别", "电话号码","年级","所属班级","所属专业","所属学院", "入学时间", "资料最近修改时间", "备注"};
        teachersTable = new JTable(tableData, title);
        teachersTable.setEnabled(false);
        container.add(new JScrollPane(teachersTable));

        init();
    }

    private void init() {
        setTitle("所有学生信息");
        setBounds(SimsUtil.setWidth(1280, 2), SimsUtil.setHeight(720, 2), 1280, 720);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
