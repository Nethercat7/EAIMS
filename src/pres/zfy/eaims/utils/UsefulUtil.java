package pres.zfy.eaims.utils;

/**
 * @Description:一些常用到的方法
 * @Author:赵富源
 * @CreateDate:2020.1.8 0:59
 */
public class UsefulUtil {

    /**
     * @param str
     * @Author 赵富源
     * @Description 字符串去重
     * @Return java.lang.String
     */
    public static String removeRepeat(String str) {
        StringBuffer sb = new StringBuffer(str);
        String rs = sb.reverse().toString().replaceAll("(.)(?=.*\\1)", "");
        StringBuffer out = new StringBuffer(rs);
        return out.reverse().toString();
    }

    /**
    *@param startNumber
    *@param endNumber
    *@Author 赵富源
    *@Description 获取指定范围的整形随机数
    *@Return int
    */
    public static int getRandomNumber(int startNumber, int endNumber) {
        return (int)   (Math.random() * (endNumber - startNumber + 1) + startNumber);
    }
}
