package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import blog.com.service.AdminService;


@Controller
public class AdminRegister {
	@Autowired
	private AdminService adminService;

//登録画面を表示
	@GetMapping("/admin/register")
	public String getAdminRegisterPage() {
		return "register.html";
	}

	// 登録処理
	@PostMapping("/admin/register/process")
	public String adminRegisterProcess(@RequestParam String adminName, @RequestParam String adminEmail,
			@RequestParam String password) {
		if (adminService.createAdmin(adminName, adminEmail, password)) {
			return "login.html";
		} else {
			return "register.html";
		}

	}
}
