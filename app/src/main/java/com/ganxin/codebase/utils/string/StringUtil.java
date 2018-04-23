package com.ganxin.codebase.utils.string;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS;
import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;

/**
 * Description : 字符串工具类  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public class StringUtil {

    /**
     * 新添加号段:181,183,184,170,176,177,178,145,
     */
    private static final String MOBILE_REGEX = "^1(3[0-9]|4[5]|5[0-35-9]|7[0678]|8[0-9])\\d{8}";

    /**
     * 中国移动：China Mobile
     * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     */
    private static final String CM_REGEX = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}";

    /**
     * 中国联通：China Unicom
     * 130,131,132,152,155,156,185,186
     */
    private static final String CU_REGEX = "^1(3[0-2]|4[5]|5[256]|8[56])\\d{8}";

    /**
     * 中国电信：China Telecom
     * 133,1349,153,180,189
     */
    private static final String CT_REGEX = "^1((33|53|8[01349]|7[0678])[0-9]|349)\\d{7}";

    /**
     * 大陆地区固话及小灵通
     *  区号：010,020,021,022,023,024,025,027,028,029
     *  号码：七位或八位
     */
    private static final String PHS_REGEX = "^0(10|2[0-5789]|\\d{3})\\d{7,8}";

    private StringUtil() {

    }

    public static boolean isEmpty(String strName) {
        try {
            if (TextUtils.isEmpty(strName) || TextUtils.isEmpty(strName.trim())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是乱码
     *
     * @param strName
     * @return
     */
    public static boolean isMessyCode(String strName) {
        try {
            Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
            Matcher m = p.matcher(strName);
            String after = m.replaceAll("");
            String temp = after.replaceAll("\\p{P}", "");
            char[] ch = temp.trim().toCharArray();

            int length = (ch != null) ? ch.length : 0;
            for (int i = 0; i < length; i++) {
                char c = ch[i];
                if (!Character.isLetterOrDigit(c)) {
                    String str = "" + ch[i];
                    if (!str.matches("[\u4e00-\u9fa5]+")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 手机号验证
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobile);
        b = m.matches();
        return b;
    }

    /**
     * 手机号验证  added 2017-10-26
     *
     * @param phone
     * @return
     */
    public static boolean isMobile2(String phone) {
        if (phone == null || phone.length() != 11 || !phone.startsWith("1"))
            return false;

        Pattern mobilePattern = Pattern.compile(MOBILE_REGEX);
        Pattern cmPattern = Pattern.compile(CM_REGEX);
        Pattern cuPattern = Pattern.compile(CU_REGEX);
        Pattern ctPattern = Pattern.compile(CT_REGEX);

        Matcher mobileMatcher = mobilePattern.matcher(phone);
        Matcher cmMatcher = cmPattern.matcher(phone);
        Matcher cuMatcher = cuPattern.matcher(phone);
        Matcher ctMatcher = ctPattern.matcher(phone);

        if (mobileMatcher.matches() || cmMatcher.matches() || cuMatcher.matches() || ctMatcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        Pattern p = Pattern
                .compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        Matcher matcher = p.matcher(str);
        boolean matches = matcher.matches();
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }

    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 判断字符串是否为字母开头
     *
     * @param str
     * @return
     */
    public static boolean isStartWithEnglish(String str) {
        try {
            char c = str.charAt(0);
            int i = (int) c;

            if ((i >= 65 && i <= 90) || (i >= 97 && i <= 122)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(str);
        boolean matches = m.matches();
        return matches;
    }

    /**
     * 获取获取字符串编码格式
     *
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), "UTF-16"))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
            exception1.printStackTrace();
        }
        encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), "UTF-16"))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
            exception2.printStackTrace();
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), "UTF-16"))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
            exception3.printStackTrace();
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), "UTF-16"))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
            exception3.printStackTrace();
        }
        return "";
    }

    public static String getFileName(String path) {
        String fileName = path.substring(path.lastIndexOf("/") + 1,
                path.lastIndexOf("."));
        return fileName;
    }

    /**
     * 将汉字转成UTF—8
     *
     * @param s
     * @return
     */
    public static String toUTF8String(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try {
            char c;
            for (int i = 0; i < s.length(); i++) {
                c = s.charAt(i);
                if (c >= 0 && c <= 255) {
                    sb.append(c);
                } else {
                    byte[] b;
                    b = Character.toString(c).getBytes("utf-8");
                    for (int j = 0; j < b.length; j++) {
                        int k = b[j];
                        if (k < 0)
                            k += 256;
                        sb.append("%" + Integer.toHexString(k).toUpperCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /***
     * 阿拉伯数字字符串转中文小写数字字符串
     *
     * @param number
     * @return 中文小写数字字符串，最大 <b>十亿</b>
     */
    public static String numberToZNCH(Integer number) {
        String numberStr = String.valueOf(number);

        String[] aa = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿"};
        String[] bb = {"一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] ch = numberStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        int maxindex = ch.length;
        if (ch.length > 1 && ch[0] == '-') {
            sb.append("负");
            char[] temp = ch.clone();
            ch = new char[maxindex - 1];
            for (int i = 1; i < maxindex; i++) {
                ch[i - 1] = temp[i];
            }
            maxindex -= 1;
        }
        if (maxindex == 2) {
            for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
                if (ch[j] != 48) {
                    if (j == 0 && ch[j] == 49) {
                        sb.append(aa[i]);
                    } else {
                        sb.append(bb[ch[j] - 49] + aa[i]);
                    }
                }
            }
            return sb.toString();
        } else {
            for (int i = maxindex - 1, j = 0; i >= 0; i--, j++) {
                if (ch[j] != 48) {
                    sb.append(bb[ch[j] - 49] + aa[i]);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 去除Html标签、style样式、script脚本
     *
     * @param htmlString
     * @return
     */
    public static String clearHtml(String htmlString) {
        if (htmlString == null) {
            return null;
        }
        String htmlStr = htmlString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签
            textStr = htmlStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;// 返回文本字符串
    }

    /**
     * 是否包含中文
     * @param checkStr
     * @return
     */
    private static boolean containChinese(String checkStr) {
        if (!TextUtils.isEmpty(checkStr)) {
            char[] checkChars = checkStr.toCharArray();
            for (int i = 0; i < checkChars.length; i++) {
                char checkChar = checkChars[i];
                if (checkCharContainChinese(checkChar)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkCharContainChinese(char checkChar) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);
        if (CJK_UNIFIED_IDEOGRAPHS == ub || CJK_COMPATIBILITY_IDEOGRAPHS == ub || CJK_COMPATIBILITY_FORMS == ub ||
                CJK_RADICALS_SUPPLEMENT == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub) {
            return true;
        }
        return false;
    }
}
