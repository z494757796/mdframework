package cn.mdsoftware.mdframework.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MemberSexEnum {
	
	MALE(1, "男"),
	FEMALE(2, "女"),
	OTHER(3, "其他");

	private Integer code;
	private String message;
	
	private MemberSexEnum(Integer code, String message) {
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
		for(MemberSexEnum item : MemberSexEnum.values()) { 
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
		MemberSexEnum[] enums = MemberSexEnum.values();
		for (MemberSexEnum e : enums) {
			Map m = new HashMap();
			m.put("code", e.code());
			m.put("message", e.message());
			list.add(m);
		}
		return list;
	}
}
