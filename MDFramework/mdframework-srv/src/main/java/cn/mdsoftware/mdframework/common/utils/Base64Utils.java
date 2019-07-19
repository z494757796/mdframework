package cn.mdsoftware.mdframework.common.utils;

import cn.mdsoftware.mdframework.common.Constants;
import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Utils {
	
	/**
	 * Base64编码
	 * @param data 待编码数据
	 * @return String 编码后的数据  异常返回null
	 */
	public static String encode(String data) {
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(Constants.Charset.UTF8));
			return new String(b, Constants.Charset.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Base64安全编码<br>
	 * 遵循RFC 2045实现<br>
	 * 邮件标准形式，每行为76个字符，行末加入一个回车换行符"\r\n"
	 * @param data 待编码数据
	 * @return String 编码后的数据  异常返回null
	 */
	public static String encodeSafe(String data) {
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(Constants.Charset.UTF8), true);
			return new String(b, Constants.Charset.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Base64 解码
	 * @param data 待解码数据
	 * @return String 解码后的数据  异常返回null
	 */
	public static String decode(String data) {
		try {
			byte[] b = Base64.decodeBase64(data.getBytes(Constants.Charset.UTF8));
			return new String(b, Constants.Charset.UTF8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(encodeSafe("123456"));
//		System.out.println(decode("MTIzNDU2"));
	}
}
