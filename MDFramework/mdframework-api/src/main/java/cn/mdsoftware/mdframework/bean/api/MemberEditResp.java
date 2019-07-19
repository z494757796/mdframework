package cn.mdsoftware.mdframework.bean.api;

import java.io.Serializable;

public class MemberEditResp implements Serializable {

	private static final long serialVersionUID = -7470620436932670466L;
	
	private String nickname;
	
	private String headerUrl;

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

	@Override
	public String toString() {
		return "MemberEditResp [nickname=" + nickname + ", headerUrl=" + headerUrl + "]";
	}

}
