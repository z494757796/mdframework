package cn.mdsoftware.mdframework.interceptor;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.utils.DateUtils;
import cn.mdsoftware.mdframework.common.utils.IPUtils;
import cn.mdsoftware.mdframework.common.utils.JSONUtils;
import cn.mdsoftware.mdframework.properties.AuthProperties;
import cn.mdsoftware.mdframework.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 用于接口权限拦截校验
 * @author Jax
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private final static Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

	private AuthProperties authProperties;

	private RedisTemplate<String, Object> redisTemplate;

	private static final String SECURITY_SIGN_SET = "security_sign_set:";

	private MemberService memberService;

	public AuthInterceptor(AuthProperties authProperties, RedisTemplate<String, Object> redisTemplate, MemberService memberService) {
		this.authProperties = authProperties;
		this.redisTemplate = redisTemplate;
		this.memberService = memberService;
	}

	public AuthInterceptor(AuthProperties authProperties, RedisTemplate<String, Object> redisTemplate) {
		this.authProperties = authProperties;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		log.info("当前请求IP地址：{}, 请求接口：{}, 请求方式：{}", IPUtils.getIpAddr(request), request.getRequestURI(), request.getMethod());

		if("OPTIONS".equals(request.getMethod())){
			return true;
		}

		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement().toString();
			String value = request.getHeader(name);
			sb.append(name).append(":").append(value).append("\n");
			headerMap.put(name, value);
		}
		log.info(sb.toString());

//		if (!validateParam(headerMap, response)) {
//			return false;
//		}
//
		if (!securityCheck(request, response)) {
//			throw new TokenNullException("accessToken不能为空");
			return false;
		}
		return true;
	}

	private boolean securityCheck(HttpServletRequest request, HttpServletResponse response) {
		HttpResponse<Object> resp = new HttpResponse<>();

//		System.out.println(headerMap.get("accessToken"));
		String accessToken = request.getHeader("accessToken") == null ? "" : request.getHeader("accessToken").toString();
//		String accessToken = request.getParameter("accessToken") == null ? "" : request.getParameter("accessToken").toString();
//		String time = headerMap.get("time") == null ? "" : headerMap.get("time").toString();
//		String sign = headerMap.get("sign") == null ? "" : headerMap.get("sign").toString();



		if (StringUtils.isBlank(accessToken)) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage("accessToken不能为空");
			responseOutJson(response, resp);
			return false;
		}

//		this.memberService


//		String signStr = getSignString(time);
//		log.info("signStr:"+signStr);
//		final String tmpSign = MD5Utils.encode(signStr);
//		// 如验签不对则返回false
//		if (!tmpSign.equals(sign)) {
//			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
//			resp.setMessage("验签不正确");
//			log.error("请求header:{}, tmpSign:{}", headerMap, tmpSign);
//			responseOutJson(response, resp);
//			return false;
//		}

		// 缓存30分钟
//		String key = SECURITY_SIGN_SET + sign;
//		boolean canReq = redisTemplate.opsForValue().setIfAbsent(key, tmpSign);
//		redisTemplate.expire(key, 30, TimeUnit.MINUTES);
//		if (!canReq) {
//			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
//			resp.setMessage("发现重复请求，如不是请确认网络安全-签名被盗用");
//			log.error("发现重复请求，如不是请确认网络安全-签名被盗用");
//			responseOutJson(response, resp);
//			return false;
//		}

		Object obj = redisTemplate.opsForValue().get(Constants.RedisKey.ACCESS_TOKEN.concat(accessToken));
		if (null == obj) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage("accessToken已失效");
			responseOutJson(response, resp);
			return false;
		}

		MemberDO memberDO = memberService.get(Integer.valueOf(obj.toString()));
		if (null == memberDO) {
			resp.setHttpResponseEnum(HttpResponseEnum.TOKEN_ERROR);
			resp.setMessage("accessToken错误");
			responseOutJson(response, resp);
			return false;
		}

		//重新设置redis
		redisTemplate.opsForValue().set(Constants.RedisKey.ACCESS_TOKEN.concat(accessToken),memberDO.getId().toString() , 2, TimeUnit.HOURS);
		return true;
	}

	/**
	 * 获取md5以前的字符串
	 * 如：UserId=PalyMusic&Key=Play@2017#Music&Time=201510012359592e2dd002-23b2-4ecc-bf51-530775e98a2b
	 * @param time
	 * @return
	 */
	private String getSignString(String time) {
		StringBuffer securityStrBuff = new StringBuffer();
		securityStrBuff.append("UserId=");
		securityStrBuff.append(authProperties.getUserid());
		securityStrBuff.append("&Key=");
		securityStrBuff.append(authProperties.getPassword());
		securityStrBuff.append("&Time=");
		securityStrBuff.append(time);
		return securityStrBuff.toString();
	}

	private boolean validateParam(Map<String, Object> headerMap, HttpServletResponse response) {

		HttpResponse<Object> resp = new HttpResponse<>();

		String userid = headerMap.get("userid") == null ? "" : headerMap.get("userid").toString();
		String time = headerMap.get("time") == null ? "" : headerMap.get("time").toString();
		String sign = headerMap.get("sign") == null ? "" : headerMap.get("sign").toString();

		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(userid)) {
			sb.append("验签userid为空;");
		}
		if (StringUtils.isBlank(time)) {
			sb.append("验签time为空;");
		}
		if (StringUtils.isBlank(sign)) {
			sb.append("验签sign为空;");
		}

		if (StringUtils.isNotBlank(sb.toString())) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage(sb.toString());
			responseOutJson(response, resp);
			log.error(sb.toString());
			return false;
		}

		String sendTimeStr = time.substring(0, 14);
		Date sendTime = null;
		try {
			sendTime = DateUtils.from(sendTimeStr, Constants.DatetimePattern.DP14);
			if (sendTime == null) {
				resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
				resp.setMessage("发送时间为：" + sendTimeStr + "格式不正确");
				responseOutJson(response, resp);
				log.error("发送时间经过转换后为空！");
				return false;
			}
		} catch (Exception e) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage("发送时间为：" + sendTimeStr + "格式不正确");
			responseOutJson(response, resp);
			log.error("时间格式转换错误", e);
			return false;
		}

		Calendar now = Calendar.getInstance();

		log.info(" ==>" + DateUtils.toDate(now.getTime(), Constants.DatetimePattern.DP14));

		// 早于当前时间半个小时以上
		now.add(Calendar.MINUTE, -30);
		log.info("-30 ==>" + DateUtils.toDate(now.getTime(), Constants.DatetimePattern.DP14));
		if (sendTime.before(now.getTime())) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage("发送时间为：" + sendTimeStr + ",时间和北京时间不匹配");
			responseOutJson(response, resp);
			log.error("发送时间为：" + sendTimeStr + ",时间和北京时间不匹配");
			return false;
		}
		// 晚于当前时间半个小时以上
		now.add(Calendar.MINUTE, 60);
		log.info("60 ==>" + DateUtils.toDate(now.getTime(), Constants.DatetimePattern.DP14));
		if (sendTime.after(now.getTime())) {
			resp.setHttpResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
			resp.setMessage("发送时间为：" + sendTimeStr + ",时间和北京时间不匹配");
			responseOutJson(response, resp);
			log.error("发送时间为：" + sendTimeStr + ",时间和北京时间不匹配");
			return false;
		}

		return true;
	}

	private void responseOutJson(HttpServletResponse response, Object obj) {
		response.setCharacterEncoding(Constants.Charset.UTF8);
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin","*");
		response.setHeader("Access-Control-Allow-Methods","*");
		response.setHeader("Access-Control-Max-Age","3600");
		response.setHeader("Access-Control-Allow-Headers","*");
		response.setHeader("","");response.setHeader("","");

		PrintWriter out = null;
		String json = null;
		try {
			json = JSONUtils.beanToJson(obj);
			out = response.getWriter();
			out.write(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
