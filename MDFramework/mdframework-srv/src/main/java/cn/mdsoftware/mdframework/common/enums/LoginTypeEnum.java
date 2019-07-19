package cn.mdsoftware.mdframework.common.enums;

public enum LoginTypeEnum {

	LOGIN_NAME(1, "账号登录"),
	PHONE(2, "手机号登录");
	
	private Integer code;
	private String message;
	
	private LoginTypeEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return code;
	}

	public String message() {
		return message;
	}
}
