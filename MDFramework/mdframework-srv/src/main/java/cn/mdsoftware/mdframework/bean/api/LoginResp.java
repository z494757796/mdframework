package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

/**
 * 会员登陆返回数据
 * @author Jax
 *
 */
public class LoginResp implements Serializable {

	private static final long serialVersionUID = 8644640399546187217L;

	private String nickname;
	
	private String sex;
	
	private String headerUrl;
	
	private String phone;
	/**
	 * 登陆成功，后面的url请求带上token校验，缓存1天
	 */
	private String token;
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
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
		return "LoginResp [nickname=" + nickname + ", sex=" + sex + ", headerUrl=" + headerUrl + ", phone=" + phone
				+ ", token=" + token + "]";
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeaderUrl() {
		return headerUrl;
	}
	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}
}
