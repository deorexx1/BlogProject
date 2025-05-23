package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.models.entity.Admin;

import blog.com.service.AdminService;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLoginController {
	@Autowired
	private AdminService adminService;
	//Sessionを宣言
	@Autowired
	private HttpSession session;

	// ログイン画面を表示
	@GetMapping("/admin/login")
	public String getAdminLoginPage() {
		return "login.html";
	}

	// ログイン処理
	@PostMapping("/admin/login/process")
	public String adminLoginProcess(@RequestParam String adminEmail, @RequestParam String password) {

		Admin admin = adminService.loginCheck(adminEmail, password);
		//もしユーザーログイン情報がなければ、ログイン画面へ
		if (admin == null) {
			return "login.html";
		} else {
			 // sessionにログイン情報を保存し、welcomeへ
			session.setAttribute("loginAdminInfo", admin);
			session.setAttribute("adminName", admin.getAdminName());
			return "welcome.html";
		}
	}
}
