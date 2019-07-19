package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.api.LoginReq;
import cn.mdsoftware.mdframework.bean.api.LoginResp;
import cn.mdsoftware.mdframework.bean.api.RegisterReq;
import cn.mdsoftware.mdframework.service.ApiLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * app登陆接口
 * @author Jax
 *
 */
@RestController
@RequestMapping("/outer")
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ApiLoginService apiLoginService;

	/**
	 * 获取验证码
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.POST)
	public HttpResponse<String> getCaptcha(@RequestBody Map<String, String> map) {
		String phone = map.get("phone");
		HttpResponse<String> resp = new HttpResponse<>();
		apiLoginService.getCaptcha(phone, resp);
		log.info("/outer/captcha 接口返回：{}", resp.toString());
		return resp;
	}
	
	/**
	 * 登录
	 * @param loginReq
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpResponse<LoginResp> login(@RequestBody LoginReq loginReq) {
		HttpResponse<LoginResp> resp = new HttpResponse<>();
		apiLoginService.login(loginReq, resp);
		log.info("/outer/login 接口返回：{}", resp.toString());
		return resp;
	}
	
	/**
	 * 注册
	 * @param registerReq
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HttpResponse<String> register(@RequestBody RegisterReq registerReq) {
		HttpResponse<String> resp = new HttpResponse<>();
		apiLoginService.register(registerReq, resp);
		log.info("/outer/register 接口返回：{}", resp.toString());
		return resp;
	}
	
	/**
	 * 忘记密码
	 * @param registerReq
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public HttpResponse<String> forgetPassword(@RequestBody RegisterReq registerReq) {
		HttpResponse<String> resp = new HttpResponse<>();
		apiLoginService.forgetPassword(registerReq, resp);
		log.info("/outer/forgetPassword 接口返回：{}", resp.toString());
		return resp;
	}
	
}
