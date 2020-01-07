package pres.zfy.eaims.major;

import pres.zfy.eaims.db.CollegeModule;
import pres.zfy.eaims.db.MajorModule;
import pres.zfy.eaims.entity.Major;
import pres.zfy.eaims.utils.SimsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @Description:查询所有专业功能
 * @Author:赵富源
 * @CreateDate:2019.12.20 23:23
 */
public class QueryAllMajors extends JFrame {
    JTable teachersTable;

    public QueryAllMajors() {
        Container container = getContentPane();
        List<Major> majors = MajorModule.queryAllMajors();
        Object[][] tableData = new Object[majors.size()][5];
        for (int i = 0; i < majors.size(); i++) {
            tableData[i][0] = majors.get(i).getmCode();
            tableData[i][1] = majors.get(i).getmName();
            tableData[i][2] = CollegeModule.queryCollegeNameById(majors.get(i).getCollegeId());
            tableData[i][3] = majors.get(i).getmCreateTime();
            tableData[i][4] = majors.get(i).getmUpdTime();
        }
        String[] title = {"专业编号", "专业名字", "所属院系", "创建时间", "资料最近修改时间"};
        teachersTable = new JTable(tableData, title);
        teachersTable.setEnabled(false);
        container.add(new JScrollPane(teachersTable));

        init();
    }

    private void init() {
        setTitle("所有专业信息");
        setBounds(SimsUtil.setWidth(668, 2), SimsUtil.setHeight(427, 2), 668, 427);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
