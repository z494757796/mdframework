package cn.mdsoftware.mdframework.controller;

import cn.mdsoftware.mdframework.bean.Tree;
import cn.mdsoftware.mdframework.bean.entity.MenuDO;
import cn.mdsoftware.mdframework.common.Constants;
import cn.mdsoftware.mdframework.common.annotation.Log;
import cn.mdsoftware.mdframework.common.utils.MD5Utils;
import cn.mdsoftware.mdframework.common.utils.R;
import cn.mdsoftware.mdframework.common.utils.ShiroUtils;
import cn.mdsoftware.mdframework.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    MenuService menuService;

    @Value("${file-upload-path-url-pre-front}")
    private String fileUploadUrl;

	@Log("请求访问主页")
	@GetMapping({ "/", "", "/index" })
	String Index(Model model,HttpSession session) {
		// Tree<MenuDO> menuTree = menuService.getSysMenuTree(getUserId());
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		logger.info(getUser().getName());

		session.setAttribute(Constants.SessionKey.FILE_BASE_UPLOAD,fileUploadUrl);
		return "index";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@PostMapping("/login_bak")
	String doLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return "redirect:/index";
		} catch (AuthenticationException e) {
			System.out.println("登录失败信息------" + e.getMessage());
			return "redirect:/login";
		}
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {
		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@GetMapping("/403")
	String error403() {
		return "403";
	}

}
