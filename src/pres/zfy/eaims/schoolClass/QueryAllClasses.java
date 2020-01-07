package pres.zfy.eaims.schoolClass;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.db.SchoolClassModule;
import pres.zfy.eaims.entity.SchoolClass;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Description:查询所有班级
 * @Author:赵富源
 * @CreateDate:2019.12.23 20:49
 */
public class QueryAllClasses extends JFrame {
    JTable teachersTable;

    public QueryAllClasses() {
        Container container = getContentPane();
        List<SchoolClass> schoolClasses = SchoolClassModule.queryAllClasses();
        Object[][] tableData = new Object[schoolClasses.size()][7];
        for (int i = 0; i < schoolClasses.size(); i++) {
            tableData[i][0] = schoolClasses.get(i).getClassCode();
            tableData[i][1] = schoolClasses.get(i).getClassGrade();
            tableData[i][2] = schoolClasses.get(i).getClassName();
            tableData[i][3] = MajorModule.queryMajorNameById(schoolClasses.get(i).getMajorId());
            tableData[i][4] = CollegeModule.queryCollegeNameById(schoolClasses.get(i).getCollegeId());
            tableData[i][5] = schoolClasses.get(i).getClassCreateTime();
            tableData[i][6] = schoolClasses.get(i).getClassUpdateTime();
        }
        String[] title = {"班级编号", "所属年级", "班级名字", "所属专业", "所属院系", "创建时间", "资料最近修改时间"};
        teachersTable = new JTable(tableData, title);
        teachersTable.setEnabled(false);
        container.add(new JScrollPane(teachersTable));

        init();
    }

    private void init() {
        setTitle("所有班级信息");
        setBounds(SimsUtil.setWidth(1024, 2), SimsUtil.setHeight(768, 2), 1027, 768);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
