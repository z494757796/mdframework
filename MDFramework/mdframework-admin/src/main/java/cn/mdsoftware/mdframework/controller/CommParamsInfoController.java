package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.entity.CommParamsInfoDO;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.common.utils.Query;
import cn.mdsoftware.mdframework.common.utils.R;
import cn.mdsoftware.mdframework.service.CommParamsInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-01-24 11:20:01
 */
@Controller
@RequestMapping("/commParamsInfo")
public class CommParamsInfoController {
	@Autowired
	private CommParamsInfoService commParamsInfoService;
	
	@GetMapping()
	@RequiresPermissions("commParamsInfo:commParamsInfo")
	String CommParamsInfo(){
	    return "commParamsInfo/commParamsInfo";
	}
	
	@ResponseBody
	@GetMapping("/list/{uniqueType}")
	public List<CommParamsInfoDO> listByUnique(@PathVariable("uniqueType") String uniqueType ){
		//查询列表数据
		Map<String, Object> params = new HashMap<>();
		params.put("uniqueType",uniqueType);
		List commParamsInfoList = commParamsInfoService.listByUnique(params);
		return commParamsInfoList;
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("commParamsInfo:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<CommParamsInfoDO> commParamsInfoList = commParamsInfoService.list(query);
		int total = commParamsInfoService.count(query);
		PageUtils pageUtils = new PageUtils(commParamsInfoList, (long)total);
		return pageUtils;
	}


	@GetMapping("/add")
	//@RequiresPermissions("blog:bComments")
	String add(){
	    return "commParamsInfo/add";
	}

	@GetMapping("/edit/{id}")
	//@RequiresPermissions("blog:bComments")
	String edit(Model model, @PathVariable("id") Integer id){
		CommParamsInfoDO commParamsInfo = commParamsInfoService.get(id);
		model.addAttribute("commParamsInfo", commParamsInfo);
	    return "commParamsInfo/edit";
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("commParamsInfo:info")
	public R info(@PathVariable("id") Integer id){
		CommParamsInfoDO commParamsInfo = commParamsInfoService.get(id);
		return R.ok().put("commParamsInfo", commParamsInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("commParamsInfo:save")
	public R save( CommParamsInfoDO commParamsInfo){

		if(commParamsInfoService.save(commParamsInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    @ResponseBody
	@RequiresPermissions("commParamsInfo:update")
	public R update(CommParamsInfoDO commParamsInfo){
		commParamsInfoService.update(commParamsInfo);
		
		return R.ok();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("commParamsInfo:remove")
	public R remove( Integer id){
		if(commParamsInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("commParamsInfo:remove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		commParamsInfoService.batchRemove(ids);
		
		return R.ok();
	}
	
}
