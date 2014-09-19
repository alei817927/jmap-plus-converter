package com.makenv.mapdc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	/**
	 * 判断字符串是否时数字
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isDigit(String text) {
		String reg = "[-]*[\\d]+[\\.\\d+]*";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(text);
		return mat.matches();
	}

	/**
	 * 判断一句话是否是汉语
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isChiness(String text) {
		String reg = "[\\w]*[\\u4e00-\\u9fa5]+[\\w]*";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(text);
		boolean result = mat.matches();
		return result;
	}

	/**
	 * 判断单个字符是否是汉语
	 * 
	 * @param cha
	 * @return
	 */
	public static boolean isChineseChar(char cha) {
		String reg = "[\\u4e00-\\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		String text = Character.toString(cha);
		Matcher mat = pat.matcher(text);
		boolean result = mat.matches();
		return result;
	}

	/**
	 * 判断字符是否是字母(包括大小写)或者数字
	 * 
	 * @param cha
	 * @return
	 */
	public static boolean isLetterAndDigit(String cha) {
		String reg = "[\\w]+";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(cha);
		boolean result = mat.matches();
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return true,字符串是空的;false,字符串不是空的
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static String getkey(Object... keys) {
		StringBuilder _sb = new StringBuilder();
		for (int i = 0; i < keys.length; i++) {
			if (i > 0) {
				_sb.append("-");
			}
			_sb.append(keys[i]);
		}
		return _sb.toString();
	}

	public static String[] phraseKey(String key) {
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		return key.split("-");
	}
}
