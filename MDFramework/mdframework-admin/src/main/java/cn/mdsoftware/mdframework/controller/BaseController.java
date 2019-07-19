package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.entity.UserDO;
import cn.mdsoftware.mdframework.common.utils.ShiroUtils;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}