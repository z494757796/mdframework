package cn.mdsoftware.mdframework.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MemberStatusEnum {

	INVALID(0, "停用"),
	EFFECTIVE(1, "启用");

	private Integer code;
	private String message;
	
	private MemberStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return code;
	}

	public String message() {
		return message;
	}
	
	public static String getMessageByCode(Integer code){
		for(MemberStatusEnum item : MemberStatusEnum.values()) { 
	        if(item.code().equals(code)){
	        	return item.message();
	        }
	     }
		return null;
	}
	
	/**
	 * 获取所有枚举
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> getEnumList(){
		List<Map> list = new ArrayList<Map>();
		MemberStatusEnum[] enums = MemberStatusEnum.values();
		for (MemberStatusEnum e : enums) {
			Map m = new HashMap();
			m.put("code", e.code());
			m.put("message", e.message());
			list.add(m);
		}
		return list;
	}
}
