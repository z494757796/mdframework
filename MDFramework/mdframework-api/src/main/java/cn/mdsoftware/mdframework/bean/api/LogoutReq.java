package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

/**
 * 退出登录请求参数
 * @author Jax
 *
 */
public class LogoutReq implements Serializable {

	private static final long serialVersionUID = 4418673466891769132L;

	private String phone;
	
	private String token;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LogoutReq [phone=" + phone + ", token=" + token + "]";
	}
}
