package cn.mdsoftware.mdframework.common.enums;

/**
 * 接口返回码
 * @author Jax
 *
 */
public enum HttpResponseEnum {


	SUCCESS(0, "执行成功"),
    NO_RECORDS(404,"查无记录"),
	NOT_FOND_LOGINNAME(1001, "查无此账号"),
	NOT_FOND_PHONE(1002, "查无此手机号"),
	PASSWORD_ERROR(1003, "密码错误"),
	PARAM_REQUIRED_PHONE(1004, "手机号不能为空"),
	PARAM_REQUIRED_PASSWORD(1005, "密码不能为空"),
	CAPTCHA_ERROR(1006, "验证码错误"),
	PARAM_REQUIRED_TOKEN(1007, "token不能为空"),
	TOKEN_ERROR(1008, "token错误"),
	PHONE_EXIST(1009, "该手机号已经存在"),
	FILE_PARAM_NAME_ERROR(1010, "不存在对应的文件key值，请核对接口"),
	PARAM_REQUIRED_ID(1011, "id不能为空"),
	PRODUCT_EXIST(1012, "不存在该产品"),
	COMMENT_EXIST(1013, "该产品没有评论"),
	HAD_COLLECTED_PRODUCT(1014, "该会员已经收藏过当前作品"),
	NEWS_EXIST(1015, "不存在该资讯"),
	HAD_COLLECTED_NEWS(1016, "该会员已经收藏过当前资讯"),
	NOT_FONDMUSIC_COLLECTED(1017, "该会员还没有收藏音乐"),
	NOT_FONDNEWS_COLLECTED(1018, "该会员还没有收藏资讯"),

	PARAM_ERROR(2030, "参数错误"),
	DATA_NOT_FOUND(2001, "数据不存在"),
	DATA_HAS_EXIST(2002, "数据已经存在"),
    ILLEGALITY_ERROR(2002, "非法操作"),

	PARAM_REQUIRED(2000, "参数必填"),
	CODE_ACCESS_DENIED(9000, "权限校验失败"),


	WECHAT_USER_NOT_EXIST(2005, "获取微信用户信息失败"),
	DATA_UNILLEGAL(2006, "数据非法"),
	SYSTEM_ERROR(9999, "系统异常");



	private Integer code;
	private String message;

	private HttpResponseEnum(Integer code, String message) {
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
