package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.entity.OrganizationInfoTree;
import cn.mdsoftware.mdframework.common.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.bean.entity.OrganizationInfoDO;
import cn.mdsoftware.mdframework.service.OrganizationInfoService;

import javax.servlet.http.HttpSession;


/**
 * 组织架构
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-12-07 15:24:13
 */
@Controller
@RequestMapping("/organizationInfo")
public class OrganizationInfoController {
	@Autowired
	private OrganizationInfoService organizationInfoService;
	
	@GetMapping()
	@RequiresPermissions("organizationInfo:organizationInfo")
	String OrganizationInfo(){
	    return "organizationInfo/organizationInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("organizationInfo:list")
	public List list(@RequestParam Map<String, Object> params){
		//查询列表数据
		params.put("parentId",0);
        List<OrganizationInfoDO> result = organizationInfoService.tree(params);
		return result;
	}

	@GetMapping("getTree")
	@RequiresPermissions("organizationInfo:organizationInfo")
	String getTree(){
		return "organizationInfo/organizationInfoTree";
	}

	@ResponseBody
	@GetMapping("/tree")
	@RequiresPermissions("organizationInfo:list")
	public List tree(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
		params.put("sort","parent_id");
		params.put("order","asc");
		List<OrganizationInfoDO> OrganizationInfoDOs = this.organizationInfoService.tree(params);

		if (CollectionUtils.isEmpty(OrganizationInfoDOs)) return new ArrayList();
		List<OrganizationInfoTree> organizationInfoTrees = OrganizationInfoTree.buildTree(OrganizationInfoDOs,0);
		return organizationInfoTrees ;

//		int total = columnInfoService.count(query);
//		PageUtils pageUtils = new PageUtils(columnInfoList, total);
//		return columnInfoList;
	}


	
	@GetMapping("/add")
	//@RequiresPermissions("blog:bComments")
	String add(Model model,HttpSession session,Integer parentId){
		if (null != parentId && parentId.intValue() != 0) {
			OrganizationInfoDO parentInfo = this.organizationInfoService.get(parentId);
			model.addAttribute("parentId",parentId);
			model.addAttribute("parentName",parentInfo.getName());
		} else {
			model.addAttribute("parentId",0);
			model.addAttribute("parentName","一级组织");
		}
	    return "organizationInfo/add";
	}

	@GetMapping("/edit/{id}")
	//@RequiresPermissions("blog:bComments")
	String edit(Model model, @PathVariable("id") Integer id){
		OrganizationInfoDO organizationInfo = organizationInfoService.get(id);
		model.addAttribute("organizationInfo", organizationInfo);
	    return "organizationInfo/edit";
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("organizationInfo:info")
	public R info(@PathVariable("id") Integer id){
		OrganizationInfoDO organizationInfo = organizationInfoService.get(id);
		if (0 == organizationInfo.getParentId().intValue())
			organizationInfo.setParentName("一级组织");
		return R.ok().put("organizationInfo", organizationInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("organizationInfo:save")
	public R save( OrganizationInfoDO organizationInfo){
		organizationInfo.setCreateId(ShiroUtils.getUserId().intValue());
		organizationInfo.setUpdateId(ShiroUtils.getUserId().intValue());

		//设置所有fu父级id
		OrganizationInfoDO parent = this.organizationInfoService.get(organizationInfo.getParentId());
		if (null == parent) {
			organizationInfo.setAllParentIds("0");
		} else {
			organizationInfo.setAllParentIds(parent.getAllParentIds().concat(",").concat(parent.getId().toString()));
		}

		if(organizationInfoService.save(organizationInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    @ResponseBody
	@RequiresPermissions("organizationInfo:update")
	public R update(OrganizationInfoDO organizationInfo){
		organizationInfo.setUpdateId(ShiroUtils.getUserId().intValue());
		organizationInfoService.update(organizationInfo);
		return R.ok();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("organizationInfo:remove")
	public R remove( Integer id){
		if(organizationInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("organizationInfo:remove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		organizationInfoService.batchRemove(ids);
		
		return R.ok();
	}
	
}
