package cn.mdsoftware.mdframework.service;

import cn.mdsoftware.mdframework.bean.api.*;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.utils.Base64Utils;
import cn.mdsoftware.mdframework.common.utils.MD5Utils;
import cn.mdsoftware.mdframework.dao.MemberDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiMemberService {
	
	private final Logger log = LoggerFactory.getLogger(ApiMemberService.class);
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private MemberDao memberMapper;
	

	@Autowired
	private AuthService authService;

	@Transactional(rollbackFor = Exception.class)
	public void updatePassword(ChangePasswordReq req, HttpResponse<String> resp) {
		
		if (!authService.isLogin(req.getPhone(), req.getToken(), resp)) {
			return;
		}
		
		if (StringUtils.isBlank(req.getPassword())) {
			log.error("密码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED_PASSWORD);
			return;
		}
		if (StringUtils.isBlank(req.getCaptcha())) {
			log.error("验证码不能为空！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		String key = Constants.RedisKey.APP_CAPTCHA.concat(req.getPhone());
		String capthca = (String) redisTemplate.opsForValue().get(key);
		log.info("系统验证码：{}，请求的验证码：{}", capthca, req.getCaptcha());
		if (!req.getCaptcha().equals(capthca)) {
			log.error("验证码错误！");
			resp.setHttpResponseEnum(HttpResponseEnum.CAPTCHA_ERROR);
			return;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("phone", req.getPhone());
		List<MemberDO> list = memberMapper.list(map);
		if (CollectionUtils.isEmpty(list)) {
			log.error("查无此手机号：{}", req.getPhone());
			resp.setHttpResponseEnum(HttpResponseEnum.NOT_FOND_PHONE);
			return;
		}
		
		MemberDO member = new MemberDO();
		member.setId(list.get(0).getId());
//		member.setPassword(MD5Utils.encrypt(Base64Utils.decode(req.getPassword())));
		memberMapper.update(member);
	}

	/**
	 * 退出登录
	 * @param req
	 * @param resp
	 */
	public void logout(LogoutReq req, HttpResponse<String> resp) {
		if (!authService.isLogin(req.getPhone(), req.getToken(), resp)) {
			return;
		}
		String key = Constants.RedisKey.APP_SESSION.concat(req.getPhone());
		
		redisTemplate.delete(key);
	}

	/**
	 * 会员编辑
	 * @param req
	 * @param fileMap
	 * @param resp
	 */
	@Transactional(rollbackFor = Exception.class)
	public void edit(MemberEditReq req, Map<String, MultipartFile> fileMap, HttpResponse<MemberEditResp> resp) {
		
		MemberEditResp data = new MemberEditResp();
		
		log.info("请求参数：{}", req.toString());
		if (!authService.isLogin(req.getPhone(), req.getToken(), resp)) {
			return;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("phone", req.getPhone());
		List<MemberDO> members = memberMapper.list(map);
		if (CollectionUtils.isEmpty(members)) {
			log.error("查无此会员，当前手机号：{}", req.getPhone());
			resp.setHttpResponseEnum(HttpResponseEnum.NOT_FOND_PHONE);
			return;
		}
		
		MemberDO member = new MemberDO();
		member.setId(members.get(0).getId());
		if (StringUtils.isNotBlank(req.getNickname())) {
			member.setName(req.getNickname());
			memberMapper.update(member);
			data.setNickname(req.getNickname());
		}
		

		resp.setData(data);
	}
}
