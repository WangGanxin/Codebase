package com.ganxin.codebase.utils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Pattern;

/**
 * Description : 通过 pinyin4j 将汉字转换为拼音  <br/>
 * author : WangGanxin <br/>
 * date : 2016/9/5 <br/>
 * email : ganxinvip@163.com <br/>
 */
public final class Pinyin4JUtil {
    /**
     * 匹配中文的正则表达式
     */
    private static final Pattern CHINESE_CHAR = Pattern.compile("[\\u4E00-\\u9FFF]+");

    /**
     * 将汉字转换为汉语拼音首字母，其他字母不变
     *
     * @param chinese 待转换字符串
     * @return 拼音首字母组成的字符串
     */
    public static String converterToFirstSpell(String chinese) {
        return converter(chinese, true);
    }

    private static String converter(String chinese, boolean first) {
        StringBuilder sb = new StringBuilder();
        if (chinese != null && chinese.length() > 0) {
            HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
            defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            for (char str : chinese.toCharArray()) {
                if (CHINESE_CHAR.matcher(String.valueOf(str)).find()) {
                    try {
                        String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(str, defaultFormat);
                        if (pinyinArr != null && pinyinArr.length > 0) {
                            String pinyin = pinyinArr[0];
                            if (pinyin != null && pinyin.length() > 0) {
                                if (first) {
                                    sb.append(pinyin.charAt(0));
                                } else {
                                    sb.append(pinyin);
                                }
                            }
                        }
                    } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                        badHanyuPinyinOutputFormatCombination.printStackTrace();
                    }
                } else {
                    sb.append(str);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 将汉字转换为汉语拼音，其他字符不变
     *
     * @param chinese 待转换字符串
     * @return 汉语拼音组成的字符串
     */
    public static String converterToSpell(String chinese) {
        return converter(chinese, false);
    }

    private Pinyin4JUtil() {
    }
}
