package blog.com.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Admin;
import blog.com.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogRegisterController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	// ブログ画面を表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		// sessionからログインしている情報を格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしユーザーログイン情報がなければ、ログイン画面へ
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			model.addAttribute("adminName", admin.getAdminName());
			return "blog_register.html";
		}
	}
	//登録処理
	@PostMapping("/blog/register/process")
	public String blogRegisterProcess(@RequestParam String blogTitle, @RequestParam String blogContents,
			@RequestParam String createdAt, @RequestParam MultipartFile blogImg) {

		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		// createdAt を Timestamp に変換
		Timestamp timestamp = null;
		// 現在時刻を "yyyy-MM-dd-HH-mm-ss-" 形式で取得し
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImg.getOriginalFilename();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date parsedDate = dateFormat.parse(createdAt);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return "blog_register.html";
		}

		// 画像ファイルの保存処理
		try {
			Files.copy(blogImg.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return "blog_register.html";
		}

		// ブログ登録処理
		boolean result = blogService.creatBlog(blogTitle, blogContents, timestamp, fileName, admin.getAdminId());
		if (result) {

			return "redirect:/blog/list";
		} else {
			return "blog_register.html";
		}
	}
}