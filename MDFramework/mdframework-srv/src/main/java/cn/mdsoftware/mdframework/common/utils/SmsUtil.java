package cn.mdsoftware.mdframework.common.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 手机短信接口util
 * @author Jesse
 * 2014-07-23
 */
public class SmsUtil {
	private static Logger log = LoggerFactory.getLogger(SmsUtil.class);

	/**
	 * 固定地址串，后面方法名需要传参数
	 */
//	private static final String  address = "http://120.55.205.5/webservice/sms.php?method=";
	private static final String  address = "http://106.wx96.com/webservice/sms.php?method=";

	/**
	 * 用户名
	 */
//	private static final String account = "cf_lwsc"; //账号
	private static final String account = "C34151765";

	/**
	 * 密码
	 */
//	private static final String password = "lwsc123";//密码
	private static final String password = "3fe5dd40ce58fad47232c3eb8ca3b92b";//密码

	private static final String pid = "21";//PID



	public static StringBuffer sendMessage(String mobile,String content){

		String result="";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(address+"Submit");
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		//多个手机号码请用英文,号隔开

		NameValuePair[] data = {//提交短信
				new NameValuePair("account", account),
				new NameValuePair("password", password),
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", content),
		};

		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			result = method.getResponseBodyAsString();

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new StringBuffer(result);
	}



	/**
	 * 获取剩余可发信息数量
	 * @return
	 */
	public static String getSurplusMessageNum() {
		String result="";
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(address+"GetNum");
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		//多个手机号码请用英文,号隔开

		NameValuePair[] data = {//提交短信
				new NameValuePair("account", account),
				new NameValuePair("password", password),
				//new NameValuePair("pid", pid),
		};

		method.setRequestBody(data);

		try {
			client.executeMethod(method);
			result = method.getResponseBodyAsString();

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 短信接口处理重复代码
	 * @param addressReal
	 * @param paramString
	 * @return
	 */
	private static Map<String,Object> connectDoSomething(StringBuffer addressReal,StringBuffer paramString) {
		BufferedReader in = null;
		URL url;
		byte[] xmlData = paramString.toString().getBytes();
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			url = new URL(addressReal.toString());
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			urlConn.setRequestProperty("Content-length",String.valueOf(xmlData.length));
			DataOutputStream printout = new DataOutputStream(urlConn.getOutputStream());
			printout.write(xmlData);
			printout.flush();
			in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			StringBuffer sb2 = new StringBuffer();
			String lines = "";
			while(null!=(lines = in.readLine()))  {
				sb2.append(lines);
			}
			//获取返回结果
			//		log.info(sb2.toString());
			//获取真正可处理结果  code--msg -- smsid
			result = XmlUtil.parseStringXmlToMap(sb2);
			//遍历
			Iterator it = result.keySet().iterator();
			while (it.hasNext()) {
				Object obj = result.get(it.next());
				//			log.info(obj);
			}
			// in.read(b)
			in.close();
			printout.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}


	public static void main(String[] args) {
//		StringBuffer a = sendMessage("15889963910","您的验证码是：5874。请不要把验证码泄露给其他人。");
		StringBuffer a = sendMessage("15889963910","您的确认码是：5874。请不要把确认码泄露给其他人。");

		Map<String,Object> map = XmlUtil.parseStringXmlToMap(a);
		System.out.print(map.get("code"));
//		log.info(getSurplusMessageNum());

	}
}
