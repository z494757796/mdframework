package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public boolean isLogin(String phone, String token, HttpResponse resp) {
		
		if (StringUtils.isBlank(phone)) {
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PHONE);
			log.error("手机号不能为空");
			return false;
		}
		if (StringUtils.isBlank(token)) {
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_TOKEN);
			log.error("token不能为空");
			return false;
		}
		
		String key = Constants.RedisKey.APP_SESSION.concat(phone);
		String sysToken = (String) redisTemplate.opsForValue().get(key);
		if (!token.equals(sysToken)) {
			log.error("不存在此token");
			resp.setHttpResponseEnum(HttpResponseEnum.TOKEN_ERROR);
			return false;
		}
		
		return true;
	}
}
