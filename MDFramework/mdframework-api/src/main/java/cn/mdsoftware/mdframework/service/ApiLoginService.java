package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.api.LoginReq;
import cn.mdsoftware.mdframework.bean.api.LoginResp;
import cn.mdsoftware.mdframework.bean.api.RegisterReq;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.enums.MemberSexEnum;
import cn.mdsoftware.mdframework.common.enums.MemberStatusEnum;
import cn.mdsoftware.mdframework.common.utils.Base64Utils;
import cn.mdsoftware.mdframework.common.utils.MD5Utils;
import cn.mdsoftware.mdframework.common.utils.RandomUtils;
import cn.mdsoftware.mdframework.dao.MemberDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ApiLoginService {
	
	private final Logger log = LoggerFactory.getLogger(ApiLoginService.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private MemberDao memberMapper;
	
	public void getCaptcha(String phone, HttpResponse<String> resp) {
		
		try {
			String key = Constants.RedisKey.APP_CAPTCHA.concat(phone);
			
			String data = null;
			if (redisTemplate.hasKey(key)) {
				data = (String) redisTemplate.opsForValue().get(key);
			}
			if (StringUtils.isBlank(data)) {
				
				data = RandomUtils.randomInt(6, 9);
				
				//TODO 发送短信接口
				
				// 缓存 1分钟
				redisTemplate.opsForValue().set(key, data, 1, TimeUnit.MINUTES);
			}
			
			resp.setData(data);
			
		} catch (Exception e) {
			log.error("获取验证码异常，当前phone:{}, error:", phone, e);
			resp.setHttpResponseEnum(HttpResponseEnum.SYSTEM_ERROR);
		}
	}

	public void login(LoginReq loginReq, HttpResponse<LoginResp> resp) {
		
		if (StringUtils.isBlank(loginReq.getPhone())) {
			log.error("手机号不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PHONE);
			return;
		}
		if (StringUtils.isBlank(loginReq.getPassword())) {
			log.error("密码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PASSWORD);
			return;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("phone", loginReq.getPhone());
		List<MemberDO> list = memberMapper.list(map);
		if (CollectionUtils.isEmpty(list)) {
			log.error("查无此手机号：{}", loginReq.getPhone());
			resp.setHttpResponseEnum(HttpResponseEnum.NOT_FOND_PHONE);
			return;
		}
		
		MemberDO entity = list.get(0);
		
		String password = MD5Utils.encrypt(Base64Utils.decode(loginReq.getPassword()));
		
	/*	if(!entity.getPassword().equals(password)) {
			log.error("密码不对！当前请求的密码为：{}", loginReq.getPassword());
			resp.setHttpResponseEnum(HttpResponseEnum.PASSWORD_ERROR);
			return;
		}*/
		
		Map<String , Object> photoMap = new HashMap<>();
		photoMap.put("memberId", entity.getId());
		photoMap.put("isHead", 1);
		LoginResp data = new LoginResp();
		data.setNickname(entity.getName());
		data.setPhone(entity.getMobile());
//		data.setSex(MemberSexEnum.getMessageByCode(entity.getSex()));
		String token = RandomUtils.randomToken();
		data.setToken(token);
		
		resp.setData(data);
		redisTemplate.opsForValue().set(Constants.RedisKey.APP_SESSION.concat(entity.getMobile()), token, 1, TimeUnit.DAYS);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void register(RegisterReq register, HttpResponse<String> resp) {

		if (StringUtils.isBlank(register.getPhone())) {
			log.error("手机号不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PHONE);
			return;
		}
		if (StringUtils.isBlank(register.getPassword())) {
			log.error("密码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PASSWORD);
			return;
		}
		if (StringUtils.isBlank(register.getCaptcha())) {
			log.error("验证码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		String key = Constants.RedisKey.APP_CAPTCHA.concat(register.getPhone());
		String capthca = (String) redisTemplate.opsForValue().get(key);
		log.info("系统验证码：{}，请求的验证码：{}", capthca, register.getCaptcha());
		if (!register.getCaptcha().equals(capthca)) {
			log.error("验证码错误！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("phone", register.getPhone());
		int count = memberMapper.count(map);
		if (count > 0) {
			log.warn("该手机号{}已经存在", register.getPhone());
			resp.setHttpResponseEnum(HttpResponseEnum.PHONE_EXIST);
			return;
		}
		
		MemberDO member = new MemberDO();
		member.setMobile(register.getPhone());
//		member.setPassword(MD5Utils.encrypt(Base64Utils.decode(register.getPassword())));
//		member.setStatus(MemberStatusEnum.EFFECTIVE.code());
//		member.setCreateTime(new Date());
		memberMapper.save(member);
		
		redisTemplate.delete(key);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void forgetPassword(RegisterReq register, HttpResponse<String> resp) {
		
		if (StringUtils.isBlank(register.getPhone())) {
			log.error("手机号不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PHONE);
			return;
		}
		if (StringUtils.isBlank(register.getPassword())) {
			log.error("密码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PASSWORD);
			return;
		}
		if (StringUtils.isBlank(register.getCaptcha())) {
			log.error("验证码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		String key = Constants.RedisKey.APP_CAPTCHA.concat(register.getPhone());
		String capthca = (String) redisTemplate.opsForValue().get(key);
		log.info("系统验证码：{}，请求的验证码：{}", capthca, register.getCaptcha());
		if (!register.getCaptcha().equals(capthca)) {
			log.error("验证码错误！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("phone", register.getPhone());
		List<MemberDO> list = memberMapper.list(map);
		if (CollectionUtils.isEmpty(list)) {
			log.error("查无此手机号：{}", register.getPhone());
			resp.setHttpResponseEnum(HttpResponseEnum.NOT_FOND_PHONE);
			return;
		}
		
		MemberDO member = new MemberDO();
		member.setId(list.get(0).getId());
//		member.setPassword(MD5Utils.encrypt(Base64Utils.decode(register.getPassword())));
		memberMapper.update(member);
		
		redisTemplate.delete(key);
	}
}
