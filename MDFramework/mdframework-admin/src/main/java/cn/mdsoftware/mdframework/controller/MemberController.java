package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.common.utils.PageUtils;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.common.utils.Query;
import cn.mdsoftware.mdframework.common.utils.R;
import cn.mdsoftware.mdframework.bean.entity.MemberDO;
import cn.mdsoftware.mdframework.service.MemberService;





/**
 * 
 * 
 * @author ·½ÒæÖ¾
 * @email 573110463.com
 * @date 2018-11-30 15:57:00
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping()
	@RequiresPermissions("member:member")
	String Member(){
	    return "member/member";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("member:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        PageInfo<List<MemberDO>> result = memberService.list(query);
		PageUtils pageUtils = new PageUtils(result.getList(), result.getTotal());
		return pageUtils;
	}
	
	@GetMapping("/add")
	//@RequiresPermissions("blog:bComments")
	String add(){
	    return "member/add";
	}

	@GetMapping("/edit/{id}")
	//@RequiresPermissions("blog:bComments")
	String edit(Model model, @PathVariable("id") Integer id){
		MemberDO member = memberService.get(id);
		model.addAttribute("member", member);
	    return "member/edit";
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("member:info")
	public R info(@PathVariable("id") Integer id){
		MemberDO member = memberService.get(id);
		return R.ok().put("member", member);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("member:save")
	public R save( MemberDO member){
		if(memberService.save(member)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    @ResponseBody
	@RequiresPermissions("member:update")
	public R update(MemberDO member){
		memberService.update(member);
		
		return R.ok();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("member:remove")
	public R remove( Integer id){
		if(memberService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("member:remove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		memberService.batchRemove(ids);
		
		return R.ok();
	}
	
}
