package pres.zfy.eaims.utils;

import java.util.regex.Pattern;

/**
 * @Description:正则表达式
 * @Author:赵富源
 * @CreateDate:2019.12.25 0:28
 */
public class RegexUntil {
    public static final String CHINESE_REGEX = "^[\\u4e00-\\u9fa5]{0,}$";
    public static final String NUMBER_REGEX = "^[0-9]*$";
    public static final String LETTER_REGEX = "^[A-Za-z]+$";
    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static final String TEL_REGEX = "^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}$";
    public static final String ONLY_CHINESE_OR_LETTER = "^[a-zA-Z\\u4e00-\\u9fa5]+$";
    public static final String ONLY_LETTER_OR_NUMBER = "^[A-Za-z0-9]+$";
    public static final String PWD_REGEX = "^[a-zA-Z]\\w{5,17}$";

    /**
     * @param str
     * @Author 赵富源
     * @Description 判断是否为中文
     * @Return java.lang.Boolean
     */
    public static Boolean isChinese(String str) {
        return Pattern.compile(CHINESE_REGEX).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 判断是否为数字
     * @Return java.lang.Boolean
     */
    public static Boolean isNumber(String str) {
        return Pattern.compile(NUMBER_REGEX).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 判断是否为字母
     * @Return java.lang.Boolean
     */
    public static Boolean isLetter(String str) {
        return Pattern.compile(LETTER_REGEX).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 判断邮箱格式是否正确
     * @Return java.lang.Boolean
     */
    public static Boolean checkEmailFormat(String str) {
        return Pattern.compile(EMAIL_REGEX).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 判断手机号码格式是否正确
     * @Return java.lang.Boolean
     */
    public static Boolean checkTelFormat(String str) {
        return Pattern.compile(TEL_REGEX).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 只能为汉字或字母
     * @Return java.lang.Boolean
     */
    public static Boolean onlyChineseOrLetter(String str) {
        return Pattern.compile(ONLY_CHINESE_OR_LETTER).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 只能为字母或数字
     * @Return java.lang.Boolean
     */
    public static Boolean onlyLetterOrNumber(String str) {
        return Pattern.compile(ONLY_LETTER_OR_NUMBER).matcher(str).matches();
    }

    /**
     * @param str
     * @Author 赵富源
     * @Description 密码格式检查
     * @Return java.lang.Boolean
     */
    public static Boolean checkPwdFormat(String str) {
        return Pattern.compile(PWD_REGEX).matcher(str).matches();
    }

}
