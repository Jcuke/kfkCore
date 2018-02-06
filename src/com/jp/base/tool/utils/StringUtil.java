package com.jp.base.tool.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 类说明：字符串工具类
 * 
 * @author gaoLi
 * @version 1.0
 */
public class StringUtil {

	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static boolean isLetter(char c) {
		int k = 128;
		return c / k == 0;
	}

	public static int getStrLength(String src) {
		if (src == null) {
			return 0;
		}
		char[] c = src.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static boolean isEmpty(String s) {
		if (s != null && s.length() > 0) {
			return false;
		}
		return true;
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * JSON字符串特殊字符处理，比如：“\A1;1300”
	 * 
	 * @param s
	 * @return String
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String listToString(List list, char separator) {
		return org.apache.commons.lang.StringUtils.join(list.toArray(), separator);
	}

	public static String generateSerialno() {
		String nanotime = String.valueOf(System.nanoTime());// 纳秒
		String randomString = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		return nanotime + randomString;
	}

	public static Map<String, String> getXmlString(String path) {
		Map<String, String> mp = getFile(path);
		return mp;
	}

	public static Map<String, String> getFile(String filename) {
		TransformerFactory tf = TransformerFactory.newInstance();
		ByteArrayOutputStream bos = null;
		try {
			Document doc = null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(Thread.currentThread().getContextClassLoader().getResource("").getPath() + filename);
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");// 解决中文问题
			t.setOutputProperty(OutputKeys.METHOD, "html");
			t.setOutputProperty(OutputKeys.VERSION, "4.0");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));

			Map<String, String> map = new HashMap<>();
			map.put(filename, bos.toString());
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 是否是手机号以1开始后接10位
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobileWith1Begin(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][0-9]{10}$");
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		if (isNotEmpty(string)) {
			string = string.trim();
		}
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (c > 255) {
				unicode.append("\\u" + Integer.toHexString(c));
			} else {
				unicode.append("\\u00" + Integer.toHexString(c));
			}
		}
		return unicode.toString();
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 截取给定字符两端空格
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String trim(String str) {
		if (str == null)
			return null;
		str = str.trim();
		if (str.length() == 0)
			return null;
		else
			return str;
	}

	/**
	 * 验证电话号码合法格式，格式为02584555112
	 * 
	 * @param phoneCode
	 *            设置电话号码字符串
	 * @return boolean 返回结果
	 */
	public static boolean isPhoneNum(String phoneCode) {
		Pattern p = Pattern.compile("[0][1-9]{2,3}[1-9]{6,8}");
		Matcher m = p.matcher(phoneCode);
		boolean b = m.matches();
		return b;
	}

	public static String subUrl(String url) {
		String[] urls = url.split("/");

		return urls[urls.length - 1];
	}

	//
	/**
	 * 金钱小数末尾去0
	 */
	public static String subZeroAndDot(String fee) {
		if (fee.indexOf(".") > 0) {
			fee = fee.replaceAll("0+?$", "");
			fee = fee.replaceAll("[.]$", "");
		}
		return fee;
	}

	public static String accuracy(double num, double total, int scale) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		// 可以设置精确几位小数
		df.setMaximumFractionDigits(scale);
		// 模式 例如四舍五入
		df.setRoundingMode(RoundingMode.HALF_UP);
		double accuracy_num = num / total * 100;
		return df.format(accuracy_num) + "%";
	}

	public static int[] StringtoInt(String str) {
		int[] order_int = new int[] {};
		if (StringUtils.isNotEmpty(str)) {
			String[] order = str.split(",");
			order_int = new int[order.length];
			for (int i = 0; i < order.length; i++) {
				order_int[i] = Integer.parseInt(order[i]);

			}

		}
		return order_int;
	}

	/**
	 * 生成Token
	 * 
	 * @return
	 */
	public static String getToken() {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(30);
		for (int i = 0; i < 30; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	/**
	 * 获取32位随机数据小写字母
	 * 
	 * @return
	 */
	public static String getUUid() {
		return RandomStringUtils.randomAlphanumeric(32).toLowerCase();
	}

	
	/**
	 * 比较两个bigdecimal大小
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static BigDecimal compToMin(BigDecimal d1,BigDecimal d2)
	{
		if(d1.compareTo(d2)>0)
		{
			return d2;
		}
		return d1;
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		System.out.println(StringUtils.isEmpty("  "));
	}

}

