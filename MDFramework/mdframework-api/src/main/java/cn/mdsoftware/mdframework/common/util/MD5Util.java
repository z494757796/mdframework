package cn.mdsoftware.mdframework.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 采用MD5加密
 * 
 * @author relax
 */
public class MD5Util {

	public static String md5U(String inStr) {
		return md5Encode(inStr).toUpperCase();
	}

	/***
	 * MD5加密 生成32位md5码
	 * 
	 * @param
	 * @return 返回32位md5码
	 */
	public static String md5Encode(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray;
		StringBuffer hexValue = new StringBuffer();
		try {
			byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hexValue.toString();
	}

	public static String md5For16U(String inStr) {
		return md5For16(inStr).toUpperCase();
	}

	public static String md5For16(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray;
		StringBuffer hexValue = new StringBuffer();
		try {
			byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hexValue.toString().substring(8, 24);
	}

	/**
	 * 测试主函数
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		String str = new String("amigoxiexiexingxing");
		System.out.println("原始：" + str);
		System.out.println("MD5后：" + md5Encode(str));
	}
}