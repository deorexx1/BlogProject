package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import blog.com.models.entity.Admin;
import blog.com.models.entity.Blog;
import blog.com.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller

public class BlogContentsController {
	@Autowired
	private HttpSession session;
	@Autowired
	private BlogService blogService;
    //内容画面を表示
	@GetMapping("/blog/contents/{blogId}")
	public String viewBlog(@PathVariable Long blogId, Model model) {
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		//もしユーザーログイン情報がなければ、ログイン画面へ
		if (admin == null) {
			return "redirect:/admin/login";
		}
		Blog blog = blogService.blogEditCheck(blogId);
		model.addAttribute("adminName", admin.getAdminName());
		model.addAttribute("blog", blog);
		return "blog_contents.html";
	}
}
