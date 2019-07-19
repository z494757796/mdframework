package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

/**
 * 会员信息修改
 * @author Jax
 *
 */
public class MemberEditReq implements Serializable {

	private static final long serialVersionUID = 7580890408450637807L;
	
	private String phone;
	
	private String token;
	
	private String nickname;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
		return "MemberEditReq [phone=" + phone + ", token=" + token + ", nickname=" + nickname + "]";
	}

}
