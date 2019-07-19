package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.entity.BaseInfoDO;
import cn.mdsoftware.mdframework.common.annotation.JSON;
import cn.mdsoftware.mdframework.service.BaseInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-02-08 17:57:16
 */
@Controller
@RequestMapping("/baseInfo")
public class BaseInfoController {
	@Autowired
	private BaseInfoService baseInfoService;
	
	@GetMapping()
	@RequiresPermissions("baseInfo:baseInfo")
	String BaseInfo(){
	    return "baseInfo/baseInfo";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/detail")
	@JSON(type=BaseInfoDO.class,include = "name,mainPic")
	public Object info(){
		HttpResponse<BaseInfoDO> resp = new HttpResponse<>();
		BaseInfoDO baseInfo = baseInfoService.get(1);
		resp.setData(baseInfo);
		return resp;
	}

}
