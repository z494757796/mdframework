package cn.mdsoftware.mdframework.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 自定义随机数生成工具类
 * @author Jax
 *
 */
public class RandomUtils {

	/**
	 * 返回下个随机字符串，它是num个均匀分布的 int值的组合  
	 * @param num
	 * @return
	 */
	public static String randomInt(int num) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i=0; i<num ; i++) {
			sb.append(r.nextInt());
		}
		return sb.toString();
	}
	
	/**
	 * 从0到max均匀分布的num个随机数组合
	 * @param num
	 * @param max
	 * @return
	 */
	public static String randomInt(int num, int max) {
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i=0; i<num ; i++) {
			sb.append(r.nextInt(max));
		}
		return sb.toString();
	}
	
	/**
	 * 生成登录token<br>
	 * 规则：6位数的0-9随机数+时间戳 通过MD5加密（确保唯一）
	 * @return
	 */
	public static String randomToken() {
		
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i=0; i<6 ; i++) {
			sb.append(r.nextInt(9));
		}
		sb.append(System.currentTimeMillis());
		
		return MD5Utils.encode(sb.toString());
	}


    /**
     * 高并发下生成唯一订单号
     * @param id 确保唯一
     * @return
     */
    public static String randomOrderNum(Integer id) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        int ranNum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return str + id + ranNum ;
    }

	public static void main(String[] args) {
		System.out.println(randomOrderNum(1));
//		System.out.println(randomToken());
	}
}
