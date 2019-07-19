package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

/**
 * 修改密码&设置密码请求参数
 * @author Jax
 *
 */
public class ChangePasswordReq implements Serializable {

	private static final long serialVersionUID = -8131637238584253360L;

	private String phone;
	
	private String password;
	
	private String captcha;
	
	private String token;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@Override
	public String toString() {
		return "RegisterReq [phone=" + phone + ", password=" + password + ", captcha=" + captcha + "]";
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
