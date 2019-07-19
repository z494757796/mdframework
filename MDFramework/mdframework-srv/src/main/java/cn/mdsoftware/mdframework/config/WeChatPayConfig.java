package cn.mdsoftware.mdframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:wechatpay.properties")
@Component
@ConfigurationProperties(prefix = "wechatpay")
public class WeChatPayConfig {

    public static String APP_ID;

    public static String NOTIFY_URL;

    public static String TRADE_TYPE;

    public static String API_KEY;

    public static String MCH_ID;

    public static String CREATE_IP;

    public final static String BODY_PRE = "潮派股权-";


    public static void setAppId(String appId) {
        APP_ID = appId;
    }

    public static void setNotifyUrl(String notifyUrl) {
        NOTIFY_URL = notifyUrl;
    }

    public static void setTradeType(String tradeType) {
        TRADE_TYPE = tradeType;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public static void setMchId(String mchId) {
        MCH_ID = mchId;
    }

    public static void setCreateIp(String createIp) {
        CREATE_IP = createIp;
    }
}
