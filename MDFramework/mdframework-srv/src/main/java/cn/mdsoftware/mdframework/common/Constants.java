package cn.mdsoftware.mdframework.common;

/**
 * 常量写成接口类型
 * @author Jax
 *
 */
public interface Constants {

    interface DatetimePattern {
        static final String DP10 = "yyyy-MM-dd";
        static final String DP14 = "yyyyMMddHHmmss";
        static final String DP19 = "yyyy-MM-dd HH:mm:ss";
        static final String DP23 = "yyyy-MM-dd HH:mm:ss.SSS";
        static final byte MILLS_LEN = 13; // long millis = System.currentTimeMillis(); 13 == millis.length
    }

    interface ContentType {
        static final String JSON = "application/json";
        static final String FORM = "application/x-www-form-urlencoded";
    }

    interface Charset {
        static final String UTF8 = "UTF-8";
        static final String GBK = "GBK";
        static final String ISO88591 = "ISO8859-1";
    }

    interface RedisKey {
        static final String APP_CAPTCHA = "app:captcha:";// 后面接会员手机号，缓存1分钟, String 类型
        static final String APP_SESSION = "app:session:";// 后面接会员手机号，value为token， String 类型

        static final String ACCESS_TOKEN = "app:accessToken:";// 后面生成的token
        static final String HUIDATA_ACCESS_TOKEN = "hui:accessToken";// 汇数据认证平台需要的token
    }
    interface SessionKey {
        static final String FILE_BASE_UPLOAD = "fileBaseUpload";// 文件上传前缀
    }
}
