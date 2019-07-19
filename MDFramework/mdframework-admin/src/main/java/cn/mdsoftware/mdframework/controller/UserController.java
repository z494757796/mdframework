package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.entity.RoleDO;
import cn.mdsoftware.mdframework.bean.entity.UserDO;
import cn.mdsoftware.mdframework.common.annotation.Log;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.utils.*;
import cn.mdsoftware.mdframework.service.RoleService;
import cn.mdsoftware.mdframework.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequiresPermissions("sys:user:user")
	@GetMapping("")
	String user(Model model) {
		return "sys/user/user";
	}

	@GetMapping("/profile")
	String profile(Model model) {
		Long userId = ShiroUtils.getUserId();
		UserDO userDO = userService.get(userId);
		model.addAttribute("user", userDO);
		return "sys/user/profile";
	}

	@GetMapping("/updatePassword")
	String updatePassword() {
		return "sys/user/updatePassword";
	}

	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, (long)total);
		return pageUtil;
	}

	@RequiresPermissions("sys:user:add")
	@Log("添加用户")
	@GetMapping("/add")
	String add(Model model) {
		List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		return "sys/user/add";
	}

	@RequiresPermissions("sys:user:edit")
	@Log("编辑用户")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Long id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<RoleDO> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return "sys/user/edit";
	}

	@RequiresPermissions("sys:user:add")
	@Log("保存用户")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		if ("test"==getUsername()) {
			return R.error(1, "演示系统不允许删除,完整体验请部署程序");
		}
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:edit")
	@Log("更新用户")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		// return R.error(1, "演示系统不允许修改");
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("用户修改自己信息")
	@PostMapping("/updateInfo")
	@ResponseBody
	R updateInfo(UserDO user) {
		if (userService.updateInfo(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@Log("用户修改自己密码")
	@PostMapping("/updatePassword")
	@ResponseBody
	Object updatePassword(@NotBlank(message = "请输入旧密码") String oldPassword,
						  @NotBlank(message ="请输入新密码") String password
	) {

		Long userId = ShiroUtils.getUserId();
		UserDO userDO = userService.get(userId);

//		if ()
		if(!MD5Utils.encrypt(userDO.getUsername(), oldPassword.trim()).equals(userDO.getPassword()))
			return R.error(HttpResponseEnum.PASSWORD_ERROR);

		UserDO UserDO = new UserDO();
		UserDO.setUserId(userDO.getUserId());
		UserDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), password.trim()));
		if (userService.updateInfo(UserDO) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:remove")
	@Log("删除用户")
	@PostMapping("/remove")
	@ResponseBody
	R remove(Long id) {
		// return R.error("演示系统不允许删除");
		if (userService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@RequiresPermissions("sys:user:batchRemove")
	@Log("批量删除用户")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Long[] userIds) {
		// return R.error("演示系统不允许删除");
		List<Long> Ids = Arrays.asList(userIds);
		int r = userService.batchremove(Ids);
		System.out.println(r);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// Query query = new Query(params);
		return !userService.exit(params);// 存在，不通过，false
	}

	@RequiresPermissions("sys:user:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") Long userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return "sys/user/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserDO user) {
		if (1L == user.getUserId()) {
			return R.error("演示系统不允许修改管理员密码");
		}
		user.setPassword(MD5Utils.encrypt(userService.get(user.getUserId()).getUsername(), user.getPassword()));
		if (userService.resetPwd(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

}
