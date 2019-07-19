package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

/**
 * 登陆请求参数
 * @author Jax
 *
 */
public class LoginReq implements Serializable {

	private static final long serialVersionUID = -7963573239452874865L;
	
	private String phone;
	
	private String password;
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
