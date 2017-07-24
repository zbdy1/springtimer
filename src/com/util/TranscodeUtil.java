package com.util;

import java.io.ByteArrayOutputStream;
import java.lang.Character.UnicodeBlock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ���ദ���ַ�����ת�룬���Դ����ַ������������ַ���16�����ַ���unicode�ַ���base64�ַ�֮���ת��
 * @author ShaoJiang
 *
 */
public class TranscodeUtil {

    /**
     * ���ַ���ת����unicode��
     * @param str Ҫת����ַ���
     * @return ����ת�����ַ���
     */
    public static String strToUnicodeStr(String str) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            UnicodeBlock ub = UnicodeBlock.of(ch);
            if (ub == UnicodeBlock.BASIC_LATIN) {//Ӣ�ļ����ֵ�
                buffer.append(ch);
            } else if ((int)ch > 255) {
                buffer.append("\\u" + Integer.toHexString((int)ch));
            } else {
                buffer.append("\\" + Integer.toHexString((int)ch));
            }
        }
        return buffer.toString();
    }

    /**
     * ��unicode�뷴ת���ַ���
     * @param unicodeStr unicode��
     * @return ����ת�����ַ���
     */
    public static String unicodeStrToStr(String unicodeStr) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicodeStr);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicodeStr = unicodeStr.replace(matcher.group(1), ch + "");
        }
        return unicodeStr;
    }

    /**
     * ���ַ���ͨ��base64ת��
     * @param str Ҫת����ַ���
     * @return ����ת�����ַ���
     */
    public static String strToBase64Str(String str) {
        return new String(encode(str.getBytes()));
    }

    /**
     * ��base64�뷴ת���ַ���
     * @param base64Str base64��
     * @return ����ת�����ַ���
     */
    public static String base64StrToStr(String base64Str) {
        char[] dataArr = new char[base64Str.length()];
        base64Str.getChars(0, base64Str.length(), dataArr, 0);
        return new String(decode(dataArr));
    }

    /**
     * ���ֽ�����ͨ��base64ת��
     * @param byteArray �ֽ�����
     * @return ����ת�����ַ���
     */
    public static String byteArrayToBase64Str(byte byteArray[]) {
        return new String(encode(byteArray));
    }

    /**
     * ��base64��ת�����ֽ�����
     * @param base64Str base64��
     * @return ����ת������ֽ�����
     */
    public static byte[] base64StrToByteArray(String base64Str) {
        char[] dataArr = new char[base64Str.length()];
        base64Str.getChars(0, base64Str.length(), dataArr, 0);
        return decode(dataArr);
    }

    /**
     * ��һ���ֽ�����ת����base64���ַ�����
     * @param data �ֽ�����
     * @return base64�ַ�����
     */
    private static char[] encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }
        return out;
    }

    /**
     * ��һ��base64�ַ���������һ���ֽ�����
     * @param data base64�ַ�����
     * @return ���ؽ����Ժ���ֽ�����
     */
    private static byte[] decode(char[] data) {
        int len = ((data.length + 3) / 4) * 3;
        if (data.length > 0 && data[data.length - 1] == '=') --len;
        if (data.length > 1 && data[data.length - 2] == '=') --len;
        byte[] out = new byte[len];
        int shift = 0;
        int accum = 0;
        int index = 0;
        for (int ix = 0; ix < data.length; ix++) {
            int value = codes[data[ix] & 0xFF];
            if (value >= 0) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    out[index++] = (byte) ((accum >> shift) & 0xff);
                }
            }
        }
        if (index != out.length)
            throw new Error("miscalculated data length!");
        return out;
    }

    /**
     * base64�ַ��� 0..63
     */
    static private char[] alphabet =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
        .toCharArray();

    /**
     * ��ʼ��base64�ַ�����
     */
    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++) codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++) codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++) codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++) codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * 16���������ַ���
     */
    private static String hexString = "0123456789ABCDEF";

    /**
     * ���ַ��������16��������,�����������ַ����������ģ�
     * @param str �ַ���
     * @return ����16�����ַ���
     */
    public static String strToHexStr(String str) {
        // ����Ĭ�ϱ����ȡ�ֽ�����
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // ���ֽ�������ÿ���ֽڲ���2λ16��������
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * ��16�������ֽ�����ַ���,�����������ַ����������ģ�
     * @param hexStr 16�����ַ���
     * @return �����ַ���
     */
    public static String hexStrToStr(String hexStr) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
            hexStr.length() / 2);
        // ��ÿ2λ16����������װ��һ���ֽ�
        for (int i = 0; i < hexStr.length(); i += 2)
            baos.write((hexString.indexOf(hexStr.charAt(i)) << 4 | hexString
                        .indexOf(hexStr.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    /**
     * ���ֽ�����ת����16�����ַ���
     * @param byteArray Ҫת����ֽ�����
     * @return ����ת����16�����ַ���
     */
    public static String byteArrayToHexStr(byte byteArray[]) {
        StringBuffer buffer = new StringBuffer(byteArray.length * 2);
        int i;
        for (i = 0; i < byteArray.length; i++) {
            if (((int) byteArray[i] & 0xff) < 0x10)//С��ʮǰ�油��
                buffer.append("0");
            buffer.append(Long.toString((int) byteArray[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    /**
     * ��16�����ַ���ת�����ֽ�����
     * @param hexStr Ҫת����16�����ַ���
     * @return ����ת�����ֽ�����
     */
    public static byte[] hexStrToByteArray(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] encrypted = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);//ȡ��λ�ֽ�
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);//ȡ��λ�ֽ�
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

    /**
     * ���ַ���ת���ɶ������ַ������Կո����
     * @param str �ַ���
     * @return ���ض������ַ���
     */
    public static String strToBinStr(String str) {
        char[] chars=str.toCharArray();
        StringBuffer result = new StringBuffer();
        for(int i=0; i<chars.length; i++) {
            result.append(Integer.toBinaryString(chars[i]));
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * ���������ַ���ת����Unicode�ַ���
     * @param binStr �������ַ���
     * @return �����ַ���
     */
    public static String binStrToStr(String binStr) {
        String[] tempStr=strToStrArray(binStr);
        char[] tempChar=new char[tempStr.length];
        for(int i=0; i<tempStr.length; i++) {
            tempChar[i]=binstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    /**
     * ���������ַ���ת��Ϊchar
     * @param binStr �������ַ���
     * @return �����ַ�
     */
    private static char binstrToChar(String binStr) {
        int[] temp=binstrToIntArray(binStr);
        int sum=0;
        for(int i=0; i<temp.length; i++) {
            sum += temp[temp.length-1-i]<<i;
        }
        return (char)sum;
    }

    /**
     * ����ʼ�������ַ���ת�����ַ������飬�Կո����
     * @param str �������ַ���
     * @return �����ַ�������
     */
    private static String[] strToStrArray(String str) {
        return str.split(" ");
    }

    /**
     * ���������ַ���ת����int����
     * @param binStr �������ַ���
     * @return ����int����
     */
    private static int[] binstrToIntArray(String binStr) {
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];
        for(int i=0; i<temp.length; i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
}
