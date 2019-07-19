package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.entity.BaseInfoDO;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.common.utils.Query;
import cn.mdsoftware.mdframework.common.utils.R;
import cn.mdsoftware.mdframework.service.BaseInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;





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

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("baseInfo:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<BaseInfoDO> baseInfoList = baseInfoService.list(query);
		int total = baseInfoService.count(query);
		PageUtils pageUtils = new PageUtils(baseInfoList, (long)total);
		return pageUtils;
	}

	@GetMapping("/add")
		//@RequiresPermissions("blog:bComments")
	String add(){
		return "baseInfo/add";
	}

	@GetMapping("/edit/{id}")
		//@RequiresPermissions("blog:bComments")
	String edit(Model model, @PathVariable("id") Integer id){
		BaseInfoDO baseInfo = baseInfoService.get(id);
		model.addAttribute("baseInfo", baseInfo);
		return "baseInfo/edit";
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("baseInfo:info")
	public R info(@PathVariable("id") Integer id){
		BaseInfoDO baseInfo = baseInfoService.get(id);
		return R.ok().put("baseInfo", baseInfo);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("baseInfo:save")
	public R save( BaseInfoDO baseInfo){
		if(baseInfoService.save(baseInfo)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@ResponseBody
	@RequiresPermissions("baseInfo:update")
	public R update(BaseInfoDO baseInfo){
		baseInfoService.update(baseInfo);

		return R.ok();
	}


	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("baseInfo:remove")
	public R remove( Integer id){
		if(baseInfoService.remove(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("baseInfo:remove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		baseInfoService.batchRemove(ids);

		return R.ok();
	}

}
