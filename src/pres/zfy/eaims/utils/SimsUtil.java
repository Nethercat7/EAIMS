package pres.zfy.eaims.utils;

import javax.swing.*;
import java.awt.*;

/**
 * @Description:常用方法
 * @Author:赵富源
 * @CreateDate:2019.12.15 0:31
 */
public class SimsUtil {
    /**
     * @param width
     * @param location
     * @Author 赵富源
     * @Description 设置宽度
     * @Return int
     */
    public static int setWidth(int width, int location) {
        return (Toolkit.getDefaultToolkit().getScreenSize().width - width) / location;
    }

    /**
     * @param height
     * @param location
     * @Author 赵富源
     * @Description 设置高度
     * @Return int
     */
    public static int setHeight(int height, int location) {
        return (Toolkit.getDefaultToolkit().getScreenSize().height - height) / location;
    }

    /**
     * @param msg
     * @Author 赵富源
     * @Description 信息弹窗
     * @Return void
     */
    public static void setInfoMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "E.A.I.M.S", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param msg
     * @Author 赵富源
     * @Description 警告弹窗
     * @Return void
     */
    public static void setWarningMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "E.A.I.M.S", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * @param msg
     * @Author 赵富源
     * @Description 错误弹窗
     * @Return void
     */
    public static void setErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "E.A.I.M.S", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @param msg
     * @Author 赵富源
     * @Description 确认弹窗
     * @Return int
     */
    public static int setConfirmMessage(String msg) {
        return JOptionPane.showConfirmDialog(null, msg, "E.A.I.M.S", JOptionPane.YES_NO_OPTION);
    }

}
