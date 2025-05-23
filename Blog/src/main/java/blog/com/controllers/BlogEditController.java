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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.models.entity.Admin;
import blog.com.models.entity.Blog;
import blog.com.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogEditController {
	@Autowired
	private BlogService blogService;
	// Sessionを宣言
	@Autowired
	private HttpSession session;

	// 編集画面表示
	@GetMapping("/blog/edit/{blogId}")
	public String getBlogEditPage(@PathVariable Long blogId, Model model) {
		// sessionからログインしている情報を格納
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしユーザーログイン情報がなければ、ログイン画面へ
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			Blog blog = blogService.blogEditCheck(blogId);
			if (blog == null) {
				return "redirect:/blog/list";
			} else {
				model.addAttribute("adminName", admin.getAdminName());
				model.addAttribute("blog", blog);
				return "blog_edit.html";
			}

		}

	}

     //更新処理
	@PostMapping("/blog/edit/process")
	public String blogUpdate(@RequestParam Long blogId, @RequestParam String blogTitle,
			@RequestParam String blogContents, @RequestParam String createdAt, @RequestParam MultipartFile blogImg,
			Model model) {
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		if (admin == null) {
			return "redirect:/admin/login";
		}

		// createdAt を Timestamp に変換
		Timestamp timestamp = null;
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
				+ blogImg.getOriginalFilename();
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			Date parsedDate = dateFormat.parse(createdAt);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();

			return "blog_edit.html";
		}
		// 画像を上書き
		try {
			Files.copy(blogImg.getInputStream(), Path.of("src/main/resources/static/blog-img/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			return "blog_register.html";
		}
		// 更新する
		boolean result = blogService.blogUpdate(blogId, blogTitle, blogContents, timestamp, fileName);
		if (result) {
			return "redirect:/blog/list";
		} else {

			return "blog_edit.html";
		}
	}
}
