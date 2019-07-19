package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.bean.entity.BannerInfoDO;
import cn.mdsoftware.mdframework.common.annotation.JSON;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.service.BannerInfoService;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-02-07 12:10:13
 */
@Controller
@RequestMapping("/banner")
public class BannerInfoController {
	@Autowired
	private BannerInfoService bannerInfoService;
	
	@GetMapping()
	@RequiresPermissions("bannerInfo:bannerInfo")
	String BannerInfo(){
	    return "bannerInfo/bannerInfo";
	}
	
	@GetMapping("/list")
	@JSON(type=BannerInfoDO.class,include = "id,title,linkUrl,mainPic")
	public Object list(){
		//查询列表数据
		HttpResponse<PageUtils> resp = new HttpResponse<>();
        Map query = Maps.newHashMap();
		query.put("status",1);
		query.put("sort","order_By");
		query.put("order","asc");

		List<BannerInfoDO> bannerInfoList = bannerInfoService.list(query);
		int total = bannerInfoService.count(query);
		PageUtils pageUtils = new PageUtils(bannerInfoList, Long.parseLong(total+""));
		resp.setData(pageUtils);
		return resp;
	}
	

}
