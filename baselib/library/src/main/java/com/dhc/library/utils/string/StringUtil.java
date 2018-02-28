package com.dhc.library.utils.string;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {


    public static String getPercentString(float percent) {
        return String.format(Locale.US, "%d%%", (int) (percent * 100));
    }

    /**
     * 删除字符串中的空白符
     *
     * @param content
     * @return String
     */
    public static String removeBlanks(String content) {
        if (content == null) {
            return null;
        }
        StringBuilder buff = new StringBuilder();
        buff.append(content);
        for (int i = buff.length() - 1; i >= 0; i--) {
            if (' ' == buff.charAt(i) || ('\n' == buff.charAt(i)) || ('\t' == buff.charAt(i))
                    || ('\r' == buff.charAt(i))) {
                buff.deleteCharAt(i);
            }
        }
        return buff.toString();
    }

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static boolean isEmpty(String input) {
        return TextUtils.isEmpty(input);
    }

    /**
     * 生成唯一号
     *
     * @return
     */
    public static String get36UUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static String makeMd5(String source) {
        return MD5.getStringMD5(source);
    }

    public static final String filterUCS4(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        if (str.codePointCount(0, str.length()) == str.length()) {
            return str;
        }

        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < str.length()) {
            int codePoint = str.codePointAt(index);
            index += Character.charCount(codePoint);
            if (Character.isSupplementaryCodePoint(codePoint)) {
                continue;
            }

            sb.appendCodePoint(codePoint);
        }

        return sb.toString();
    }

    /**
     * counter ASCII character as one, otherwise two
     *
     * @param str
     * @return count
     */
    public static int counterChars(String str) {
        // return
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            int tmp = (int) str.charAt(i);
            if (tmp > 0 && tmp < 127) {
                count += 1;
            } else {
                count += 2;
            }
        }
        return count;
    }

    /**
     * 是否符合邮箱格式
     *
     * @param src 源字符串
     * @return 是否符合邮箱格式
     */
    public static boolean isEmail(String src) {
        Pattern pattern = Pattern.compile("\\w+@(\\w+\\.){1,3}\\w+");
        return pattern.matcher(src).find();
    }

    /**
     * 是否包含特殊字符（除字母、数字外的字符为特殊字符）
     *
     * @param str 源字符串
     * @return 是否包含特殊字符
     */
    public static boolean hasSpecialLetter(String str) {
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher mat = pattern.matcher(str);
        return !mat.find();
    }

    /**
     * 是否是手机号码，只做1开头和位数判断
     *
     * @param src 源字符串
     * @return 是否是手机号码
     */
    public static boolean isPhoneNumber(String src) {
        //        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        //        Matcher m = p.matcher(src);
        Pattern p = Pattern.compile("^[1]\\d{10}$");
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 是否是中国移动手机号码(134 135 136 137 138 139 151 150 158 159)
     *
     * @param src 源字符串
     * @return 是否是中国移动手机号码
     */
    public static boolean isCmccNumber(String src) {
        Pattern p = Pattern.compile("^((13[4-9])|(15[0-1])|(15[8-9]))\\d{8}$");
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 是否是密码
     *
     * @param src 源字符串
     * @return 是否是密码
     */
    public static boolean isPassword(String src) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]*$");
        Matcher m = p.matcher(src);
        return m.matches();
    }

    /**
     * 是否是昵称和手机号
     *
     * @param nickNameID
     * @return
     */
    public static boolean isNumberFormat(String nickNameID) {
        Pattern p = Pattern.compile("^[0-9]*$");
        Matcher m = p.matcher(nickNameID);
        return m.matches();
    }

    /**
     * 中英文数字正则表达式(4-16位)
     *
     * @param str
     * @return
     */
    public static boolean isChineseEnglishFormat(String str) {
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2DA-Za-z0-9]+$");
        Matcher m = p.matcher(str);
        str = str.replaceAll("[^\\x00-\\xff]", "**");    //匹配双字节字符
        int length = str.length();
        return m.matches() && length >= 4 && length <= 16;
    }

    /**
     * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
     *
     * @param str                原始字符串
     * @param specialCharsLength 截取长度(汉、日、韩文字符长度为2)
     * @return
     */
    public static String trim(String str, int specialCharsLength) {
        if (str == null || "".equals(str) || specialCharsLength < 1) {
            return "";
        }
        char[] chars = str.toCharArray();
        int charsLength = getCharsLength(chars, specialCharsLength);
        return new String(chars, 0, charsLength);
    }

    /**
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
     *
     * @param chars              一段字符
     * @param specialCharsLength 输入长度，汉、日、韩文字符长度为2
     * @return 输出长度，所有字符均长度为1
     */
    private static int getCharsLength(char[] chars, int specialCharsLength) {
        int count = 0;
        int normalCharsLength = 0;
        for (int i = 0; i < chars.length; i++) {
            int specialCharLength = getSpecialCharLength(chars[i]);
            if (count <= specialCharsLength - specialCharLength) {
                count += specialCharLength;
                normalCharsLength++;
            } else {
                break;
            }
        }
        return normalCharsLength;
    }


    /**
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
     *
     * @param str 一段字符
     * @return 输出长度，所有字符均长度为1
     */
    public static int getStringLength(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int normalCharsLength = 0;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            normalCharsLength += getSpecialCharLength(chars[i]);
        }
        return normalCharsLength;
    }

    /**
     * 字符串截取，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1，超过长度尾部加上<b>...</b>
     *
     * @param str         一段字符
     * @param limitLength 长度限制，超出截取尾部加上<b>...</b>
     * @return 输出长度，所有字符均长度为1
     */
    public static String subLength(String str, int limitLength) {
        final int length = getStringLength(str);
        String content = str;
        if (length > limitLength) {
            content = StringUtil.trim(str, limitLength) + "...";
        }
        return content;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     *
     * @param c 字符
     * @return 字符长度
     */
    private static int getSpecialCharLength(char c) {
        if (isAscill(c)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
     *
     * @param c, 需要判断的字符
     * @return boolean, 返回true,Ascill字符
     */
    private static boolean isAscill(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 为字符串添加双引号
     *
     * @param obj 字符串
     * @return 加入双引号后的完整字符串
     */
    public static String addQuotationMarks(String obj) {
        return "\"" + obj + "\"";
    }

    /**
     * 保留1位小数
     *
     * @param num
     * @return
     */
    public static String onlyOneFloat(double num) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(num);
    }

    /**
     * 连接多个字符串标签
     *
     * @param tags                  所有字符标签
     * @param linker                标签之间的连接字符
     * @param isLastTagAppendLinker 最后一个标签末尾是否也需要追加linker
     * @return 连接得到的字符串
     */
    public static String concatTags(List<String> tags, String linker, boolean
            isLastTagAppendLinker) {
        if (tags != null) {
            int size = tags.size();
            if (size > 0) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    sb.append(tags.get(i));
                    if (i != size - 1) {
                        sb.append(linker);
                    } else if (isLastTagAppendLinker) {
                        sb.append(linker);
                    }
                }
                return sb.toString();
            } else {
                return "";
            }
        }
        return null;
    }

    //转码

    /**
     * 将unicode转编码utf-8格式
     *
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }


    /**
     转半角的函数(DBC case)<br/><br/>
     全角空格为12288，半角空格为32
     其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * @param input 任意字符串
     * @return 半角字符串
     *
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                //全角空格为12288，半角空格为32
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

        /**
         * 检验字符串只能为纯数字，正小数、正整数均可
         */
    public static boolean isNumber(String str){
        Pattern p = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
