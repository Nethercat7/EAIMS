package pres.zfy.eaims.college;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.entity.College;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Description:查询所有院系功能
 * @Author:赵富源
 * @CreateDate:2019.12.19 0:36
 */
public class QueryAllColleges extends JFrame {
    JTable teachersTable;

    public QueryAllColleges() {
        Container container = getContentPane();
        List<College> colleges = CollegeModule.queryAllColleges();
        Object[][] tableData = new Object[colleges.size()][4];
        for (int i = 0; i < colleges.size(); i++) {
            tableData[i][0] = colleges.get(i).getcCode();
            tableData[i][1] = colleges.get(i).getcName();
            tableData[i][2] = colleges.get(i).getcCreateTime();
            tableData[i][3] = colleges.get(i).getcUpdTime();
        }
        String[] title = {"院系编号", "院系名字", "创建时间", "资料最近修改时间"};
        teachersTable = new JTable(tableData, title);
        teachersTable.setEnabled(false);
        container.add(new JScrollPane(teachersTable));

        init();
    }

    private void init() {
        setTitle("所有院系信息");
        setBounds(SimsUtil.setWidth(508, 2), SimsUtil.setHeight(429, 2), 508, 427);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
