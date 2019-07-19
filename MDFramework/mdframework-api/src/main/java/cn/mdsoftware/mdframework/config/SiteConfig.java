package cn.mdsoftware.mdframework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SiteConfig {


    public static Integer INVITE_INTEGRAL;  //邀请积分

    @Value("${site.invite.integral}")
    public void setInviteIntegral(Integer inviteIntegral) {
        this.INVITE_INTEGRAL = inviteIntegral;
    }
}
